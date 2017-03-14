package com.baluobo.common.views.gesture;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;

import com.baluobo.R;
import com.baluobo.common.tools.UIHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestureDrawline extends View {
    private int mov_x;
    private int mov_y;
    private Paint paint;
    private Canvas canvas;
    private Bitmap bitmap;
    private List<GesturePoint> list;
    private List<Pair<GesturePoint, GesturePoint>> lineList; //记录划过的线
    private Map<String, GesturePoint> autoCheckPointMap;
    private boolean isDrawEnable = true; //
    private int[] screenDispaly;
    private GesturePoint currentPoint;
    private GestureCallBack callBack;
    //当前绘制的图形
    private StringBuilder passWordSb;
    private boolean isVerify;
    //传入的密码
    private String passWord;
    private int normalColor;
    private int errorColor;

    public GestureDrawline(Context context, List<GesturePoint> list, boolean isVerify,
                           String passWord, GestureCallBack callBack) {
        super(context);
        screenDispaly = UIHelper.getScreenDisplay(context);
        paint = new Paint(Paint.DITHER_FLAG);
        bitmap = Bitmap.createBitmap(screenDispaly[0], screenDispaly[0], Bitmap.Config.ARGB_8888);
        canvas = new Canvas();
        canvas.setBitmap(bitmap);

        normalColor = ContextCompat.getColor(context, R.color.colorBase);
        errorColor = ContextCompat.getColor(context, R.color.color_c4);
        paint.setStyle(Style.STROKE);
        paint.setStrokeWidth(getResources().getDimension(R.dimen.invest_line_height));//
        paint.setColor(normalColor);
        paint.setAntiAlias(true);//

        this.list = list;
        this.lineList = new ArrayList<>();

        initAutoCheckPointMap();
        this.callBack = callBack;
        this.isVerify = isVerify;
        this.passWordSb = new StringBuilder();
        this.passWord = passWord;
    }

    private void initAutoCheckPointMap() {
        autoCheckPointMap = new HashMap<>();
        autoCheckPointMap.put("1,3", getGesturePointByNum(2));
        autoCheckPointMap.put("1,7", getGesturePointByNum(4));
        autoCheckPointMap.put("1,9", getGesturePointByNum(5));
        autoCheckPointMap.put("2,8", getGesturePointByNum(5));
        autoCheckPointMap.put("3,7", getGesturePointByNum(5));
        autoCheckPointMap.put("3,9", getGesturePointByNum(6));
        autoCheckPointMap.put("4,6", getGesturePointByNum(5));
        autoCheckPointMap.put("7,9", getGesturePointByNum(8));
    }

    private GesturePoint getGesturePointByNum(int num) {
        for (GesturePoint point : list) {
            if (point.getNum() == num) {
                return point;
            }
        }
        return null;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // super.onDraw(canvas);
        canvas.drawBitmap(bitmap, 0, 0, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isDrawEnable) {
            return true;
        }
        paint.setColor(normalColor);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mov_x = (int) event.getX();
                mov_y = (int) event.getY();
                currentPoint = getPointAt(mov_x, mov_y);
                if (currentPoint != null) {
                    currentPoint.setPointState(Constants.POINT_STATE_SELECTED);
                    passWordSb.append(currentPoint.getNum());
                }
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                clearScreenAndDrawList();
                GesturePoint pointAt = getPointAt((int) event.getX(), (int) event.getY());
                if (currentPoint == null && pointAt == null) {
                    return true;
                } else {
                    if (currentPoint == null) {
                        currentPoint = pointAt;
                        currentPoint.setPointState(Constants.POINT_STATE_SELECTED);
                        passWordSb.append(currentPoint.getNum());
                    }
                }
                if (pointAt == null || currentPoint.equals(pointAt) || Constants.POINT_STATE_SELECTED == pointAt.getPointState()) {
                    canvas.drawLine(currentPoint.getCenterX(), currentPoint.getCenterY(), event.getX(), event.getY(), paint);// ����
                } else {
                    canvas.drawLine(currentPoint.getCenterX(), currentPoint.getCenterY(), pointAt.getCenterX(), pointAt.getCenterY(), paint);// ����
                    pointAt.setPointState(Constants.POINT_STATE_SELECTED);

                    GesturePoint betweenPoint = getBetweenCheckPoint(currentPoint, pointAt);
                    if (betweenPoint != null && Constants.POINT_STATE_SELECTED != betweenPoint.getPointState()) {
                        Pair<GesturePoint, GesturePoint> pair1 = new Pair<>(currentPoint, betweenPoint);
                        lineList.add(pair1);
                        passWordSb.append(betweenPoint.getNum());
                        Pair<GesturePoint, GesturePoint> pair2 = new Pair<>(betweenPoint, pointAt);
                        lineList.add(pair2);
                        passWordSb.append(pointAt.getNum());
                        betweenPoint.setPointState(Constants.POINT_STATE_SELECTED);
                        currentPoint = pointAt;
                    } else {
                        Pair<GesturePoint, GesturePoint> pair = new Pair<>(currentPoint, pointAt);
                        lineList.add(pair);
                        passWordSb.append(pointAt.getNum());
                        currentPoint = pointAt;
                    }
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (isVerify) {
                    if (passWord.equals(passWordSb.toString())) {
                        callBack.checkedSuccess();
                    } else {
                        callBack.checkedFail();
                    }
                } else {
                    callBack.onGestureCodeInput(passWordSb.toString());
                }
                break;
            default:
                break;
        }
        return true;
    }

    public void clearDrawlineState(long delayTime) {
        if (delayTime > 0) {
            isDrawEnable = false;
            drawErrorPathTip();
        }
        new Handler().postDelayed(new clearStateRunnable(), delayTime);
    }

    final class clearStateRunnable implements Runnable {
        public void run() {
            passWordSb = new StringBuilder();
            lineList.clear();
            clearScreenAndDrawList();
            for (GesturePoint p : list) {
                p.setPointState(Constants.POINT_STATE_NORMAL);
            }
            invalidate();
            isDrawEnable = true;
        }
    }

    private GesturePoint getPointAt(int x, int y) {

        for (GesturePoint point : list) {
            int leftX = point.getLeftX();
            int rightX = point.getRightX();
            if (!(x >= leftX && x < rightX)) {
                continue;
            }

            int topY = point.getTopY();
            int bottomY = point.getBottomY();
            if (!(y >= topY && y < bottomY)) {
                continue;
            }

            return point;
        }

        return null;
    }

    private GesturePoint getBetweenCheckPoint(GesturePoint pointStart, GesturePoint pointEnd) {
        int startNum = pointStart.getNum();
        int endNum = pointEnd.getNum();
        String key;
        if (startNum < endNum) {
            key = startNum + "," + endNum;
        } else {
            key = endNum + "," + startNum;
        }
        return autoCheckPointMap.get(key);
    }

    private void clearScreenAndDrawList() {
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        for (Pair<GesturePoint, GesturePoint> pair : lineList) {
            canvas.drawLine(pair.first.getCenterX(), pair.first.getCenterY(),
                    pair.second.getCenterX(), pair.second.getCenterY(), paint);
        }
    }

    private void drawErrorPathTip() {
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        paint.setColor(errorColor);
        for (Pair<GesturePoint, GesturePoint> pair : lineList) {
            pair.first.setPointState(Constants.POINT_STATE_WRONG);
            pair.second.setPointState(Constants.POINT_STATE_WRONG);
            canvas.drawLine(pair.first.getCenterX(), pair.first.getCenterY(),
                    pair.second.getCenterX(), pair.second.getCenterY(), paint);
        }
        invalidate();
    }

}
