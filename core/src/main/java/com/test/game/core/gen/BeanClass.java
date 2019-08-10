package com.test.game.core.gen;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Bean消息类 @Auther: zhouwenbin @Date: 2019/8/10 16:31 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface BeanClass {
    String desc() default "";

    int start() default 1;

    String client_pkg() default "";

    String client_name() default "";
}
