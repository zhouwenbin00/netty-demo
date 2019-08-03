package com.test.game.core.utils;

import java.time.Clock;

/** @Auther: zhouwenbin @Date: 2019/8/3 16:30 */
public abstract class GameClock {

    private static Clock clock = Clock.systemDefaultZone();

    private GameClock() {}

    public static long millis()  {
        return clock.millis();
    }
}
