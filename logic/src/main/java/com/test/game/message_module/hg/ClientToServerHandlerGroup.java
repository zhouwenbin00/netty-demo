package com.test.game.message_module.hg;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class ClientToServerHandlerGroup {
    public final ImmutableMap<Integer, com.test.game.message_module.handler.ClientToServerHandler> handlers;

    @Inject
    public ClientToServerHandlerGroup(Injector injector) {
        ImmutableMap.Builder<Integer, com.test.game.message_module.handler.ClientToServerHandler> builder = ImmutableMap.builder();
        builder.put(1, injector.getInstance(com.test.game.message_module.account.AccountLoginResponseHandler.class));
        builder.put(2, injector.getInstance(com.test.game.message_module.account.AccountLoginErrorHandler.class));
        this.handlers = builder.build();
    }

    public com.test.game.message_module.handler.ClientToServerHandler handler(int id) {
        return handlers.get(id);
    }
}
