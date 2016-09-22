package com.ancx.ancxutil.utils;

import android.os.Handler;

import com.ancx.ancxutil.listener.OnProgressListener;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 文件下载(可续传)
 * Created by Ancx
 */
public class DownLoadUtil {

    private static final String RANGE = "Range";

    private static int BUFFER_SIZE = 8096;//缓冲区大小

    public static void downFile(final String urlStr, final String savePath, final OnProgressListener onProgressListener) {

        ThreadUtil.startThread(new Runnable() {
            @Override
            public void run() {

                startDown(urlStr, savePath, onProgressListener);

            }
        });

    }

    private static boolean isCanDown;

    private static void startDown(String urlStr, String savePath, final OnProgressListener onProgressListener) {
        File saveFiel = new File(savePath);
        if (!saveFiel.exists()) {
            isCanDown = false;
            if (!saveFiel.getParentFile().exists()) {
                saveFiel.getParentFile().mkdirs();
            }
            try {
                if (saveFiel.createNewFile())
                    isCanDown = true;
            } catch (IOException e) {
                MsgUtil.LogTag("新建文件失败.");
                return;
            }
        } else {
            isCanDown = true;
        }

        if (!isCanDown)
            return;

        long sum = 0;
        RandomAccessFile randomAccessFile = null;
        BufferedInputStream bis = null;
        HttpURLConnection httpURLConnection = null;
        URL url = null;
        byte[] buf = new byte[BUFFER_SIZE];
        int size = 0;
        try {
            url = new URL(urlStr);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.addRequestProperty(RANGE, "bytes=" + saveFiel.length() + "-");
            MsgUtil.LogTag("连接中...");
            httpURLConnection.connect();
            MsgUtil.LogTag("连接成功.");
            long total = httpURLConnection.getContentLength();
            bis = new BufferedInputStream(httpURLConnection.getInputStream());
            randomAccessFile = new RandomAccessFile(saveFiel, "rw");
            randomAccessFile.seek(saveFiel.length());
            while ((size = bis.read(buf)) != -1) {
                randomAccessFile.write(buf, 0, size);
                if (onProgressListener != null) {
                    sum += size;
                    final double progress = sum * 1.0d / total;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            onProgressListener.onProgressUpdate(Util.decimalFormat.format(progress));
                        }
                    });
                }
            }
        } catch (MalformedURLException e) {
            MsgUtil.LogTag("创建URL异常.可能url地址错误!");
        } catch (IOException e) {
            MsgUtil.LogException(e);
        } finally {
            httpURLConnection.disconnect();
            try {
                bis.close();
            } catch (IOException e) {
                MsgUtil.LogTag("输出流关闭失败.");
            }
            try {
                randomAccessFile.close();
            } catch (IOException e) {
                MsgUtil.LogTag("输入流关闭失败.");
            }

        }
    }

    private static Handler handler = new Handler();
}
