package com.test.game.core.utils;

import com.test.game.core.net.message.MessageFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicInteger;

/** @Auther: zhouwenbin @Date: 2019/8/5 10:45 */
public abstract class NetUtils {
    private static final Logger log = LoggerFactory.getLogger(NetUtils.class);
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
    public static String host(Channel channel) {
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
