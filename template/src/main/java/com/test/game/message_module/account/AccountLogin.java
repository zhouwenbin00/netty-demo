package com.test.game.message_module.account;

import com.test.game.core.gen.ClientMessage;
import com.test.game.core.gen.MessageField;

/** @Auther: zhouwenbin @Date: 2019/8/10 16:25 */
@ClientMessage(desc = "用户登录")
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
