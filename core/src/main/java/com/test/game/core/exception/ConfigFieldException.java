package com.test.game.core.exception;

/**
 * @Auther: zhouwenbin
 * @Date: 2019/8/10 03:22
 */
public class ConfigFieldException  extends RuntimeException{
    public ConfigFieldException(String message, String field, Throwable cause) {
        super("[field:" + field + "]" + message, cause);
    }
}
