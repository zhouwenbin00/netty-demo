package com.test.game.logic;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import com.test.game.data.CmdParams;
import com.test.game.data.Launcher;
import com.test.game.data.ShutdownHooks;
import com.test.game.logic.guice.DataModule;

/** logic层入口 @Auther: zhouwenbin @Date: 2019/8/5 20:38 */
public class LogicMain extends com.test.game.data.Main {

    @Override
    public void start(CmdParams params) throws Exception {
        Injector injector = jar(params);
        injector.getInstance(Launcher.class).launch();
        injector.getInstance(ShutdownHooks.class).init();
    }

    @Override
    public Injector jar(CmdParams params) throws Exception {
        Injector injector = Guice.createInjector(Stage.PRODUCTION, new DataModule());
        Launcher launcher = injector.getInstance(Launcher.class);
        launcher.setParams(params);
        launcher.registerScript();
        return injector;
    }
}
