package com.test.game.core.utils;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/** @Auther: zhouwenbin @Date: 2019/8/11 21:36 */
public abstract class FileUtils {
    public static final Logger log = LoggerFactory.getLogger(FileUtils.class);

    private FileUtils() {}

    public static void writeBytes(byte[] bytes, File file) throws IOException {
        Preconditions.checkNotNull(file, "文件为空");
        Preconditions.checkNotNull(bytes, "没有文件路径", file.getAbsolutePath());
        FileOutputStream fos = new FileOutputStream(file);
        try {
            fos.write(bytes);
        } finally {
            fos.close();
        }
    }

    public static String package2path(String pkg) {
        Preconditions.checkNotNull(pkg, "pkg为空");
        return pkg.replace(".", File.separator);
    }

    /**
     * 删除文件夹以及子文件
     *
     * @param dir
     * @throws IOException
     */
    public static void deleteDir(File dir) throws IOException {
        if (dir != null && dir.exists()) {
            if (dir.delete()) {
                log.info("删除文件 : {}", dir.getAbsolutePath());
            } else {
                File[] files = dir.listFiles();
                for (File file : files) {
                    deleteDir(file);
                }
                if (dir.delete()) {
                    log.info("删除文件 : {}", dir.getAbsolutePath());
                } else {
                    throw new IOException("can not remove file:" + dir.getAbsolutePath());
                }
            }
        }
    }

    public static boolean mkDir(File file) {
        if (file.getParentFile().exists()) {
            return file.mkdir();
        } else {
            mkDir(file.getParentFile());
            return file.mkdir();
        }
    }
}
