package com.ancx.ancxutil.listener;

import com.android.volley.VolleyError;

/**
 * 监听Http请求的接口
 * Created by Ancx
 */
public interface OnHttpRequestListener {

    void onResponse(String response);

    void onErrorResponse(VolleyError error);
}
