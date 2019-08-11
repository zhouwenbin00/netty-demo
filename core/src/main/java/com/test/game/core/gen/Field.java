package com.test.game.core.gen;

import lombok.Data;

/** 属性 */
@Data
public class Field {
    public final String fieldName;
    public final String fieldDesc;
    public final String asType;
    public final String javaType;
    public final BelongType belong;
    public int col = -1;

    public Field(
            String fieldName, String asType, String javaType, String fieldDesc, BelongType belong) {
        this.fieldName = fieldName;
        this.asType = asType;
        this.javaType = javaType;
        this.fieldDesc = fieldDesc;
        this.belong = belong;
    }

    /**
     * 生成md5
     *
     * @param sb
     */
    public void buildClientMD5(StringBuilder sb) {
        if (this.belong.isClient()) {
            if (this.javaType != null) {
                sb.append(this.javaType);
            }

            if (this.asType != null) {
                sb.append(this.asType);
            }

            sb.append(this.fieldName).append(this.belong.toString());
        }
    }
}
