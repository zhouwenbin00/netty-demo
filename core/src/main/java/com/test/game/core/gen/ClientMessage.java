package com.test.game.core.gen;

import com.test.game.core.net.message.Message;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**从客户端发向服务端的消息
 * @Auther: zhouwenbin
 * @Date: 2019/8/10 16:34
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ClientMessage {
    String desc() default "";
    Message.NodeType from() default Message.NodeType.CLIENT;
    Message.NodeType to() default Message.NodeType.SERVER;
}
