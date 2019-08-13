package com.test.game.core.gen;

import java.util.List;

public class HandlerGroup {
    private final String pkg;
    private final List<String> javaImports;
    private final List<String> as3Imports;
    private final List<Handler> handlers;
    private final String name;
    private final String javaParent;

    public HandlerGroup(
            String pkg,
            List<String> javaImports,
            List<String> as3Imports,
            List<Handler> handlers,
            String name,
            String javaParent) {
        this.pkg = pkg;
        this.javaImports = javaImports;
        this.as3Imports = as3Imports;
        this.handlers = handlers;
        this.name = name;
        this.javaParent = javaParent;
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

    public List<Handler> getHandlers() {
        return this.handlers;
    }

    public String getName() {
        return this.name;
    }

    public String getJavaParent() {
        return this.javaParent;
    }

    public void add(Handler handler) {
        this.as3Imports.add(
                "com.game.net.handler."
                        + handler.getClient_package()
                        + "."
                        + handler.getClient_name()
                        + "Handler");
        this.handlers.add(handler);
    }
}
