package com.test.game.core.utils;

import io.netty.buffer.ByteBuf;

/**
 * @Auther: zhouwenbin
 * @Date: 2019/8/5 13:50
 */
public abstract class ByteBufUtils {

    private ByteBufUtils() {
    }

    public static int readPositiveInt(ByteBuf buf) {
        try{
            return readInt(buf);
        }catch (IndexOutOfBoundsException e){
            return -1;
        }
    }

    public static int readInt(ByteBuf buf) {
        byte tmp = buf.readByte();
        if (tmp >= 0) {
            return tmp;
        } else {
            int result = tmp & 127;
            if ((tmp = buf.readByte()) >= 0) {
                result |= tmp << 7;
            } else {
                result |= (tmp & 127) << 7;
                if ((tmp = buf.readByte()) >= 0) {
                    result |= tmp << 14;
                } else {
                    result |= (tmp & 127) << 14;
                    if ((tmp = buf.readByte()) >= 0) {
                        result |= tmp << 21;
                    } else {
                        result |= (tmp & 127) << 21;
                        result |= (tmp = buf.readByte()) << 28;
                        if (tmp < 0) {
                            for(int i = 0; i < 5; ++i) {
                                if (buf.readByte() >= 0) {
                                    return result;
                                }
                            }

                            throw new RuntimeException("Malformed VarInt");
                        }
                    }
                }
            }

            return result;
        }
    }

    public static void writeInt(ByteBuf buf, int value) {
        while (true) {
            if ((value & ~0x7F) == 0) {
                buf.writeByte(value);
                return;
            } else {
                buf.writeByte((value & 0x7F) | 0x80);
                value >>>= 7;
            }
        }
    }

    public static int readBytes(ByteBuf buf, byte[] bytes) {
        int length = Math.min(readableBytes(buf), bytes.length);
        if (length > 0) {
            buf.readBytes(bytes);
            return length;
        } else if (length == 0) {
            return 0;
        } else {
            throw new IllegalArgumentException("内部错误,检查buf吧");
        }
    }

    private static int readableBytes(ByteBuf buf) {
        return buf.readableBytes();
    }
}
