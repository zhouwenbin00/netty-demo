package com.test.game.data.message.account;


import io.netty.buffer.ByteBuf;
import com.test.game.core.net.message.Message;
import com.test.game.core.utils.ByteBufUtils;

import javax.annotation.Nullable;

import static com.test.game.core.net.message.Message.NodeType.CLIENT;
import static com.test.game.core.net.message.Message.NodeType.SERVER;

/** 用户登录 */
public class ReqAccountLoginFailmessage extends Message {
    private int error;

    public ReqAccountLoginFailmessage(int error) {
        this.error = error;
    }

    /** 登录失败 */
    public static byte[] ERROR_LOGIN = Message.encodeToBytes(new ReqAccountLoginFailmessage(1));

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
        return 2;
    }

    @Override
    public NodeType from() {
        return SERVER;
    }

    @Override
    public NodeType to() {
        return CLIENT;
    }

    @Nullable
    public Message error(int e) {
        return null;
    }
}
