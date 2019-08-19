package com.test.game.data.net;

import com.test.game.core.base.Factory;
import com.test.game.core.net.Governor;
import com.test.game.core.net.message.Message;
import com.test.game.core.server.hander.MessageHandler;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;

/**
 * @Auther: zhouwenbin
 * @Date: 2019/8/19 10:26
 */
public abstract class Dispatcher extends MessageHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(Dispatcher.class);

    public Dispatcher(String name, @Nullable Factory<Governor> governorFactory) {
        super(name, governorFactory);
    }

    @Override
    protected void onChannelActive(Channel channel) {
        IDispatcher script = script();
        if (script!=null){
            script.onChannelActive(channel);
        }
    }

    @Override
    protected void onChannelRead(Channel channel, Message msg) {
        IDispatcher script = script();
        if (script!=null){
            script.onChannelRead(channel, msg);
        }
    }

    @Override
    protected void onChannelInactive(Channel channel) {
        IDispatcher script = script();
        if (script!=null){
            script.onChannelInactive(channel);
        }
    }

    @Override
    public void tick(Channel channel,long time) {
        IDispatcher script = script();
        if (script!=null){
            script.tick(channel, time);
        }
    }

    protected abstract IDispatcher script();
}
