package com.test.game.core.gen;

import java.util.List;

public class Handler {
    private final String pkg;
    private final List<String> javaImports;
    private final List<String> as3Imports;
    private final int id;
    private final String name;
    private final String javaParent;
    private final String messageName;
    private final String desc;
    private final String client_package;
    private final String client_name;
    private final String cfgPkg;

    public Handler(
            String pkg,
            List<String> javaImports,
            List<String> as3Imports,
            int id,
            String name,
            String javaParent,
            String messageName,
            String desc,
            String client_package,
            String client_name,
            String cfgPkg) {
        this.pkg = pkg;
        this.javaImports = javaImports;
        this.as3Imports = as3Imports;
        this.id = id;
        this.name = name;
        this.javaParent = javaParent;
        this.messageName = messageName;
        this.desc = desc;
        this.client_package = client_package;
        this.client_name = client_name;
        this.cfgPkg = cfgPkg;
    }

    public String getCfgPkg() {
        return this.cfgPkg;
    }

    public String getMessageName() {
        return this.messageName;
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

    public String getName() {
        return this.name;
    }

    public String getJavaParent() {
        return this.javaParent;
    }

    public int getId() {
        return this.id;
    }

    public String getClient_package() {
        return this.client_package;
    }

    public String getClient_name() {
        return this.client_name;
    }

    public String getDesc() {
        return this.desc;
    }
}
