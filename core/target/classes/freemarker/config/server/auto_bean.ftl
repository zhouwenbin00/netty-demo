<#macro read field><#if field.basic>builder.${field.name}<#else>${class.name?cap_first}Group.create_${field.name}(builder, builder.${field.name})</#if></#macro>
<#macro type field>${field.javaType}</#macro>
<#macro read2 field><#if field.basic>ByteBufUtil.read${field.stype?cap_first}(buf)<#else>ByteBufUtil.readString(buf)</#if></#macro>
<#macro type2 field><#if field.basic>${field.javaType}<#else>String</#if></#macro>
package ${package};

import shell.nio.ByteBufUtil;
import io.netty.buffer.ByteBuf;

/** Created by FreeMarker. DO NOT EDIT!!! ${class.desc} */
public class ${class.name?cap_first} {
<#list class.fields as field>
  <#if field.belong.server>
  public final <@type field/> ${field.name}; // ${field.desc}
  </#if>
</#list>

  public static class Builder {
    <#list class.fields as field>
        <#if field.belong.server>
    public final <@type2 field/> ${field.name}; // ${field.desc}
        </#if>
    </#list>
    protected Builder(ByteBuf buf) {
    <#list class.fields as field>
        <#if field.belong.server>
      try {
        this.${field.name} = <@read2 field/>;
      } catch (Throwable e) {
        throw new shell.game.misc.ConfigFieldException(e.getMessage(), "${field.name}", e);
      }
        </#if>
    </#list>
    }
  }

  protected ${class.name?cap_first}(ByteBuf buf) {
    Builder builder = new Builder(buf);
    <#list class.fields as field>
        <#if field.belong.server>
        <#if field.basic>
    this.${field.name} = <@read field/>;
        <#else>
    try {
      this.${field.name} = <@read field/>;
    } catch (Throwable e) {
      throw new shell.game.misc.ConfigFieldException(e.getMessage(), "${field.name}", e);
    }
        </#if>
        </#if>
    </#list>
  }

  protected ${class.name?cap_first}(${class.name?cap_first} cfg) {
<#list class.fields as field>
    <#if field.belong.server>
    this.${field.name} = cfg.${field.name};
    </#if>
</#list>
  }

  public static abstract class Group {
<#if class.belong.server>

    private ${class.name?cap_first}[] datas;

    protected Group(ByteBuf buf) {
<#if class.belong.server>
      datas = new ${class.name?cap_first}[ByteBufUtil.readInt(buf)];
      for (int i = 0; i < datas.length; ++i) {
        try {
          ${class.name?cap_first} data = new ${class.name?cap_first}(buf);
          datas[i] = data;
        } catch (Throwable e) {
          throw new shell.game.misc.ConfigRowException(e.getMessage(), i + 6, e);
        }
      }
</#if>
    }
<#if class.belong.server>

    protected final ${class.name?cap_first}[] getAndSetNull() {
      ${class.name?cap_first}[] r = get();
      this.datas = null;
      return r;
    }

    protected final ${class.name?cap_first}[] get() {
      return datas;
    }
</#if>
  }
</#if>
}
