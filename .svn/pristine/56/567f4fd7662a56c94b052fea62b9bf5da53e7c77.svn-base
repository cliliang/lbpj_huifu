package com.baluobo.common.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.baluobo.R;
import com.baluobo.user.model.TradingRecord;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/24.
 */
public class TradingCircleView extends View {
    private Paint circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int circleRadius;
    private int circleColor;
    private int textSize;
    private String typeText = "投资";
    private Context mContext;
    public TradingCircleView(Context context) {
        super(context);
    }

    public TradingCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TradingCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        setMeasuredDimension(width, height);
    }

    private void init(Context cnt, AttributeSet attributeSet){
        this.mContext = cnt;
        TypedArray typedArray = cnt.obtainStyledAttributes(attributeSet, R.styleable.TradingCircleView);
        try {
            circleRadius = typedArray.getDimensionPixelSize(R.styleable.TradingCircleView_tradingCircleRadius, 50);
            circleColor = ContextCompat.getColor(cnt, R.color.white);

            circlePaint.setStyle(Paint.Style.FILL);
            circlePaint.setColor(ContextCompat.getColor(cnt, R.color.colorBase));

            textPaint.setColor(circleColor);
            textPaint.setTextAlign(Paint.Align.CENTER);
        }finally {
            typedArray.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(circleRadius, circleRadius, circleRadius, circlePaint);

        if (typeText.length() == 2){
            textSize = mContext.getResources().getDimensionPixelSize(R.dimen.font_size_11);
            textPaint.setTextSize(textSize);
            canvas.drawText(typeText, circleRadius, circleRadius + textSize / 2, textPaint);
        }else {
            textSize = mContext.getResources().getDimensionPixelSize(R.dimen.font_size_8);
            textPaint.setTextSize(textSize);
            if (typeText.length() == 4){
                canvas.drawText(typeText.substring(0, 2), circleRadius, circleRadius, textPaint);
                canvas.drawText(typeText.substring(2, 4), circleRadius, circleRadius + textSize, textPaint);
            }
        }
    }

    public void setCircleData(TradingRecord record){
        if (record == null){
            return;
        }
        int isSuccess = record.getFlg();
        int type = record.getType();

        int circleColor;
        switch (type){
            case 1:
                circleColor = ContextCompat.getColor(mContext, R.color.color_invest);
                break;
            case 2:
                circleColor = ContextCompat.getColor(mContext, R.color.color_withdraw);
                break;
            case 3:
                circleColor = ContextCompat.getColor(mContext, R.color.color_recharge);
                break;
            case 4:
                circleColor = ContextCompat.getColor(mContext, R.color.color_redeem);
                break;
            case 5:
                circleColor = ContextCompat.getColor(mContext, R.color.color_repayment);
                break;
            default:
                circleColor = ContextCompat.getColor(mContext, R.color.color_fail_action);
                break;
        }
        if(isSuccess == 0){
            circleColor = ContextCompat.getColor(mContext, R.color.color_fail_action);
        }
        circlePaint.setColor(circleColor);
        typeText = record.getMessage();
        invalidate();
    }
}
