package com.test.game.message_module.role;

/**  */
public enum JavaType {
    /** s */
    ONR(1),
    /** s */
    ONR1(2),
    /** s */
    ONR11(3),
    ;
    private final int value;

    JavaType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static JavaType valueOf(int value) {
        switch (value) {
            case 1:
            /** s */
            return ONR;
            case 2:
            /** s */
            return ONR1;
            case 3:
            /** s */
            return ONR11;
        }
        return null;
    }
}