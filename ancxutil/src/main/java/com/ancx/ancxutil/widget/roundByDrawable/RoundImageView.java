package com.ancx.ancxutil.widget.roundByDrawable;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

import com.ancx.ancxutil.R;

/**
 * 性能好，适合正方形
 * 缺点：不能拉伸。圆角大小和图片大小和View大小的比列要控制好，可能很大的图片在很小的View上显示导致圆角很小
 * 建议：正方形；在加载图片时设置显示的宽高
 * Created by Ancx
 */
public class RoundImageView extends ImageView {

    /**
     * 图片的类型，圆形or圆角
     */
    private int type;
    public static final int TYPE_CIRCLE = 0;
    public static final int TYPE_ROUND = 1;
    /**
     * 圆角的大小
     */
    private int mBorderRadius;

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);
        type = a.getInt(R.styleable.RoundImageView_type, TYPE_CIRCLE);
        if (type == TYPE_ROUND)
            mBorderRadius = a.getDimensionPixelSize(
                    R.styleable.RoundImageView_borderRadius,
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()));
        a.recycle();
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        if (type == TYPE_CIRCLE)
            setImageDrawable(new CircleDrawable(bm));
        else
            setImageDrawable(new RoundDrawable(bm, mBorderRadius));
    }
}
