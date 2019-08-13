package com.test.game.core.gen;

public class Version {
    private final String pkg;
    private final String version;
    private final String name;

    public Version(String pkg, String version, String name) {
        this.pkg = pkg;
        this.version = version;
        this.name = name;
    }

    public String getPackage() {
        return this.pkg;
    }

    public String getVersion() {
        return this.version;
    }

    public String getName() {
        return this.name;
    }
}
