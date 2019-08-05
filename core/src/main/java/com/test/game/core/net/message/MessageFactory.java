package com.test.game.core.net.message;

import com.test.game.core.net.message.Message;
import lombok.extern.slf4j.Slf4j;

/** @Auther: zhouwenbin @Date: 2019/8/5 13:49 */
@Slf4j
public abstract class MessageFactory {

    public MessageFactory() {}

    public Message create(int id) {
        try {
            return (Message) this.getClass(id).newInstance();
        } catch (Exception e) {
            log.error("message:{}", id, e);
            return null;
        }
    }

    protected abstract Class<? extends Message> getClass(int var1);
}
