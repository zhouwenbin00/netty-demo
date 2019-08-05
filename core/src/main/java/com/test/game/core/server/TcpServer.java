package com.test.game.core.server;

import com.test.game.core.base.Factory;
import com.test.game.core.concurrent.ThreadFactory;
import com.test.game.core.server.hander.ByteToMessageHandler;
import com.test.game.core.server.hander.ExceptionHandler;
import com.test.game.core.server.hander.MessageHandler;
import com.test.game.core.server.hander.MessageToByteHandler;
import com.test.game.core.utils.Num;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.util.concurrent.EventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

/** @Auther: zhouwenbin @Date: 2019/8/3 15:12 */
public class TcpServer {

    private static final Logger log = LoggerFactory.getLogger(TcpServer.class);
    private final String name;
    private final int port;
    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workerGroup;
    private final ServerBootstrap bootstrap;
    private final int LOW_WATER_MARK = 32 * Num.KB;
    private final int HIGH_WATER_MARK = 64 * Num.KB;

    public TcpServer(String name, int port, ChannelInitializer<SocketChannel> initializer) {
        this.bootstrap = new ServerBootstrap();
        this.name = name;
        this.port = port;
        this.bossGroup =
                new NioEventLoopGroup(
                        Num.ONE, new ThreadFactory("Boss-Thread", Thread.MAX_PRIORITY));
        this.workerGroup =
                new NioEventLoopGroup(
                        Num.ZERO, new ThreadFactory("Worker-Thread", Thread.MAX_PRIORITY));
        bootstrap
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(initializer)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_RCVBUF, 32 * Num.KB)
                .option(ChannelOption.SO_SNDBUF, 64 * Num.KB)
                .option(ChannelOption.SO_REUSEADDR, true)
                .option(
                        ChannelOption.WRITE_BUFFER_WATER_MARK,
                        new WriteBufferWaterMark(LOW_WATER_MARK, HIGH_WATER_MARK))
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_RCVBUF, 32 * Num.KB)
                .childOption(ChannelOption.SO_SNDBUF, 64 * Num.KB)
                .childOption(ChannelOption.SO_REUSEADDR, true)
                .childOption(
                        ChannelOption.WRITE_BUFFER_WATER_MARK,
                        new WriteBufferWaterMark(LOW_WATER_MARK, HIGH_WATER_MARK));
    }

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
        this(name, port, workerNum, timeOutSeconds, messageHandler, decoderFactory, encoderFactory);
    }

    public TcpServer(
            String name,
            int port,
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
                workerNum);
    }

    public TcpServer(
            String name, int port, ChannelInitializer<SocketChannel> initializer, int workerNum) {
        log.info("init TcpServer start...");
        this.name = name;
        this.port = port;
        this.bossGroup =
                new NioEventLoopGroup(
                        Num.ONE, new ThreadFactory("Boss-Thread", Thread.MAX_PRIORITY));
        this.workerGroup =
                new NioEventLoopGroup(
                        workerNum > Num.ZERO
                                ? workerNum
                                : Runtime.getRuntime().availableProcessors() * 2,
                        new ThreadFactory("Worker-Thread", Thread.MAX_PRIORITY));
        this.bootstrap = new ServerBootstrap();
        this.bootstrap
                .group(this.bossGroup, this.workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(initializer)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT) // 重用缓冲区
                .option(ChannelOption.TCP_NODELAY, Boolean.TRUE) // 关闭Nagle算法
                .option(ChannelOption.SO_RCVBUF, LOW_WATER_MARK) // 定义接收或者传输的系统缓冲区buf的大小
                .option(ChannelOption.SO_SNDBUF, HIGH_WATER_MARK) // 定义接收或者传输的系统缓冲区buf的大小
                .option(ChannelOption.SO_REUSEADDR, Boolean.TRUE) // 允许启动一个监听服务器并捆绑其众所周知端口
                .option(
                        ChannelOption.WRITE_BUFFER_WATER_MARK,
                        new WriteBufferWaterMark(LOW_WATER_MARK, HIGH_WATER_MARK)) // 读写水位控制
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .childOption(ChannelOption.TCP_NODELAY, Boolean.TRUE)
                .childOption(ChannelOption.SO_RCVBUF, LOW_WATER_MARK)
                .childOption(ChannelOption.SO_SNDBUF, HIGH_WATER_MARK)
                .childOption(ChannelOption.SO_REUSEADDR, Boolean.TRUE)
                .childOption(
                        ChannelOption.WRITE_BUFFER_WATER_MARK,
                        new WriteBufferWaterMark(LOW_WATER_MARK, HIGH_WATER_MARK));
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

    public String getName() {
        return name;
    }

    public int getPort() {
        return port;
    }

    public EventLoopGroup getBossGroup() {
        return bossGroup;
    }

    public EventLoopGroup getWorkerGroup() {
        return workerGroup;
    }

    public ServerBootstrap getBootstrap() {
        return bootstrap;
    }
}
