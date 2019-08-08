package com.test.game.core.utils;

/** @Auther: zhouwenbin @Date: 2019/8/9 00:35 */
public abstract class StringUtils {
    private StringUtils() {}

    public static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public static boolean allEnglish(String s) {
        for (char c : s.toCharArray()) {
            if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z') && c != '_' && (c < '0' || c > '9')) {
                return false;
            }
        }
        return true;
    }

    public static boolean notNullOrEmpty(String value) {
        return !isNullOrEmpty(value);
    }
}
