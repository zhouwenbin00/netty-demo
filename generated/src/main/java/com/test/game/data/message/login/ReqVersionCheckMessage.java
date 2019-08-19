package com.test.game.data.message.login;


import io.netty.buffer.ByteBuf;
import com.test.game.core.net.message.Message;
import com.test.game.core.utils.ByteBufUtils;

import javax.annotation.Nullable;

import static com.test.game.core.net.message.Message.NodeType.CLIENT;
import static com.test.game.core.net.message.Message.NodeType.SERVER;

/** 版本验证 */
public class ReqVersionCheckMessage extends Message {
  public ReqVersionCheckMessage() {}

  public ReqVersionCheckMessage(String messageCodeVersion, String configCodeVersion, String token) {
    this.messageCodeVersion = messageCodeVersion;
    this.configCodeVersion = configCodeVersion;
    this.token = token;
  }

        /** 消息代码版本号 */
        private String messageCodeVersion;
        /** 配置代码版本号 */
        private String configCodeVersion;
        /** 验证token,本地登陆则为空 */
        private String token;

        /** 消息代码版本号 */
        public ReqVersionCheckMessage setMessageCodeVersion(String messageCodeVersion) {
        this.messageCodeVersion = messageCodeVersion;
        return this;
        }

        /** 消息代码版本号 */
        public String getMessageCodeVersion() {
        return this.messageCodeVersion;
        }

        /** 配置代码版本号 */
        public ReqVersionCheckMessage setConfigCodeVersion(String configCodeVersion) {
        this.configCodeVersion = configCodeVersion;
        return this;
        }

        /** 配置代码版本号 */
        public String getConfigCodeVersion() {
        return this.configCodeVersion;
        }

        /** 验证token,本地登陆则为空 */
        public ReqVersionCheckMessage setToken(String token) {
        this.token = token;
        return this;
        }

        /** 验证token,本地登陆则为空 */
        public String getToken() {
        return this.token;
        }

    @Override
    public void write(ByteBuf buf) {
                ByteBufUtils.writeString(buf, this.messageCodeVersion);
                ByteBufUtils.writeString(buf, this.configCodeVersion);
                ByteBufUtils.writeString(buf, this.token);
    }

    @Override
    public ReqVersionCheckMessage read(ByteBuf buf) {
    int size52413035;
                this.messageCodeVersion = ByteBufUtils.readString(buf);
                this.configCodeVersion = ByteBufUtils.readString(buf);
                this.token = ByteBufUtils.readString(buf);
    return this;
    }

    @Override
    public String toString() {
    return "ReqVersionCheckMessage{" +
        "messageCodeVersion='" + messageCodeVersion + '\'' +
        "configCodeVersion='" + configCodeVersion + '\'' +
        "token='" + token + '\'' +
    '}';
    }
  @Override
  public int id() {
    return 3;
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
    return new ReqVersionCheckFailmessage(e);
  }
}
