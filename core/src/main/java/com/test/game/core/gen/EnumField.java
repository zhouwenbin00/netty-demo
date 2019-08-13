package com.test.game.core.gen;

import java.util.Set;

public class EnumField extends AbstractField {
    private final int value;

    public EnumField(NameAndDesc nameAndDesc, int value) {
        super(nameAndDesc);
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public void buildImport(Set<String> ji, Set<String> ai) {}
}
