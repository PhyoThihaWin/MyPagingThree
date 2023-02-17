package com.pthw.mypagingthree.feature.firestorechat;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

public class ScaledImageView extends AppCompatImageView {
    public ScaledImageView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final Drawable d = getDrawable();

        if (d != null) {
            int width;
            int height;
            if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) {
                height = MeasureSpec.getSize(heightMeasureSpec);
                width = (int) Math.ceil(height * (float) d.getIntrinsicWidth() / d.getIntrinsicHeight());
            } else {
                width = MeasureSpec.getSize(widthMeasureSpec);
                height = (int) Math.ceil(width * (float) d.getIntrinsicHeight() / d.getIntrinsicWidth());
            }
            if (height>= width) width = width- (height-width);
            setMeasuredDimension(width, height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}

//public class ScaledImageView extends AppCompatImageView {
//
//    public ScaledImageView(Context context) {
//        super(context);
//    }
//
//    public ScaledImageView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    public ScaledImageView(Context context, AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//    }
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        try {
//            Drawable drawable = getDrawable();
//            if (drawable == null) {
//                setMeasuredDimension(0, 0);
//            } else {
//                int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
//                int measuredHeight = MeasureSpec.getSize(heightMeasureSpec);
//                if (measuredHeight == 0 && measuredWidth == 0) { //Height and width set to wrap_content
//                    setMeasuredDimension(measuredWidth, measuredHeight);
//                } else if (measuredHeight == 0) { //Height set to wrap_content
//                    int width = measuredWidth;
//                    int height = width *  drawable.getIntrinsicHeight() / drawable.getIntrinsicWidth();
//                    setMeasuredDimension(width, height);
//                } else if (measuredWidth == 0){ //Width set to wrap_content
//                    int height = measuredHeight;
//                    int width = height * drawable.getIntrinsicWidth() / drawable.getIntrinsicHeight();
//                    setMeasuredDimension(width, height);
//                } else { //Width and height are explicitly set (either to match_parent or to exact value)
//                    setMeasuredDimension(measuredWidth, measuredHeight);
//                }
//            }
//        } catch (Exception e) {
//            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        }
//    }
//
//}