package com.baluobo.common.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.baluobo.R;
import com.baluobo.user.model.KuaiZhuan;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/26.
 */
public class KuaiZhuanMapView extends View {

    private Paint linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mapLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mapBlockPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path mapPath = new Path();

    private int mapMargin;
    private int xMarginBottom;
    private int textMargin;
    private int textMarginBottom;
    private int colorWhite;

    private int totalStringMarginTop;

    private String dateString;
    private String incomeString;

    private List<Float> incomeList = new ArrayList<>();
    private List<String> dayList = new ArrayList<>();

    private Point[] points = new Point[5];

    public KuaiZhuanMapView(Context context) {
        super(context);
        init(context);
    }

    public KuaiZhuanMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public KuaiZhuanMapView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        linePaint.setColor(ContextCompat.getColor(context, R.color.color_cf));
        linePaint.setStrokeWidth(2);

        textPaint.setColor(ContextCompat.getColor(context, R.color.color_40));
        textPaint.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.font_size_9));
        textPaint.setTextAlign(Paint.Align.RIGHT);

        mapLinePaint.setColor(ContextCompat.getColor(context, R.color.color_map_line));
        mapLinePaint.setStrokeWidth(2);

        mapBlockPaint.setColor(ContextCompat.getColor(context, R.color.color_map_block));
        mapBlockPaint.setStyle(Paint.Style.FILL);

        dateString = context.getString(R.string.date);
        incomeString = context.getString(R.string.total_income);

        mapMargin = context.getResources().getDimensionPixelSize(R.dimen.kuai_zhuan_map_margin);
        xMarginBottom = context.getResources().getDimensionPixelSize(R.dimen.kuai_zhuan_map_margin_bottom);
        textMargin = context.getResources().getDimensionPixelSize(R.dimen.product_view_margin);
        textMarginBottom = context.getResources().getDimensionPixelSize(R.dimen.kuai_zhuan_text_margin_bottom);
        totalStringMarginTop = context.getResources().getDimensionPixelSize(R.dimen.map_total_income_margin_top);

        colorWhite = ContextCompat.getColor(context, R.color.white);

        for (int i = 0; i < points.length; i++){
            points[i] = new Point();
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
            width = 720;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = 1000;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(colorWhite);
        int width = getWidth();
        int height = getHeight();
        //画X轴
        canvas.drawLine(mapMargin, height - xMarginBottom, width - mapMargin, height - xMarginBottom, linePaint);
        //画Y轴
        canvas.drawLine(mapMargin, height - xMarginBottom, mapMargin, mapMargin, linePaint);

        //画y轴 累计收益
        textPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(incomeString, mapMargin, totalStringMarginTop, textPaint);

        //画x轴 表示日期
        textPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(dateString, width - mapMargin + mapMargin / 2, height - xMarginBottom + 5, textPaint);

        if (incomeList.size() == 0 || dayList.size() == 0){
            return;
        }
        //画Y轴收入值
        float max = getMaxY();
        if (max > 0){
            textPaint.setTextAlign(Paint.Align.RIGHT);
            float incomeWidth = (max - getMinY()) / 5;
            int yWidth = (height - mapMargin - xMarginBottom) / 5;
            for (int i = 0; i < incomeList.size() + 1; i++) {
                float yValue = getMinY() + (i * incomeWidth);
                int y = height - xMarginBottom - i * yWidth;
                canvas.drawText(String.format(Locale.US, "%.2f", yValue), mapMargin - textMargin, y, textPaint);
            }
        }

        //画X轴日期值
        textPaint.setTextAlign(Paint.Align.CENTER);
        int xWidth = (width - 2 * mapMargin) / 4;
        for (int j = 0; j < dayList.size(); j++) {
            canvas.drawText(dayList.get(j), mapMargin + j * xWidth, height - textMarginBottom, textPaint);
        }

        //画折线
        if (max > 0){
            if (max == getMinY()){
                return;
            }
            for (int k = 0; k < incomeList.size(); k++) {
                float pointX = mapMargin + (width - 2 * mapMargin) / 4 * k;
                float pointY = mapMargin + getYHeight(incomeList.get(k), height - mapMargin - xMarginBottom);
                points[k].set((int) pointX, (int) pointY);
            }

            for (int n = 1; n < incomeList.size(); n++) {
                canvas.drawLine(points[n - 1].x, points[n - 1].y, points[n].x, points[n].y, mapLinePaint);
            }

            //画折线区域
            mapPath.moveTo(points[0].x, points[0].y);
            for (int m = 1; m < incomeList.size(); m++){
                mapPath.lineTo(points[m].x, points[m].y);
            }
            mapPath.lineTo(points[incomeList.size() - 1].x, height - xMarginBottom);
            mapPath.lineTo(mapMargin, height - xMarginBottom);
            mapPath.close();
            canvas.drawPath(mapPath, mapBlockPaint);
        }
    }

    private float getMinY() {
//        if (incomeList.size() < 1){
//            return 0;
//        }
//        float min = incomeList.get(0);
//        for (float value : incomeList){
//            if (min > value){
//                min = value;
//            }
//        }
        return 0;
    }

    private float getMaxY() {
        if (incomeList.size() == 0){
            return 0;
        }

        float max = 0;
        for (float value : incomeList){
            if (max < value){
                max = value;
            }
        }
        return max * 5 / 4;
    }

    private float getYHeight(float value, int yHeight) {
        return (getMaxY() - value) * yHeight / (getMaxY() - getMinY());
    }

    public void setMapData(List<KuaiZhuan> list){
        if (list == null && list.size() == 0){
            return;
        }
        incomeList.clear();
        dayList.clear();
        float totalIncome = 0;
        for (KuaiZhuan kuaiZhuan : list){
            totalIncome = totalIncome + kuaiZhuan.getIncomeSum();
            incomeList.add(kuaiZhuan.getIncomeSum());
            dayList.add(kuaiZhuan.getCreateTime());
        }
        invalidate();
    }
}
