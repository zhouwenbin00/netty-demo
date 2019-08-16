package com.test.game.logic;

import com.google.inject.AbstractModule;
import com.test.game.data.Launcher;

/**
 * @Auther: zhouwenbin
 * @Date: 2019/8/16 20:04
 */
public class LogicModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Launcher.class).to(LogicLauncher.class);
    }

}
