package com.test.game;

import com.test.game.core.utils.ConfigUtils;

import java.io.File;

/**
 * @Auther: zhouwenbin
 * @Date: 2019/8/8 19:52
 */
public class MfgGen {

    private static final String cfg_gen_dir = "com.test.game.data.config";
    private static final String execl_dir = "cfg";
    private static final File server_file = new File("cfg/server.bin");
    private static final File client_file = new File("cfg/client.bin");

    public static void main(String[] args) {
        ConfigUtils.export(execl_dir, server_file ,client_file);
    }
}
