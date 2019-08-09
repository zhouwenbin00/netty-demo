package com.test.game.core.gen;

import lombok.Data;

import java.util.List;

/** 类 */
@Data
public class Class {
    public final String name;
    public final String desc;
    public final List<Field> fields;
    public final String fileName;
    public final BelongType belong;

    public Class(String className, String classDesc, List<Field> fields, String fileName) {
        this.name = className;
        this.desc = classDesc;
        this.fields = fields;
        this.fileName = fileName;
        this.belong = belong(this.fields);
    }

    private BelongType belong(List<Field> fields) {
        BelongType tmp = BelongType.NULL;
        for (Field field : fields) {
            tmp = tmp.add(field.belong);
        }
        return tmp;
    }

    /**
     * 生成md5
     *
     * @param sb
     */
    public void buildClientMD5(StringBuilder sb) {
        if (this.belong.isClient()) {
            sb.append(this.name).append(this.belong.toString());

            for (Field field : fields) {
                field.buildClientMD5(sb);
            }
        }
    }
}
