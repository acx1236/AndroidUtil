package com.ancx.ancxutil.widget.shop;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ancx.ancxutil.App;
import com.ancx.ancxutil.R;
import com.ancx.ancxutil.adapter.shop.ShopChildAdapter;
import com.ancx.ancxutil.adapter.shop.ShopGroupAdapter;
import com.ancx.ancxutil.beans.ShopBean;

import java.util.List;

/**
 * 商品列表
 * Created by ancx on 2016/11/4.
 */

public class ShopListView extends RelativeLayout implements AdapterView.OnItemClickListener, ExpandableListView.OnGroupClickListener, ExpandableListView.OnChildClickListener, AbsListView.OnScrollListener, ShopChildAdapter.OnAddListener {

    // ==================== 自 定 义 接 口 ====================

    public interface OnItemClickListener {
        void onItemClick(Object clickObj);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    // ======================================================

    public ShopListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.layout_shop_listview, this);
        initView();
    }

    private ListView lv_group;
    private ExpandableListView lv_child;
    private TextView tv_title;

    private void initView() {
        lv_group = (ListView) findViewById(R.id.lv_group);
        lv_group.setOnItemClickListener(this);
        lv_child = (ExpandableListView) findViewById(R.id.lv_child);
        lv_child.setGroupIndicator(null);
        lv_child.setOnGroupClickListener(this);
        lv_child.setOnChildClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        return true;
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        if (onItemClickListener != null)
            onItemClickListener.onItemClick(childAdapter.getChild(groupPosition, childPosition));
        return true;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    private RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (isClickSelected) {
            isClickSelected = false;
            return;
        }
        long currentExpandableListPosition = lv_child.getExpandableListPosition(firstVisibleItem);
        int currentGroupPosition = ExpandableListView.getPackedPositionGroup(currentExpandableListPosition);
        int currentChildPosition = ExpandableListView.getPackedPositionChild(currentExpandableListPosition);
        // 动画
        if (currentChildPosition == childAdapter.getChildrenCount(currentGroupPosition) - 1) {
            // 当前显示的第一个是组View中的最后一个
            View firstGroupView = lv_child.getChildAt(1);
            int top = firstGroupView.getTop() - tv_title.getHeight();
            layoutParams.setMargins(0, top > 0 ? 0 : top, 0, 0);
            tv_title.setLayoutParams(layoutParams);
        } else {
            layoutParams.setMargins(0, 0, 0, 0);
            tv_title.setLayoutParams(layoutParams);
        }
        if (lv_group.getCheckedItemPosition() != currentGroupPosition) {
            selectItem(currentGroupPosition);
            tv_title.setText(groupAdapter.getItem(currentGroupPosition).getTitle());
        }
    }

    private boolean isClickSelected;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        isClickSelected = true;
        // 更新Title数据
        tv_title.setText(groupAdapter.getItem(position).getTitle());
        lv_child.setSelectedGroup(position);
    }

    private ShopGroupAdapter groupAdapter;

    private ShopChildAdapter childAdapter;

    public void setShop(List<ShopBean.ResultBean.RecBean> rec, List<ShopBean.ResultBean.ComboBean> combo, List<ShopBean.ResultBean.CuisineBean> cuisine, TextView tv_shops) {
        // 设置组列表数据
        groupAdapter = new ShopGroupAdapter(rec, combo, cuisine);
        lv_group.setAdapter(groupAdapter);
        // 设置Title数据
        tv_title.setText(groupAdapter.getItem(0).getTitle());
        // 设置子列表数据
        ViewGroup rootView = (ViewGroup) ((Activity) getContext()).getWindow().getDecorView();
        childAdapter = new ShopChildAdapter(rec, combo, cuisine, rootView, tv_shops);
        childAdapter.setOnAddListener(this);
        lv_child.setAdapter(childAdapter);
        for (int i = 0; i < childAdapter.getGroupCount(); i++) {
            lv_child.expandGroup(i);
        }
        lv_group.setItemChecked(0, true);
        lv_child.setOnScrollListener(this);
    }

    @Override
    public void onAddSuccess(Object addProduct) {
        notifyGroupDataSetChanged();
        if (addProduct instanceof ShopBean.ResultBean.RecBean.ListBean) {
            Toast.makeText(App.getInstance(), ((ShopBean.ResultBean.RecBean.ListBean) addProduct).getTitle(), Toast.LENGTH_SHORT).show();
        } else if (addProduct instanceof ShopBean.ResultBean.ComboBean.ListBean) {
            Toast.makeText(App.getInstance(), ((ShopBean.ResultBean.ComboBean.ListBean) addProduct).getTitle(), Toast.LENGTH_SHORT).show();
        } else if (addProduct instanceof ShopBean.ResultBean.CuisineBean.ListBean) {
            Toast.makeText(App.getInstance(), ((ShopBean.ResultBean.CuisineBean.ListBean) addProduct).getTitle(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(App.getInstance(), "错误!", Toast.LENGTH_SHORT).show();
        }
    }

    public void notifyGroupDataSetChanged() {
        if (groupAdapter != null)
            groupAdapter.notifyDataSetChanged();
    }

    public void notifyChildDataSetChanged() {
        if (childAdapter != null)
            childAdapter.notifyDataSetChanged();
    }

    private void selectItem(int position) {
        lv_group.setItemChecked(position, true);
        lv_group.setSelection(position);
    }

}