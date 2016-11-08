package com.ancx.ancxutil.adapter.shop;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ancx.ancxutil.App;
import com.ancx.ancxutil.beans.GroupBean;
import com.ancx.ancxutil.beans.ShopBean;
import com.ancx.ancxutil.widget.shop.SingleGroupView;

import java.util.List;

/**
 * Created by ancx on 2016/11/4.
 */

public class ShopGroupAdapter extends BaseAdapter {

    private List<ShopBean.ResultBean.RecBean> rec;

    private List<ShopBean.ResultBean.ComboBean> combo;

    private List<ShopBean.ResultBean.CuisineBean> cuisine;

    public ShopGroupAdapter(List<ShopBean.ResultBean.RecBean> rec, List<ShopBean.ResultBean.ComboBean> combo, List<ShopBean.ResultBean.CuisineBean> cuisine) {
        this.rec = rec;
        this.combo = combo;
        this.cuisine = cuisine;
    }

    @Override
    public int getCount() {
        return rec.size() + combo.size() + cuisine.size();
    }

    @Override
    public GroupBean getItem(int position) {
        if (position < rec.size())
            return rec.get(position);
        else if (position >= rec.size() && position < rec.size() + combo.size())
            return combo.get(position - rec.size());
        else
            return cuisine.get(position - rec.size() - combo.size());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new SingleGroupView(App.getInstance());
        }
        ((SingleGroupView) convertView).setData(getItem(position));
        return convertView;
    }

}