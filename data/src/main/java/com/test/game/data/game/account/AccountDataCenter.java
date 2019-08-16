package com.test.game.data.game.account;

import com.test.game.core.utils.GameClock;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/** @Auther: zhouwenbin @Date: 2019/8/5 20:26 */
public class AccountDataCenter {
    // 在线玩家数量
    private AtomicInteger onlineCount = new AtomicInteger(0);

    private Map<Integer, Map<Long, Data>> map2 = new ConcurrentHashMap<>();

    public AtomicInteger getOnlineCount() {
        return onlineCount;
    }

    public int size() {
        int count = 0;

        for (Map<Long, Data> map : map2.values()) {
            count += map.size();
        }
        return count;
    }

    public static class Data {
        private Account account;
        private volatile long lastUseTime;

        public Data(Account account) {
            this.account = account;
            this.lastUseTime = GameClock.millis();
        }

        public Account getAccount() {
            this.lastUseTime = GameClock.millis();
            return account;
        }

        public Account justGetAccount() {
            return account;
        }

        public long getLastUseTime() {
            return lastUseTime;
        }
    }
}
