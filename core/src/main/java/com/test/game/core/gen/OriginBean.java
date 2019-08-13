package com.test.game.core.gen;

import com.google.common.base.Preconditions;
import com.test.game.core.utils.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/** @Auther: zhouwenbin @Date: 2019/8/10 16:54 */
public class OriginBean extends NameAndDesc {
    public final String pkg;
    public final List<OriginField> fields;
    private final String client_package;
    private final String client_name;
    private final String cfgPkg;

    public OriginBean(java.lang.Class<?> clazz, String cfgPkg) {
        super(
                clazz.getSimpleName(),
                clazz.getAnnotation(BeanClass.class) == null
                        ? ""
                        : (clazz.getAnnotation(BeanClass.class)).desc());

        this.pkg = clazz.getPackage().getName();
        this.cfgPkg = cfgPkg;
        this.fields = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            this.fields.add(new OriginField(field));
        }

        BeanClass annotation = clazz.getAnnotation(BeanClass.class);
        Preconditions.checkNotNull(annotation, "%s的注解为空", clazz.getName());
        if (StringUtils.isNullOrEmpty(annotation.client_pkg())) {
            this.client_package = this.pkg.substring(this.pkg.lastIndexOf(".") + 1);
        } else {
            this.client_package = annotation.client_pkg();
        }

        if (StringUtils.isNullOrEmpty(annotation.client_name())) {
            this.client_name = this.name;
        } else {
            this.client_name = annotation.client_name();
        }
    }

    public String getPkg() {
        return pkg;
    }

    public List<OriginField> getFields() {
        return fields;
    }

    public String getClient_package() {
        return client_package;
    }

    public String getClient_name() {
        return client_name;
    }

    public String getCfgPkg() {
        return cfgPkg;
    }

    public void buildMD5(StringBuilder sb) {

        sb.append(this.name).append(this.pkg);
        Iterator var2 = this.fields.iterator();

        while (var2.hasNext()) {
            OriginField field = (OriginField) var2.next();
            field.buildMD5(sb);
        }

        sb.append(this.client_package);
        sb.append(this.client_name);
    }
}
