package com.test.game.logic.message.handler;

import com.test.game.core.net.message.Message;
import com.test.game.data.game.user.User;

public interface ClientToServerHandler<T extends Message> {
  void exec(User user, T message);
}
