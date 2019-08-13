package com.test.game.message_module.hg;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class ServerToClientHandlerGroup {
    public final ImmutableMap<Integer, com.test.game.message_module.handler.ServerToClientHandler> handlers;

    @Inject
    public ServerToClientHandlerGroup(Injector injector) {
        ImmutableMap.Builder<Integer, com.test.game.message_module.handler.ServerToClientHandler> builder = ImmutableMap.builder();
        builder.put(0, injector.getInstance(com.test.game.message_module.account.AccountLoginRequestHandler.class));
        this.handlers = builder.build();
    }

    public com.test.game.message_module.handler.ServerToClientHandler handler(int id) {
        return handlers.get(id);
    }
}
