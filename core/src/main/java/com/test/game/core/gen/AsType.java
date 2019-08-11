package com.test.game.core.gen;

import com.test.game.core.utils.ByteBufUtils;
import com.test.game.core.utils.StringUtils;
import io.netty.buffer.ByteBuf;

/** @Auther: zhouwenbin @Date: 2019/8/11 21:23 */
public enum AsType {
    BOOLEAN("Boolean") {
        public void write(ByteBuf buf, String value) {
            boolean v = false;
            if (!StringUtils.isNullOrEmpty(value)) {
                try {
                    v = Integer.parseInt(value) != 0;
                } catch (Exception e) {
                    v = Boolean.parseBoolean(value);
                }
            }

            ByteBufUtils.writeBoolean(buf, v);
        }
    },
    INT("int") {
        public void write(ByteBuf buf, String value) {
            ByteBufUtils.writeInt(
                    buf, StringUtils.isNullOrEmpty(value) ? 0 : Integer.parseInt(value));
        }
    },
    NUMBER("Number") {
        public void write(ByteBuf buf, String value) {
            ByteBufUtils.writeDouble(
                    buf, StringUtils.isNullOrEmpty(value) ? 0.0D : Double.parseDouble(value));
        }
    },
    STRING("String") {
        public void write(ByteBuf buf, String value) {
            ByteBufUtils.writeString(buf, value);
        }
    };

    final String name;

    AsType(String name) {
        this.name = name;
    }

    public static AsType create(String type) {
        AsType[] var1 = values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            AsType t = var1[var3];
            if (t.name.equals(type)) {
                return t;
            }
        }

        return null;
    }

    public abstract void write(ByteBuf var1, String var2);
}
