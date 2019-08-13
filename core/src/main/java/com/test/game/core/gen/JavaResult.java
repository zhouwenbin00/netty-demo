package com.test.game.core.gen;

import java.util.ArrayList;
import java.util.List;

/** @Auther: zhouwenbin @Date: 2019/8/13 11:23 */
public class JavaResult {
    public final List<Bean> beans;
    public final List<Enum> enums;
    public final List<ErrorMessage> errors;
    public final List<Message> messages;
    public final Version version;
    public final List<Handler> handlers;
    public final List<MessageGroup> mgs = new ArrayList<>();
    public final List<HandlerGroup> hgs = new ArrayList<>();
    public final List<JavaGuiceModule> jgms = new ArrayList<>();

    public JavaResult(
            List<Bean> beans,
            List<Enum> enums,
            List<ErrorMessage> errors,
            List<Message> messages,
            Version version,
            List<Handler> handlers) {
        this.beans = beans;
        this.enums = enums;
        this.errors = errors;
        this.messages = messages;
        this.version = version;
        this.handlers = handlers;
    }
}
