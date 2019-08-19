package com.test.game.logic.group;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class ClientToServerHandlerGroup {
    public final ImmutableMap<Integer, com.test.game.logic.message.handler.ClientToServerHandler> handlers;

    @Inject
    public ClientToServerHandlerGroup(Injector injector) {
        ImmutableMap.Builder<Integer, com.test.game.logic.message.handler.ClientToServerHandler> builder = ImmutableMap.builder();
        builder.put(1, injector.getInstance(com.test.game.logic.message.account.handler.ResAccountLoginMessageHandler.class));
        builder.put(2, injector.getInstance(com.test.game.logic.message.account.handler.ReqAccountLoginFailmessageHandler.class));
        builder.put(3, injector.getInstance(com.test.game.logic.message.login.handler.ReqVersionCheckMessageHandler.class));
        this.handlers = builder.build();
    }

    public com.test.game.logic.message.handler.ClientToServerHandler handler(int id) {
        return handlers.get(id);
    }
}
