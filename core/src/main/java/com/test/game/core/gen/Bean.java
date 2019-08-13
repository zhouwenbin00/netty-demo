package com.test.game.core.gen;

import com.google.common.collect.Lists;

import java.util.*;

public class Bean {
    private final String pkg;
    private final List<String> javaImports;
    private final List<String> as3Imports;
    private final NameAndDesc nameAndDesc;
    private final List<AbstractField> beanFields;
    private final String client_package;
    private final String client_name;
    private final String cfgPkg;

    public Bean(
            String pkg,
            NameAndDesc nameAndDesc,
            List<BeanField> beanFields,
            String client_package,
            String client_name,
            String cfgPkg) {
        this.pkg = pkg;
        this.nameAndDesc = nameAndDesc;
        this.beanFields = beanFields == null ? null : Lists.newArrayList(beanFields);
        this.client_package = client_package;
        this.client_name = client_name;
        this.cfgPkg = cfgPkg;
        Set<String> ji = new HashSet<>();
        Set<String> ai = new HashSet<>();
        if (beanFields != null) {

            for (BeanField field : beanFields) {
                field.buildImport(ji, ai);
            }
        }

        this.javaImports = new ArrayList<>(ji);
        this.as3Imports = new ArrayList<>(ai);
    }

    public String getCfgPkg() {
        return this.cfgPkg;
    }

    public String getPackage() {
        return this.pkg;
    }

    public List<String> getJavaImports() {
        return this.javaImports;
    }

    public List<String> getAs3Imports() {
        return this.as3Imports;
    }

    public String getDesc() {
        return this.nameAndDesc.desc;
    }

    public String getName() {
        return this.nameAndDesc.name;
    }

    public List<AbstractField> getFields() {
        return this.beanFields;
    }

    public String getClient_package() {
        return this.client_package;
    }

    public String getClient_name() {
        return this.client_name;
    }
}
