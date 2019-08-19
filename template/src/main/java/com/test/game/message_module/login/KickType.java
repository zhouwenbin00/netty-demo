package com.test.game.message_module.login;


import com.test.game.core.gen.BeanClass;
import com.test.game.core.gen.MessageField;

/**
 * @Auther: zhouwenbin
 * @Date: 2019/8/19 10:14
 */
@BeanClass(desc = "踢人类型")
public enum  KickType {
    @MessageField(desc = "版本不一致")
    VERSION_ILLEGAL,
    @MessageField(desc = "验证未通过")
    NOT_VERIFY,
    @MessageField(desc = "多次发送登录消息")
    LOGIN_MULTI_TIMES,
    @MessageField(desc = "顶号")
    REPLACE,
    @MessageField(desc = "流程错误")
    FLOW,
    @MessageField(desc = "非法消息")
    ILLEGAL_MSG,
}
