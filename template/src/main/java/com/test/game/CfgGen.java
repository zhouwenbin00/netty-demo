package com.test.game;

import com.google.common.io.Files;
import com.test.game.core.utils.GenerateUtils;

import java.io.File;
import java.io.IOException;

/** 生成config的bin文件 @Auther: zhouwenbin @Date: 2019/8/8 19:52 */
public class CfgGen {

    private static final String execl_dir = "cfg";
    private static final File server_file = new File("cfg/server.bin");
    private static final File client_file = new File("cfg/client.bin");
    private static final File share_cfg_dir = new File("../share/" + GitIgnore.NAME + "/config");

    public static void main(String[] args) throws IOException {
        GenerateUtils.export(execl_dir, server_file, client_file);
        if (share_cfg_dir.exists()) {
            for (File file : share_cfg_dir.listFiles()) {
                file.delete();
            }
        } else {
            share_cfg_dir.mkdirs();
        }
        File dir = new File(execl_dir);
        for (File file : dir.listFiles()) {
            if (file.getName().startsWith("~")) {
                continue;
            }
            if (share_cfg_dir.exists()) {
                File newFile = new File(share_cfg_dir.getAbsolutePath() + "/" + file.getName());
                newFile.createNewFile();
                Files.copy(file, newFile);
            }
        }
    }
}
