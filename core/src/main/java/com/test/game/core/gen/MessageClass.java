package com.test.game.core.gen;

import com.test.game.core.net.message.Message;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** @Auther: zhouwenbin @Date: 2019/8/10 17:30 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface MessageClass {
    String desc() default "";

    Message.NodeType from();

    Message.NodeType to();

    String client_pkg() default "";

    String client_name() default "";
}
