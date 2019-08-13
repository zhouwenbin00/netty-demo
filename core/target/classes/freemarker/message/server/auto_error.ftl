<#include "head.ftl">
package ${package};

<@import javaImports/>

import io.netty.buffer.ByteBuf;
import com.test.game.core.net.message.Message;
import com.test.game.core.utils.ByteBufUtils;

import javax.annotation.Nullable;

import static com.test.game.core.net.message.Message.NodeType.${from};
import static com.test.game.core.net.message.Message.NodeType.${to};

/** ${desc} */
public class ${name} extends Message {
    private int error;

    public ${name}(int error) {
        this.error = error;
    }

    <#list fields as field>
    /** ${field.desc} */
    public static byte[] ${field.name} = Message.encodeToBytes(new ${name}(${field.value}));
    </#list>

    public int getError() {
        return this.error;
    }

    @Override
    public void write(ByteBuf buf) {
        ByteBufUtils.writeInt(buf, error);
    }

    @Override
    public Message read(ByteBuf buf) {
        error = ByteBufUtils.readInt(buf);
        return this;
    }

    @Override
    public int id() {
        return ${id};
    }

    @Override
    public NodeType from() {
        return ${to};
    }

    @Override
    public NodeType to() {
        return ${from};
    }

    @Nullable
    public Message error(int e) {
        return null;
    }
}
