package com.test.game.data.net;

import com.google.inject.Singleton;
import com.test.game.data.Launcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Auther: zhouwenbin
 * @Date: 2019/8/18 21:07
 */
@Singleton
public class ServerLogicHolder {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerLogicHolder.class);

    public volatile Launcher launcher;
    public volatile IDispatcher dispatcher;

}
