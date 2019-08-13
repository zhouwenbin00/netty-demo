package com.test.game.core.gen;

public enum MessageType {
    Request(0),
    Response(2),
    Error(3);

    private final int value;

    private MessageType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}