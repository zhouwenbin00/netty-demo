package com.test.game.data.message.account;

import io.netty.buffer.ByteBuf;
import com.test.game.core.net.message.Bean;;
import com.test.game.core.utils.ByteBufUtils;
/** 用户bean */
public class AccountBean extends Bean<AccountBean> {
public AccountBean() {}

    public AccountBean(int id, int level) {
        this.id = id;
        this.level = level;
    }

        /** id */
        private int id;
        /** 等级 */
        private int level;

        /** id */
        public AccountBean setId(int id) {
        this.id = id;
        return this;
        }

        /** id */
        public int getId() {
        return this.id;
        }

        /** 等级 */
        public AccountBean setLevel(int level) {
        this.level = level;
        return this;
        }

        /** 等级 */
        public int getLevel() {
        return this.level;
        }

    @Override
    public void write(ByteBuf buf) {
                ByteBufUtils.writeInt(buf, this.id);
                ByteBufUtils.writeInt(buf, this.level);
    }

    @Override
    public AccountBean read(ByteBuf buf) {
    int size52413035;
                this.id = ByteBufUtils.readInt(buf);
                this.level = ByteBufUtils.readInt(buf);
    return this;
    }

    @Override
    public String toString() {
    return "AccountBean{" +
        "id='" + id + '\'' +
        "level='" + level + '\'' +
    '}';
    }
}