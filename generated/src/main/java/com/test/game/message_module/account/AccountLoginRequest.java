package com.test.game.message_module.account;


import io.netty.buffer.ByteBuf;
import com.test.game.core.net.message.Message;
import com.test.game.core.utils.ByteBufUtils;

import javax.annotation.Nullable;

import static com.test.game.core.net.message.Message.NodeType.SERVER;
import static com.test.game.core.net.message.Message.NodeType.CLIENT;

/** 用户登录 */
public class AccountLoginRequest extends Message {
  public AccountLoginRequest() {}

  public AccountLoginRequest(int id) {
    this.id = id;
  }

        /** id */
        private int id;

        /** id */
        public AccountLoginRequest setId(int id) {
        this.id = id;
        return this;
        }

        /** id */
        public int getId() {
        return this.id;
        }

    @Override
    public void write(ByteBuf buf) {
                ByteBufUtils.writeInt(buf, this.id);
    }

    @Override
    public AccountLoginRequest read(ByteBuf buf) {
    int size52413035;
                this.id = ByteBufUtils.readInt(buf);
    return this;
    }

    @Override
    public String toString() {
    return "AccountLoginRequest{" +
        "id='" + id + '\'' +
    '}';
    }
  @Override
  public int id() {
    return 0;
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
    return new AccountLoginError(e);
  }
}
