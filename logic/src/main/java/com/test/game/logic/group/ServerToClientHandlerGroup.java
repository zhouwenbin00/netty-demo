package com.test.game.logic.group;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class ServerToClientHandlerGroup {
    public final ImmutableMap<Integer, com.test.game.logic.message.handler.ServerToClientHandler> handlers;

    @Inject
    public ServerToClientHandlerGroup(Injector injector) {
        ImmutableMap.Builder<Integer, com.test.game.logic.message.handler.ServerToClientHandler> builder = ImmutableMap.builder();
        builder.put(0, injector.getInstance(com.test.game.logic.message.account.handler.ReqAccountLoginMessageHandler.class));
        builder.put(4, injector.getInstance(com.test.game.logic.message.login.handler.ResVersionCheckMessageHandler.class));
        builder.put(5, injector.getInstance(com.test.game.logic.message.login.handler.ReqVersionCheckFailmessageHandler.class));
        builder.put(6, injector.getInstance(com.test.game.logic.message.login.handler.ReqKickMessageHandler.class));
        this.handlers = builder.build();
    }

    public com.test.game.logic.message.handler.ServerToClientHandler handler(int id) {
        return handlers.get(id);
    }
}
