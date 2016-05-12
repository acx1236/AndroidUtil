package com.ancx.application.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.ancx.ancxutil.utils.ImageLoader;
import com.ancx.application.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView mImageView = (ImageView) findViewById(R.id.mImageView);
        ImageLoader.display("http://pic33.nipic.com/20130913/13008390_223449507127_2.jpg", mImageView);

    }

}
