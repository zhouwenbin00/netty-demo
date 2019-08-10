<#setting number_format="#">
package datasets {

import engine.base.data.ByteArray;
import engine.base.data.ByteBufferUtil;
<#list classes as class>
    <#if class.belong.client>
        import datasets.container.${class.name?cap_first}Container;
    </#if>
</#list>

/** Created by FreeMarker. DO NOT EDIT!!! */
public class ConfigGroup {
public static const CODE_VERSION:String = "${version}";
public var DATA_VERSION:int;

public function ConfigGroup(buf:ByteArray) {
var ac:String = ByteBufferUtil.readString(buf);
if (ac != CODE_VERSION) {
throw new Error("expect version " + CODE_VERSION + ", actual version " + ac);
}
this.DATA_VERSION = ByteBufferUtil.readInt(buf);

<#list classes as class>
    <#if class.belong.client>
        ${class.name?cap_first}Container.SetData(buf);
    </#if>
</#list>
}
}
}