<#include "type.ftl">
<#macro read field>read${field.asType?cap_first}(_buf)</#macro>
<#macro type field>${field.asType}</#macro>
package datasets.bean {

import engine.base.data.Bean;
import engine.base.data.ByteArray;

/**
* Created by FreeMarker. DO NOT EDIT!!!
* ${class.desc}
*/
public class ${class.name?cap_first} extends Bean {
<#list class.fields as field>
    <#if field.belong.client>
        /** ${field.desc} */
        private var _${field.name}:<@type field/>;
    </#if>
</#list>

override public function read(_buf:ByteArray): void {
<#list class.fields as field>
    <#if field.belong.client>
        this._${field.name} = <@read field/>;
    </#if>
</#list>
}

<#list class.fields as field>
    <#if field.belong.client>
        public function get ${field.name}():<@type field/> {
        return _${field.name};
        }
    </#if>
</#list>
}
}
