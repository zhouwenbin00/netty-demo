package com.test.game.message_module.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class ClientToServerGuiceModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(com.test.game.message_module.account.AccountLoginResponseHandler.class).in(Singleton.class);
    bind(com.test.game.message_module.account.AccountLoginErrorHandler.class).in(Singleton.class);
  }
}
