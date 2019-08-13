package com.test.game.core.gen;

import com.test.game.core.utils.CharSetUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/** @Auther: zhouwenbin @Date: 2019/8/10 16:54 */
public class IDGenerator {

    private final File file;
    private Map<Integer, String> id2name = new HashMap<>();
    private Map<String, Integer> name2id = new HashMap<>();
    private int tmp = 0;

    public IDGenerator(File idFile) {
        this.file = idFile;
    }

    public int id(java.lang.Class<?> clazz) {
        return clazz == null ? 0 : this.id(clazz.getName());
    }

    private int id(String name) {
        Integer id = this.name2id.get(name);
        if (id != null) {
            return id;
        } else {
            for (; ; ++this.tmp) {
                if (!this.id2name.containsKey(this.tmp)) {
                    id = this.tmp;
                    this.add(id, name);
                    return id;
                }
            }
        }
    }

    private void add(int id, String name) {
        this.id2name.put(id, name);
        this.name2id.put(name, id);
    }

    public void save() throws IOException {
        FileOutputStream fos = new FileOutputStream(this.file);

        try {

            for (Map.Entry<Integer, String> entry : this.id2name.entrySet()) {
                fos.write(
                        (entry.getKey() + "," + entry.getValue() + "\n")
                                .getBytes(CharSetUtils.UTF8));
            }
        } finally {
            fos.close();
        }
    }
}
