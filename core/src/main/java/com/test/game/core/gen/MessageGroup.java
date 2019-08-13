package com.test.game.core.gen;

import java.util.List;

public class MessageGroup {
    private final String pkg;
    private final List<String> javaImports;
    private final List<String> as3Imports;
    private final List<AbstractMessage> messages;
    private final String name;

    public MessageGroup(
            String pkg,
            List<String> javaImports,
            List<String> as3Imports,
            List<AbstractMessage> messages,
            String name) {
        this.pkg = pkg;
        this.javaImports = javaImports;
        this.as3Imports = as3Imports;
        this.messages = messages;
        this.name = name;
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

    public List<AbstractMessage> getMessages() {
        return this.messages;
    }

    public String getName() {
        return this.name;
    }

    public void add(AbstractMessage message) {
        this.as3Imports.add(
                "com.game.net.message."
                        + message.getClient_package()
                        + "."
                        + message.getClient_name()
                        + "Message");
        this.messages.add(message);
    }
}
