package com.baluobo.app.ui;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baluobo.R;
import com.baluobo.app.AppManager;
import com.baluobo.user.router.UserUI;

/**
 * desc: Activity需要引导栏时，以这个类为父类
 * Created by:chenliliang
 * Created on:16/5/4.
 */
public class BaseToolBarActivity extends BaseActivity {
    private TextView titleView;
    private LinearLayout rightLayout;
    private static int[] ATTRS = {
            R.attr.windowActionBarOverlay,
            R.attr.actionBarSize
    };
    @Override
    public void setContentView(int layoutResID) {
        FrameLayout rootLayout = new FrameLayout(this);
        ViewGroup.LayoutParams rootParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rootLayout.setLayoutParams(rootParams);

        LayoutInflater inflater = LayoutInflater.from(this);

        View userView = inflater.inflate(layoutResID, null);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        TypedArray typedArray = getTheme().obtainStyledAttributes(ATTRS);
        /*获取主题中定义的悬浮标志*/
        boolean overly = typedArray.getBoolean(0, false);
        /*获取主题中定义的toolbar的高度*/
//        int toolBarSize = (int) typedArray.getDimension(1,(int)getResources().getDimension(R.dimen.abc_action_bar_default_height_material));
        typedArray.recycle();
        /*如果是悬浮状态，则不需要设置间距*/
        int actionBarHeight = getResources().getDimensionPixelSize(R.dimen.actionbar_default_height);
        params.topMargin = overly ? 0 : actionBarHeight;
        rootLayout.addView(userView, params);

        View toolbarRoot = inflater.inflate(R.layout.toolbar_layout, rootLayout);
        Toolbar toolbar = (Toolbar) toolbarRoot.findViewById(R.id.id_tool_bar);
        toolbar.setTitle("");
        titleView = (TextView) toolbarRoot.findViewById(R.id.base_toolbar_title);
        rightLayout = (LinearLayout) toolbarRoot.findViewById(R.id.base_right_icon_layout);
        toolbarRoot.findViewById(R.id.base_navigation_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackClick();
            }
        });

        setContentView(rootLayout);
        setSupportActionBar(toolbar);
    }

    public void setTitle(String title){
        if (titleView != null && !TextUtils.isEmpty(title)){
            titleView.setText(title);
        }
    }

    public void onBackClick(){
        AppManager.getAppManager().finishActivity(this);
    }

    public void setRightTextMenu(String menu, View.OnClickListener listener){
        if (rightLayout != null){
            TextView textView = (TextView) getLayoutInflater().inflate(R.layout.common_action_bar_rigth_text_menu_layout, null);
            if (textView != null){
                textView.setText(menu);
                textView.setOnClickListener(listener);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                rightLayout.addView(textView, lp);
            }
        }
    }

    /**
     * 用于界面手打电话
     */
    public void showHotLine() {
        final Dialog dialog = new Dialog(this, R.style.DialogStyle);
        View view = LayoutInflater.from(this).inflate(R.layout.show_hot_line_dialog_layout, null);
        view.findViewById(R.id.call_hot_line_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        view.findViewById(R.id.call_hot_line_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                callHotLine();
            }
        });
        dialog.setContentView(view);
        dialog.show();
    }

    private final int CALL_PHONE_REQUEST_CODE = UserUI.ContactUs + 1;
    public void callHotLine() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, CALL_PHONE_REQUEST_CODE);
        } else {
            callPhone();
        }
    }

    public void callPhone() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + "4008258626");
        intent.setData(data);
        try {
            startActivity(intent);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == CALL_PHONE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callPhone();
            } else {
                // Permission Denied
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
