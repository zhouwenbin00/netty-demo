package com.test.game.core.utils;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/** @Auther: zhouwenbin @Date: 2019/8/9 00:35 */
public abstract class StringUtils {
    /*占位符*/
    private static String placeholder = "{@}";

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

    public static boolean notNullOrEmpty(String s) {
        return !isNullOrEmpty(s);
    }

    public static boolean isBlank(String s) {
        if (isNullOrEmpty(s)) {
            return true;
        }
        return s.trim().equals("");
    }

    public static boolean isNotBlank(String s) {
        return !isBlank(s);
    }

    /**
     * 生成md5
     *
     * @param s
     * @return
     */
    public static String md5(String s) {
        if (s == null) {
            return null;
        }
        byte[] b = s.getBytes(Charset.forName("UTF-8"));

        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            return null;
        }

        byte[] bytes = md5.digest(b);
        StringBuilder ret = new StringBuilder(bytes.length << 1);

        for (int i = 0; i < bytes.length; ++i) {
            ret.append(Character.forDigit(bytes[i] >> 4 & 15, 16));
            ret.append(Character.forDigit(bytes[i] & 15, 16));
        }

        return ret.toString();
    }

    /**
     * 首字母大写
     *
     * @param s
     * @return
     */
    public static String capFirst(String s) {
        return Character.isUpperCase(s.charAt(0))
                ? s
                : Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    /**
     * 首字母大写，其他小写
     *
     * @param s
     * @return
     */
    public static String upperFirstAndLowerOther(String s) {
        return Character.toUpperCase(s.charAt(0)) + s.substring(1).toLowerCase();
    }

    /**
     * 格式化字符串
     *
     * @param s
     * @param params
     * @return
     */
    public static String format(String s, Object... params) {
        StringBuilder sb = new StringBuilder();
        if (params != null && params.length > 0) {
            for (Object param : params) {
                int index = s.indexOf(placeholder);
                if (index != -1) {
                    sb.append(s, 0, index);
                    sb.append(param.toString());
                    s = s.substring(index + 3);
                }
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String format = format("jar:file {@} !/ {@}", true, "is");
        System.out.println(format);
    }
}
