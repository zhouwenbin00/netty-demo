package com.test.game.core.gen;

import com.google.common.base.Preconditions;
import com.test.game.core.net.message.Message;
import com.test.game.core.utils.ReflectUtils;
import com.test.game.core.utils.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
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

        this.pkg = cfgPkg + clazz.getPackage().getName().substring(clazz.getPackage().getName().lastIndexOf("."));
        java.lang.Class<?> requestClass = ReflectUtils.staticInsideClass(clazz, "Req");
        Preconditions.checkNotNull(requestClass, "%s 没有 Req", clazz.getName());
        this.request = new ArrayList<>();
        this.requestID = idGenerator.id(requestClass);
        this.ignoreRequestHandler = requestClass.getAnnotation(NoHandlerAnnotation.class) != null;
        for (Field field : requestClass.getDeclaredFields()) {
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
            for (Field field : responseClass.getDeclaredFields()) {
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
            for (Field field : errorClass.getDeclaredFields()) {
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

    public String getPkg() {
        return pkg;
    }

    public Message.NodeType getFrom() {
        return from;
    }

    public Message.NodeType getTo() {
        return to;
    }

    public int getRequestID() {
        return requestID;
    }

    public int getResponseID() {
        return responseID;
    }

    public int getErrorID() {
        return errorID;
    }

    public boolean isIgnoreRequestHandler() {
        return ignoreRequestHandler;
    }

    public boolean isIgnoreResponseHandler() {
        return ignoreResponseHandler;
    }

    public boolean isIgnoreErrorHandler() {
        return ignoreErrorHandler;
    }

    public List<OriginField> getRequest() {
        return request;
    }

    public List<OriginField> getResponse() {
        return response;
    }

    public List<NameAndDesc> getError() {
        return error;
    }

    public String getClient_package() {
        return client_package;
    }

    public String getClient_name(Message.NodeType from, Message.NodeType to) {
        if (to == Message.NodeType.CLIENT) {
            return "Res" + this.client_name;
        } else {
            return from == Message.NodeType.CLIENT ? "Req" + this.client_name : this.client_name;
        }
    }

    public String getCfgPkg() {
        return cfgPkg;
    }

    public void buildMD5(StringBuilder sb) {
        sb.append(this.name)
                .append(this.pkg)
                .append(this.from.toString())
                .append(this.to.toString())
                .append(this.requestID)
                .append(this.responseID)
                .append(this.errorID)
                .append(this.ignoreRequestHandler)
                .append(this.ignoreResponseHandler)
                .append(this.ignoreErrorHandler)
                .append(this.client_package)
                .append(this.client_name);
        if (this.request != null) {
            for (OriginField field : this.request) {
                field.buildMD5(sb);
            }
        }

        if (this.response != null) {
            for (OriginField field : this.response) {
                field.buildMD5(sb);
            }
        }

        if (this.error != null) {
            for (NameAndDesc nameAndDesc : this.error) {
                sb.append(nameAndDesc.name);
            }
        }
    }
}
