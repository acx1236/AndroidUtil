package com.ancx.application.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import com.ancx.ancxutil.listener.OnDownFileListener;
import com.ancx.ancxutil.utils.HttpUtil;
import com.ancx.ancxutil.utils.ImageLoader;
import com.ancx.ancxutil.utils.MsgUtil;
import com.ancx.ancxutil.widget.LoadingWaitView;
import com.ancx.ancxutil.widget.WheelView;
import com.ancx.ancxutil.widget.roundByDrawable.RoundImageView;
import com.ancx.application.R;
import com.android.volley.VolleyError;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements LoadingWaitView.OnReloadDataListener {

//    private LoadingWaitView mLoadingWaitView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RoundImageView mImageView = (RoundImageView) findViewById(R.id.mImageView);
//        ImageView mImageView = (ImageView) findViewById(R.id.mImageView);
        ImageLoader.display("http://img4.duitang.com/uploads/item/201312/05/20131205172421_QKF4K.jpeg", mImageView);

        String[] mDisplayNames = {"北京", "上海", "天津", "深圳", "哈尔滨", "杭州", "宁波"};
        WheelView mWheelView = (WheelView) findViewById(R.id.mWheelView);
        mWheelView.setDisplayedValues(mDisplayNames);
        mWheelView.setMinValue(0);
        mWheelView.setMaxValue(6);
        mWheelView.setValue(3);

//        mLoadingWaitView = (LoadingWaitView) findViewById(R.id.mLoadingWaitView);
//        mLoadingWaitView.setOnReloadDataListener(this);
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mLoadingWaitView.errorNet();
//            }
//        }, 5000);

//        DownLoadUtil.downFile("http://esbook.easou.com/download/esbooks-blf1298_11982_011.apk", Environment.getExternalStorageDirectory() + "/update.apk", new OnProgressListener() {
//            @Override
//            public void onProgressUpdate(double progress) {
//                Log.e("TAG", "progress = " + progress);
//            }
//        });

        HttpUtil.downFile("http://esbook.easou.com/download/esbooks-blf1298_11982_011.apk", Environment.getExternalStorageDirectory() + "/update.apk", new OnDownFileListener() {
            @Override
            public void onResponse(InputStream is) {
                try {
                    MsgUtil.LogTag("文件大小 = " + is.available());
                } catch (IOException e) {
                    MsgUtil.LogException(e);
                }
            }

            @Override
            public void error(VolleyError error) {
                MsgUtil.LogTag("下载失败");
            }
        });

    }

//    private Handler handler = new Handler();

    @Override
    public void onReload() {
//        mLoadingWaitView.loading();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mLoadingWaitView.loadComplete();
//            }
//        }, 5000);
    }

}
