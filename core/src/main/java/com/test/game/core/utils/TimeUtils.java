package com.test.game.core.utils;

import java.time.Clock;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Locale;

/** @Auther: zhouwenbin @Date: 2019/8/11 21:13 */
public abstract class TimeUtils {

    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static Clock clock = Clock.systemDefaultZone();
    private static final WeekFields weekFields = WeekFields.of(Locale.getDefault());

    private TimeUtils() {
    }

    public static long millis() {
        return clock.millis();
    }

    public static int seconds() {
        return (int) (millis() / 1000L);
    }
}
