package com.baluobo.product.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.baluobo.R;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/8/10.
 */
public class XfermodeView extends View {
    private Bitmap fgBitmap;
    private Paint mPaint;
    private Canvas mCanvas;
    private Path mPath = new Path();
    private Bitmap blankBitmap;
    private boolean touch = false;
    public XfermodeView(Context context) {
        super(context);
    }

    public XfermodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public XfermodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        mPaint = new Paint();
        mPaint.setAlpha(0);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(50);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        fgBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.gua_gua_fg);
        blankBitmap = Bitmap.createBitmap(fgBitmap.getWidth(), fgBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(blankBitmap);
        mCanvas.drawBitmap(fgBitmap, 0, 0, null);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(blankBitmap, 0, 0, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mPath.reset();
                mPath.moveTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(event.getX(), event.getY());
                if (!touch){
                    touch = true;
                }
                break;
        }
        mCanvas.drawPath(mPath, mPaint);
        invalidate();
        return true;
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
            width = fgBitmap.getWidth();
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = fgBitmap.getHeight();
        }
        setMeasuredDimension(width, height);
    }
}
