package com.test.game.core.utils;

import com.test.game.data.DataCenter;
import com.test.game.data.account.AccountDataCenter;

import java.util.concurrent.atomic.AtomicInteger;

/** @Auther: zhouwenbin @Date: 2019/8/5 20:27 */
public abstract class SessionUtils {
    private static final AccountDataCenter accountDataCenter =
            DataCenter.getInstance().accountDataCenter;
    private static AtomicInteger onlineCount = accountDataCenter.getOnlineCount();

    private SessionUtils() {}

    /** 增加链接数量 */
    public static void addLinkCount() {
        onlineCount.incrementAndGet();
    }

    /**
     * 用户总数
     *
     * @return
     */
    public static int count() {
        return accountDataCenter.size();
    }

    public static void reduceCount() {
        onlineCount.decrementAndGet();
    }
}
