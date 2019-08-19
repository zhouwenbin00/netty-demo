package com.test.game.core.gen;

public enum MessageType {
    Req(0),
    Res(2),
    Fail(3);

    private final int value;

    private MessageType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}