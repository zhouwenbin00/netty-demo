package com.test.game.core.gen;

import java.util.List;

public class Enum {
    private final NameAndDesc nameAndDesc;
    private final String pkg;
    private final List<EnumField> fields;

    public Enum(NameAndDesc nameAndDesc, String pkg, List<EnumField> fields) {
        this.nameAndDesc = nameAndDesc;
        this.pkg = pkg;
        this.fields = fields;
    }

    public String getPackage() {
        return this.pkg;
    }

    public String getDesc() {
        return this.nameAndDesc.desc;
    }

    public String getName() {
        return this.nameAndDesc.name;
    }

    public List<EnumField> getFields() {
        return this.fields;
    }
}
