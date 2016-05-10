package com.ancx.ancxutil.utils;

import com.ancx.ancxutil.App;
import com.ancx.ancxutil.listener.OnHttpRequestListener;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 请求网络数据工具类
 * Created by Ancx
 */
public class HttpUtil {

    private static DefaultRetryPolicy mDefaultRetryPolicy = new DefaultRetryPolicy(10000, 1, 1f);

    public static void addRequest(String httpUrl, final OnHttpRequestListener onHttpRequestListener) {
        StringRequest stringRequest = new StringRequest(httpUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (onHttpRequestListener != null)
                    onHttpRequestListener.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (onHttpRequestListener != null)
                    onHttpRequestListener.onErrorResponse(error);
            }
        }) {
            /**
             * @return 请求头
             * @throws AuthFailureError
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return super.getHeaders();
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String content = null;
                try {
                    content = new String(response.data, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return Response.success(content, HttpHeaderParser.parseCacheHeaders(response));
            }

            /**
             * 设置超时时间和连接次数
             * @return
             */
            @Override
            public RetryPolicy getRetryPolicy() {
                return mDefaultRetryPolicy;
            }
        };
        App.getQueue().add(stringRequest);
    }

}
