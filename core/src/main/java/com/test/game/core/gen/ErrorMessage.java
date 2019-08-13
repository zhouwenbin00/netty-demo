package com.test.game.core.gen;

import com.google.common.collect.Lists;
import com.test.game.core.net.message.Message;

import java.util.List;

public class ErrorMessage extends AbstractMessage {
    private final List<AbstractField> fields;

    public ErrorMessage(
            String pkg,
            NameAndDesc nameAndDesc,
            List<BeanField> beanFields,
            int id,
            com.test.game.core.net.message.Message.NodeType from,
            Message.NodeType to,
            List<EnumField> fields,
            String client_package,
            String client_name,
            String cfgPkg) {
        super(pkg, nameAndDesc, beanFields, id, from, to, client_package, client_name, cfgPkg);
        this.fields = Lists.newArrayList(fields);
    }

    public List<AbstractField> getFields() {
        return this.fields;
    }
}
