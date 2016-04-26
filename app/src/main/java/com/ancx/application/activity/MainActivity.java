package com.ancx.application.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.ancx.ancxutil.utils.ImageLoader;
import com.ancx.ancxutil.utils.MsgUtil;
import com.ancx.application.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView mImageView = (ImageView) findViewById(R.id.mImageView);
        String imgUrl = "http://www.0739i.com.cn/data/attachment/portal/201603/09/120158ksjocrjsoohrmhtg.jpg";
        ImageLoader.display(imgUrl, mImageView);

        MsgUtil.LogTag(imgUrl);
    }

}
