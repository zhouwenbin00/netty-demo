package com.test.game.core.gen;

import com.test.game.core.net.message.Message;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 从服务端发向客户端的消息
 * @Auther: zhouwenbin
 * @Date: 2019/8/10 16:32
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ServerMessage {
    String desc() default "";
    Message.NodeType from() default Message.NodeType.SERVER;
    Message.NodeType to() default Message.NodeType.CLIENT;
    String client_pkg() default "";

    String client_name() default "";
}
