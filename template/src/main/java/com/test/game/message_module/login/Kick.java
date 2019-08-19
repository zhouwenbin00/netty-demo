package com.test.game.message_module.login;

import com.test.game.core.gen.MessageClass;
import com.test.game.core.gen.MessageField;
import com.test.game.core.net.message.Message;

@MessageClass(from = Message.NodeType.SERVER, to = Message.NodeType.CLIENT, desc = "踢人")
public class Kick {
  static class Req {
    @MessageField(desc = "为何踢人")
    KickType type;
  }
}