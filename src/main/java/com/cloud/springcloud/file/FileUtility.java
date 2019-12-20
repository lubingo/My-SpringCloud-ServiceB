package com.cloud.springcloud.file;
import java.io.File;

public class FileUtility {

    /**
     * 创建文件夹
     * @param str
     */
    public static void judeDirExists(String str) {
        File file = new File(str);
        if (!file.exists())
            file.mkdirs();
    }

}
