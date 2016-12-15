package com.ancx.ancxutil.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * 带有右滑扩展功能
 * Created by ancx on 2016/12/14.
 */

public class RightFunctionExtensionView extends ViewGroup {

    public RightFunctionExtensionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private View rightView, mainView;

    private int rightViewW, rightViewH, mainViewW, mainViewH;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        rightView = getChildAt(0);
        rightViewW = rightView.getMeasuredWidth();
        rightViewH = rightView.getMeasuredHeight();

        mainView = getChildAt(1);
        mainViewW = mainView.getMeasuredWidth();
        mainViewH = mainView.getMeasuredHeight();

        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), mainViewH);
    }

    // ====================================================================================

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        rightView.layout(mainViewW - rightViewW, 0, mainViewW, rightViewH);
        mainView.layout(0, 0, mainViewW, mainViewH);
    }

    // ==============================左右滑动主布局的效果==============================

    private int startXPosition, mainViewLeft, offsetX, startYPosition;
    private long startTime;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startXPosition = (int) event.getX();
                startYPosition = (int) event.getY();
                startTime = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_MOVE:

                offsetX = (int) event.getX() - startXPosition;

                if (Math.abs(offsetX) > Math.abs(event.getY() - startYPosition))
                    getParent().requestDisallowInterceptTouchEvent(true);
                else
                    getParent().requestDisallowInterceptTouchEvent(false);

                if (mainViewLeft + offsetX < -rightViewW)
                    mainView.layout(-rightViewW, 0, mainViewW - rightViewW, mainViewH);
                else if (mainViewLeft + offsetX > 0)
                    mainView.layout(0, 0, mainViewW, mainViewH);
                else
                    mainView.layout(mainViewLeft + offsetX, 0, mainViewW + (mainViewLeft + offsetX), mainViewH);

                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:

                if (System.currentTimeMillis() - startTime < 500)
                    if (event.getX() - startXPosition > 50) {
                        mainViewLeft = 0;
                        mainView.layout(mainViewLeft, 0, mainViewW + mainViewLeft, mainViewH);
                        break;
                    } else if (event.getX() - startXPosition < -50) {
                        mainViewLeft = -rightViewW;
                        mainView.layout(mainViewLeft, 0, mainViewW + mainViewLeft, mainViewH);
                        break;
                    }

                if (mainViewLeft + offsetX < -rightView.getMeasuredWidth() / 2)
                    mainViewLeft = -rightViewW;
                else
                    mainViewLeft = 0;
                mainView.layout(mainViewLeft, 0, mainViewW + mainViewLeft, mainViewH);

                break;
        }
        return true;
    }

    // ==============================当滑动距离大于10时，屏蔽子View的事件消费==============================

    private float interceptX;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                interceptX = ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(ev.getX() - interceptX) > 10) {
                    startXPosition = (int) ev.getX();
                    startYPosition = (int) ev.getY();
                    startTime = System.currentTimeMillis();
                    return true;
                }
                break;
        }
        return false;
    }

}