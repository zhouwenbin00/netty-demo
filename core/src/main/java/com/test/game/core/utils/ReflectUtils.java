package com.test.game.core.utils;

import com.google.common.base.Preconditions;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.reflect.ClassPath;
import com.test.game.core.gen.MessageField;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/** @Auther: zhouwenbin @Date: 2019/8/10 16:56 */
public abstract class ReflectUtils {
    private ReflectUtils() {
        super();
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
}
