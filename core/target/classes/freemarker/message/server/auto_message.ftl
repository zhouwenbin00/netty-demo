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
  public ${name}() {}

<#if fields?has_content>
  public ${name}(<#list fields as field>${field.javaType} ${field.name}<#if field_has_next>, </#if></#list>) {
<#list fields as field>
    this.${field.name} = ${field.name};
</#list>
  }

</#if>
<@content fields name/>
  @Override
  public int id() {
    return ${id};
  }

  @Override
  public NodeType from() {
    return ${from};
  }

  @Override
  public NodeType to() {
    return ${to};
  }

<#if error??>
  @Nullable
  public Message error(int e) {
    return new ${error}(e);
  }
<#else>
  @Nullable
  public Message error(int e) {
    return null;
  }
</#if>
}
