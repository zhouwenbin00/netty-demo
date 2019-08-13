<#include "head.ftl">
package ${package};

<@import javaImports/>

import com.google.common.collect.ImmutableMap;
import com.test.game.core.net.message.Message;
import com.test.game.core.net.message.MessageFactory;

public class ${name} extends MessageFactory {
    public final ImmutableMap<Integer, Class<? extends Message>> messages;

    public ${name}() {
        ImmutableMap.Builder<Integer, Class<? extends Message>> builder = ImmutableMap.builder();
<#list messages as message>
        builder.put(${message.id}, ${message.package}.${message.name}.class);
</#list>
        messages = builder.build();
    }

    @Override
    protected Class<? extends Message> getClass(int id) {
        return messages.get(id);
    }
}
