package com.test.game.core.utils;

import com.test.game.core.gen.*;
import com.test.game.core.gen.Class;
import com.test.game.core.gen.Enum;
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
                FreeMarkerUtils.gen(
                        autoDir.getAbsolutePath()
                                + File.separator
                                + FileUtils.package2path(pkg)
                                + File.separator
                                + StringUtils.capFirst(clazz.name)
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
                                + StringUtils.capFirst(clazz.name)
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
            String pkg, List<Class> classes, File autoDir, File customDir, String version)
            throws Exception {
        Configuration configuration =
                FreeMarkerUtils.create(
                        GenerateUtils.class.getClassLoader(), "freemarker/config/client");
        Template autoBeanTemplate = configuration.getTemplate("auto_bean.ftl");
        Template autoBeanGroupTemplate = configuration.getTemplate("auto_bean_group.ftl");
        Template autoGroupTemplate = configuration.getTemplate("auto_group.ftl");
        FileUtils.deleteDir(new File(autoDir.getAbsolutePath() + File.separator + "datasets"));
        Iterator var10 = classes.iterator();

        while (var10.hasNext()) {
            Class clazz = (Class) var10.next();
            if (clazz.belong.isClient()) {
                FreeMarkerUtils.gen(
                        autoDir.getAbsolutePath()
                                + File.separator
                                + "datasets/bean"
                                + File.separator
                                + StringUtils.capFirst(clazz.name)
                                + ".as",
                        autoBeanTemplate,
                        (new MapBuilder<String, Object>(new HashMap<>()))
                                .put("package", pkg)
                                .put("class", clazz)
                                .build(),
                        true);
                FreeMarkerUtils.gen(
                        autoDir.getAbsolutePath()
                                + File.separator
                                + "datasets/container"
                                + File.separator
                                + StringUtils.capFirst(clazz.name)
                                + "Container"
                                + ".as",
                        autoBeanGroupTemplate,
                        (new MapBuilder<String, Object>(new HashMap<>()))
                                .put("package", pkg)
                                .put("class", clazz)
                                .build(),
                        true);
            }
        }

        //        var10 = customClasses.iterator();
        //
        //        while (var10.hasNext()) {
        //            CustomClass customClass = (CustomClass) var10.next();
        //            Preconditions.checkNotNull(customClass.belong, "没被引用,赶紧删除%s",
        // customClass.name);
        //            if (!customClass.belong.isClient()) {}
        //        }

        FreeMarkerUtils.gen(
                autoDir.getAbsolutePath()
                        + File.separator
                        + "datasets"
                        + File.separator
                        + "ConfigGroup.as",
                autoGroupTemplate,
                (new MapBuilder<String, Object>(new HashMap<>()))
                        .put("package", pkg)
                        .put("classes", classes)
                        .put("version", version)
                        .build(),
                true);
    }

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

    public static void genMsgSrc(
            String pkg,String target_pkg, File idFile, File sAutoDir, File sCustomDir, File cAutoDir, File cCustomDir)
            throws Exception {
        MessageParser parser = parse(pkg, target_pkg, idFile);
        Assemble assemble = Assemble.assemble(parser);
        FileUtils.deleteDir(
                new File(
                        sAutoDir.getAbsolutePath() + File.separator + FileUtils.package2path(pkg)));
        generateJava(sCustomDir, sAutoDir, assemble.javaResult);
        FileUtils.deleteDir(new File(cAutoDir.getAbsolutePath() + File.separator + "com"));
        FileUtils.deleteDir(new File(cCustomDir.getAbsolutePath() + File.separator + "com"));
    }

    public static void generateJava(File customDir, File autoDir, JavaResult result)
            throws Exception {
        Configuration configuration =
                FreeMarkerUtils.create(
                        GenerateUtils.class.getClassLoader(), "freemarker/message/server");
        Template template = configuration.getTemplate("auto_bean.ftl");
        for (Bean bean : result.beans) {
            generateJava(autoDir, bean.getPackage(), bean.getName(), bean, template, true);
        }

        template = configuration.getTemplate("auto_enum.ftl");
        for (Enum e : result.enums) {
            generateJava(autoDir, e.getPackage(), e.getName(), e, template, true);
        }

        template = configuration.getTemplate("auto_error.ftl");
        for (ErrorMessage error : result.errors) {
            generateJava(autoDir, error.getPackage(), error.getName(), error, template, true);
        }

        template = configuration.getTemplate("auto_message.ftl");
        for (Message message : result.messages) {
            generateJava(autoDir, message.getPackage(), message.getName(), message, template, true);
        }
        template = configuration.getTemplate("auto_message_group.ftl");
        for (MessageGroup mg : result.mgs) {
            generateJava(autoDir, mg.getPackage(), mg.getName(), mg, template, true);
        }

        template = configuration.getTemplate("auto_message_version.ftl");
        generateJava(
                autoDir,
                result.version.getPackage(),
                result.version.getName(),
                result.version,
                template,
                true);
        template = configuration.getTemplate("custom_handler.ftl");
        for (Handler handler : result.handlers) {
            generateJava(
                    customDir, handler.getPackage(), handler.getName(), handler, template, false);
        }

        template = configuration.getTemplate("custom_handler_group.ftl");
        for (HandlerGroup hg : result.hgs) {
            generateJava(customDir, hg.getPackage(), hg.getName(), hg, template, true);
        }
        template = configuration.getTemplate("custom_handler_guice_module.ftl");
        for (JavaGuiceModule jgm : result.jgms) {
            generateJava(customDir, jgm.getPackage(), jgm.getName(), jgm, template, true);
        }
    }

    static void generateJava(
            File dir, String pkg, String name, Object object, Template template, boolean rewrite)
            throws Exception {
        String pathPrefix = dir.getAbsolutePath() + File.separator + FileUtils.package2path(pkg);
        FreeMarkerUtils.gen(
                pathPrefix + File.separator + name + ".java", template, object, rewrite);
    }

    public static MessageParser parse(String pkg,String target_pkg, File idFile) throws IOException {
        List<OriginBean> obs = new ArrayList<>();
        List<OriginEnum> oes = new ArrayList<>();
        List<OriginMessage> oms = new ArrayList<>();
        IDGenerator idGenerator = new IDGenerator(idFile);
        ClassLoader classLoader = GenerateUtils.class.getClassLoader();

        for (java.lang.Class<?> clazz : ReflectUtils.allClass(classLoader,pkg)) {
            if (clazz.isAnnotationPresent(BeanClass.class)) {
                if (clazz.isEnum()) {
                    oes.add(new OriginEnum(clazz , target_pkg));
                } else {
                    obs.add(new OriginBean(clazz, target_pkg));
                }
            } else {
                if (clazz.isAnnotationPresent(MessageClass.class)) {
                    oms.add(new OriginMessage(clazz, idGenerator, target_pkg));
                } else {
                    throw new IOException(clazz + "没有注解");
                }
            }
        }

        idGenerator.save();
        return new MessageParser(target_pkg, obs, oes, oms);
    }
}
