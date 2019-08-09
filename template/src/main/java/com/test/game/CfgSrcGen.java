package com.test.game;

import com.test.game.core.utils.GenerateUtils;

import java.io.File;

/** 生成配置代码 @Auther: zhouwenbin @Date: 2019/8/30 01:47 */
public class CfgSrcGen {

    private static final String excel_dir = "cfg";
    private static final String pkg = "com.test.game.data.config";
    private static final File server_src_dir = new File("generated/src/main/java");
    private static final File server_custom_src_dir = new File("generated/src/main/java");
    private static final File client_src_dir = new File("../share/" + GitIgnore.NAME + "/src");
    private static final File client_custom_src_dir = client_src_dir;

    public static void main(String[] args) throws Exception {
        GenerateUtils.genCfgSrc(
                excel_dir,
                pkg,
                server_src_dir,
                server_custom_src_dir,
                client_src_dir,
                client_custom_src_dir);
        CfgGen.main(args);
        //        Copy.copy("cfg/client.bin", "../share/" + GitIgnore.NAME + "/client.bin");
    }
}
