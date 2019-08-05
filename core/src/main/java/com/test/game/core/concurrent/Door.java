package com.test.game.core.concurrent;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ref.WeakReference;

/** @Auther: zhouwenbin @Date: 2019/8/5 21:14 */
public class Door<T extends IUser> {
    private static final Logger logger = LoggerFactory.getLogger(Door.class);
    private transient volatile WeakReference<T> ref;

    public boolean isOpenForMe(T user) {
        Preconditions.checkArgument(user.thread() == Thread.currentThread());
        return user.thread() == Thread.currentThread();
    }

    public T getUser() {
        WeakReference<T> tmp = this.ref;
        if (tmp == null) {
            return null;
        }
        return tmp.get();
    }

    public synchronized boolean close(T user){
        T old = getUser();
        Preconditions.checkArgument(old == user, "close error,old:%s,yours:%s", old, user);
        if (this.ref != null) {
            this.ref.clear();
        }
        this.ref = null;
        return true;
    }
}
