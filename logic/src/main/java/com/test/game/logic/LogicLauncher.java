package com.test.game.logic;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.test.game.core.server.Server;
import com.test.game.data.Launcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

/** logic层启动器 @Auther: zhouwenbin @Date: 2019/8/16 20:22 */
@Singleton
public class LogicLauncher extends Launcher {
    private final Logger logger = LoggerFactory.getLogger(LogicLauncher.class);

    @Inject
    private Server server;

    @Override
    public void launch() throws Exception {
        jar();
        // 第一次启动的时候处理一下
        launch0(finish -> {});
        // 启动
        server.startup();
    }

    private void launch0(Consumer<AtomicBoolean> consumer) throws InterruptedException {
        AtomicBoolean finish = new AtomicBoolean(false);
        consumer.accept(finish);
        while (!finish.get()) {
            Thread.sleep(1);
        }
    }

    @Override
    public void jar() throws Exception {}

    @Override
    public void registerScript() {}

    @Override
    public void shutdown() throws InterruptedException {
        // 保存数据
    }

    @Override
    public void tick(long now) {

    }
}
