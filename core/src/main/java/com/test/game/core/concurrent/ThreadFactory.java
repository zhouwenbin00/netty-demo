package com.test.game.core.concurrent;

import io.netty.util.concurrent.FastThreadLocalThread;

import java.util.concurrent.atomic.AtomicInteger;

/** @Auther: zhouwenbin @Date: 2019/8/5 16:14 */
public class ThreadFactory implements java.util.concurrent.ThreadFactory {

    private final String name;
    private final int priority;
    private final AtomicInteger index;

    public ThreadFactory(String name, int priority) {
        this.name = name;
        this.priority = priority;
        this.index = new AtomicInteger();
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new FastThreadLocalThread(r, name + "-" + index.getAndIncrement());
        thread.setPriority(priority);
        return thread;
    }
}
