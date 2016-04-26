package com.ancx.ancxutil.utils.cache;

import android.os.Build;
import android.os.Environment;

import com.ancx.ancxutil.App;

import java.io.File;
import java.io.IOException;

/**
 * 本地存储的工具类
 * Created by Ancx
 */
public class FileUtil {

    private static String cacheDirPath;

    /**
     * App的SD卡cache目录的路径,这个路径会在应用卸载时自动删除
     *
     * @return SD根目录/Android/data/app包名/cache
     */
    private static String getCacheDirPath() {
        if (cacheDirPath == null) {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
                    cacheDirPath = App.getInstance().getExternalCacheDir().getPath();
                }
            }
        }
        return cacheDirPath;
    }

    private static String fileDirPath;

    /**
     * App的SD卡files目录的路径,这个路径会在应用卸载时自动删除
     *
     * @return SD根目录/Android/data/app包名/files
     */
    private static String getFileDirPath() {
        if (fileDirPath == null) {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
                    fileDirPath = App.getInstance().getExternalFilesDir(null).getPath();
                }
            }
        }
        return fileDirPath;
    }

    /**
     * 获取cache目录下的子路径
     *
     * @param subName 子目录文件
     * @return SD根目录/Android/data/app包名/cache/subName
     */
    public static String getCacheSubPath(String subName) {
        String subPath = null;
        if (getCacheDirPath() == null)
            return subPath;
        subPath = getCacheDirPath() + "/" + subName;
        return subPath;
    }

    /**
     * 获取files目录下的子路径
     *
     * @param subName 子目录文件
     * @return SD根目录/Android/data/app包名/files/subName
     */
    public static String getFileSubPath(String subName) {
        String subPath = null;
        if (getFileDirPath() == null)
            return subPath;
        subPath = getFileDirPath() + "/" + subName;
        return subPath;
    }

    /**
     * 创建文件，判断上一级文件夹是否创建并创建
     *
     * @param filePath
     * @return true 创建成功，false 创建失败
     */
    public static boolean createFile(String filePath) {
        int end = filePath.lastIndexOf(File.separator);
        String dirPath = filePath.substring(0, end);
        createDir(dirPath);
        File file = new File(filePath);
        try {
            file.createNewFile();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 创建文件夹
     *
     * @param dirPath 文件夹路径
     */
    public static void createDir(String dirPath) {
        File dirFile = new File(dirPath);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
    }
}
