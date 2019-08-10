package com.test.game.message_module.account;

import com.test.game.core.gen.BeanClass;
import com.test.game.core.gen.MessageField;

/**
 * @Auther: zhouwenbin
 * @Date: 2019/8/10 16:24
 */
@BeanClass(desc = "用户bean")
public class AccountBean {
    @MessageField(desc = "id")
    int id;
    @MessageField(desc = "等级")
    int level;
}
