package com.baluobo.common.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.baluobo.R;

import java.util.Locale;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/19.
 */
public class InvestCircleView extends View {
    private int circleRadius;
    private int strokeWidth;
    private int textSize;
    private float ratio = 0;
    private float textY;
    private String textRatio = "0%";


    private Context context;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private RectF rectF = new RectF();
    public InvestCircleView(Context context) {
        super(context);
    }

    public InvestCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public InvestCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet){
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.InvestCircleView);
        try {
            circleRadius = typedArray.getDimensionPixelSize(R.styleable.InvestCircleView_circleRadius, 100);
            strokeWidth = typedArray.getDimensionPixelSize(R.styleable.InvestCircleView_strokeWidth, 8);
            textSize = typedArray.getDimensionPixelSize(R.styleable.InvestCircleView_textSize, 22);

            paint.setDither(true);
            paint.setColor(ContextCompat.getColor(context, R.color.colorBase));
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(strokeWidth);

            bgPaint.setDither(true);
            bgPaint.setColor(ContextCompat.getColor(context, R.color.color_dd));
            bgPaint.setStyle(Paint.Style.STROKE);
            bgPaint.setStrokeWidth(strokeWidth);

            textPaint.setColor(ContextCompat.getColor(context, R.color.color_70));
            textPaint.setTextSize(textSize);
            textPaint.setTextAlign(Paint.Align.CENTER);
        }finally {
            typedArray.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width, height;
        if (widthMode == MeasureSpec.EXACTLY){
            width = widthSize;
        }else {
            width = circleRadius * 2;
        }

        if (heightMode == MeasureSpec.EXACTLY){
            height = heightSize;
        }else {
            height = circleRadius * 2;
        }
        rectF.set(strokeWidth / 2 , strokeWidth / 2, width - strokeWidth / 2, height - strokeWidth / 2);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float startAngle = 270;
        canvas.drawArc(rectF, 0, 360, false, bgPaint);
        canvas.drawArc(rectF, startAngle, ratio * 360, false, paint);
        canvas.drawText(textRatio, circleRadius, circleRadius + textSize / 2, textPaint);
    }


    public void setRadiu(float radiu){
        this.ratio = radiu;
        this.textRatio = String.format(Locale.US, "%.2f%%", radiu * 100);
        if (radiu == 1){
            textRatio = context.getResources().getString(R.string.product_buy_button_no_product);
        }
        if (ratio < 1){
            paint.setColor(ContextCompat.getColor(context, R.color.colorBase));
            textPaint.setColor(ContextCompat.getColor(context, R.color.colorBase));
        }else {
            paint.setColor(ContextCompat.getColor(context, R.color.color_dd));
            textPaint.setColor(ContextCompat.getColor(context, R.color.color_cc));
        }
        invalidate();
    }
}
