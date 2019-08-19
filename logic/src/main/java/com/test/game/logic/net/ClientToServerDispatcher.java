package com.test.game.logic.net;

import com.google.inject.Inject;
import com.test.game.core.net.message.Message;
import com.test.game.core.utils.GameClock;
import com.test.game.core.utils.NetUtils;
import com.test.game.data.game.user.User;
import com.test.game.data.game.user.UserManager;
import com.test.game.data.message.login.KickType;
import com.test.game.data.message.login.ReqVersionCheckMessage;
import com.test.game.data.net.ClientToServerUser;
import com.test.game.data.net.IDispatcher;
import com.test.game.logic.group.ClientToServerHandlerGroup;
import com.test.game.logic.message.handler.ClientToServerHandler;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 客户端向服务端的消息处理器
 * @Auther: zhouwenbin
 * @Date: 2019/8/18 20:39
 */
public class ClientToServerDispatcher implements IDispatcher {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientToServerDispatcher.class);

    @Inject
    UserManager userManager;
    @Inject
    ClientToServerHandlerGroup handlerGroup;

    @Override
    public void tick(Channel channel, long now) {
        ClientToServerUser netUser = channel.attr(ClientToServerUser.KEY).get();
        if (netUser == null){
            LOGGER.info("[1]:{} 的netUser是空的", NetUtils.host(channel));
            return;
        }
        User user = netUser.getUser();
        if (user == null){
            LOGGER.info("[2]:{} 的玩家是空的" , NetUtils.host(channel));
            return;
        }
        if (user.isOpenForMe(netUser)){
            LOGGER.info("[3]:{} 玩家可能被挤了");
            return;
        }
        userManager.timer(user ,now);

    }

    @Override
    public void onChannelActive(Channel channel) {
        ClientToServerUser netUser = new ClientToServerUser(channel, Thread.currentThread());
        channel.attr(ClientToServerUser.KEY).set(netUser);
        LOGGER.info("{} 连接到服务器", NetUtils.host(channel));
    }

    @Override
    public void onChannelInactive(Channel channel) {
        //离线
        ClientToServerUser netUser = channel.attr(ClientToServerUser.KEY).getAndRemove();
        if (netUser == null){
            return;
        }
        User user = netUser.getUser();
        if (user== null){
            return;
        }
        if (user.isOpenForMe(netUser)){
            return;
        }
        user.setOnline(false);
        user.close(netUser);
//        listener.onLoginOut(user);
    }

    @Override
    public void onChannelRead(Channel channel, Message message) {
        ClientToServerUser netUser = channel.attr(ClientToServerUser.KEY).get();
        if (netUser == null){
            LOGGER.error("{} 的netUser是空的", NetUtils.host(channel), new NullPointerException());
            return;
        }
        if (!netUser.isVerified()){
            if (message instanceof ReqVersionCheckMessage){
                checkVersion(netUser, (ReqVersionCheckMessage) message);
            }else {
                netUser.disconnect(KickType.VERSION_ILLEGAL, " version check error");
            }
            return;
        }
        User user = netUser.getUser();
        if (user == null ) {
//            if( message instanceof LoginForTypeRequest){
//                LoginForTypeRequest request = (LoginForTypeRequest)message;
//                SDKType sdkType = request.getSdkType();
//                if (sdkType == null) {
//                    netUser.disconnect(KickType.NOT_VERIFY, "not verify");
//                    return;
//                }
//                logger.info("{}登录", message);
//                sdkModule.sdk(sdkType).login( netUser , request );
//                heroModule.calculate( user , Reason.INIT);
//            }
            return;
        }

        if ( !user.isOpenForMe( netUser ) ) {
            netUser.disconnect(KickType.REPLACE, "replace");
            return;
        }
//        if (message instanceof CreateMainHeroRequest) {
//            createHero(user, (CreateMainHeroRequest) message);
//            return;
//        }
        ClientToServerHandler handler = handlerGroup.handler(message.id());
        if (handler == null){
            return;
        }
        long now = GameClock.millis();
        handler.exec(user, message);
        long payment = GameClock.millis() - now;
        if( payment > 10 ){
            System.out.println(handler +" 执行速度时间:"+payment);
        }

    }

    private void checkVersion(ClientToServerUser netUser, ReqVersionCheckMessage message) {
    }
}
