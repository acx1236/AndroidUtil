package com.ancx.ancxutil.listener;

import com.android.volley.VolleyError;

import java.io.InputStream;

/**
 * Created by Ancx
 */
public interface OnDownFileListener {

    void onResponse(InputStream is);

    void error(VolleyError error);
}
