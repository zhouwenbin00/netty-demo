package com.test.game.logic.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class ClientToServerGuiceModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(com.test.game.logic.message.account.handler.ResAccountLoginMessageHandler.class).in(Singleton.class);
    bind(com.test.game.logic.message.account.handler.ReqAccountLoginFailmessageHandler.class).in(Singleton.class);
    bind(com.test.game.logic.message.login.handler.ReqVersionCheckMessageHandler.class).in(Singleton.class);
  }
}
