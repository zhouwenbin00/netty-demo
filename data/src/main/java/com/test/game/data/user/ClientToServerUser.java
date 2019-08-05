package com.test.game.data.user;

import com.test.game.core.concurrent.AbstractNetUser;
import com.test.game.core.utils.NetUtils;
import com.test.game.data.account.Account;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

import java.util.concurrent.Executor;

/** @Auther: zhouwenbin @Date: 2019/8/5 20:42 */
public class ClientToServerUser extends AbstractNetUser<Account> {
    public static final AttributeKey<ClientToServerUser> KEY =
            NetUtils.createAttributeKey(ClientToServerUser.class);

    @Override
    public Thread thread() {
        return null;
    }

    @Override
    public Executor executor() {
        return null;
    }

    public ClientToServerUser(Channel channel, Thread thread) {
        super(channel, thread);
    }
}
