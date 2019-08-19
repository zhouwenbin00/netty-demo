package com.test.game.data;

import com.google.inject.Injector;
import com.test.game.core.utils.JarClassLoader;

import java.io.IOException;

/** data层启动入口 @Auther: zhouwenbin @Date: 2019/8/5 16:22 */
public abstract class Main {
    /*
     * Stage.PRODUCTION(异常检查与性能，启动较慢)
     * Stage.DEVELOPMENT(快速启动，不做检验)
     * Stage.TOOL(最小代价，有些功能无法使用)
     */
    /*logic层Main方法的包名*/
    private static final String logicMain = "com.test.game.ScriptMain";

    public static void main(String[] args) throws Exception {
        CmdParams params = new CmdParams(args);
        Main main = createMain(params.getLogicPath());
        main.start(params);
    }

    /**
     * 找到logic层的Main入口
     *
     * @param logicPath
     * @return
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static Main createMain(String logicPath)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException,
                    IOException {
        JarClassLoader classLoader = new JarClassLoader(logicPath);
        Class<?> clazz = classLoader.loadClass(logicMain);
        return (Main) clazz.newInstance();
    }

    /**
     * 启动
     *
     * @throws Exception
     */
    public abstract void start(CmdParams params) throws Exception;

    /**
     * 热更
     *
     * @param params
     * @return
     * @throws Exception
     */
    public abstract Injector jar(CmdParams params) throws Exception;
}
