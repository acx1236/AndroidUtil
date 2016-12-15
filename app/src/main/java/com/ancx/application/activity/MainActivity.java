package com.ancx.application.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.ancx.application.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private TextView mTextView;

    private void initView() {
        mTextView = (TextView) findViewById(R.id.mTextView);
    }

    private void initData() {
    }

}