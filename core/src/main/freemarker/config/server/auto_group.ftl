<#setting number_format="#">
package ${package};

import shell.net.Message;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import shell.io.IOUtil;
import shell.nio.ByteBufUtil;
import java.io.IOException;
import java.io.InputStream;

import static com.google.common.base.Preconditions.checkArgument;

/** Created by FreeMarker. DO NOT EDIT!!! */
public class ConfigGroup {
  public final String CODE_VERSION = "${version}";
  public final byte[] DATA_VERSION;
<#list classes as class>
    <#if class.belong.server>
  public final ${class.name?cap_first}Group ${class.name?uncap_first}Group; // ${class.desc}
    </#if>
</#list>

  public ConfigGroup(InputStream is, shell.misc.Factory2<byte[], Integer> factory) throws IOException {
    ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
    try {
      byte[] bytes = IOUtil.readBytes(is);
      ByteBufUtil.writeBytes(buf, bytes, 0, bytes.length);

      checkArgument(CODE_VERSION.equals(ByteBufUtil.readString(buf)), "配置文件和程序版本不一致");
      this.DATA_VERSION = factory.create(ByteBufUtil.readInt(buf));

<#list classes as class>
    <#if class.belong.server>
      try {
        this.${class.name?uncap_first}Group = new ${class.name?cap_first}Group(buf);
      } catch(Throwable e) {
        throw new shell.game.misc.ConfigFileException(e.getMessage(), "${class.desc}", e);
      }
    </#if>
</#list>
    } finally {
      buf.release();
    }
  }

  public void check() {
<#list classes as class>
    <#if class.belong.server>
    try {
      this.${class.name?uncap_first}Group.check(this);
    } catch(Throwable e) {
      throw new shell.game.misc.ConfigFileException(e.getMessage(), "${class.desc}", e);
    }
    </#if>
</#list>
  }
}
