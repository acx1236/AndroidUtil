package com.ancx.ancxutil.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.ancx.ancxutil.R;
import com.ancx.ancxutil.utils.MsgUtil;

import java.lang.reflect.Field;

/**
 * Created by Ancx
 */
public class WheelView extends NumberPicker {

    private int editTextColor;
    private int splitLineColor;
    private float textSize;

    public WheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.WheelView);
        editTextColor = typeArray.getColor(R.styleable.WheelView_pickerEditTextColor, 0X000000);
        splitLineColor = typeArray.getColor(R.styleable.WheelView_splitLineColor, 0X000000);
        textSize = typeArray.getDimension(R.styleable.WheelView_textSize, 0);
        typeArray.recycle();
        setNumberPickerDividerColor();
    }

    @Override
    public void addView(View child) {
        super.addView(child);
        updateView(child);
    }

    @Override
    public void addView(View child, int index) {
        super.addView(child, index);
        updateView(child);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        updateView(child);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        super.addView(child, params);
        updateView(child);
    }

    @Override
    public void addView(View child, int width, int height) {
        super.addView(child, width, height);
        updateView(child);
    }

    public void updateView(View view) {
        if (view instanceof EditText) {
            //这里修改字体的属性
            ((EditText) view).setTextColor(Color.parseColor("#000000"));
            MsgUtil.LogTag("editTextColor = " + Color.parseColor("#000000"));
            if (textSize != 0)
                ((EditText) view).setTextSize(textSize);
        }
    }

    private void setNumberPickerDividerColor() {
        Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    pf.set(this, new ColorDrawable(splitLineColor));
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

}
