package com.ancx.ancxutil.utils;

import android.util.Log;
import android.widget.Toast;

import com.ancx.ancxutil.App;

/**
 * Log日志输出和吐司工具类
 * Created by Ancx
 */
public class MsgUtil {

    /**
     * 短吐司
     *
     * @param text
     */
    public static void ToastShort(String text) {
        Toast.makeText(App.getInstance(), text + "", Toast.LENGTH_SHORT).show();
    }

    /**
     * 长吐司
     *
     * @param text
     */
    public static void ToastLong(String text) {
        Toast.makeText(App.getInstance(), text + "", Toast.LENGTH_LONG).show();
    }

    /**
     * Log打印TAG
     *
     * @param text
     */
    public static void LogTag(String text) {
        if (App.isDEBUG())
            Log.e("TAG", text + "");
    }

    /**
     * Log打印Exception
     *
     * @param e
     */
    public static void LogException(Exception e) {
        if (App.isDEBUG())
            if (e == null)
                Log.e("Exception", "LogException ---> Exception null");
            else
                Log.e("Exception", e.getMessage() + "");
    }
}
