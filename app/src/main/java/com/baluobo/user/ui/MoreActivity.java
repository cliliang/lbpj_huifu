package com.baluobo.user.ui;

import android.app.Dialog;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.baluobo.R;
import com.baluobo.app.AppContext;
import com.baluobo.app.ui.BaseToolBarActivity;
import com.baluobo.common.db.LuoBoDBM;
import com.baluobo.common.tools.UIHelper;
import com.baluobo.common.tools.Util;
import com.baluobo.common.views.CustomItemView;
import com.baluobo.user.actions.WebAction;
import com.baluobo.user.model.User;
import com.baluobo.user.router.UserRouter;
import com.baluobo.user.router.UserUI;
import com.xiaomi.market.sdk.UpdateResponse;
import com.xiaomi.market.sdk.UpdateStatus;
import com.xiaomi.market.sdk.XiaomiUpdateAgent;
import com.xiaomi.market.sdk.XiaomiUpdateListener;

import java.util.Locale;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/14.
 */
public class MoreActivity extends BaseToolBarActivity implements View.OnClickListener{
    private Dialog checkUpdateDialog;
    private TextView logoutButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_activity_layout);
        setTitle(getString(R.string.mine_more));
        setupViews();
    }

    @Override
    protected void setupViews() {
        super.setupViews();
        CustomItemView contactUsItem = (CustomItemView) findViewById(R.id.more_info_contact_us);
        contactUsItem.setOnClickListener(this);
        findViewById(R.id.more_info_about_us).setOnClickListener(this);
        findViewById(R.id.more_info_feedback).setOnClickListener(this);
        CustomItemView checkVersionView = (CustomItemView) findViewById(R.id.more_info_check_update);
        checkVersionView.setOnClickListener(this);
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            String versionName = packageInfo.versionName;
            checkVersionView.setSubContent("v" + versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        findViewById(R.id.more_info_luobo_coin).setOnClickListener(this);
        logoutButton = (TextView) findViewById(R.id.account_info_logout_safe);
        logoutButton.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mUser == null){
            logoutButton.setEnabled(false);
        }else {
            logoutButton.setEnabled(true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.more_info_contact_us:
                UserRouter.getInstance(this).showActivity(UserUI.ContactUs);
                break;
            case R.id.more_info_about_us:
                Bundle bundle = new Bundle();
                bundle.putString(WebAction.STATIC_WEB_BOUND_TYPE, WebAction.STATIC_WEB_TYPE_ABOUT_US);
                UserRouter.getInstance(this).showActivity(UserUI.StaticWeb, bundle);
                break;
            case R.id.more_info_feedback:
                if (mUser == null){
                    UserRouter.getInstance(this).showActivity(UserUI.LoginActivity);
                }else {
                    UserRouter.getInstance(this).showActivity(UserUI.Feedback);
                }
                break;
            case R.id.more_info_check_update:
                if (!Util.isNetworkAvailable(appContext)){
                    UIHelper.ToastMessage(appContext, getString(R.string.net_work_invalid));
                    return;
                }
                checkUpdate();
                checkUpdateDialog();
                break;
            case R.id.more_info_luobo_coin:
                showLuoboCoinDialog();
                break;
            case R.id.account_info_logout_safe:
                logoutAccount();
                break;
        }
    }

    public void checkUpdate(){
        XiaomiUpdateAgent.setUpdateAutoPopup(false);
        XiaomiUpdateAgent.setUpdateListener(new XiaomiUpdateListener() {

            @Override
            public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
                checkUpdateDialog.dismiss();
                switch (updateStatus) {
                    case UpdateStatus.STATUS_UPDATE:
                        // 有更新， UpdateResponse为本次更新的详细信息
                        // 其中包含更新信息，下载地址，MD5校验信息等，可自行处理下载安装
                        // 如果希望 SDK继续接管下载安装事宜，可调用
                        showUpdateDialog(updateInfo);
                        break;
                    case UpdateStatus.STATUS_NO_UPDATE:
                        // 无更新， UpdateResponse为null
                        UIHelper.ToastMessage(MoreActivity.this, getString(R.string.update_info_no_new));
                        break;
                    case UpdateStatus.STATUS_NO_WIFI:
                        // 设置了只在WiFi下更新，且WiFi不可用时， UpdateResponse为null
                        break;
                    case UpdateStatus.STATUS_NO_NET:
                        // 没有网络， UpdateResponse为null
                        break;
                    case UpdateStatus.STATUS_FAILED:
                        // 检查更新与服务器通讯失败，可稍后再试， UpdateResponse为null
                        break;
                    case UpdateStatus.STATUS_LOCAL_APP_FAILED:
                        // 检查更新获取本地安装应用信息失败， UpdateResponse为null
                        break;
                    default:
                        break;
                }
            }
        });
        XiaomiUpdateAgent.update(this);
    }

    private void showLuoboCoinDialog(){
        final Dialog coinDialog = new Dialog(this, R.style.DialogStyle);
        View view = LayoutInflater.from(this).inflate(R.layout.more_about_luo_bo_icon_dialog_layout, null);
        view.findViewById(R.id.luobo_icon_dialog_sure_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coinDialog.dismiss();
            }
        });
        coinDialog.setContentView(view);
        coinDialog.show();
    }

    private void checkUpdateDialog(){
        checkUpdateDialog = new Dialog(this, R.style.DialogStyle);
        View view = LayoutInflater.from(this).inflate(R.layout.check_update_dialog_layout, null);
        checkUpdateDialog.setContentView(view);
        checkUpdateDialog.show();
    }

    private void showUpdateDialog(UpdateResponse updateInfo){
        final Dialog updateDialog = new Dialog(this, R.style.DialogStyle);
        View view = LayoutInflater.from(this).inflate(R.layout.show_new_version_layout, null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XiaomiUpdateAgent.arrange();
                updateDialog.dismiss();
            }
        });
        updateDialog.setContentView(view);
        updateDialog.show();
    }

    private void logoutAccount(){

        final Dialog logoutDialog = new Dialog(this, R.style.DialogStyle);
        View view = LayoutInflater.from(this).inflate(R.layout.show_logout_dialog_bg, null);
        view.findViewById(R.id.logout_dialog_cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutDialog.dismiss();
            }
        });
        view.findViewById(R.id.logout_dialog_ok_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
                logoutDialog.dismiss();
            }
        });
        logoutDialog.setContentView(view);
        logoutDialog.show();
    }

    private void logout(){
        if (LuoBoDBM.getInstance(this).logout()){
            UIHelper.ToastMessage(this, getString(R.string.already_logout_safe));
            appContext.setUser(null);
            finish();
        }else {
            UIHelper.ToastMessage(this, getString(R.string.logout_fail));
        }
    }
}
