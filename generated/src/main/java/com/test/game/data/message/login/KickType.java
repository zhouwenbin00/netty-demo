package com.test.game.data.message.login;

/** 踢人类型 */
public enum KickType {
    /** 版本不一致 */
    VERSION_ILLEGAL(1),
    /** 验证未通过 */
    NOT_VERIFY(2),
    /** 多次发送登录消息 */
    LOGIN_MULTI_TIMES(3),
    /** 顶号 */
    REPLACE(4),
    /** 流程错误 */
    FLOW(5),
    /** 非法消息 */
    ILLEGAL_MSG(6),
    ;
    private final int value;

    KickType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static KickType valueOf(int value) {
        switch (value) {
            case 1:
            /** 版本不一致 */
            return VERSION_ILLEGAL;
            case 2:
            /** 验证未通过 */
            return NOT_VERIFY;
            case 3:
            /** 多次发送登录消息 */
            return LOGIN_MULTI_TIMES;
            case 4:
            /** 顶号 */
            return REPLACE;
            case 5:
            /** 流程错误 */
            return FLOW;
            case 6:
            /** 非法消息 */
            return ILLEGAL_MSG;
        }
        return null;
    }
}