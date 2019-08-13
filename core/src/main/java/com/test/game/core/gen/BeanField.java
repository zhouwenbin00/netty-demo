package com.test.game.core.gen;

import com.test.game.core.utils.ReflectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

public class BeanField extends AbstractField {
    private final String javaType;
    private final String as3Type;
    private final String type;
    private final boolean list;
    private final boolean isEnum;
    private final boolean isBean;
    private final String javaInit;
    private final String as3Init;
    private final String client_bean_type;
    private final String clientPkg;

    public BeanField(NameAndDesc nameAndDesc, Field field) {
        super(nameAndDesc);
        Type type = field.getType();
        if (type == List.class) {
            type =
                    ReflectUtils.getActualTypeArgument(
                            (ParameterizedType) field.getGenericType(), 0);
            this.list = true;
        } else {
            this.list = false;
        }

        TypeConvert typeConvert = TypeConvert.valueOf(type);
        if (typeConvert != null) {
            this.javaType = typeConvert.javaType(this.list);
            this.as3Type = typeConvert.as3Type(this.list);
            this.isEnum = false;
            this.isBean = false;
        } else {
            this.javaType =
                    this.list ? "java.util.List<" + type.getTypeName() + ">" : type.getTypeName();
            this.isEnum = ReflectUtils.isEnum(type);
            if (this.isEnum) {
                this.as3Type = this.list ? "Vector.<int>" : "int";
            } else {
                this.as3Type =
                        this.list
                                ? "Vector.<"
                                        + type.getTypeName()
                                                .substring(type.getTypeName().lastIndexOf(".") + 1)
                                        + ">"
                                : type.getTypeName()
                                        .substring(type.getTypeName().lastIndexOf(".") + 1);
            }

            this.isBean = !this.isEnum;
        }

        this.client_bean_type =
                type.getTypeName().substring(type.getTypeName().lastIndexOf(".") + 1);
        if (this.isBean) {
            String tmp = type.getTypeName().substring(0, type.getTypeName().lastIndexOf("."));
            this.clientPkg = tmp.substring(tmp.lastIndexOf(".") + 1);
        } else {
            this.clientPkg = "heh";
        }

        this.javaInit = this.list ? " = new java.util.ArrayList<>()" : "";
        this.as3Init = this.list ? " = new " + this.as3Type + "()" : "";
        this.type = typeConvert != null ? typeConvert.javaType(false) : type.getTypeName();
    }

    public String getJavaInit() {
        return this.javaInit;
    }

    public String getAs3Init() {
        return this.as3Init;
    }

    public boolean isEnum() {
        return this.isEnum;
    }

    public boolean isBean() {
        return this.isBean;
    }

    public boolean isList() {
        return this.list;
    }

    public String getType() {
        return this.type;
    }

    public String getJavaType() {
        return this.javaType;
    }

    public String getAs3Type() {
        return this.as3Type;
    }

    public String getClient_bean_type() {
        return this.client_bean_type;
    }

    public void buildImport(Set<String> ji, Set<String> ai) {
        if (this.isBean) {
            ai.add("com.game.net.message." + this.clientPkg + "." + this.client_bean_type);
        }
    }
}
