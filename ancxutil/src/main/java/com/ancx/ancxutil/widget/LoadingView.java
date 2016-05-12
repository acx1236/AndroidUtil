package com.ancx.ancxutil.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 加载中动画
 * Created by Ancx
 */
public class LoadingView extends View {

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //    private int dpSize = DensityUtil.dp2px(50);
    private int dpSize = 100;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(dpSize, MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(dpSize, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(2);
        canvas.drawLine(0, 0, dpSize, dpSize, paint);
    }
}
