package com.ancx.application.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ancx.ancxutil.utils.ImageLoader;
import com.ancx.ancxutil.widget.LoadingWaitView;
import com.ancx.ancxutil.widget.WheelView;
import com.ancx.ancxutil.widget.roundByDrawable.RoundImageView;
import com.ancx.application.R;

public class MainActivity extends AppCompatActivity implements LoadingWaitView.OnReloadDataListener {

//    private LoadingWaitView mLoadingWaitView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RoundImageView mImageView = (RoundImageView) findViewById(R.id.mImageView);
//        ImageView mImageView = (ImageView) findViewById(R.id.mImageView);
        ImageLoader.display("http://img4.duitang.com/uploads/item/201312/05/20131205172421_QKF4K.jpeg", mImageView);

        String[] mDisplayNames = {"tianjin", "heibei", "shanxi"};
        WheelView mWheelView = (WheelView) findViewById(R.id.mWheelView);
        mWheelView.setDisplayedValues(mDisplayNames);

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
