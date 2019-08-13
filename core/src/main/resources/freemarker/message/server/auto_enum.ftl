package ${package};

/** ${desc} */
public enum ${name} {
<#list fields as field>
    /** ${field.desc} */
    ${field.name}(${field.value}),
</#list>
    ;
    private final int value;

    ${name}(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static ${name} valueOf(int value) {
        switch (value) {
<#list fields as field>
            case ${field.value}:
            /** ${field.desc} */
            return ${field.name};
</#list>
        }
        return null;
    }
}