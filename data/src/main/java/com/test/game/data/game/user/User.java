package com.test.game.data.game.user;

import com.test.game.core.concurrent.Door;
import com.test.game.data.net.ClientToServerUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Auther: zhouwenbin
 * @Date: 2019/8/18 20:50
 */
public class User extends Door<ClientToServerUser> {
    private static final Logger LOGGER = LoggerFactory.getLogger(User.class);

    private boolean online;

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}
