package com.ivb.udacity.widget;

/**
 * Created by S.Shivasurya on 11/28/2015 - androidStudio.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;


public class SquareImageView extends ImageView {


    public SquareImageView(Context context) {
        super(context);
    }


    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        int width = getMeasuredWidth();
        setMeasuredDimension(width, width);
    }
}
