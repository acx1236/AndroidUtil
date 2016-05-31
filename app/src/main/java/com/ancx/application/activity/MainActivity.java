package com.ancx.application.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.ancx.ancxutil.utils.ImageLoader;
import com.ancx.ancxutil.widget.LoadingWaitView;
import com.ancx.ancxutil.widget.RoundImageView;
import com.ancx.application.R;

public class MainActivity extends AppCompatActivity implements LoadingWaitView.OnReloadDataListener {

//    private LoadingWaitView mLoadingWaitView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RoundImageView mImageView = (RoundImageView) findViewById(R.id.mImageView);
        ImageLoader.display("http://pic33.nipic.com/20130913/13008390_223449507127_2.jpg", mImageView);

//        ImageView mImageView = (ImageView) findViewById(R.id.mImageView);
//        ImageLoader.display("http://pic33.nipic.com/20130913/13008390_223449507127_2.jpg", mImageView);

//        mLoadingWaitView = (LoadingWaitView) findViewById(R.id.mLoadingWaitView);
//        mLoadingWaitView.setOnReloadDataListener(this);
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mLoadingWaitView.errorNet();
//            }
//        }, 5000);
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
