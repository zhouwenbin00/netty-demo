<#include "head.ftl">
package ${package};

<@import javaImports/>
import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class ${name} {
    public final ImmutableMap<Integer, ${javaParent}> handlers;

    @Inject
    public ${name}(Injector injector) {
        ImmutableMap.Builder<Integer, ${javaParent}> builder = ImmutableMap.builder();
    <#list handlers as handler>
        builder.put(${handler.id}, injector.getInstance(${handler.package}.${handler.name}.class));
    </#list>
        this.handlers = builder.build();
    }

    public ${javaParent} handler(int id) {
        return handlers.get(id);
    }
}
