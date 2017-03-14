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

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/17.
 */
public class TotalAssetsView extends View {
    private int outsideRadius;
    private int insideRadius;
    private float[] assetsArray = new float[6];
    private float maxAngle;
    private Paint assetsPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private RectF rectF = new RectF();
    private Context context;

    public TotalAssetsView(Context context) {
        super(context);
    }

    public TotalAssetsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TotalAssetsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context cnt, AttributeSet attributeSet) {
        this.context = cnt;
        TypedArray typedArray = cnt.obtainStyledAttributes(attributeSet, R.styleable.TotalAssetsView);
        try {
            outsideRadius = typedArray.getDimensionPixelSize(R.styleable.TotalAssetsView_outsideRadius, 150);
            insideRadius = typedArray.getDimensionPixelSize(R.styleable.TotalAssetsView_insideRadius, 75);
            assetsPaint.setDither(true);
            assetsPaint.setStyle(Paint.Style.STROKE);
            assetsPaint.setStrokeWidth(outsideRadius - insideRadius);
        } finally {
            typedArray.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width, height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = outsideRadius * 2;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = outsideRadius * 2;
        }
        int strokeWidth = outsideRadius - insideRadius;
        rectF.set(strokeWidth / 2, strokeWidth / 2, width - strokeWidth / 2, height - strokeWidth / 2);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float total = 0;
        for (float number : assetsArray) {
            total += number;
        }
        if (total == 0) {
            total = 1;
            assetsArray[5] = 1;
        }

        float startAngle = 0;
        for (int i = 0; i < assetsArray.length; i++) {
            float sweepAngle = sweepAngle(assetsArray[i], total);
            if (sweepAngle == 0) {
                continue;
            }
            if (i == 0) {
                assetsPaint.setColor(ContextCompat.getColor(context, R.color.color_luobo_kuai_zhuan));
            } else if (i == 1) {
                assetsPaint.setColor(ContextCompat.getColor(context, R.color.color_luobo_ding_tou));
            } else if (i == 2) {
                assetsPaint.setColor(ContextCompat.getColor(context, R.color.color_luobo_xin_shou));
            } else if (i == 3) {
                assetsPaint.setColor(ContextCompat.getColor(context, R.color.color_luobo_yin_piao));
            } else if (i == 4) {
                assetsPaint.setColor(ContextCompat.getColor(context, R.color.color_luobo_exp_color));
            } else if (i == 5) {
                assetsPaint.setColor(ContextCompat.getColor(context, R.color.color_luobo_usable_money));
            }
            if (startAngle + sweepAngle < maxAngle) {
                canvas.drawArc(rectF, startAngle, sweepAngle, false, assetsPaint);
            } else {
                canvas.drawArc(rectF, startAngle, maxAngle - startAngle, false, assetsPaint);
                break;
            }
            startAngle += sweepAngle;
        }
    }

    private float sweepAngle(float money, float total) {
        return money / total * 360f;
    }

    /**
     * 数据表示的顺序为萝卜快赚、萝卜定投、 新手快赚、银票苗、可用余额
     *
     * @param assets
     */
    public void setTotalAssets(float[] assets) {
        this.assetsArray = assets;
        invalidate();
    }

    public void setMaxAngle(float angle) {
        this.maxAngle = angle;
        invalidate();
    }

}
