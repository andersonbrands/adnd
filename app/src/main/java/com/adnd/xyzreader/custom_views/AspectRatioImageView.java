package com.adnd.xyzreader.custom_views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class AspectRatioImageView extends android.support.v7.widget.AppCompatImageView {

    private float aspectratio = 1.5f;

    public AspectRatioImageView(Context context) {
        super(context);
    }

    public AspectRatioImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AspectRatioImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setAspectRatio(float aspectRatio) {
        this.aspectratio = aspectRatio;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = getMeasuredWidth();
        setMeasuredDimension(measureWidth, (int) (measureWidth / aspectratio));
    }
}
