package com.test.game.core.gen;

import com.test.game.core.gen.NameAndDesc;

import java.util.Set;

public abstract class AbstractField {
    private final NameAndDesc nameAndDesc;

    public AbstractField(NameAndDesc nameAndDesc) {
        this.nameAndDesc = nameAndDesc;
    }

    public String getName() {
        return this.nameAndDesc.name;
    }

    public String getDesc() {
        return this.nameAndDesc.desc;
    }

    public abstract void buildImport(Set<String> var1, Set<String> var2);
}
