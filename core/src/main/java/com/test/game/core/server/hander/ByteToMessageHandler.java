package com.test.game.core.server.hander;

import com.test.game.core.net.message.Message;
import com.test.game.core.net.message.MessageFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/** @Auther: zhouwenbin @Date: 2019/8/3 16:15 */
public class ByteToMessageHandler extends ByteToMessageDecoder {
    private final Logger logger = LoggerFactory.getLogger(ByteToMessageHandler.class);
    private final MessageFactory factory;

    public ByteToMessageHandler(MessageFactory factory) {
        this.factory = factory;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out)
            throws Exception {
        Message message;
        while ((message = Message.decode(in, this.factory)) != null) {
            out.add(message);
//            if (this.logger.isTraceEnabled()
//                    && !message.getClass()
//                            .getName()
//                            .equals("com.popcorn.legend.message.modules.misc.ClientWrapperResponse")
//                    && !message.getClass()
//                            .getName()
//                            .equals(
//                                    "com.popcorn.legend.message.modules.misc.ClientWrapperRequest")) {
//                this.logger.trace("RCV<-{}:{}", NetUtils.host(ctx), message.getClass().getName());
//            }
        }
    }
}
