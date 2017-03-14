package com.baluobo.common.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.baluobo.R;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/3.
 */
public class IndicatorView extends View {
    private int normalColor, selectedColor;
    private int dotMargin;
    private int dotNumber = -1;
    private int dotRadius;
    private float mTranslationX;
    private Paint normalPaint;
    private Paint selectedPaint;
    private ViewPager mViewPager;

    public IndicatorView(Context context) {
        super(context);
    }

    public IndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public IndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet set){
        TypedArray typedArray = context.obtainStyledAttributes(set, R.styleable.IndicatorView);
        try {
            normalColor = typedArray.getColor(R.styleable.IndicatorView_normalColor, 0xFF3F51B5);
            selectedColor = typedArray.getColor(R.styleable.IndicatorView_selectedColor, 0xFFFF4081);
            dotMargin = typedArray.getDimensionPixelSize(R.styleable.IndicatorView_dotMargin, 20);
            dotNumber = typedArray.getInt(R.styleable.IndicatorView_dotNumber, 0);
            dotRadius = typedArray.getDimensionPixelSize(R.styleable.IndicatorView_dotRadius, 4);

            normalPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            normalPaint.setStyle(Paint.Style.STROKE);
            normalPaint.setStrokeWidth(context.getResources().getDimensionPixelSize(R.dimen.line_height));
            normalPaint.setColor(normalColor);

            selectedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            selectedPaint.setStyle(Paint.Style.FILL);
            selectedPaint.setColor(selectedColor);
        }finally {
            typedArray.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (dotNumber <= 0){
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width, height;
        if (widthMode == MeasureSpec.EXACTLY){
            width = widthSize;
        }else {
            width = dotRadius * 2 * dotNumber + (dotNumber - 1) * dotMargin;
        }

        if (heightMode == MeasureSpec.EXACTLY){
            height = heightSize;
        }else {
            height = dotRadius * 2;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(0xffffff);
        if (dotNumber <= 0){
            return;
        }
        int y = dotRadius;
        for (int i = 1; i <= dotNumber; i++){
            int x = (i - 1) * (dotRadius * 2 + dotMargin) + dotRadius;
            canvas.drawCircle(x, y, dotRadius - 2, normalPaint);
        }
        canvas.drawCircle(mTranslationX, y, dotRadius, selectedPaint);
    }

    public  void setViewPager(ViewPager viewPager){
        if (viewPager == null){
            return;
        }
        this.mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                float currentScroll = positionOffset * (dotRadius * 2 + dotMargin);
                mTranslationX = dotRadius + position * (dotRadius * 2 + dotMargin) + currentScroll;
                invalidate();
            }

            @Override
            public void onPageSelected(int position) {
                mTranslationX = position * (dotRadius * 2 + dotMargin) + dotRadius;
                invalidate();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
