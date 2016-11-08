package com.ancx.ancxutil.adapter.shop;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseExpandableListAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ancx.ancxutil.App;
import com.ancx.ancxutil.R;
import com.ancx.ancxutil.beans.GroupBean;
import com.ancx.ancxutil.beans.ShopBean;

import java.util.List;

/**
 * Created by ancx on 2016/11/4.
 */

public class ShopChildAdapter extends BaseExpandableListAdapter implements Animation.AnimationListener {

    public class Position {

        private int groupPosition;

        private int childPosition;

        public int getGroupPosition() {
            return groupPosition;
        }

        public void setGroupPosition(int groupPosition) {
            this.groupPosition = groupPosition;
        }

        public int getChildPosition() {
            return childPosition;
        }

        public void setChildPosition(int childPosition) {
            this.childPosition = childPosition;
        }
    }

    public interface OnAddListener {
        void onAddSuccess(Object addProduct);
    }

    private OnAddListener onAddListener;

    public void setOnAddListener(OnAddListener onAddListener) {
        this.onAddListener = onAddListener;
    }

    private List<ShopBean.ResultBean.RecBean> rec;

    private List<ShopBean.ResultBean.ComboBean> combo;

    private List<ShopBean.ResultBean.CuisineBean> cuisine;

    private ViewGroup rootView;

    private TextView tv_shops;

    public ShopChildAdapter(List<ShopBean.ResultBean.RecBean> rec, List<ShopBean.ResultBean.ComboBean> combo, List<ShopBean.ResultBean.CuisineBean> cuisine, ViewGroup rootView, TextView tv_shops) {
        this.rec = rec;
        this.combo = combo;
        this.cuisine = cuisine;
        this.rootView = rootView;
        this.tv_shops = tv_shops;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    // ==================== 设 置 数 据 ====================

    @Override
    public int getGroupCount() {
        return rec.size() + combo.size() + cuisine.size();
    }

    @Override
    public GroupBean getGroup(int groupPosition) {
        if (groupPosition < rec.size())
            return rec.get(groupPosition);
        else if (groupPosition >= rec.size() && groupPosition < rec.size() + combo.size())
            return combo.get(groupPosition - rec.size());
        else
            return cuisine.get(groupPosition - rec.size() - combo.size());
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(App.getInstance(), R.layout.layout_shop_title_view, null);
        }
        ((TextView) convertView).setText(getGroup(groupPosition).getTitle());
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (groupPosition < rec.size())
            return rec.get(groupPosition).getList().size();
        else if (groupPosition >= rec.size() && groupPosition < rec.size() + combo.size())
            return combo.get(groupPosition - rec.size()).getList().size();
        else
            return cuisine.get(groupPosition - rec.size() - combo.size()).getList().size();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (groupPosition < rec.size())
            return rec.get(groupPosition).getList().get(childPosition);
        else if (groupPosition >= rec.size() && groupPosition < rec.size() + combo.size())
            return combo.get(groupPosition - rec.size()).getList().get(childPosition);
        else
            return cuisine.get(groupPosition - rec.size() - combo.size()).getList().get(childPosition);
    }

