package com.test.game.core.net;

import com.test.game.core.base.Factory;
import com.test.game.core.net.message.Message;
import com.test.game.core.server.hander.MessageHandler;
import com.test.game.core.utils.NetUtils;
import com.test.game.core.utils.Num;
import com.test.game.core.utils.SessionUtils;
import com.test.game.data.DataCenter;
import com.test.game.data.account.Account;
import com.test.game.data.role.Role;
import com.test.game.data.user.ClientToServerUser;
import com.test.game.logic.Main;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicInteger;

/** @Auther: zhouwenbin @Date: 2019/8/5 20:07 */
public class Dispatcher extends MessageHandler {

    private static final Logger log = LoggerFactory.getLogger(Dispatcher.class);
    private DataCenter dataCenter = DataCenter.getInstance();
    private final int MAX_IP_COUNT = 6;

    public Dispatcher(String name, @Nullable Factory<Governor> governorFactory) {
        super(name, governorFactory);
    }

    @Override
    protected void onChannelActive(Channel channel) {
        ipAdd(channel);
        SessionUtils.addLinkCount();
        if (dataCenter.serverClosed) {
            NetUtils.close(channel, "server is closed");
            return;
        }
        if (SessionUtils.count() > Main.MAX_SESSION) {
            NetUtils.close(channel, "连接数达到上限");
            return;
        }
        ClientToServerUser netUser = new ClientToServerUser(channel, Thread.currentThread());
        channel.attr(ClientToServerUser.KEY).set(netUser);
    }

    @Override
    protected void onChannelRead(Channel channel, Message msg) {}

    @Override
    protected void onChannelInactive(Channel channel) {
        log.info("[1] channel: {} 断开", channel);
        ipSub(channel);
        SessionUtils.reduceCount();
        ClientToServerUser netUser = channel.attr(ClientToServerUser.KEY).getAndRemove();
        if (netUser == null) {
            log.info("[2] channel : {} 找不到 user", channel);
            return;
        }
        Account account = netUser.getUser();
        if (account == null) {
            log.info("[3] user找不到账号 : {}", channel);
            return;
        }
        if (!account.isOpenForMe(netUser)) {
            log.info("[4] user 与账号：{}不匹配 ，可能为顶号", account.getName());
            return;
        }
        Role role = account.getRole();
        if (role == null) {
            log.info("[6]没有创建角色", account.getName());
            return;
        }
        account.close(netUser);
    }

    private void ipSub(Channel channel) {
        AtomicInteger count = dataCenter.ip2count.get(NetUtils.host(channel));
        if (count != null) {
            count.decrementAndGet();
        } else {
            log.error("??!!{}", channel, new NullPointerException());
        }
    }

    private void ipAdd(Channel channel) {
        String ip = NetUtils.host(channel);
        dataCenter.ip2count.putIfAbsent(ip, new AtomicInteger(Num.ZERO));
        int count = dataCenter.ip2count.get(ip).incrementAndGet();
        if (count > MAX_IP_COUNT) {
            log.warn("too many user in ip : {}", ip);
        }
    }
}
