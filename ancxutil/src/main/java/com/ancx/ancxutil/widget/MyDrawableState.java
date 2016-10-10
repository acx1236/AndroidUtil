package com.ancx.ancxutil.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.ancx.ancxutil.R;

/**
 * 自定义Drawable的状态
 * 1⃣️ 在res/valuse文件夹下新建drawable_status.xml
 * 2⃣️ 继承Item的容器（本类）
 * Created by Ancx
 */

public class MyDrawableState extends RelativeLayout {

    private boolean stateAncx;
    private static final int[] STATE_MESSAGE_READED = {R.attr.state_ancx};

    public MyDrawableState(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setStateAncx(boolean state) {
        if (stateAncx != state) {
            stateAncx = state;
            refreshDrawableState();
        }
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        if (stateAncx) {
            final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
            mergeDrawableStates(drawableState, STATE_MESSAGE_READED);
            return drawableState;
        }
        return super.onCreateDrawableState(extraSpace);
    }
}
