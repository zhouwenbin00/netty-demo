package com.test.game.data;

/**
 * data层启动器
 * @Auther: zhouwenbin
 * @Date: 2019/8/16 19:53
 */
public abstract class Launcher {
    private CmdParams params;

    public CmdParams getParams() {
        return params;
    }

    public void setParams(CmdParams params) {
        this.params = params;
    }

    public abstract void launch() throws Exception;

    public abstract void jar() throws Exception;

    public abstract void registerScript();

    public abstract void shutdown() throws InterruptedException;

    public abstract void tick(long now);
}
