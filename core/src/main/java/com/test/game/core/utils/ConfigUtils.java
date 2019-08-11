package com.test.game.core.utils;

import com.test.game.core.gen.Class;
import com.test.game.core.gen.DataWithClass;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.zip.Deflater;

/** @Auther: zhouwenbin @Date: 2019/8/8 20:00 */
public abstract class ConfigUtils {
    public static final Logger log = LoggerFactory.getLogger(ConfigUtils.class);

    private ConfigUtils() {}

    class Data {}

    class CustomClass {}

    public static void export(String execl_dir, File server_file, File client_file)
            throws IOException {
        Map<String, CustomClass> name2custom = new HashMap<>();
        List<Class> classes = new LinkedList<>();
        List<DataWithClass> datas = new LinkedList<>();
        // 解析excel
        ExcelUtils.parse(execl_dir, name2custom, classes, datas);
        // 代码版本
        String codeVersion = md5(classes);
        // 日期版本
        int dataVersion = TimeUtils.seconds();
        ByteBuf serverBuf = ByteBufAllocator.DEFAULT.buffer();
        ByteBufUtils.writeString(serverBuf, codeVersion);
        ByteBufUtils.writeInt(serverBuf, dataVersion);
        ByteBuf clientBuf = ByteBufAllocator.DEFAULT.buffer();
        ByteBufUtils.writeString(clientBuf, codeVersion);
        ByteBufUtils.writeInt(clientBuf, dataVersion);
        // 写入数据
        for (DataWithClass data : datas) {
            data.writeServer(serverBuf);
            data.writeClient(clientBuf);
        }
        // 写入文件
        try {
            FileUtils.writeBytes(ByteBufUtils.toByteArray(serverBuf), server_file);
            FileUtils.writeBytes(compress(ByteBufUtils.toByteArray(clientBuf)), client_file);
        } finally {
            serverBuf.release();
            clientBuf.release();
        }
    }

    private static byte[] compress(byte[] data) {
        Deflater compressor = new Deflater();
        compressor.setLevel(9);
        compressor.reset();
        compressor.setInput(data);
        compressor.finish();
        ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);

        byte[] output;
        try {
            byte[] buf = new byte[1024];

            while(!compressor.finished()) {
                int i = compressor.deflate(buf);
                bos.write(buf, 0, i);
            }

            output = bos.toByteArray();
        } catch (Exception e) {
            output = data;
            log.error(e.getMessage(), e);
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }

        }

        compressor.end();
        return output;
    }

    private static String md5(List<Class> classes) {
        StringBuilder sb = new StringBuilder();

        for (Class clazz : classes) {
            clazz.buildClientMD5(sb);
        }

        return StringUtils.md5(sb.toString());
    }
}
