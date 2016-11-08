package com.ancx.ancxutil.widget.shop;

import android.content.Context;
import android.graphics.Color;
import android.widget.Checkable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ancx.ancxutil.R;
import com.ancx.ancxutil.beans.GroupBean;

/**
 * Created by ancx on 2016/11/4.
 */

public class SingleGroupView extends RelativeLayout implements Checkable {

    private boolean mChecked;

    public SingleGroupView(Context context) {
        super(context);
        inflate(context, R.layout.layout_single_shop_group, this);
        initView();
    }

    private TextView tv_title, tv_num, view;

    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_num = (TextView) findViewById(R.id.tv_num);
        view = (TextView) findViewById(R.id.view);
    }

    @Override
    public void setChecked(boolean checked) {
        mChecked = checked;
        tv_title.setBackgroundColor(mChecked ? getResources().getColor(R.color.colorWhite) : getResources().getColor(R.color.colorGray));
        tv_title.setTextColor(mChecked ? Color.parseColor("#FF9D00") : Color.parseColor("#000000"));
        view.setVisibility(mChecked ? VISIBLE : GONE);
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void toggle() {
        setChecked(mChecked);
    }

    public void setData(GroupBean groupBean) {
        tv_title.setText(groupBean.getTitle());
        if (groupBean.getNum() == 0) {
            tv_num.setVisibility(GONE);
        } else {
            tv_num.setVisibility(VISIBLE);
            tv_num.setText(String.valueOf(groupBean.getNum()));
        }
    }

}