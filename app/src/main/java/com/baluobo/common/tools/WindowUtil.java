package com.baluobo.common.tools;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;

import com.baluobo.R;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/6/12.
 */
public class WindowUtil {
    public static final int TYPE_MINE = 1;
    public static final int TYPE_INVEST = 2;
    public static final int TYPE_BUY = 3;
    private static View mView = null;
    private static WindowManager mWindowManager = null;
    public static Boolean isShown = false;

    /**
     * 显示弹出框
     *
     * @param context
     */
    public static void showPopupWindow(Context context, int type) {
        if (isShown) {
            return;
        }
        isShown = true;
        // 获取WindowManager
        mWindowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);

        mView = setUpView(context, type);

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.gravity = Gravity.LEFT | Gravity.TOP;   //调整悬浮窗口至左上角
        //以屏幕左上角为原点，设置x、y初始值
        params.x = 0;
        params.y = 0;

        // 类型
        params.type = LayoutParams.TYPE_SYSTEM_ALERT;

        // 设置flag

        int flags = WindowManager.LayoutParams.FLAG_FULLSCREEN | LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        // | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        // 如果设置了WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE，弹出的View收不到Back键的事件
        params.flags = flags;
        // 不设置这个弹出框的透明遮罩显示为黑色
        params.format = PixelFormat.TRANSLUCENT;
        // FLAG_NOT_TOUCH_MODAL不阻塞事件传递到后面的窗口
        // 设置 FLAG_NOT_FOCUSABLE 悬浮窗口较小时，后面的应用图标由不可长按变为可长按
        // 不设置这个flag的话，home页的划屏会有问题

        params.width = LayoutParams.MATCH_PARENT;
        params.height = LayoutParams.MATCH_PARENT;

        params.gravity = Gravity.CENTER;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mWindowManager.addView(mView, params);
        }
    }

    /**
     * 隐藏弹出框
     */
    public static void hidePopupWindow() {
        if (isShown && null != mView) {
            mWindowManager.removeView(mView);
            isShown = false;
        }

    }

    private static View setUpView(final Context context, int type) {
        View view = LayoutInflater.from(context).inflate(R.layout.show_use_app_guide_layout, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.show_use_guide_image);
        if (type == TYPE_BUY){
            imageView.setImageResource(R.drawable.masking_buy);
        }else if (type == TYPE_INVEST){
            imageView.setImageResource(R.drawable.masking_invest);
        }else{
            imageView.setImageResource(R.drawable.masking_mine);
        }

        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hidePopupWindow();
            }
        });

        // 点击back键可消除
        view.setOnKeyListener(new OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_BACK:
                        hidePopupWindow();
                        return true;
                    default:
                        return false;
                }
            }
        });

        return view;

    }
}
