package com.test.game.core.gen;

import com.test.game.core.utils.ByteBufUtils;
import com.test.game.core.utils.GenerateUtils;
import io.netty.buffer.ByteBuf;

/** 带值的属性 @Auther: zhouwenbin @Date: 2019/8/11 15:49 */
public class FieldWithValue {

    public final Field field;
    public final String value;

    public FieldWithValue(Field field, String value) {
        this.field = field;
        this.value = value;
    }

    public void write(ByteBuf buf, boolean server) {
        try {
            this.write(buf, this.value, server);
        } catch (Throwable e) {
            GenerateUtils.log.error("write:{}", this.toString(), e);
            System.exit(-1);
        }
    }

    public void write(ByteBuf buf, String value, boolean server) {
        if (server) {
            JavaType type = JavaType.create(this.field.javaType);
            if (type == null) {
                ByteBufUtils.writeString(buf, value);
            } else {
                type.write(buf, value);
            }
        } else {
            AsType type = AsType.create(this.field.asType);
            if (type == null) {
                ByteBufUtils.writeString(buf, value);
            } else {
                type.write(buf, value);
            }
        }

    }
}
