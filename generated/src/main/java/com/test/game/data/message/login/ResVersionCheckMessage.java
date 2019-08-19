package com.test.game.data.message.login;


import io.netty.buffer.ByteBuf;
import com.test.game.core.net.message.Message;
import com.test.game.core.utils.ByteBufUtils;

import javax.annotation.Nullable;

import static com.test.game.core.net.message.Message.NodeType.SERVER;
import static com.test.game.core.net.message.Message.NodeType.CLIENT;

/** 版本验证 */
public class ResVersionCheckMessage extends Message {
  public ResVersionCheckMessage() {}

  public ResVersionCheckMessage(byte[] config, int configDataVersion) {
    this.config = config;
    this.configDataVersion = configDataVersion;
  }

        /** 如果存在,则用该配置 */
        private byte[] config;
        /** 如果config为空,则根据该配置数据版本号去获取配置 */
        private int configDataVersion;

        /** 如果存在,则用该配置 */
        public ResVersionCheckMessage setConfig(byte[] config) {
        this.config = config;
        return this;
        }

        /** 如果存在,则用该配置 */
        public byte[] getConfig() {
        return this.config;
        }

        /** 如果config为空,则根据该配置数据版本号去获取配置 */
        public ResVersionCheckMessage setConfigDataVersion(int configDataVersion) {
        this.configDataVersion = configDataVersion;
        return this;
        }

        /** 如果config为空,则根据该配置数据版本号去获取配置 */
        public int getConfigDataVersion() {
        return this.configDataVersion;
        }

    @Override
    public void write(ByteBuf buf) {
                ByteBufUtils.writeBytesWithLengthFlag(buf, this.config);
                ByteBufUtils.writeInt(buf, this.configDataVersion);
    }

    @Override
    public ResVersionCheckMessage read(ByteBuf buf) {
    int size52413035;
                this.config = ByteBufUtils.readBytesWithLengthFlag(buf);
                this.configDataVersion = ByteBufUtils.readInt(buf);
    return this;
    }

    @Override
    public String toString() {
    return "ResVersionCheckMessage{" +
        "config='" + config + '\'' +
        "configDataVersion='" + configDataVersion + '\'' +
    '}';
    }
  @Override
  public int id() {
    return 4;
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
    return new ReqVersionCheckFailmessage(e);
  }
}
