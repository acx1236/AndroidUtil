package com.ancx.application.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.ancx.ancxutil.widget.LoadingWaitView;
import com.ancx.application.R;

public class MainActivity extends AppCompatActivity implements LoadingWaitView.OnReloadDataListener {

    private LoadingWaitView mLoadingWaitView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ImageView mImageView = (ImageView) findViewById(R.id.mImageView);
//        ImageLoader.display("http://pic33.nipic.com/20130913/13008390_223449507127_2.jpg", mImageView);

        mLoadingWaitView = (LoadingWaitView) findViewById(R.id.mLoadingWaitView);
        mLoadingWaitView.setOnReloadDataListener(this);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mLoadingWaitView.errorNet();
            }
        }, 5000);
    }

    private Handler handler = new Handler();

    @Override
    public void onReload() {
        mLoadingWaitView.loading();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mLoadingWaitView.loadComplete();
            }
        }, 5000);
    }
}
