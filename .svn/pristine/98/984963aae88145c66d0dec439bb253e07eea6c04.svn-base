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
public class ProductCircleView extends View {
    private Context context;

    private int largeCircleRadius;
    private int largeStrokeWidth;

    private int blackTextColor;
    private int redTextColor;

    private int textReterestSize;
    private int numberReterestSize;

    private float radius = 0.84873f;
    private Paint largebgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint largePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private RectF rectF = new RectF();
    public ProductCircleView(Context context) {
        super(context);
    }

    public ProductCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ProductCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet){
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.ProductCircleView);
        try {
            largeCircleRadius = typedArray.getDimensionPixelSize(R.styleable.ProductCircleView_largeCircleRadius, 200);
            largeStrokeWidth = typedArray.getDimensionPixelSize(R.styleable.ProductCircleView_largeStrokeWidth, 10);
            blackTextColor = typedArray.getColor(R.styleable.ProductCircleView_blackTextColor, 0x000000);
            redTextColor = typedArray.getColor(R.styleable.ProductCircleView_redTextColor, 0xff0000);
        }finally {
            typedArray.recycle();
        }
        largebgPaint.setColor(ContextCompat.getColor(context, R.color.default_background));
        largebgPaint.setStyle(Paint.Style.STROKE);
        largebgPaint.setStrokeWidth(largeStrokeWidth);

        largePaint.setColor(ContextCompat.getColor(context, R.color.colorBase));
        largePaint.setStyle(Paint.Style.STROKE);
        largePaint.setStrokeWidth(largeStrokeWidth);

        textPaint.setTextAlign(Paint.Align.CENTER);
        textReterestSize = context.getResources().getDimensionPixelSize(R.dimen.font_size_11);
        numberReterestSize = context.getResources().getDimensionPixelSize(R.dimen.font_size_13);
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
            width = largeCircleRadius * 2;
        }

        if (heightMode == MeasureSpec.EXACTLY){
            height = heightSize;
        }else {
            height = largeCircleRadius * 2;
        }
        rectF.set(width /2 - largeCircleRadius + largeStrokeWidth / 2, largeStrokeWidth / 2, width / 2 + largeCircleRadius - largeStrokeWidth / 2, height - largeStrokeWidth / 2);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float startAngle = 150f;
        //画底部大圆
        canvas.drawArc(rectF, startAngle, 240f, false, largebgPaint);

        canvas.drawArc(rectF, startAngle, getAngle(), false, largePaint);

        textPaint.setColor(blackTextColor);
        textPaint.setTextSize(textReterestSize);
        String text = getRadiusText();
        canvas.drawText(text, largeCircleRadius, largeCircleRadius, textPaint);

        float textWidth = textPaint.measureText(text);


    }

    public void setRadius(float radius){
        this.radius = radius;
    }

    private float getAngle(){
        return radius * 240;
    }

    private String getRadiusText(){
        return String.format(Locale.US, "%.2f", radius * 100);
    }
}
