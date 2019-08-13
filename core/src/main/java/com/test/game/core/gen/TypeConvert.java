package com.test.game.core.gen;

import java.lang.reflect.Type;

public enum TypeConvert {
    BOOLEAN(Boolean.TYPE, Boolean.class) {
        public String javaType(boolean list) {
            return list ? "java.util.List<Boolean>" : "boolean";
        }

        public String as3Type(boolean list) {
            return list ? "Vector.<Boolean>" : "Boolean";
        }
    },
    BYTE(Byte.TYPE, Byte.class) {
        public String javaType(boolean list) {
            return list ? "java.util.List<Byte>" : "byte";
        }

        public String as3Type(boolean list) {
            return list ? "Vector.<int>" : "int";
        }
    },
    SHORT(Short.TYPE, Short.class) {
        public String javaType(boolean list) {
            return list ? "java.util.List<Short>" : "short";
        }

        public String as3Type(boolean list) {
            return list ? "Vector.<int>" : "int";
        }
    },
    INT(Integer.TYPE, Integer.class) {
        public String javaType(boolean list) {
            return list ? "java.util.List<Integer>" : "int";
        }

        public String as3Type(boolean list) {
            return list ? "Vector.<int>" : "int";
        }
    },
    LONG(Long.TYPE, Long.class) {
        public String javaType(boolean list) {
            return list ? "java.util.List<Long>" : "long";
        }

        public String as3Type(boolean list) {
            return list ? "Vector.<long>" : "long";
        }
    },
    STRING(String.class) {
        public String javaType(boolean list) {
            return list ? "java.util.List<String>" : "String";
        }

        public String as3Type(boolean list) {
            return list ? "Vector.<String>" : "String";
        }
    },
    BYTE_ARRAY(byte[].class) {
        public String javaType(boolean list) {
            return list ? "java.util.List<byte[]>" : "byte[]";
        }

        public String as3Type(boolean list) {
            return list ? "Vector.<ByteArray>" : "ByteArray";
        }
    };

    private final Type[] types;

    private TypeConvert(Type... types) {
        this.types = types;
    }

    public static TypeConvert valueOf(Type type) {
        for (TypeConvert t : values()) {
            for (Type tt : t.types) {
                if (tt == type) {
                    return t;
                }
            }
        }

        return null;
    }

    public abstract String javaType(boolean var1);

    public abstract String as3Type(boolean var1);
}
