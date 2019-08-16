package com.test.game.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 关闭钩子 @Auther: zhouwenbin @Date: 2019/8/16 19:54 */
public abstract class ShutdownHooks {
    private static final Logger log = LoggerFactory.getLogger(ShutdownHooks.class);

    /*
     * 这个钩子可以在以下几种场景被调用：
     * 1. 程序正常退出
     * 2. 使用System.exit()
     * 3. 终端使用Ctrl+C触发的中断
     * 4. 系统关闭
     * 5. 使用Kill pid命令干掉进程
     */

    /** 注册一个JVM关闭的钩子 */
    public void init() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
    }

    protected void shutdown(Task task, String taskName) {
        log.info("begin showdown {} ...", taskName);
        try {
            task.exec();
        } catch (Exception e) {
            log.error("", e);
        }

        log.info("end showdown {} ...", taskName);
    }

    protected abstract void shutdown();

    public interface Task {
        void exec() throws Exception;
    }
}
