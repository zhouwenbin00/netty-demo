package com.test.game;

import com.test.game.core.utils.GenerateUtils;

import java.io.File;

/** @Auther: zhouwenbin @Date: 2019/8/10 16:42 */
public class MsgSrcGen {

    private static final ClassLoader class_loader = MsgSrcGen.class.getClassLoader();
    private static final String pkg = "com.test.game.message_modules";
    private static final File id_file = new File("template/src/main/resources/msg-id.txt");
    private static final File server_custom_src_dir = new File("generated/src/main/java");
    private static final File server_src_dir = new File("generated/src/main/java");
    private static final File client_custom_src_dir =
            new File("../share/" + GitIgnore.NAME + "/src");
    private static final File client_src_dir = client_custom_src_dir;

    public static void main(String[] args) {
        GenerateUtils.genMsgSrc(
                pkg, server_src_dir, server_custom_src_dir, client_src_dir, client_custom_src_dir);
    }
}
