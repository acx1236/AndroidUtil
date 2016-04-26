package com.ancx.application;

import android.app.Application;

/**
 * Created by Ancx on 2016/4/25.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        com.ancx.ancxutil.App.setInstance(getApplicationContext());
        com.ancx.ancxutil.App.setDEBUG(true);
    }
}
