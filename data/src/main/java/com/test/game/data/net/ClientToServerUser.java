package com.test.game.data.net;

import com.test.game.core.concurrent.IUser;
import com.test.game.core.utils.NetUtils;
import com.test.game.data.game.user.User;
import com.test.game.data.message.login.KickType;
import com.test.game.data.message.login.ReqKickMessage;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executor;

/**
 * @Auther: zhouwenbin
 * @Date: 2019/8/18 20:41
 */
public class ClientToServerUser extends CommonChannel implements IUser {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientToServerUser.class);

    public static final AttributeKey<ClientToServerUser> KEY = NetUtils.createAttributeKey(ClientToServerUser.class);

    private final Thread thread;
    /* 验证*/
    private boolean verified = false;
    /* 绑定的User对象*/
    private User user;

    public ClientToServerUser(Channel channel, Thread thread) {
        super(channel);
        this.thread = thread;
    }

    public void disconnect(KickType type, String because) {
        closeAndWrite(new ReqKickMessage(type), because);
    }

    @Override
    public Thread thread() {
        return this.thread;
    }

    @Override
    public Executor executor() {
        return getChannel().eventLoop();
    }

    //===============set/get=======================
    public User getUser() {
        return user;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}
