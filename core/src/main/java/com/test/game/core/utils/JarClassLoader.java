package com.test.game.core.utils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/** jar包加载器 @Auther: zhouwenbin @Date: 2019/8/16 16:44 */
public class JarClassLoader extends ClassLoader {
    private String path;
    private final Map<String, byte[]> datas = new HashMap<>();
    private final Map<String, Class> classes = new HashMap<>();

    public JarClassLoader(String jarFile) throws IOException {
        this.loadJar(jarFile);
        for (String s : this.datas.keySet()) {
            if (s.endsWith(".class")) {
                String name = s.substring(0, s.length() - 6).replaceAll("/", ".");
                if (!this.classes.containsKey(name)) {
                    this.defineClass(name);
                }
            }
        }
    }

    /**
     * 加载jar
     *
     * @param jarFile
     * @throws IOException
     */
    private void loadJar(String jarFile) throws IOException {
        FileInputStream fis = null;

        try {
            File file = new File(jarFile);
            this.path = file.getAbsolutePath();
            fis = new FileInputStream(file);
            this.loadJar(fis);
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
    }

    private void loadJar(InputStream fis) throws IOException {
        BufferedInputStream bis = null;
        JarInputStream jis = null;
        try {
            bis = new BufferedInputStream(fis);
            jis = new JarInputStream(bis);
            JarEntry jarEntry;
            while (true) {
                do {
                    do {
                        if ((jarEntry = jis.getNextJarEntry()) == null) {
                            return;
                        }
                    } while (jarEntry.isDirectory());
                } while (this.datas.containsKey(jarEntry.getName()));
                byte[] b = new byte[2028];
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                int len;
                while ((len = jis.read(b)) > 0) {
                    out.write(b, 0, len);
                }
                this.datas.put(jarEntry.getName(), out.toByteArray());
                out.close();
            }
        } finally {
            if (jis != null) {
                jis.close();
            }
            if (bis != null) {
                bis.close();
            }
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class clazz = this.classes.get(name);
        if (clazz == null) {
            clazz = this.defineClass(name);
        }
        return clazz == null ? super.findClass(name) : clazz;
    }

    private Class defineClass(String name) {
        String rn = String.format("%s.class", name.replaceAll("\\.", "/"));
        byte[] bytes = this.datas.get(rn);
        if (bytes == null) {
            return null;
        } else {
            Class<?> clazz = this.defineClass(name, bytes, 0, bytes.length);
            if (clazz.getPackage() == null) {
                int lastDotIndex = name.lastIndexOf(46);
                String packageName = lastDotIndex >= 0 ? name.substring(0, lastDotIndex) : "";
                this.definePackage(packageName, null, null, null, null, null, null, null);
            }
            this.classes.put(name, clazz);
            return clazz;
        }
    }

    @Override
    protected URL findResource(String name) {
        try {

            return new URL(StringUtils.format("jar:file {@} !/ {@}", this.path, name));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
