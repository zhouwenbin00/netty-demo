package com.test.game.logic.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.test.game.data.Launcher;
import com.test.game.data.game.user.UserManager;
import com.test.game.data.net.ClientToServerDispatcher;
import com.test.game.data.net.ServerLogicHolder;
import com.test.game.logic.LogicLauncher;
import com.test.game.logic.group.ClientToServerHandlerGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Auther: zhouwenbin
 * @Date: 2019/8/18 20:18
 */
public class DataModule extends AbstractModule {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataModule.class);

    @Override
    protected void configure() {
        bind(Launcher.class).to(LogicLauncher.class);
        bind(ClientToServerHandlerGroup.class).in(Singleton.class);
        bind(ServerLogicHolder.class);
        bind(ClientToServerDispatcher.class);


        bind(UserManager.class);

    }
}
