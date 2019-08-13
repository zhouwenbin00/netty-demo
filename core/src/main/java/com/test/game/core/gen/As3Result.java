package com.test.game.core.gen;

import java.util.List;

public class As3Result {
    public final List<Bean> beans;
    public final List<Enum> enums;
    public final List<ErrorMessage> errors;
    public final List<Message> messages;
    public final Version version;
    public final List<Handler> handlers;
    public final MessageGroup mg;
    public final HandlerGroup hg;

    public As3Result(
            List<Bean> beans,
            List<Enum> enums,
            List<ErrorMessage> errors,
            List<Message> messages,
            Version version,
            List<Handler> handlers,
            MessageGroup mg,
            HandlerGroup hg) {
        this.beans = beans;
        this.enums = enums;
        this.errors = errors;
        this.messages = messages;
        this.version = version;
        this.handlers = handlers;
        this.mg = mg;
        this.hg = hg;
    }
}
