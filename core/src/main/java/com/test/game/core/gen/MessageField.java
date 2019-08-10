package com.test.game.core.gen;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 消息属性
 * @Auther: zhouwenbin
 * @Date: 2019/8/10 16:29
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface MessageField {
    String desc() default "";
}
