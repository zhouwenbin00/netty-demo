<#include "type.ftl">
<#macro read field><#if field.basic>ByteBufUtil.read${field.type?cap_first}(buf)<#else>readString()</#if></#macro>
<#macro type field>${field.type}</#macro>
package datasets.container {

import datasets.bean.${class.name?cap_first};
import engine.base.data.ByteArray;
import engine.base.data.ByteBufferUtil;

/**
* Created by FreeMarker. DO NOT EDIT!!!
* ${class.desc}
*/
public class ${class.name?cap_first}Container {

private static var _list: Vector.<${class.name?cap_first}> = new Vector.<${class.name?cap_first}>();

private static var _dict: Object = new Object();

<#--private static var _version: int;-->

public static function SetData(bytes: ByteArray): void{
_list.length = 0;
_dict = new Object();
<#--_version = bytes.readInt();-->
var num:int = ByteBufferUtil.readInt(bytes);
for (var i:int = 0; i < num; i++) {
var bean:${class.name?cap_first} = new ${class.name?cap_first}();
bean.read(bytes);
_list.push(bean);
_dict[String(bean.${class.fields?first.name})] = bean;
}
}

public static function get list(): Vector.<${class.name?cap_first}> {
return _list;
}

public static function get dict(): Object {
return _dict;
}

public static function GetValue(key:*): ${class.name?cap_first} {
return _dict[key.toString()];
}
}
}
