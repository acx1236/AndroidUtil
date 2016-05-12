package com.ancx.ancxutil.listener;

import android.graphics.Bitmap;

/**
 * Created by Ancx
 */
public interface OnHttpBitmapListener {

    void setBitmap(Bitmap bitmap);

    void setError();

    boolean isLoadBitmap();
}
