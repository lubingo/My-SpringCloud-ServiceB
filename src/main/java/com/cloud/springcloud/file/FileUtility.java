package com.cloud.springcloud.file;
import java.io.File;

public class FileUtility {
    // 判断文件夹是否存在
    public static void judeDirExists(String str) {
        File file = new File(str);
        if (!file.exists())
            file.mkdir();
    }

}
