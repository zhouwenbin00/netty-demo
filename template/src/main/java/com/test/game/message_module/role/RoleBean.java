package com.test.game.message_module.role;

import com.test.game.core.gen.BeanClass;
import com.test.game.core.gen.MessageField;

/**
 * @Auther: zhouwenbin
 * @Date: 2019/8/10 16:39
 */
@BeanClass(desc = "用户Bean")
public class RoleBean {

    @MessageField(desc = "id")
    int id;
}
