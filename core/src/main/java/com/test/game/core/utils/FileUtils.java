package com.test.game.core.utils;

import com.google.common.base.Preconditions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Auther: zhouwenbin
 * @Date: 2019/8/11 21:36
 */
public abstract class FileUtils {
    private FileUtils() {
    }

    public static void writeBytes(byte[] bytes, File file) throws IOException {
        Preconditions.checkNotNull(file, "文件为空");
        Preconditions.checkNotNull(bytes, "没有文件路径", file.getAbsolutePath());
        FileOutputStream fos = new FileOutputStream(file);
        try{
            fos.write(bytes);
        }finally{
            fos.close();
        }
    }
}
