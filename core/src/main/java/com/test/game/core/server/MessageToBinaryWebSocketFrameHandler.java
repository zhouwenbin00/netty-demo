package com.test.game.core.server;

import com.test.game.core.net.message.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

import java.util.List;

public class MessageToBinaryWebSocketFrameHandler extends MessageToMessageEncoder<Message> {
    public MessageToBinaryWebSocketFrameHandler() {
    }

    protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> out) throws Exception {
        BinaryWebSocketFrame frame = new BinaryWebSocketFrame();
        Message.encode(msg, frame.content());
        out.add(frame);
    }
}