package com.test.game.message_module.account;

import com.test.game.core.gen.MessageClass;
import com.test.game.core.gen.MessageField;
import com.test.game.core.net.message.Message;

/** @Auther: zhouwenbin @Date: 2019/8/10 16:25 */
@MessageClass(desc = "用户登录", from = Message.NodeType.SERVER, to = Message.NodeType.CLIENT)
public class AccountLogin {

    static class Req {
        @MessageField(desc = "id")
        int id;
    }

    static class Res {
        @MessageField(desc = "用户对象")
        AccountBean accountBean;
    }

    enum Fail {
        @MessageField(desc = "登录失败")
        ERROR_LOGIN,
        ;
    }
}
