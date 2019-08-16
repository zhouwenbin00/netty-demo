package com.test.game.data;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

/** 虚拟机启动参数 @Auther: zhouwenbin @Date: 2019/8/16 16:28 */
public class CmdParams {

    /*逻辑层jar包路径*/
    @Option(
            name = "-l",
            aliases = "--logic",
            metaVar = "逻辑层jar包路径",
            required = true,
            usage = "/xx/xx/xx/logic.jar")
    private String logicPath;

    /*模式*/
    @Option(
            name = "-m",
            aliases = "--mode",
            metaVar = "模式",
            required = true,
            usage = "SERVER|CLIENT")
    private String mode;

    // ---------------getter------------
    public String getLogicPath() {
        return logicPath;
    }

    public String getMode() {
        return mode;
    }
    // ---------------getter------------

    public CmdParams(String[] args) throws CmdLineException {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            parser.printUsage(System.err);
            throw e;
        }
    }
}