    private class ChildViewHolder {
        TextView tv_title, tv_memo, tv_price_unit, tv_add;
        Position position;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder;
        if (convertView == null) {
            holder = new ChildViewHolder();
            convertView = View.inflate(App.getInstance(), R.layout.layout_shop_child_view, null);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_memo = (TextView) convertView.findViewById(R.id.tv_memo);
            holder.tv_price_unit = (TextView) convertView.findViewById(R.id.tv_price_unit);
            holder.tv_add = (TextView) convertView.findViewById(R.id.tv_add);
            holder.tv_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAnim(v);
                    Position position = (Position) v.getTag();
                    GroupBean group = getGroup(position.getGroupPosition());
                    group.setNum(group.getNum() + 1);
                    if (onAddListener != null)
                        onAddListener.onAddSuccess(getChild(position.getGroupPosition(), position.getChildPosition()));
                }
            });
            holder.position = new Position();
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }
        holder.position.setGroupPosition(groupPosition);
        holder.position.setChildPosition(childPosition);
        holder.tv_add.setTag(holder.position);
        if (groupPosition < rec.size()) {
            // 推荐的数据
            ShopBean.ResultBean.RecBean.ListBean child = (ShopBean.ResultBean.RecBean.ListBean) getChild(groupPosition, childPosition);
            holder.tv_title.setText(child.getTitle());
            if (TextUtils.isEmpty(child.getKey_word())) {
                holder.tv_memo.setVisibility(View.GONE);
            } else {
                holder.tv_memo.setVisibility(View.VISIBLE);
                holder.tv_memo.setText(child.getKey_word());
            }
            holder.tv_price_unit.setText(child.getPrice() + "/" + child.getUnit());
        } else if (groupPosition >= rec.size() && groupPosition < rec.size() + combo.size()) {
            // 套餐的数据
            ShopBean.ResultBean.ComboBean.ListBean child = (ShopBean.ResultBean.ComboBean.ListBean) getChild(groupPosition, childPosition);
            holder.tv_title.setText(child.getTitle());
            if (TextUtils.isEmpty(child.getKey_word())) {
                holder.tv_memo.setVisibility(View.GONE);
            } else {
                holder.tv_memo.setVisibility(View.VISIBLE);
                holder.tv_memo.setText(child.getKey_word());
            }
            holder.tv_price_unit.setText(child.getPrice() + "/" + child.getUnit());
        } else {
            // 单品的数据
            ShopBean.ResultBean.CuisineBean.ListBean child = (ShopBean.ResultBean.CuisineBean.ListBean) getChild(groupPosition, childPosition);
            holder.tv_title.setText(child.getTitle());
            if (TextUtils.isEmpty(child.getKey_word())) {
                holder.tv_memo.setVisibility(View.GONE);
            } else {
                holder.tv_memo.setVisibility(View.VISIBLE);
                holder.tv_memo.setText(child.getKey_word());
            }
            holder.tv_price_unit.setText(child.getPrice() + "/" + child.getUnit());
        }
        return convertView;
    }

    private FrameLayout animLayout;
    private int[] end_location;
    private FrameLayout.LayoutParams layoutParams;
    private Animation shopTranAnim = AnimationUtils.loadAnimation(App.getInstance(), R.anim.anim_shop_list_driver);

    private void showAnim(View view) {
        // 获取动画结束的位置,只获取一次
        if (end_location == null) {
            end_location = new int[2];
            tv_shops.getLocationInWindow(end_location);
        }
        // 在当前Activity的Window的根布局下添加一个FrameLayout布局，用来承载动画View的。只添加一次
        if (animLayout == null) {
            animLayout = new FrameLayout(App.getInstance());
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            animLayout.setLayoutParams(lp);
            animLayout.setBackgroundColor(Color.TRANSPARENT);
            rootView.addView(animLayout);
        }
        // 创建动画View的LayoutParams对象,只创建一次。
        if (layoutParams == null) {
            layoutParams = new FrameLayout.LayoutParams(view.getWidth(), view.getHeight());
        }
        // 创建View
        View animView = View.inflate(App.getInstance(), R.layout.window_add_to_shop, null);
        animView.setLayoutParams(layoutParams);
        // 获取动画开始的位置,每次都获取
        int[] start_location = new int[2];
        view.getLocationInWindow(start_location);
        // 创建动画,根据起始位置不同，每次都创建对象
        TranslateAnimation translateAnimationX = new TranslateAnimation(start_location[0], 40, 0, 0);
        translateAnimationX.setInterpolator(new LinearInterpolator());
        TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0, start_location[1], end_location[1] + 40);
        translateAnimationY.setInterpolator(new AccelerateInterpolator());
        translateAnimationY.setAnimationListener(this);
        // 旋转动画和缩放动画
        Animation mRotateAnimation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.3f, Animation.RELATIVE_TO_SELF, 0.3f);
        Animation mScaleAnimation = new ScaleAnimation(1.2f, 0.6f, 1.2f, 0.6f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        // 添加到承载动画View的布局内
        animLayout.addView(animView);
        // 创建动画集合对象
        AnimationSet animationSet = new AnimationSet(false);
        animationSet.setFillAfter(true);
        animationSet.addAnimation(mScaleAnimation);
        animationSet.addAnimation(mRotateAnimation);
        animationSet.addAnimation(translateAnimationX);
        animationSet.addAnimation(translateAnimationY);
        animationSet.setDuration(500);
        // 开始动画
        animView.startAnimation(animationSet);
    }

    private int animCount;

    @Override
    public void onAnimationStart(Animation animation) {
        animCount++;
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        animCount--;
        if (animCount == 0)
            animLayout.removeAllViews();
        tv_shops.startAnimation(shopTranAnim);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }

}