<#include "head.ftl">
package ${package};

<@import javaImports/>
import io.netty.buffer.ByteBuf;
import com.test.game.core.net.message.Bean;;
import com.test.game.core.utils.ByteBufUtils;
/** ${desc} */
public class ${name} extends Bean<${name}> {
public ${name}() {}

<#if fields?has_content>
    public ${name}(<#list fields as field>${field.javaType} ${field.name}<#if field_has_next>, </#if></#list>) {
    <#list fields as field>
        this.${field.name} = ${field.name};
    </#list>
    }
</#if>

<@content fields name/>
}