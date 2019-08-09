package ${package};

import io.netty.buffer.ByteBuf;

/** Created by FreeMarker. ${class.desc} */
public class ${class.name?cap_first}Group extends ${class.name?cap_first}.Group {
    public ${class.name?cap_first}Group(ByteBuf buf) {
        super(buf);
        // TODO 自己写初始化代码
    }

    public void check(ConfigGroup group) {
        // TODO 检测配置
    }
}
