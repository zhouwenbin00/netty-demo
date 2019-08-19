package com.test.game.data.net;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.test.game.core.net.Governor;
import io.netty.channel.Channel;

import java.util.Set;

@Singleton
public class ClientToServerDispatcher extends Dispatcher {
  private final ServerLogicHolder scriptHolder;

  private static final ThreadLocal<Set<Channel>> localChannels = new ThreadLocal<>();

  @Inject
  public ClientToServerDispatcher(ServerLogicHolder scriptHolder) {
    super("client-to-server-dispatcher", () -> new Governor(30, 1000));
    this.scriptHolder = scriptHolder;
  }

  @Override
  protected IDispatcher script() {
    return scriptHolder.dispatcher;
  }

  @Override
  protected ThreadLocal<Set<Channel>> localChannels() {
    return localChannels;
  }
}
