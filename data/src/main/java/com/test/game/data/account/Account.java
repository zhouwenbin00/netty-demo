package com.test.game.data.account;

import com.test.game.core.concurrent.Door;
import com.test.game.core.utils.NetUtils;
import com.test.game.data.role.Role;
import com.test.game.data.user.ClientToServerUser;

/**
 * @Auther: zhouwenbin
 * @Date: 2019/8/5 20:34
 */
public class Account extends Door<ClientToServerUser> {
    private final String name; // 账号
    private Role role;

    public Account(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public synchronized boolean close(ClientToServerUser user) {
        boolean close = super.close(user);
//        NetUtils.closeAndWrite(user.getChannel(), ResAccountLoginFailMessage.REPLACE, "顶号");
        NetUtils.close(user.getChannel(),"顶号");
        return close;
    }

}
