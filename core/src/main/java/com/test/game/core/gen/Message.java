package com.test.game.core.gen;

import java.util.List;

public class Message extends AbstractMessage {
    private final String error;

    public Message(
            String pkg,
            NameAndDesc nameAndDesc,
            List<BeanField> beanFields,
            int id,
            com.test.game.core.net.message.Message.NodeType from,
            com.test.game.core.net.message.Message.NodeType to,
            String error,
            String client_package,
            String client_name,
            String cfgPkg) {
        super(pkg, nameAndDesc, beanFields, id, from, to, client_package, client_name, cfgPkg);
        this.error = error;
    }

    public String getError() {
        return this.error;
    }
}
