package com.baluobo.common.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.BoolRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.baluobo.R;

import java.util.Calendar;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/20.
 */
public class ScaleMoneyView extends View{
    private Context context;
    private Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int onDrawNumber = 0;
    private int screenWidth;
    private int viewWidth;
    private int blockWidth;
    private int lineWidth;
    private int shortLineHeight;
    private int blockNumber = 0;
    private int textMargin;
    public ScaleMoneyView(Context context) {
        super(context);
        init(context);
    }

    public ScaleMoneyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ScaleMoneyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        this.context = context;
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        screenWidth = wm.getDefaultDisplay().getWidth();

        viewWidth = 1;
        blockWidth = context.getResources().getDimensionPixelSize(R.dimen.buy_rule_height_line_height);
        shortLineHeight = context.getResources().getDimensionPixelSize(R.dimen.buy_rule_low_line_height);
        lineWidth = context.getResources().getDimensionPixelSize(R.dimen.buy_rule_line_width);

        linePaint.setColor(ContextCompat.getColor(context, R.color.color_dd));
        linePaint.setStyle(Paint.Style.FILL);
        linePaint.setStrokeWidth(lineWidth);

        textPaint.setColor(ContextCompat.getColor(context, R.color.color_40));
        textPaint.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.font_size_8));
        textPaint.setTextAlign(Paint.Align.CENTER);

        textMargin = context.getResources().getDimensionPixelSize(R.dimen.buy_rule_text_margin_top);

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
            width = viewWidth;
        }

        if (heightMode == MeasureSpec.EXACTLY){
            height = heightSize;
        }else {
            height = 200;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        if (onDrawNumber == 1){
//            onDrawNumber++;
//            return;
//        }

        canvas.drawColor(ContextCompat.getColor(context, R.color.white));
        int startX = screenWidth / 2 - blockWidth;
        int stopY;
        for (int i = 0; i < blockNumber; i++){
            if ( (i % 5) == 0){
                stopY = blockWidth;
                canvas.drawText(String.valueOf(i * 100), startX + i * blockWidth, textMargin, textPaint);
            }else {
                stopY = shortLineHeight;
            }
            canvas.drawLine(startX + i * blockWidth, 0, startX + i * blockWidth, stopY, linePaint);
        }
    }


    public void setData(float totalMoney){
        onDrawNumber = 1;
        viewWidth = ((int)(totalMoney / 100) - 1) * blockWidth + screenWidth;
        blockNumber = (int) (totalMoney / 100) + 1;
        invalidate();
    }
}
