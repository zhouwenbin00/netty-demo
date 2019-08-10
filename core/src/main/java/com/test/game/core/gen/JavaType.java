package com.test.game.core.gen;

import com.test.game.core.utils.ByteBufUtils;
import com.test.game.core.utils.StringUtils;
import io.netty.buffer.ByteBuf;

/** @Auther: zhouwenbin @Date: 2019/8/11 21:22 */
public enum JavaType {
    BOOLEAN("boolean") {
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
    SHORT("short") {
        public void write(ByteBuf buf, String value) {
            ByteBufUtils.writeShort(
                    buf, StringUtils.isNullOrEmpty(value) ? 0 : Short.parseShort(value));
        }
    },
    INT("int") {
        public void write(ByteBuf buf, String value) {
            ByteBufUtils.writeInt(
                    buf, StringUtils.isNullOrEmpty(value) ? 0 : Integer.parseInt(value));
        }
    },
    LONG("long") {
        public void write(ByteBuf buf, String value) {
            ByteBufUtils.writeLong(
                    buf, StringUtils.isNullOrEmpty(value) ? 0L : Long.parseLong(value));
        }
    },
    STRING("String") {
        public void write(ByteBuf buf, String value) {
            ByteBufUtils.writeString(buf, value);
        }
    },
    FLOAT("float") {
        public void write(ByteBuf buf, String value) {
            ByteBufUtils.writeFloat(
                    buf, StringUtils.isNullOrEmpty(value) ? 0.0F : Float.parseFloat(value));
        }
    },
    DOUBLE("double") {
        public void write(ByteBuf buf, String value) {
            ByteBufUtils.writeDouble(
                    buf, StringUtils.isNullOrEmpty(value) ? 0.0D : Double.parseDouble(value));
        }
    };

    final String name;

    JavaType(String name) {
        this.name = name;
    }

    public static JavaType create(String name) {
        for (JavaType value : values()) {
            if (value.name.equals(name)) {
                return value;
            }
        }
        return null;
    }

    public abstract void write(ByteBuf buf, String value);
}
