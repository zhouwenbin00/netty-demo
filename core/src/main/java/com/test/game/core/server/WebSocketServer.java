package com.test.game.core.server;

import com.test.game.core.base.Factory;
import com.test.game.core.server.hander.MessageHandler;
import com.test.game.core.server.hander.WebSocketServerInitializer;

/** @Auther: zhouwenbin @Date: 2019/8/5 19:51 */
public class WebSocketServer extends TcpServer {
    public WebSocketServer(
            String name,
            int port,
            Factory<WebSocketServerInitializer.BinaryWebSocketFrameToMessageHandler> decoderFactory,
            MessageHandler messageHandler) {
        super(name, port, new WebSocketServerInitializer(decoderFactory, messageHandler, 60));
    }
}
