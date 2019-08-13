package com.test.game.core.utils;

import com.google.common.base.Preconditions;
import com.google.common.reflect.ClassPath;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;

/** @Auther: zhouwenbin @Date: 2019/8/10 16:56 */
public abstract class ReflectUtils {
    private ReflectUtils() {
        super();
    }

    public static List<Class<?>> allClass0(String packageName) {
        List<Class<?>> clazzs = new ArrayList<>();
        // 是否循环搜索子包
        boolean recursive = true;
        // 包名对应的路径名称
        String packageDirName = packageName.replace('.', '/');
        Enumeration<URL> dirs;

        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            while (dirs.hasMoreElements()) {

                URL url = dirs.nextElement();
                String protocol = url.getProtocol();

                if ("file".equals(protocol)) {
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    findClassInPackageByFile(packageName, filePath, recursive, clazzs);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return clazzs;
    }

    /**
     * 在package对应的路径下找到所有的class
     */
    public static void findClassInPackageByFile(String packageName, String filePath, final boolean recursive,
                                                List<Class<?>> clazzs) {
        File dir = new File(filePath);
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        // 在给定的目录下找到所有的文件，并且进行条件过滤
        File[] dirFiles = dir.listFiles(new FileFilter() {

            public boolean accept(File file) {
                boolean acceptDir = recursive && file.isDirectory();// 接受dir目录
                boolean acceptClass = file.getName().endsWith("class");// 接受class文件
                return acceptDir || acceptClass;
            }
        });

        for (File file : dirFiles) {
            if (file.isDirectory()) {
                findClassInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, clazzs);
            } else {
                String className = file.getName().substring(0, file.getName().length() - 6);
                try {
                    clazzs.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + "." + className));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Set<Class<?>> allClass(ClassLoader classLoader, String pkg) throws IOException {
        Set<Class<?>> classSet = new HashSet<>();

        for (ClassPath.ClassInfo info :
                ClassPath.from(classLoader).getTopLevelClassesRecursive(pkg)) {
            Class<?> clas = info.load();
            classSet.add(clas);
        }

        return classSet;
    }

    public static <T extends Annotation> T annotation(Field field, Class<T> annotationClass) {
        T annotation = field.getAnnotation(annotationClass);
        Preconditions.checkNotNull(
                annotation, "%s必须要有注解%s", field.getName(), annotationClass.getSimpleName());
        return annotation;
    }

    public static <T extends Annotation> T annotation(Class<?> clazz, Class<T> annotationClass) {
        T annotation = clazz.getAnnotation(annotationClass);
        Preconditions.checkNotNull(
                annotation, "%s必须要有注解%s", clazz.getName(), annotationClass.getSimpleName());
        return annotation;
    }

    public static boolean isStatic(Field field) {
        return Modifier.isStatic(field.getModifiers());
    }

    public static boolean isFinal(Field field) {
        return Modifier.isFinal(field.getModifiers());
    }

    public static boolean isPrivate(Field field) {
        return Modifier.isPrivate(field.getModifiers());
    }

    private static boolean isStatic(Class<?> clazz) {
        return Modifier.isStatic(clazz.getModifiers());
    }

    public static Class<?> staticInsideClass(Class<?> clazz, String name) {

        for (Class<?> tmp : clazz.getDeclaredClasses()) {
            if (isStatic(tmp) && tmp.getSimpleName().equals(name)) {
                return tmp;
            }
        }
        return null;
    }

    public static Type getActualTypeArgument(ParameterizedType type, int index) {
        return type.getActualTypeArguments()[index];
    }

    public static boolean isEnum(Type type) {
        return type instanceof Class && ((Class)type).isEnum();
    }
}
