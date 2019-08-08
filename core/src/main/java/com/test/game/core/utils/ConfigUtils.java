package com.test.game.core.utils;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @Auther: zhouwenbin
 * @Date: 2019/8/8 20:00
 */
public abstract class ConfigUtils {
    private ConfigUtils() {
    }


    class Data{

    }
    class CustomClass{

    }

    public static void export(String execl_dir, File server_file, File client_file) {
        Map<String, CustomClass> name2custom = new HashMap<>();
        List<Class> classes = new LinkedList<>();
        List<Data> datas = new LinkedList<>();
        ExcelUtils.parse(execl_dir, name2custom ,classes, datas);



    }
}
