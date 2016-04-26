package com.ancx.ancxutil.utils.cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.ancx.ancxutil.App;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 图片操作工具类
 * Created by Ancx
 */
public class BitmapUtil {

    /**
     * 根据路径获取存储卡中的图片对象，设置虚加载，防止内存的浪费(maxWidth || maxHeight == 0时，加载原图)
     *
     * @param localPath 图片在存储卡中的路径
     * @param maxWidth  返回图片的最大宽度，为0时代表原图，与maxHeight是或的关系
     * @param maxHeight 返回图片的最大高度，为0时代表原图，与maxWidth是或的关系
     * @return 目标图片对象
     */
    public static Bitmap getStorageCardBitmap(String localPath, int maxWidth, int maxHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inPurgeable = true;
        options.inInputShareable = true;
        if (maxWidth != 0 && maxHeight != 0) {
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(localPath, options);
            int height = options.outHeight;
            int width = options.outWidth;
            if (height > maxHeight || width > maxWidth) {
                int heightRatio = Math.round((float) height / (float) maxHeight);
                int widthRatio = Math.round((float) width / (float) maxWidth);
                options.inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
            }
            options.inJustDecodeBounds = false;
        }
        Bitmap bitmap = BitmapFactory.decodeFile(localPath, options);
        return bitmap;
    }

    /**
     * 把资源文件转换成Bitmap对象
     *
     * @param resourceId 资源文件ID
     * @return 转换的Bitmap对象
     */
    public static Bitmap getRawResourceBitmap(int resourceId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        InputStream is = App.getInstance().getResources().openRawResource(resourceId);
        Bitmap bitmap = BitmapFactory.decodeStream(is, null, opt);
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 保存Bitmap对象到存储卡
     *
     * @param bitmap    图片对象
     * @param localPath 存储的本地路径
     * @return true 保存成功，false 保存失败
     */
    public static boolean saveBitmapToFile(Bitmap bitmap, String localPath) {
        boolean isCreateSuccess = FileUtil.createFile(localPath);
        if (!isCreateSuccess) {
            return false;
        }
        boolean hasSave = false;
        BufferedOutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(localPath));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            hasSave = true;
        } catch (FileNotFoundException e) {
            hasSave = false;
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.gc();
        }
        return hasSave;
    }
}
