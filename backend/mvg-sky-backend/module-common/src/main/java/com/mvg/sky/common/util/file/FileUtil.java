package com.mvg.sky.common.util.file;

import java.util.List;

public class FileUtil {
    public static boolean isImageFile(String file) {
        int index = file.lastIndexOf('.');
        String ext = file.substring(index + 1);
        return List.of("jpg", "png", "jpeg").contains(ext);
    }

    public static String changeFileName(String file, String name) {
        int index = file.lastIndexOf('.');
        return file.replace(file.substring(0, index), name);
    }

    public static String concatMediaMessage(String file, String suffix) {
        int index = file.lastIndexOf('.');
        return file.replace(file.substring(0, index), file.substring(0, index) + "_" + suffix);
    }
}
