<#include "head.ftl">
package ${package};

<@import javaImports/>
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class ${name} extends AbstractModule {
  @Override
  protected void configure() {
<#list handlers as handler>
    bind(${handler.package}.${handler.name}.class).in(Singleton.class);
</#list>
  }
}
