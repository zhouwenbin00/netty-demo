package com.test.game.data.account;

import com.test.game.core.concurrent.Door;
import com.test.game.core.utils.GameClock;
import com.test.game.core.utils.NetUtils;
import com.test.game.data.role.Role;
import com.test.game.data.user.ClientToServerUser;

/** @Auther: zhouwenbin @Date: 2019/8/5 20:34 */
public class Account extends Door<ClientToServerUser> {
    // 账号
    private final String name;
    private Role role;
    // 是否在线
    private boolean online;
    // 上次登录时间
    private long lastloginTime;
    // 上次登出时间
    private long lastlogoutTime;

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

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
        if (!online) {
            lastlogoutTime = GameClock.millis();
        } else {
            this.lastloginTime = GameClock.millis();
        }
    }

    public long getLastloginTime() {
        return lastloginTime;
    }

    public void setLastloginTime(long lastloginTime) {
        this.lastloginTime = lastloginTime;
    }

    public long getLastlogoutTime() {
        return lastlogoutTime;
    }

    public void setLastlogoutTime(long lastlogoutTime) {
        this.lastlogoutTime = lastlogoutTime;
    }

    @Override
    public synchronized boolean close(ClientToServerUser user) {
        boolean close = super.close(user);
        //        NetUtils.closeAndWrite(user.getChannel(), ResAccountLoginFailMessage.REPLACE,
        // "顶号");
        NetUtils.close(user.getChannel(), "顶号");
        return close;
    }
}
