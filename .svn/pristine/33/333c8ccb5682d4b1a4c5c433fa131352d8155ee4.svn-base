package com.baluobo.product.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.TextView;

import com.baluobo.R;


/**
 * desc:
 * Created by:chenliliang
 * Created on:16/12/20.
 */
public class KuaiZhuanProgress extends TextView {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int colorWhite;
    private int colorBaseWidth, colorWhiteWidth;
    private RectF rectF1 = new RectF();
    private RectF rectF2 = new RectF();
    private Path basePath = new Path();

    public KuaiZhuanProgress(Context context) {
        super(context);
    }

    public KuaiZhuanProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public KuaiZhuanProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet){
        int colorBase = ContextCompat.getColor(context, R.color.colorBase);
        colorWhite = ContextCompat.getColor(context, R.color.white);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(colorBase);
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.KuaiZhuanProgress);
        try {
            colorBaseWidth = typedArray.getDimensionPixelSize(R.styleable.KuaiZhuanProgress_colorBaseWidth, 10);
            colorWhiteWidth = typedArray.getDimensionPixelSize(R.styleable.KuaiZhuanProgress_colorWhiteWidth, 2);
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
            width = 50;
        }

        if (heightMode == MeasureSpec.EXACTLY){
            height = heightSize;
        }else {
            height = 100;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        basePath.moveTo(height / 2, 0);
        rectF1.set(0, 0, height, height);
        basePath.arcTo(rectF1, -90, -180);
        basePath.lineTo(width - height / 2, height);

        rectF2.set(width - height, 0, width, height);
        basePath.arcTo(rectF2, 90, -180);
        basePath.close();
        canvas.clipPath(basePath, Region.Op.INTERSECT);
        basePath.reset();

        canvas.drawColor(colorWhite);

        int xMove = 0;

        do {
            basePath.moveTo(xMove, 0);
            basePath.lineTo(xMove - colorBaseWidth, height);
            basePath.lineTo(xMove, height);
            basePath.lineTo(xMove + colorBaseWidth, 0);
            basePath.close();
            canvas.drawPath(basePath, paint);
            xMove += (colorBaseWidth + colorWhiteWidth);
            basePath.reset();
        }while (xMove < width);

    }
}
