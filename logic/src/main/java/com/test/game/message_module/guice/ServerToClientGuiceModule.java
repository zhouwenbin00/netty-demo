package com.test.game.message_module.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class ServerToClientGuiceModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(com.test.game.message_module.account.AccountLoginRequestHandler.class).in(Singleton.class);
  }
}
