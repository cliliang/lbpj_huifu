package com.baluobo.app.ui;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.baluobo.R;
import com.baluobo.app.AppContext;
import com.baluobo.app.AppManager;
import com.baluobo.app.flux.AppActionsCreator;
import com.baluobo.app.flux.Dispatcher;
import com.baluobo.common.db.LuoBoDBM;
import com.baluobo.common.net.APIClient;
import com.baluobo.common.tools.LoadingDialog;
import com.baluobo.user.model.User;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

import java.util.Locale;

/**
 * desc:所有Activity的父类，做Activity的全局处理
 * Created by:chenliliang
 * Created on:16/5/4.
 */
public class BaseActivity extends AppCompatActivity {
    public String[] mSharePermissionList = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CALL_PHONE,Manifest.permission.READ_LOGS,Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.SET_DEBUG_APP,Manifest.permission.SYSTEM_ALERT_WINDOW};
    public Dispatcher dispatcher;
    private LoadingDialog loadingDialog;
    public AppContext appContext;
    public AppActionsCreator appActionsCreator;
    private Dialog signDialog;
    private Handler dialogHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (signDialog != null && signDialog.isShowing()){
                signDialog.dismiss();
            }
        }
    };
    public User mUser;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PushAgent.getInstance(this).onAppStart();
        AppManager.getAppManager().addActivity(this);
        loadingDialog = new LoadingDialog.Build(this).create();
        appContext = (AppContext) getApplicationContext();
    }

    protected void initDependencies(){
        dispatcher = Dispatcher.get();
        appActionsCreator = AppActionsCreator.getInstance(dispatcher, appContext);
    }

    protected void setupViews(){

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        mUser = appContext.getUser();
    }

    @Override
    protected void onPause() {
        hideSoftInput();
        MobclickAgent.onPause(this);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        AppManager.getAppManager().finishActivity(this);
        super.onDestroy();
    }

    public void hideSoftInput() {
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null)
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public boolean isSoftInputShow(){
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null){
                return true;
            }else {
                return false;
            }
        }
        return false;
    }

    public void showLoadingDialog(){
        if (loadingDialog != null && !loadingDialog.isShowing()){
            loadingDialog.show();
        }
    }

    public void dismissLoadingDialog(){
        if (!this.isFinishing()){
            if (loadingDialog != null && loadingDialog.isShowing()){
                loadingDialog.dismiss();
            }
        }
    }

    public void updateUserToken(int uid, String oldToken, String newToken){
        if (TextUtils.isEmpty(newToken)){
            return;
        }
        if (oldToken.equals(newToken)){
            return;
        }
        appContext.getUser().setToken(newToken);
        mUser = appContext.getUser();
        LuoBoDBM.getInstance(appContext).updateUserToken(uid, newToken);
        appActionsCreator.refreshToken(uid, newToken);
    }

    public int getSharePermissionSize(String[] permissionList){
        int permissionSize = PackageManager.PERMISSION_GRANTED;
        for (String permission : permissionList){
            permissionSize += ContextCompat.checkSelfPermission(this, permission);
        }
        return permissionSize;
    }

    public void showSignDialog(Context context, String score){
        signDialog = new Dialog(context, R.style.DialogStyle);
        View view = LayoutInflater.from(context).inflate(R.layout.sign_success_dialog, null);
        signDialog.setContentView(view);
        TextView msgView = (TextView) view.findViewById(R.id.sign_success_msg);
        TextView descView = (TextView) view.findViewById(R.id.sign_success_content);
        msgView.setText(String.format(Locale.CHINESE, getString(R.string.sign_success_score_add), score));
        descView.setText(String.format(Locale.CHINESE, getString(R.string.sign_success),score));
        signDialog.show();
        dialogHandler.sendEmptyMessageDelayed(1, 2000);
    }
}
