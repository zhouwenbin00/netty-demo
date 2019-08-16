package com.test.game.logic;

import com.google.inject.Injector;
import com.test.game.data.GameProperties;
import com.test.game.data.guice.GameGuiceModule;

/** @Auther: zhouwenbin @Date: 2019/8/16 19:56 */
public class LogicParentModule extends GameGuiceModule {
    private final Injector injector;

    public LogicParentModule(Injector injector) {
        this.injector = injector;
    }

    private <T> void bindParent(Class<T> clazz){
        bind(clazz).toInstance(injector.getInstance(clazz));
    }


    @Override
    protected void bind() {
        bindParent(GameProperties.class);
    }
}
