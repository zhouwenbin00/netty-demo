package com.test.game.data.guice;

import com.google.inject.AbstractModule;

/**
 * @Auther: zhouwenbin
 * @Date: 2019/8/16 20:14
 */
public abstract class GameGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        binder().requireExplicitBindings();
        binder().requireExactBindingAnnotations();
        binder().disableCircularProxies();

        bind();
    }

    protected abstract void bind();
}
