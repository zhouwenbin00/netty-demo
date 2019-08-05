package com.test.game.core.net.message;

import com.test.game.core.utils.ByteBufUtils;
import com.test.game.core.utils.Num;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** @Auther: zhouwenbin @Date: 2019/8/5 11:08 */
public abstract class Message extends Bean<Message> {

    private static final Logger log = LoggerFactory.getLogger(Message.class);

    // 最大消息长度
    private static int MAX_LENGTH = 4 * Num.MB;
    // 初始化buf长度
    private static int INIT_BUFFER_SIZE = 16 * Num.KB;

    // 编码
    public static int encode(Message message, ByteBuf out) {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(INIT_BUFFER_SIZE);

        try {
            ByteBufUtils.writeInt(buf, message.id());
            message.write(buf);

            if (buf.readableBytes() > MAX_LENGTH) {
                throw new IllegalArgumentException(
                        message.getClass().getName() + ":" + buf.readableBytes());
            }

            int readableBytes = buf.readableBytes();
            ByteBufUtils.writeInt(out, readableBytes);
            out.writeBytes(buf);
            if (buf.readableBytes() > 0) {
                throw new IllegalArgumentException("readable bytes " + buf.readableBytes());
            }
            return readableBytes;
        } catch (Exception e) {
            log.error("{}:{}", message.getClass(), message, e);
            return 0;
        } finally {
            buf.release();
        }
    }

    public static byte[] encodeToBytes(Message message) {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(INIT_BUFFER_SIZE);
        try {
            encode(message, buf);
            byte[] bytes = new byte[buf.readableBytes()];
            ByteBufUtils.readBytes(buf, bytes);
            return bytes;
        } finally {
            buf.release();
        }
    }

    // 解码
    public static Message decode(ByteBuf in, MessageFactory factory) {
        in.markReaderIndex();
        if (in.readableBytes() < 4) {
            return null;
        } else {
            int length = in.readInt();
            if (length < 1) {
                in.resetReaderIndex();
                return null;
            } else if (length > MAX_LENGTH) {
                throw new IllegalArgumentException("message too long : " + length);
            } else if (length > in.readableBytes()) {
                in.resetReaderIndex();
                return null;
            } else {
                int id = ByteBufUtils.readInt(in);
                Message message = factory.create(id);
                if (message == null) {
                    throw new IllegalArgumentException("illegal id : " + id);
                } else {
                    message.read(in);
                    return message;
                }
            }
        }
    }

    public static Message decode(byte[] bytes, MessageFactory factory) {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(bytes.length);

        Message message;
        try {
            buf.writeBytes(bytes);
            message = decode(buf, factory);
        } finally {
            buf.release();
        }

        return message;
    }

    public abstract int id();

    public abstract Message.NodeType from();

    public abstract Message.NodeType to();

    public static enum NodeType {
        CLIENT,
        SERVER;

        private NodeType() {}
    }
}
