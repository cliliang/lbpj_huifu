package com.baluobo.common.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.baluobo.R;

import java.util.Locale;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/24.
 */
public class ProductInfoCircleView extends View {
    private int circleRadius;
    private int circleColor;
    private int arcColor;
    private int textSize;

    private double startAngle = 0;
    private double endAngle = 0;
    private String text= "0.0%";
    private RectF rectF = new RectF();
    private Paint circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public ProductInfoCircleView(Context context) {
        super(context);
    }

    public ProductInfoCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ProductInfoCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
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
        rectF.set(0, 0, width, height);
        setMeasuredDimension(width, height);
    }

    private void init(Context context, AttributeSet attributeSet){
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.ProductInfoCircleView);
        try {
            circleRadius = typedArray.getDimensionPixelSize(R.styleable.ProductInfoCircleView_infoCircleRadius, 60);
            circleColor = typedArray.getColor(R.styleable.ProductInfoCircleView_infoCircleColor, 0x000000);
            arcColor = typedArray.getColor(R.styleable.ProductInfoCircleView_infoArcColor, 0xffffff);
            textSize = typedArray.getDimensionPixelSize(R.styleable.ProductInfoCircleView_infoTextSize, 32);

            circlePaint.setStrokeJoin(Paint.Join.ROUND);
            circlePaint.setStrokeCap(Paint.Cap.ROUND);

            textPaint.setColor(ContextCompat.getColor(context, R.color.white));
            textPaint.setTextSize(textSize);
            textPaint.setTextAlign(Paint.Align.CENTER);

        }finally {
            typedArray.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        circlePaint.setColor(circleColor);
        canvas.drawCircle(circleRadius, circleRadius, circleRadius, circlePaint);

        circlePaint.setColor(arcColor);
        canvas.drawArc(rectF, (float) startAngle, (float)endAngle, false, circlePaint);

        canvas.drawText(text, circleRadius, circleRadius + textSize / 2, textPaint);
    }

    public void setRatio(float ratio){
        double start = Math.asin((circleRadius - circleRadius  * 2 * ratio) / circleRadius);
        startAngle = 180 * start / Math.PI;
        endAngle = 180 - 2 * startAngle;
        if (ratio == 1){
            text = String.format(Locale.US, "%.0f%%", ratio * 100);
        }else {
            text = String.format(Locale.US, "%.2f%%", ratio * 100);
        }
        invalidate();
    }
}
