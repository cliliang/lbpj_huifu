package com.baluobo.product.views;

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
 * Created on:16/11/15.
 */
public class ProductItemCircleView extends View {
    private int circleRadius;
    private int strokeWidth;
    private int textSize;
    private int canBuyMoney = 0;
    private float ratio = 0;
    private float moneyTextSize;
    private float buyTextSize;
    private boolean drawCanBuy = false;


    private Context context;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//    private Paint salePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint moneyPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint buyTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private RectF rectF = new RectF();
    public ProductItemCircleView(Context context) {
        super(context);
    }

    public ProductItemCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ProductItemCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet){
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.ProductItemCircleView);
        try {
            circleRadius = typedArray.getDimensionPixelSize(R.styleable.ProductItemCircleView_itemCircleRadius, 100);
            strokeWidth = typedArray.getDimensionPixelSize(R.styleable.ProductItemCircleView_itemStrokeWidth, 8);

            paint.setDither(true);
            paint.setColor(ContextCompat.getColor(context, R.color.colorBase));
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(strokeWidth);

//            salePaint.setDither(true);
//            salePaint.setColor(ContextCompat.getColor(context, R.color.product_circle_sale_color));
//            salePaint.setStyle(Paint.Style.STROKE);
//            salePaint.setStrokeWidth(strokeWidth);

            bgPaint.setDither(true);
            bgPaint.setColor(ContextCompat.getColor(context, R.color.color_e6));
            bgPaint.setStyle(Paint.Style.STROKE);
            bgPaint.setStrokeWidth(strokeWidth);

            textPaint.setColor(ContextCompat.getColor(context, R.color.color_70));
            textSize = context.getResources().getDimensionPixelSize(R.dimen.font_size_15);
            textPaint.setTextSize(textSize);
            textPaint.setTextAlign(Paint.Align.CENTER);

            moneyPaint.setColor(ContextCompat.getColor(context, R.color.color_40));
            moneyTextSize = context.getResources().getDimensionPixelSize(R.dimen.font_size_15);
            moneyPaint.setTextSize(moneyTextSize);
            moneyPaint.setTextAlign(Paint.Align.CENTER);

            buyTextPaint.setColor(ContextCompat.getColor(context, R.color.color_92));
            buyTextSize = context.getResources().getDimensionPixelSize(R.dimen.font_size_11);
            buyTextPaint.setTextSize(buyTextSize);
            buyTextPaint.setTextAlign(Paint.Align.CENTER);
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
        if (drawCanBuy){
//            canvas.drawArc(rectF, 0, 360, false, salePaint);
            canvas.drawArc(rectF, startAngle, ratio * 360, false, paint);
            canvas.drawText(String.valueOf(canBuyMoney), circleRadius, circleRadius - 10, moneyPaint);
            canvas.drawText(context.getString(R.string.product_item_circle_text), circleRadius, circleRadius + 10 + (int)buyTextSize, buyTextPaint);
        }else{
            canvas.drawText(context.getResources().getString(R.string.product_buy_button_no_product), circleRadius, circleRadius + textSize / 2, textPaint);
        }
    }


    public void setRadiu(int canBuy, float radius){
        this.ratio = radius;
        this.canBuyMoney = canBuy;
        if (radius == 1){
            this.drawCanBuy = false;
        }else{
            this.drawCanBuy = true;
        }
        invalidate();
    }
}
