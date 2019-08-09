package com.test.game.core.exception;

public class ConfigFileException extends RuntimeException {
    public ConfigFileException(String message, String file, Throwable cause) {
        super("[文件:" + file + "]" + message, cause);
    }
}
