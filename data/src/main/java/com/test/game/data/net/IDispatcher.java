package com.test.game.data.net;


import com.test.game.core.net.message.Message;
import io.netty.channel.Channel;

/**
 * 处理器
 * @Auther: zhouwenbin
 * @Date: 2019/8/18 20:38
 */
public interface IDispatcher {

    void tick(Channel channel, long now);

    void onChannelActive(Channel channel);

    void onChannelInactive(Channel channel);

    void onChannelRead(Channel channel, Message message);

}
