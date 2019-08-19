package com.test.game.data.net;

import com.test.game.core.net.message.Message;
import com.test.game.core.utils.NetUtils;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Auther: zhouwenbin
 * @Date: 2019/8/18 20:42
 */
public class CommonChannel {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonChannel.class);

    private final Channel channel;

    public CommonChannel(Channel channel) {
        this.channel = channel;
    }

    public Channel getChannel() {
        return channel;
    }

    public boolean write(Object message){
        if (!channel.isActive()){
            return false;
        }
        NetUtils.write(channel, message);
        return true;
    }

    public boolean closeAndWrite(Message message, String because){
        return closeAndWrite(Message.encodeToBytes(message), because);
    }

    public boolean closeAndWrite(byte[] message, String because){
        if (!channel.isActive()){
            return false;
        }
        NetUtils.closeAndWrite(channel, message, because);
        return true;
    }

    public void close(String because){
        NetUtils.close(channel, because);
    }
}
