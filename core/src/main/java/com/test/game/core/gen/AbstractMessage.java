package com.test.game.core.gen;

import com.test.game.core.net.message.Message;

import java.util.List;

public abstract class AbstractMessage extends Bean {
    private final int id;
    private final com.test.game.core.net.message.Message.NodeType from;
    private final com.test.game.core.net.message.Message.NodeType to;

    public AbstractMessage(
            String pkg,
            NameAndDesc nameAndDesc,
            List<BeanField> beanFields,
            int id,
            com.test.game.core.net.message.Message.NodeType from,
            Message.NodeType to,
            String client_package,
            String client_name,
            String cfgPkg) {
        super(pkg, nameAndDesc, beanFields, client_package, client_name, cfgPkg);
        this.id = id;
        this.from = from;
        this.to = to;
    }

    public int getId() {
        return this.id;
    }

    public String getFrom() {
        return this.from.name();
    }

    public String getTo() {
        return this.to.name();
    }
}
