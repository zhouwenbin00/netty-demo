package com.test.game.message_module;


import com.google.common.collect.ImmutableMap;
import com.test.game.core.net.message.Message;
import com.test.game.core.net.message.MessageFactory;

public class ClientToServerMessageGroup extends MessageFactory {
    public final ImmutableMap<Integer, Class<? extends Message>> messages;

    public ClientToServerMessageGroup() {
        ImmutableMap.Builder<Integer, Class<? extends Message>> builder = ImmutableMap.builder();
        builder.put(1, com.test.game.message_module.account.AccountLoginResponse.class);
        builder.put(2, com.test.game.message_module.account.AccountLoginError.class);
        messages = builder.build();
    }

    @Override
    protected Class<? extends Message> getClass(int id) {
        return messages.get(id);
    }
}
