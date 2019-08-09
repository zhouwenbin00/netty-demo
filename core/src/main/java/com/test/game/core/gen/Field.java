package com.test.game.core.gen;

import lombok.Data;

/** 属性 */
@Data
public class Field {
    public final String name;
    public final String desc;
    public final String asType;
    public final String javaType;
    public final BelongType belong;
    public int col = -1;

    public Field(
            String fieldName, String asType, String javaType, String fieldDesc, BelongType belong) {
        this.name = fieldName;
        this.asType = asType;
        this.javaType = javaType;
        this.desc = fieldDesc;
        this.belong = belong;
    }

    public boolean isBasic() {
        return JavaType.create(this.javaType) != null;
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

            sb.append(this.name).append(this.belong.toString());
        }
    }
    // ===================set/get======================
    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getAsType() {
        return asType;
    }

    public String getJavaType() {
        return javaType;
    }

    public BelongType getBelong() {
        return belong;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}
