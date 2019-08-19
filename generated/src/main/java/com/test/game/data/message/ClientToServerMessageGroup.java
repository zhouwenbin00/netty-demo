package com.test.game.data.message;


import com.google.common.collect.ImmutableMap;
import com.test.game.core.net.message.Message;
import com.test.game.core.net.message.MessageFactory;

public class ClientToServerMessageGroup extends MessageFactory {
    public final ImmutableMap<Integer, Class<? extends Message>> messages;

    public ClientToServerMessageGroup() {
        ImmutableMap.Builder<Integer, Class<? extends Message>> builder = ImmutableMap.builder();
        builder.put(1, com.test.game.data.message.account.ResAccountLoginMessage.class);
        builder.put(2, com.test.game.data.message.account.ReqAccountLoginFailmessage.class);
        builder.put(3, com.test.game.data.message.login.ReqVersionCheckMessage.class);
        messages = builder.build();
    }

    @Override
    protected Class<? extends Message> getClass(int id) {
        return messages.get(id);
    }
}
