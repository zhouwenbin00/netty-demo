package com.test.game.core.gen;

import com.google.common.base.Preconditions;
import com.test.game.core.net.message.Message;
import com.test.game.core.utils.ReflectUtils;
import com.test.game.core.utils.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/** @Auther: zhouwenbin @Date: 2019/8/10 16:54 */
public class OriginMessage extends NameAndDesc {
    public final String pkg;
    public final Message.NodeType from;
    public final Message.NodeType to;
    public final int requestID;
    public final int responseID;
    public final int errorID;
    public final boolean ignoreRequestHandler;
    public final boolean ignoreResponseHandler;
    public final boolean ignoreErrorHandler;
    public final List<OriginField> request;
    public final List<OriginField> response;
    public final List<NameAndDesc> error;
    private final String client_package;
    private final String client_name;
    private final String cfgPkg;

    public OriginMessage(java.lang.Class<?> clazz, IDGenerator idGenerator, String cfgPkg) {
        super(clazz.getSimpleName(), ReflectUtils.annotation(clazz, MessageClass.class).desc());
        this.cfgPkg = cfgPkg;
        MessageClass annotation = ReflectUtils.annotation(clazz, MessageClass.class);
        this.from = annotation.from();
        this.to = annotation.to();

        this.pkg = clazz.getPackage().getName();
        java.lang.Class<?> requestClass = ReflectUtils.staticInsideClass(clazz, "Req");
        Preconditions.checkNotNull(requestClass, "%s 没有 Req", clazz.getName());
        this.request = new ArrayList<>();
        this.requestID = idGenerator.id(requestClass);
        this.ignoreRequestHandler = requestClass.getAnnotation(NoHandlerAnnotation.class) != null;
        java.lang.reflect.Field[] var6 = requestClass.getDeclaredFields();
        int var7 = var6.length;
        int var8;
        for (var8 = 0; var8 < var7; ++var8) {
            java.lang.reflect.Field field = var6[var8];
            this.request.add(new OriginField(field));
        }
        java.lang.Class<?> responseClass = ReflectUtils.staticInsideClass(clazz, "Res");
        this.responseID = idGenerator.id(responseClass);
        this.ignoreResponseHandler =
                responseClass == null
                        || responseClass.getAnnotation(NoHandlerAnnotation.class) != null;
        int var16;
        if (responseClass != null) {
            this.response = new ArrayList<>();
            java.lang.reflect.Field[] var13 = responseClass.getDeclaredFields();
            var8 = var13.length;

            for (var16 = 0; var16 < var8; ++var16) {
                java.lang.reflect.Field field = var13[var16];
                this.response.add(new OriginField(field));
            }
        } else {
            this.response = null;
        }

        java.lang.Class<?> errorClass = ReflectUtils.staticInsideClass(clazz, "Fail");
        this.errorID = idGenerator.id(errorClass);
        this.ignoreErrorHandler =
                errorClass == null || errorClass.getAnnotation(NoHandlerAnnotation.class) != null;
        if (errorClass != null) {
            this.error = new ArrayList<>();
            java.lang.reflect.Field[] var15 = errorClass.getDeclaredFields();
            var16 = var15.length;

            for (int var17 = 0; var17 < var16; ++var17) {
                Field field = var15[var17];
                if (!ReflectUtils.isPrivate(field)) {
                    this.error.add(
                            new NameAndDesc(
                                    field.getName(),
                                    ReflectUtils.annotation(field, MessageField.class).desc()));
                }
            }
        } else {
            this.error = null;
        }

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
}
