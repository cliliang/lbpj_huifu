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

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/25.
 */
public class InvestDetailView extends View {
    private int columnWidth;
    private int lineHeight;
    private int margin;

    private int textSize11;
    private int textSize15;
    private int textSize20;

    private int lineColor;
    private int baseColor;
    private int colorLight;
    private int textBlackColor;
    private int textHintColor;

    private int columnHeight = 50;

    private Paint linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint columnPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint columnLightPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint blackPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint hintPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private String buyText;
    private String buyMoneyText = "100.0";

    private String aprText;
    private String aprNumberText = "9.8%";

    private String incomeText;
    private String incomeMoneyText = "12.2";

    private String paybackText;
    private String paybackMoneyText = "112.2";
    public InvestDetailView(Context context) {
        super(context);
    }

    public InvestDetailView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public InvestDetailView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet){
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.InvestDetailView);
        try {
            columnWidth = typedArray.getDimensionPixelSize(R.styleable.InvestDetailView_columnWidth, 200);

            buyText = context.getResources().getString(R.string.invest_detail_buy_money);
            aprText = context.getResources().getString(R.string.APR);
            incomeText = context.getResources().getString(R.string.invest_detail_can_money);
            paybackText = context.getResources().getString(R.string.invest_detail_reback);

            textSize11 = context.getResources().getDimensionPixelSize(R.dimen.font_size_11);
            textSize15 = context.getResources().getDimensionPixelSize(R.dimen.font_size_15);
            textSize20 = context.getResources().getDimensionPixelSize(R.dimen.font_size_20);

            lineHeight = context.getResources().getDimensionPixelSize(R.dimen.invest_line_height);
            margin = context.getResources().getDimensionPixelSize(R.dimen.product_view_margin);
            lineColor = ContextCompat.getColor(context, R.color.hint_text_color);
            baseColor = ContextCompat.getColor(context, R.color.colorBase);
            colorLight = ContextCompat.getColor(context, R.color.colorPrimary);
            textBlackColor = ContextCompat.getColor(context, R.color.normal_text_color);
            textHintColor = ContextCompat.getColor(context, R.color.hint_text_color);

            linePaint.setColor(lineHeight);

            hintPaint.setColor(textHintColor);
            hintPaint.setTextAlign(Paint.Align.CENTER);
            hintPaint.setTextSize(textSize11);

            blackPaint.setColor(textBlackColor);
            blackPaint.setTextAlign(Paint.Align.CENTER);
            blackPaint.setTextSize(textSize15);

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
            width = columnWidth * 3;
        }

        if (heightMode == MeasureSpec.EXACTLY){
            height = heightSize;
        }else {
            height = 1000;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth(); int height = getHeight();
        //画底部线
        canvas.drawRect(0, height - lineHeight, width, height, linePaint);

        //画购买金额
        canvas.drawText(buyText, columnWidth / 2, height - margin, hintPaint);
        canvas.drawText(buyMoneyText, columnWidth / 2, height - margin * 2 - textSize11, blackPaint);

        //画年化收益
        canvas.drawText(aprText, columnWidth * 5 / 2, height - margin, hintPaint);
        canvas.drawText(aprNumberText, columnWidth * 5 / 2, height - margin * 2 - textSize11, blackPaint);

        //画柱状及预计还款
    }
}
