package com.test.game.core.concurrent;

import io.netty.channel.Channel;

import java.lang.ref.WeakReference;
import java.util.concurrent.Executor;

/** @Auther: zhouwenbin @Date: 2019/8/5 20:45 */
public class AbstractNetUser<T> implements IUser {
    private final WeakReference<Channel> channel;
    private final Thread thread;
    private T user;

    public AbstractNetUser(Channel channel, Thread thread) {
        this.channel = new WeakReference<>(channel);
        this.thread = thread;
    }

    @Override
    public Thread thread() {
        return thread;
    }

    @Override
    public Executor executor() {
        Channel c = getChannel();
        if (c == null) {
            return null;
        }
        return c.eventLoop();
    }

    public Channel getChannel() {
        return channel.get();
    }

    public Thread getThread() {
        return thread;
    }

    public T getUser() {
        return user;
    }

    public void setUser(T user) {
        this.user = user;
    }
}
