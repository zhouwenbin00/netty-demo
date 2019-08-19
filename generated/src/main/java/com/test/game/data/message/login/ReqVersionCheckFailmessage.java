package com.test.game.data.message.login;


import io.netty.buffer.ByteBuf;
import com.test.game.core.net.message.Message;
import com.test.game.core.utils.ByteBufUtils;

import javax.annotation.Nullable;

import static com.test.game.core.net.message.Message.NodeType.SERVER;
import static com.test.game.core.net.message.Message.NodeType.CLIENT;

/** 版本验证 */
public class ReqVersionCheckFailmessage extends Message {
    private int error;

    public ReqVersionCheckFailmessage(int error) {
        this.error = error;
    }

    /** 消息代码不一致 */
    public static byte[] MESSAGE_CODE = Message.encodeToBytes(new ReqVersionCheckFailmessage(1));
    /** 配置代码不一致 */
    public static byte[] CONFIG_CODE = Message.encodeToBytes(new ReqVersionCheckFailmessage(2));
    /** 配置数据不一致 */
    public static byte[] CONFIG_DATA = Message.encodeToBytes(new ReqVersionCheckFailmessage(3));
    /** 验证失败 */
    public static byte[] VERIFY = Message.encodeToBytes(new ReqVersionCheckFailmessage(4));

    public int getError() {
        return this.error;
    }

    @Override
    public void write(ByteBuf buf) {
        ByteBufUtils.writeInt(buf, error);
    }

    @Override
    public Message read(ByteBuf buf) {
        error = ByteBufUtils.readInt(buf);
        return this;
    }

    @Override
    public int id() {
        return 5;
    }

    @Override
    public NodeType from() {
        return CLIENT;
    }

    @Override
    public NodeType to() {
        return SERVER;
    }

    @Nullable
    public Message error(int e) {
        return null;
    }
}
