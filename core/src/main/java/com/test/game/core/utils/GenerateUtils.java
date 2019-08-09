package com.test.game.core.utils;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.test.game.core.gen.Class;
import com.test.game.core.gen.DataWithClass;
import freemarker.template.Configuration;
import freemarker.template.Template;
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
public abstract class GenerateUtils {
    public static final Logger log = LoggerFactory.getLogger(GenerateUtils.class);

    private GenerateUtils() {}

    public static void genCfgSrc(
            String excelDir,
            String pkg,
            File serverCodeDir,
            File serverCustomCodeDir,
            File clientCodeDir,
            File clientCustomCodeDir)
            throws Exception {
        List<Class> classes = new LinkedList<>();
        ExcelUtils.parse(excelDir, classes, null);
        String version = md5(classes);
        generateServerCode(pkg, classes, serverCodeDir, serverCustomCodeDir, version);
        generateClientCode(pkg, classes, clientCodeDir, clientCustomCodeDir, version);
    }

    private static void generateServerCode(
            String pkg, List<Class> classes, File autoDir, File customDir, String version)
            throws Exception {
        Configuration configuration =
                FreeMarkerUtils.create(
                        GenerateUtils.class.getClassLoader(), "freemarker/config/server");
        Template customBeanTemplate = configuration.getTemplate("custom_bean.ftl");
        Template customGroupTemplate = configuration.getTemplate("custom_group.ftl");
        Template autoBeanTemplate = configuration.getTemplate("auto_bean.ftl");
        Template autoGroupTemplate = configuration.getTemplate("auto_group.ftl");
        FileUtils.deleteDir(
                new File(autoDir.getAbsolutePath() + File.separator + FileUtils.package2path(pkg)));
        for (Class clazz : classes) {
            if (clazz.belong.isServer()) {
                log.info(autoDir.getAbsolutePath()
                        + File.separator
                        + FileUtils.package2path(pkg)
                        + File.separator
                        + StringUtils.capFirst(clazz.className)
                        + ".java");
                FreeMarkerUtils.gen(
                        autoDir.getAbsolutePath()
                                + File.separator
                                + FileUtils.package2path(pkg)
                                + File.separator
                                + StringUtils.capFirst(clazz.className)
                                + ".java",
                        autoBeanTemplate,
                        new MapBuilder<String, Object>(new HashMap<>())
                                .put("package", pkg)
                                .put("class", clazz)
                                .build(),
                        true);
                FreeMarkerUtils.gen(
                        customDir.getAbsolutePath()
                                + File.separator
                                + FileUtils.package2path(pkg)
                                + File.separator
                                + StringUtils.capFirst(clazz.className)
                                + "Group.java",
                        customGroupTemplate,
                        new MapBuilder<String, Object>(new HashMap<>())
                                .put("package", pkg)
                                .put("package", pkg)
                                .put("class", clazz)
                                .build(),
                        false);
            }
        }
        FreeMarkerUtils.gen(
                autoDir.getAbsolutePath()
                        + File.separator
                        + FileUtils.package2path(pkg)
                        + File.separator
                        + "ConfigGroup.java",
                autoGroupTemplate,
                new MapBuilder<String, Object>(new HashMap<>())
                        .put("package", pkg)
                        .put("classes", classes)
                        .put("version", version)
                        .put("cfgPkg", pkg.substring(0, pkg.indexOf(".config")))
                        .build(),
                true);
    }

    private static void generateClientCode(
            String pkg,
            List<Class> classes,
            File clientCodeDir,
            File clientCustomCodeDir,
            String version) {}

    /**
     * 生成bin文件
     *
     * @param execl_dir
     * @param server_file
     * @param client_file
     * @throws IOException
     */
    public static void export(String execl_dir, File server_file, File client_file)
            throws IOException {
        List<Class> classes = new LinkedList<>();
        List<DataWithClass> datas = new LinkedList<>();
        // 解析excel
        ExcelUtils.parse(execl_dir, classes, datas);
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

            while (!compressor.finished()) {
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
