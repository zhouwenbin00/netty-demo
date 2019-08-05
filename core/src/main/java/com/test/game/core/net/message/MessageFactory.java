package com.test.game.core.net.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** @Auther: zhouwenbin @Date: 2019/8/5 13:49 */
public abstract class MessageFactory {

    private static final Logger log = LoggerFactory.getLogger(MessageFactory.class);

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
