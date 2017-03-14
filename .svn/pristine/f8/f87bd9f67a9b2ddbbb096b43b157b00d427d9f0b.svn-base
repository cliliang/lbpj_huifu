package com.baluobo.common.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.baluobo.R;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/26.
 */
public class SecurityView extends View {
    private int circleRadius;
    private int strokeWidth;
    private int colorBackground;
    private float sweepAngle = 0;

    private int colorR, colorG, colorB;
    private int colorBg;
    private int textSize;

    private String rankText = "";

    private Paint circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint arcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint dotPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private RectF rectF = new RectF();

    public SecurityView(Context context) {
        super(context);
    }

    public SecurityView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SecurityView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.SecurityView);
        try {
            circleRadius = typedArray.getDimensionPixelSize(R.styleable.SecurityView_securityCircleRadius, 90);
            strokeWidth = typedArray.getDimensionPixelSize(R.styleable.SecurityView_securityCircleStroke, 10);
            colorBackground = typedArray.getColor(R.styleable.SecurityView_securityColorBackground, 0xffffff);

            circlePaint.setStyle(Paint.Style.STROKE);
            circlePaint.setStrokeWidth(strokeWidth);

            arcPaint.setStyle(Paint.Style.FILL);

            textPaint.setTextAlign(Paint.Align.CENTER);
            textSize = context.getResources().getDimensionPixelSize(R.dimen.font_size_24);
            textPaint.setTextSize(textSize);

            dotPaint.setStyle(Paint.Style.FILL);
            dotPaint.setColor(ContextCompat.getColor(context, R.color.white));

            colorBg = ContextCompat.getColor(context, R.color.default_background);
        } finally {
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
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = circleRadius * 2;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = circleRadius * 2;
        }
        rectF.set(strokeWidth / 2, strokeWidth / 2, width - strokeWidth / 2, height - strokeWidth / 2);
        setMeasuredDimension(width, height);
    }

    public void setData(String rank, float angle, int r, int g, int b) {
        this.sweepAngle = angle;
        this.colorR = r;
        this.colorG = g;
        this.colorB = b;
        this.rankText = rank;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(colorBg);

        float startAngle = 150f;
        int radius = circleRadius - strokeWidth / 2;
        circlePaint.setColor(colorBackground);
        //画底部灰色圆弧
        canvas.drawArc(rectF, startAngle, 240f, false, circlePaint);

        //画开始与结束的小圆
        arcPaint.setColor(colorBackground);
        canvas.drawCircle(circleRadius - (float) Math.cos(Math.PI - startAngle / 180 * Math.PI) * radius, circleRadius + (float) Math.sin(Math.PI - startAngle / 180 * Math.PI) * radius, strokeWidth / 2, arcPaint);
        canvas.drawCircle(circleRadius - (float) Math.cos(Math.PI - (startAngle + 240f) / 180 * Math.PI) * radius, circleRadius + (float) Math.sin(Math.PI - (startAngle + 240) / 180 * Math.PI) * radius, strokeWidth / 2, arcPaint);

        int dotRadius = 3;
        //画扫过的角度圆弧
        circlePaint.setColor(Color.rgb(colorR, colorG, colorB));
        canvas.drawArc(rectF, startAngle, sweepAngle, false, circlePaint);

        //画中间字
        textPaint.setColor(Color.rgb(colorR, colorG, colorB));
        canvas.drawText(rankText, circleRadius, circleRadius + textSize / 2, textPaint);

        //画开始的小圆
        arcPaint.setColor(Color.rgb(colorR, colorG, colorB));
        canvas.drawCircle(circleRadius - (float) Math.cos(Math.PI - startAngle / 180 * Math.PI) * radius, circleRadius + (float) Math.sin(Math.PI - startAngle / 180 * Math.PI) * radius, strokeWidth / 2, arcPaint);
        canvas.drawCircle(circleRadius - (float) Math.cos(Math.PI - (startAngle + sweepAngle) / 180 * Math.PI) * radius, circleRadius + (float) Math.sin(Math.PI - (startAngle + sweepAngle) / 180 * Math.PI) * radius, strokeWidth / 2, arcPaint);

        //画结束时的小白点
        canvas.drawCircle(circleRadius - (float) Math.cos(Math.PI - (startAngle + sweepAngle) / 180 * Math.PI) * radius, circleRadius + (float) Math.sin(Math.PI - (startAngle + sweepAngle) / 180 * Math.PI) * radius, dotRadius, dotPaint);
    }
}
