package com.test.game.logic.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class ServerToClientGuiceModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(com.test.game.logic.message.account.handler.ReqAccountLoginMessageHandler.class).in(Singleton.class);
    bind(com.test.game.logic.message.login.handler.ResVersionCheckMessageHandler.class).in(Singleton.class);
    bind(com.test.game.logic.message.login.handler.ReqVersionCheckFailmessageHandler.class).in(Singleton.class);
    bind(com.test.game.logic.message.login.handler.ReqKickMessageHandler.class).in(Singleton.class);
  }
}
