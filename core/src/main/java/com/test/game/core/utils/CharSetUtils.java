package com.test.game.core.utils;

import java.nio.charset.Charset;

/** @Auther: zhouwenbin @Date: 2019/8/11 21:05 */
public abstract class CharSetUtils {
    public static Charset UTF8 = Charset.forName("UTF-8");
    public static Charset GBK = Charset.forName("gbk");
    public static String UTF8_NAME = "UTF-8";
    public static String GBK_NAME = "gbk";


    private CharSetUtils() {
    }
}
