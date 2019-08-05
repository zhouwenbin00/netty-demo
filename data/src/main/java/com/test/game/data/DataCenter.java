package com.test.game.data;

import com.test.game.data.account.AccountDataCenter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/** @Auther: zhouwenbin @Date: 2019/8/5 20:19 */
public class DataCenter {

    private static final DataCenter instance = new DataCenter();

    private DataCenter() {}

    public static DataCenter getInstance() {
        return instance;
    }

    // 统计同ip登陆玩家数量
    public final ConcurrentHashMap<String, AtomicInteger> ip2count = new ConcurrentHashMap<>();
    // 账号数据
    public final AccountDataCenter accountDataCenter = new AccountDataCenter();
    // 服务器是否关闭
    public boolean serverClosed = false;
}
