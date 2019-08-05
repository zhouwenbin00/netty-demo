package com.test.game.core.net;

import com.test.game.core.utils.GameClock;

/** 限速器 @Auther: zhouwenbin @Date: 2019/8/5 10:42 */
public class Governor {

    // 最大次数
    private final int maxCount;
    // 延迟
    private final int interval;
    // 上次时间
    private long lastTime = 0;
    // 次数
    private int count = 0;

    public Governor(int maxCount, int interval) {
        this.maxCount = maxCount;
        this.interval = interval;
    }

    public boolean overSpeed() {
        long now = GameClock.millis();
        if ((now - lastTime) > interval) {
            lastTime = now;
            count = 0;
        }
        ++count;
        if (count > maxCount) {
            return true;
        }
        return false;
    }

    // 同步限速器
    public static class ConcurrentGovernor extends Governor {

        public ConcurrentGovernor(int maxCount, int interval) {
            super(maxCount, interval);
        }

        @Override
        public boolean overSpeed() {
            synchronized (this) {
                return super.overSpeed();
            }
        }
    }
}
