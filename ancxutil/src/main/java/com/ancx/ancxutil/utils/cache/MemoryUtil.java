package com.ancx.ancxutil.utils.cache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.ancx.ancxutil.utils.MD5Util;

/**
 * 内存管理工具类
 * Created by Ancx
 */
public class MemoryUtil {

    /**
     * 缓存图片的内存
     */
    private final static LruCache<String, Bitmap> memory = new LruCache<String, Bitmap>((int) (Runtime.getRuntime().maxMemory() / 1024 / 8)) {

        @Override
        protected int sizeOf(String key, Bitmap value) {
            return value.getRowBytes() * value.getHeight() / 1024;
        }

        @Override
        protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
            super.entryRemoved(evicted, key, oldValue, newValue);
            // 存储图片的内存满了，进行回收
            oldValue = null;
        }
    };

    /**
     * 定制获取图片的命名的规则
     *
     * @param imgUrl 图片的链接
     * @return 存在内存中或存储卡中的图片名称
     */
    public static String getName(String imgUrl) {
        String key;
        if (imgUrl.split("filename=").length > 1) {
            key = imgUrl.split("filename=")[1];
        } else {
            key = MD5Util.GetMD5Code(imgUrl);
        }
        return key;
    }

    /**
     * 获取内存中的图片对象
     *
     * @param imgUrl 图片的链接
     * @return 图片对象或null
     */
    public static Bitmap getBitmap(String imgUrl) {
        return memory.get(getName(imgUrl));
    }

    /**
     * 把图片对象存储到内存中
     *
     * @param imgUrl 图片的链接
     * @param bitmap 要存储的图片对象
     */
    public static void putBitmap(String imgUrl, Bitmap bitmap) {
        memory.put(getName(imgUrl), bitmap);
    }

    public static String getLocalPath(String imgUrl) {
        return FileUtil.getCacheSubPath(".pic/" + getName(imgUrl) + ".jpeg");
    }
}
