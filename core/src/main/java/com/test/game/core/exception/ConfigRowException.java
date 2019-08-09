package com.test.game.core.exception;

public class ConfigRowException extends RuntimeException {
    public ConfigRowException(String message, int row, Throwable cause) {
        super("[行:" + row + "]" + message, cause);
    }

    public ConfigRowException(String message, String keyName, String key, Throwable cause) {
        super("[" + keyName + ":" + key + "]" + message, cause);
    }
}
