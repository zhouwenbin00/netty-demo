package com.test.game.core.server.hander;

import com.test.game.core.base.Factory;
import com.test.game.core.net.message.Message;
import com.test.game.core.net.message.MessageFactory;
import com.test.game.core.utils.Num;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.util.List;

/** @Auther: zhouwenbin @Date: 2019/8/5 19:58 */
public class WebSocketServerInitializer extends ChannelInitializer<SocketChannel> {

    private final Factory<BinaryWebSocketFrameToMessageHandler> decoderFactory;
    private final MessageHandler messageHandler;
    private final int timeOutSeconds;

    public WebSocketServerInitializer(
            Factory<BinaryWebSocketFrameToMessageHandler> decoderFactory,
            MessageHandler messageHandler,
            int timeOutSeconds) {
        this.decoderFactory = decoderFactory;
        this.messageHandler = messageHandler;
        this.timeOutSeconds = timeOutSeconds;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(64 * Num.KB));
        pipeline.addLast(new WebSocketServerCompressionHandler());
        pipeline.addLast(new WebSocketServerProtocolHandler("/", null, true));
        pipeline.addLast(decoderFactory.create());
        pipeline.addLast(new ByteToBinaryWebSocketFrameHandler()); // 会直接转发已经封装好的消息
        pipeline.addLast(new MessageToBinaryWebSocketFrameHandler());
        if (timeOutSeconds > 0) pipeline.addLast(new ReadTimeoutHandler(timeOutSeconds));
        pipeline.addLast(messageHandler);
        pipeline.addLast(new ExceptionHandler());
    }

    public static class BinaryWebSocketFrameToMessageHandler
            extends SimpleChannelInboundHandler<WebSocketFrame> {

        private final MessageFactory messageFactory;

        public BinaryWebSocketFrameToMessageHandler(MessageFactory messageFactory) {
            this.messageFactory = messageFactory;
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame)
                throws Exception {
            if (frame instanceof BinaryWebSocketFrame) {
                Message message = Message.decode(frame.content(), messageFactory);
                ctx.fireChannelRead(message);
            }
        }
    }

    public static class ByteToBinaryWebSocketFrameHandler extends MessageToMessageEncoder<byte[]> {
        @Override
        protected void encode(ChannelHandlerContext ctx, byte[] msg, List<Object> out)
                throws Exception {
            BinaryWebSocketFrame frame = new BinaryWebSocketFrame();
            frame.content().writeBytes(msg);
            out.add(frame);
        }
    }

    public static class MessageToBinaryWebSocketFrameHandler
            extends MessageToMessageEncoder<Message> {
        @Override
        protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> out)
                throws Exception {
            BinaryWebSocketFrame frame = new BinaryWebSocketFrame();
            Message.encode(msg, frame.content());
            out.add(frame);
        }
    }
}
