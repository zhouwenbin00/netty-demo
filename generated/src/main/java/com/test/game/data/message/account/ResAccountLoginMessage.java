package com.test.game.data.message.account;


import io.netty.buffer.ByteBuf;
import com.test.game.core.net.message.Message;
import com.test.game.core.utils.ByteBufUtils;

import javax.annotation.Nullable;

import static com.test.game.core.net.message.Message.NodeType.CLIENT;
import static com.test.game.core.net.message.Message.NodeType.SERVER;

/** 用户登录 */
public class ResAccountLoginMessage extends Message {
  public ResAccountLoginMessage() {}

  public ResAccountLoginMessage(com.test.game.message_module.account.AccountBean accountBean) {
    this.accountBean = accountBean;
  }

        /** 用户对象 */
        private com.test.game.message_module.account.AccountBean accountBean;

        /** 用户对象 */
        public ResAccountLoginMessage setAccountBean(com.test.game.message_module.account.AccountBean accountBean) {
        this.accountBean = accountBean;
        return this;
        }

        /** 用户对象 */
        public com.test.game.message_module.account.AccountBean getAccountBean() {
        return this.accountBean;
        }

    @Override
    public void write(ByteBuf buf) {
                ByteBufUtils.writeBean(buf, this.accountBean);
    }

    @Override
    public ResAccountLoginMessage read(ByteBuf buf) {
    int size52413035;
                this.accountBean = ByteBufUtils.readBean(buf, com.test.game.message_module.account.AccountBean.class);
    return this;
    }

    @Override
    public String toString() {
    return "ResAccountLoginMessage{" +
        "accountBean='" + accountBean + '\'' +
    '}';
    }
  @Override
  public int id() {
    return 1;
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
    return new ReqAccountLoginFailmessage(e);
  }
}
