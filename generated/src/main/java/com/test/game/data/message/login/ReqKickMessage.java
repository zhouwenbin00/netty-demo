package com.test.game.data.message.login;


import io.netty.buffer.ByteBuf;
import com.test.game.core.net.message.Message;
import com.test.game.core.utils.ByteBufUtils;

import javax.annotation.Nullable;

import static com.test.game.core.net.message.Message.NodeType.SERVER;
import static com.test.game.core.net.message.Message.NodeType.CLIENT;

/** 踢人 */
public class ReqKickMessage extends Message {
  public ReqKickMessage() {}

  public ReqKickMessage(com.test.game.data.message.login.KickType type) {
    this.type = type;
  }

        /** 为何踢人[1:版本不一致][2:验证未通过][3:多次发送登录消息][4:顶号][5:流程错误][6:非法消息] */
        private com.test.game.data.message.login.KickType type;

        /** 为何踢人[1:版本不一致][2:验证未通过][3:多次发送登录消息][4:顶号][5:流程错误][6:非法消息] */
        public ReqKickMessage setType(com.test.game.data.message.login.KickType type) {
        this.type = type;
        return this;
        }

        /** 为何踢人[1:版本不一致][2:验证未通过][3:多次发送登录消息][4:顶号][5:流程错误][6:非法消息] */
        public com.test.game.data.message.login.KickType getType() {
        return this.type;
        }

    @Override
    public void write(ByteBuf buf) {
                ByteBufUtils.writeInt(buf, this.type != null ? this.type.getValue() : 0);
    }

    @Override
    public ReqKickMessage read(ByteBuf buf) {
    int size52413035;
                this.type = com.test.game.data.message.login.KickType.valueOf(ByteBufUtils.readInt(buf));
    return this;
    }

    @Override
    public String toString() {
    return "ReqKickMessage{" +
        "type='" + type + '\'' +
    '}';
    }
  @Override
  public int id() {
    return 6;
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
