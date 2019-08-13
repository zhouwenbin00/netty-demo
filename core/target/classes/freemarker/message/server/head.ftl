<#macro import imports>
    <#list imports as import>
        import ${import};
    </#list>
</#macro>
<#macro declare fields>
    <#list fields as field>
        /** ${field.desc} */
        private ${field.javaType} ${field.name}${field.javaInit};
    </#list>
</#macro>
<#macro getset fields name>
    <#list fields as field>

        /** ${field.desc} */
        public ${name} set${field.name?cap_first}(${field.javaType} ${field.name}) {
        this.${field.name} = ${field.name};
        return this;
        }

        /** ${field.desc} */
        public ${field.javaType} get${field.name?cap_first}() {
        return this.${field.name};
        }
    </#list>
</#macro>
<#macro write fields>
    <#list fields as field>
        <#if field.list>
            ByteBufUtils.writeInt(buf, this.${field.name}.size());
            for (${field.type} i_am_tmp : this.${field.name}) {
            <#if field.enum>
                ByteBufUtils.writeInt(buf, i_am_tmp.getValue());
            <#elseif field.bean>
                i_am_tmp.write(buf);
            <#elseif field.type=="byte[]">
                ByteBufUtils.writeBytesWithLengthFlag(buf, i_am_tmp);
            <#else>
                ByteBufUtils.write${field.type?cap_first}(buf, i_am_tmp);
            </#if>
            }
        <#else>
            <#if field.enum>
                ByteBufUtils.writeInt(buf, this.${field.name} != null ? this.${field.name}.getValue() : 0);
            <#elseif field.bean>
                ByteBufUtils.writeBean(buf, this.${field.name});
            <#elseif field.type=="byte[]">
                ByteBufUtils.writeBytesWithLengthFlag(buf, this.${field.name});
            <#else>
                ByteBufUtils.write${field.type?cap_first}(buf, this.${field.name});
            </#if>
        </#if>
    </#list>
</#macro>
<#macro read fields>
    <#list fields as field>
        <#if field.list>
            size52413035 = ByteBufUtils.readInt(buf);
            this.${field.name} = new java.util.ArrayList(size52413035);
            for (int i_am_tmp_i = 0; i_am_tmp_i < size52413035; ++i_am_tmp_i) {
            <#if field.enum>
                this.${field.name}.add(${field.type}.valueOf(ByteBufUtils.readInt(buf)));
            <#elseif field.bean>
                this.${field.name}.add(new ${field.type}().read(buf));
            <#elseif field.type=="byte[]">
                this.${field.name}.add(ByteBufUtils.readBytesWithLengthFlag(buf));
            <#else>
                this.${field.name}.add(ByteBufUtils.read${field.type?cap_first}(buf));
            </#if>
            }
        <#else>
            <#if field.enum>
                this.${field.name} = ${field.type}.valueOf(ByteBufUtils.readInt(buf));
            <#elseif field.bean>
                this.${field.name} = ByteBufUtils.readBean(buf, ${field.type}.class);
            <#elseif field.type=="byte[]">
                this.${field.name} = ByteBufUtils.readBytesWithLengthFlag(buf);
            <#else>
                this.${field.name} = ByteBufUtils.read${field.type?cap_first}(buf);
            </#if>
        </#if>
    </#list>
</#macro>
<#macro content fields name>
    <@declare fields/>
    <@getset fields name/>

    @Override
    public void write(ByteBuf buf) {
    <@write fields/>
    }

    @Override
    public ${name} read(ByteBuf buf) {
    int size52413035;
    <@read fields/>
    return this;
    }

    @Override
    public String toString() {
    return "${name}{" +
    <#list fields as field>
        "${field.name}='" + ${field.name} + '\'' +
    </#list>
    '}';
    }
</#macro>
<#setting number_format="#">