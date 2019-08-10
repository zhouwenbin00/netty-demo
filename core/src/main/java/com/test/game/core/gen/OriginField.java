package com.test.game.core.gen;

import com.test.game.core.utils.ReflectUtils;

import java.lang.reflect.Field;

/** @Auther: zhouwenbin @Date: 2019/8/10 17:19 */
public class OriginField extends NameAndDesc {

    public final Field field;

    public OriginField(Field field) {
        super(field.getName(), genDesc(field));
        this.field = field;
    }

    private static String genDesc(Field field) {
        StringBuilder sb = new StringBuilder();
        String desc = ReflectUtils.annotation(field, MessageField.class).desc();
        sb.append(desc);
        if (field.getType().isEnum()) {
            int i = 1;
            for (Field f : field.getType().getDeclaredFields()) {
                if (f.isEnumConstant()) {
                    sb.append("[")
                            .append(i++)
                            .append(":")
                            .append(ReflectUtils.annotation(f, MessageField.class).desc())
                            .append("]");
                }
            }
        }
        return sb.toString();
    }
}
