package com.test.game.core.server;

import com.test.game.core.net.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** @Auther: zhouwenbin @Date: 2019/8/3 16:15 */
public class MessageToByteHandler extends MessageToByteEncoder<Object> {

    private static final Logger log = LoggerFactory.getLogger(MessageToByteHandler.class);

    public MessageToByteHandler() {}

    @Override
    protected void encode(ChannelHandlerContext ctx, Object message, ByteBuf out) throws Exception {
        if (message.getClass() == byte[].class) {
            out.writeBytes((byte[]) message);
        } else {
            if (!(message instanceof Message)) {
                throw new IllegalArgumentException(
                        "illegal message :" + message.getClass().getName());
            }
//            if (this.log.isTraceEnabled()
//                    && message.getClass()
//                            .getName()
//                            .equals("com.popcorn.legend.message.modules.misc.ClientWrapperResponse")
//                    && !message.getClass()
//                            .getName()
//                            .equals(
//                                    "com.popcorn.legend.message.modules.misc.ClientWrapperRequest")) {
//                this.log.trace("SND->{}:{}", ctx.channel(), message.getClass().getName());
//            }
            Message.encode((Message) message, out);
        }
    }
}
