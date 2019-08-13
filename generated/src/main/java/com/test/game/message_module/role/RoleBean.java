package com.test.game.message_module.role;

import io.netty.buffer.ByteBuf;
import com.test.game.core.net.message.Bean;;
import com.test.game.core.utils.ByteBufUtils;
/** 用户Bean */
public class RoleBean extends Bean<RoleBean> {
public RoleBean() {}

    public RoleBean(int id) {
        this.id = id;
    }

        /** id */
        private int id;

        /** id */
        public RoleBean setId(int id) {
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
    public RoleBean read(ByteBuf buf) {
    int size52413035;
                this.id = ByteBufUtils.readInt(buf);
    return this;
    }

    @Override
    public String toString() {
    return "RoleBean{" +
        "id='" + id + '\'' +
    '}';
    }
}