package com.test.game.core.gen;

import com.test.game.core.utils.ReflectUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/** @Auther: zhouwenbin @Date: 2019/8/10 16:54 */
public class OriginEnum extends NameAndDesc {
    public final String pkg;
    public final List<NameAndDesc> fields;
    public final int start;

    public OriginEnum(java.lang.Class<?> clazz,String cfgPkg) {
        super(
                clazz.getSimpleName(),
                clazz.getAnnotation(BeanClass.class) == null
                        ? ""
                        : (clazz.getAnnotation(BeanClass.class)).desc());
        BeanClass annotation = clazz.getAnnotation(BeanClass.class);
        this.start = annotation == null ? 1 : annotation.start();
        this.fields = new ArrayList<>();
        this.pkg = cfgPkg + clazz.getPackage().getName().substring(clazz.getPackage().getName().lastIndexOf("."));
        for (Field field : clazz.getDeclaredFields()) {
            if (!ReflectUtils.isPrivate(field)) {
                this.fields.add(
                        new NameAndDesc(
                                field.getName(),
                                ReflectUtils.annotation(field, MessageField.class).desc()));
            }
        }
    }

    public void buildMD5(StringBuilder sb) {
        sb.append(this.name).append(this.pkg);

        for (NameAndDesc field : this.fields) {
            sb.append(field.name);
        }

        sb.append(this.start);
    }
}
