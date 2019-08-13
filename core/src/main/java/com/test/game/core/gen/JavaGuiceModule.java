package com.test.game.core.gen;

import java.util.List;

public class JavaGuiceModule {
    private final String pkg;
    private final String name;
    private final List<Handler> handlers;
    private final List<String> javaImports;
    private final List<String> as3Imports;

    public JavaGuiceModule(String pkg, String name, List<Handler> handlers, List<String> javaImports, List<String> as3Imports) {
        this.pkg = pkg;
        this.name = name;
        this.handlers = handlers;
        this.javaImports = javaImports;
        this.as3Imports = as3Imports;
    }

    public String getPackage() {
        return this.pkg;
    }

    public String getName() {
        return this.name;
    }

    public List<Handler> getHandlers() {
        return this.handlers;
    }

    public void add(Handler handler) {
        this.handlers.add(handler);
    }

    public List<String> getJavaImports() {
        return this.javaImports;
    }

    public List<String> getAs3Imports() {
        return this.as3Imports;
    }
}
