package com.test.game.core.utils;

import com.sun.xml.internal.stream.writers.UTF8OutputStreamWriter;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.Template;
import freemarker.template.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;

/** FreeMarker模板工具类 @Auther: zhouwenbin @Date: 2019/8/9 19:45 */
public abstract class FreeMarkerUtils {
    public static final Logger log = LoggerFactory.getLogger(FreeMarkerUtils.class);
    private FreeMarkerUtils() {}

    public static Configuration create(ClassLoader classLoader, String dir) {
        Version version = new Version(2, 3, 23);
        Configuration configuration = new Configuration(version);
        configuration.setDefaultEncoding("UTF-8");
        configuration.setObjectWrapper((new DefaultObjectWrapperBuilder(version)).build());
        configuration.setClassLoaderForTemplateLoading(classLoader, dir);
        return configuration;
    }

    public static void gen(String path, Template template, Object object, boolean rewrite)
            throws Exception {
        File file = new File(path);
        if (file.exists()) {
            if (!rewrite) {
                return;
            }
            if (!file.delete()) {
                return;
            }
        }
        if (!file.getParentFile().exists()) {
            FileUtils.mkDir(file.getParentFile());
            log.info("生成文件 : {}", file.getParentFile().getAbsolutePath() );
        }
        if (file.createNewFile()) {
            FileOutputStream fos = new FileOutputStream(file);

            try {
                UTF8OutputStreamWriter writer = new UTF8OutputStreamWriter(fos);

                try {
                    template.process(object, writer);
                } finally {
                    writer.close();
                }
            } finally {
                fos.close();
            }
            log.info("生成文件 : {}", path );
        }
    }
}
