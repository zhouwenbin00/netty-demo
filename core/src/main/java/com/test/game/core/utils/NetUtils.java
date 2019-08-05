package com.test.game.core.utils;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicInteger;

/** @Auther: zhouwenbin @Date: 2019/8/5 10:45 */
@Slf4j
public abstract class NetUtils {

    public static final AttributeKey<AtomicInteger> WRITE_FAIL_TIMES =
            AttributeKey.newInstance("WRITE_FAIL_TIMES");

    private NetUtils() {}

    public static <T> AttributeKey<T> createAttributeKey(Class<T> clazz) {
        return AttributeKey.valueOf(clazz, String.valueOf(GameClock.seconds()));
    }

    public static String host(ChannelHandlerContext context) {
        return host(context.channel());
    }

    /**
     * 获取host地址
     *
     * @param channel
     * @return
     */
    private static String host(Channel channel) {
        try {
            InetSocketAddress remoteAddress = (InetSocketAddress) channel.remoteAddress();
            return remoteAddress.getHostString();
        } catch (Exception e) {
            return "unknown";
        }
    }

    public static void close(Channel channel, String because) {
        log.info("close {} , because {}", channel, because);
        channel.close();
    }
}
