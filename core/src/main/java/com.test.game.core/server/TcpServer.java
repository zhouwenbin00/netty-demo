package com.test.game.core.server;

import com.test.game.core.net.MessageHandler;
import com.test.game.core.utils.Num;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.util.concurrent.EventExecutor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.concurrent.ThreadFactory;

/** @Auther: zhouwenbin @Date: 2019/8/3 15:12 */
@Slf4j
@Data
public class TcpServer {
    private final String name;
    private final int port;
    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workerGroup;
    private final ServerBootstrap bootstrap;
    private final int LOW_WATER_MARK = 32 * Num.KB;
    private final int HIGH_WATER_MARK = 64 * Num.KB;

    public TcpServer(
            String name,
            int port,
            MessageHandler messageHandler,
            int workerNum,
            Factory<ByteToMessageHandler> decoderFactory,
            Factory<MessageToByteHandler> encoderFactory) {
        this(name, port, messageHandler, Num.ZERO, workerNum, decoderFactory, encoderFactory);
    }

    public TcpServer(
            String name,
            int port,
            MessageHandler messageHandler,
            int timeOutSeconds,
            int workerNum,
            Factory<ByteToMessageHandler> decoderFactory,
            Factory<MessageToByteHandler> encoderFactory) {
        this(
                name,
                port,
                (ThreadFactory) null,
                (ThreadFactory) null,
                workerNum,
                timeOutSeconds,
                messageHandler,
                decoderFactory,
                encoderFactory);
    }

    public TcpServer(
            String name,
            int port,
            ThreadFactory bossThreadFactory,
            ThreadFactory workerThreadFactory,
            int workerNum,
            final int timeoutSeconds,
            final MessageHandler messageHandler,
            final Factory<ByteToMessageHandler> decoderFactory,
            final Factory<MessageToByteHandler> encoderFactory) {

        this(
                name,
                port,
                new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast((ChannelHandler) decoderFactory.create());
                        ch.pipeline().addLast(new ByteArrayEncoder());
                        ch.pipeline().addLast((ChannelHandler) encoderFactory.create());
                        if (timeoutSeconds > Num.ZERO) {
                            ch.pipeline().addLast(new ReadTimeoutHandler(timeoutSeconds));
                        }
                        ch.pipeline().addLast(messageHandler);
                        ch.pipeline().addLast(new ExceptionHandler());
                    }
                },
                bossThreadFactory,
                workerThreadFactory,
                workerNum);
    }

    public TcpServer(
            String name,
            int port,
            ChannelInitializer<SocketChannel> initializer,
            ThreadFactory bossThreadFactory,
            ThreadFactory workerThreadFactory,
            int workerNum) {
        log.info("init TcpServer start...");
        this.name = name;
        this.port = port;
        this.bossGroup = new NioEventLoopGroup(Num.ONE, bossThreadFactory);
        this.workerGroup =
                new NioEventLoopGroup(
                        workerNum > Num.ZERO
                                ? workerNum
                                : Runtime.getRuntime().availableProcessors() * 2,
                        workerThreadFactory);
        this.bootstrap = new ServerBootstrap();
        this.bootstrap
                .group(this.bossGroup, this.workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(initializer);
        log.info("init TcpServer end...");
    }

    /**
     * all worker threads execute a task passed in from the method parameter
     *
     * @param runnable
     */
    public void allWorkerExecute(Runnable runnable) {
        Iterator iterator = this.getWorkerGroup().iterator();
        while (iterator.hasNext()) {
            EventExecutor executor = (EventExecutor) iterator.next();
            executor.execute(runnable);
        }
    }

    /** startup server */
    public void startup() {
        try {
            this.bootstrap.bind(this.port).sync();
        } catch (InterruptedException e) {
            log.error("server start fail", e);
        }
        log.info("server start at port : {}", this.port);
    }

    /** shutdown server */
    public void shutdown() {
        try {
            this.bossGroup.shutdownGracefully().sync();
            this.getWorkerGroup().shutdownGracefully().sync();
        } catch (InterruptedException e) {
            log.error("server close fail", e);
        }
        log.info("server close success");
    }
}
