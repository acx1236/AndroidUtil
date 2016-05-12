package com.ancx.ancxutil.utils;

import android.graphics.Bitmap;
import android.os.Handler;
import android.widget.ImageView;

import com.ancx.ancxutil.App;
import com.ancx.ancxutil.R;
import com.ancx.ancxutil.utils.cache.BitmapUtil;
import com.ancx.ancxutil.utils.cache.MemoryUtil;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 图片加载器
 * Created by Ancx
 */
public class ImageLoader {

    private static Handler handler = new Handler();
    /**
     * 线程池
     */
    private final static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 2);

    /**
     * 显示图片，设置默认的defaultResource、errorResource和加载原图
     *
     * @param imgUrl    图片的Url路径
     * @param imageView 显示图片的容器
     */
    public static void display(String imgUrl, ImageView imageView) {
        display(imgUrl, imageView, R.mipmap.ic_launcher, R.mipmap.ic_launcher, 0, 0);
    }

    /**
     * 显示图片，根据type的值的不同，调用不同的显示方法
     *
     * @param imgUrl    图片的Url路径
     * @param imageView 显示图片的容器
     * @param value1    type = 1时，代表 defaultResource；type = 2时，代表 maxWidth
     * @param value2    type = 1时，代表 errorResource；type = 2时，代表 maxHeight
     * @param type      等于 1 时，value为资源图片；等于 2 时，value为显示图片的最大宽和高
     */
    public static void display(String imgUrl, ImageView imageView, int value1, int value2, int type) {
        if (type == 1) {
            // value1为defaultResource；value2为errorResource
            display(imgUrl, imageView, value1, value2, 0, 0);
        } else if (type == 2) {
            // value1为maxWidth；value2为maxHeight
            display(imgUrl, imageView, R.mipmap.ic_launcher, R.mipmap.ic_launcher, value1, value2);
        }
    }

    /**
     * 根据设置的属性显示图片
     *
     * @param imgUrl          图片的Url路径
     * @param imageView       显示图片的容器
     * @param defaultResource 加载图片时的默认图片
     * @param errorResource   加载图片失败时，显示的失败图片
     * @param maxWidth        显示图片的最大宽度
     * @param maxHeight       显示图片的最大高度
     */
    public static void display(final String imgUrl, final ImageView imageView, int defaultResource, final int errorResource, final int maxWidth, final int maxHeight) {
        if (imageView.getTag() != null)
            // 如果这个ImageView之前加载了一张图片，并且没有加载完，那么取消之前的加载，加载现在的请求
            App.getQueue().cancelAll(imageView.getTag());
        if (MemoryUtil.getBitmap(imgUrl) != null) {
            // 内存中有图片，显示并结束
            imageView.setImageBitmap(MemoryUtil.getBitmap(imgUrl));
            return;
        }
        // 内存中没有图片，在存储卡中查找，由于可能是个耗时操作，需要先加载默认图片
        imageView.setTag(imgUrl);
        imageView.setImageResource(defaultResource);
        // 开启线程在存储卡中查找图片
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                String localPath = MemoryUtil.getLocalPath(imgUrl);
                // 从存储卡中获取图片
                final Bitmap bitmap = BitmapUtil.getStorageCardBitmap(localPath, maxWidth, maxHeight);
                if (bitmap != null) {
                    // 在存储卡中找到图片
                    if (imgUrl.equals(imageView.getTag())) {
                        // 如果还需要显示，那么在主线程加载图片，并缓存在内存中
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                // 显示图片
                                imageView.setImageBitmap(bitmap);
                            }
                        });
                    }
                    // 缓存图片
                    MemoryUtil.putBitmap(imgUrl, bitmap);
                } else {
                    // 没有从存储卡中找到，从网络上下载
                    if (imgUrl.equals(imageView.getTag())) {
                        downloadBitmap(imgUrl, imageView, errorResource, maxWidth, maxHeight);
                    }
                }
            }
        });
    }

    /**
     * 从网络上下载图片
     *
     * @param imgUrl        图片的Url路径
     * @param imageView     显示图片的容器
     * @param errorResource 加载图片失败时显示的错误图片
     * @param maxWidth      显示图片的最大宽度
     * @param maxHeight     显示图片的最大高度
     */
    private static void downloadBitmap(final String imgUrl, final ImageView imageView, final int errorResource, int maxWidth, int maxHeight) {
        ImageRequest imageRequest = new ImageRequest(imgUrl, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(final Bitmap bitmap) {
                // 从网络中获取到图片,判断是否要显示，进行显示操作
                if (imgUrl.equals(imageView.getTag())) {
                    imageView.setImageBitmap(bitmap);
                }
                // 网路上获取的图片缓存到内存中
                MemoryUtil.putBitmap(imgUrl, bitmap);
                // 开启分线程存储图片到存储卡中
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        // 存储图片到指定目录
                        String localPath = MemoryUtil.getLocalPath(imgUrl);
                        if (localPath != null)
                            BitmapUtil.saveBitmapToFile(bitmap, localPath);
                    }
                });
            }
        }, maxWidth, maxHeight, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                // 加载网络图片失败，显示错误图片
                imageView.setImageResource(errorResource);
            }
        });
        // 给ImageRequest设置Tag
        imageRequest.setTag(imgUrl);
        // 将请求添加到请求队列
        App.getQueue().add(imageRequest);
    }
}
