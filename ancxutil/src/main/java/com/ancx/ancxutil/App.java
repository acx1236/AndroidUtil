package com.ancx.ancxutil;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Administrator on 2016/4/26.
 */
public class App {

    private static RequestQueue mQueue;

    private static Context instance;

    public static void setInstance(Context instance) {
        App.instance = instance;
        App.mQueue = Volley.newRequestQueue(instance);
    }

    public static Context getInstance() {
        return instance;
    }

    public static RequestQueue getQueue() {
        return mQueue;
    }

    private static boolean DEBUG;

    public static boolean isDEBUG() {
        return DEBUG;
    }

    public static void setDEBUG(boolean DEBUG) {
        App.DEBUG = DEBUG;
    }
}
