package com.test.game.message_module.login;

import com.test.game.core.gen.MessageClass;
import com.test.game.core.gen.MessageField;
import com.test.game.core.net.message.Message;

/**
 * @Auther: zhouwenbin
 * @Date: 2019/8/18 21:16
 */
@MessageClass(from = Message.NodeType.CLIENT,
        to = Message.NodeType.SERVER,
        desc = "版本验证"
)
public class VersionCheck {

    static class Req{
        @MessageField(desc = "消息代码版本号")
        String messageCodeVersion;

        @MessageField(desc = "配置代码版本号")
        String configCodeVersion;

        @MessageField(desc = "验证token,本地登陆则为空")
        String token;
    }

    static class Res{
        @MessageField(desc = "如果存在,则用该配置")
        byte[] config;

        @MessageField(desc = "如果config为空,则根据该配置数据版本号去获取配置")
        int configDataVersion;
    }

    enum Fail{
        @MessageField(desc = "消息代码不一致")
        MESSAGE_CODE,
        @MessageField(desc = "配置代码不一致")
        CONFIG_CODE,
        @MessageField(desc = "配置数据不一致")
        CONFIG_DATA,
        @MessageField(desc = "验证失败")
        VERIFY,
    }

}
