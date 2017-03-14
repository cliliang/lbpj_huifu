package com.baluobo.home.ui;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baluobo.R;
import com.baluobo.app.flux.Store;
import com.baluobo.app.router.MainRouter;
import com.baluobo.app.ui.BaseToolBarActivity;
import com.baluobo.common.module.ModuleID;
import com.baluobo.common.tools.SharePopupWindow;
import com.baluobo.common.tools.UIHelper;
import com.baluobo.common.tools.Util;
import com.baluobo.home.actions.InviteAction;
import com.baluobo.home.adapter.InviteAdapter;
import com.baluobo.home.model.InviteMode;
import com.baluobo.home.stores.InviteStore;
import com.baluobo.user.router.UserUI;
import com.squareup.otto.Subscribe;
import com.umeng.socialize.UMShareAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/8/9.
 */
public class InviteActivity extends BaseToolBarActivity implements View.OnClickListener{
    private InviteStore inviteStore;
    private List<InviteMode> inviteModeList;
    private LinearLayout showCodeLayout;
    private TextView inviteNumberSizeView, inviteMoneyView, inviteCodeView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invite_activity_layout);
        setTitle(getString(R.string.luobo_invite));
        initDependencies();
        setupViews();
    }

    @Override
    protected void initDependencies() {
        super.initDependencies();
        inviteStore = InviteStore.getInstance();
        dispatcher.register(inviteStore);
    }

    @Override
    protected void onResume() {
        super.onResume();
        inviteStore.register(this);
        if (mUser != null){
            appActionsCreator.getInviteModels(mUser.getUserId(), mUser.getToken());
            showCodeLayout.setVisibility(View.VISIBLE);
            inviteCodeView.setText(String.format(Locale.CHINA, getString(R.string.invite_my_invite_code), mUser.getInviteCode()));
        }else {
            showCodeLayout.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        inviteStore.unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dispatcher.unregister(inviteStore);
    }

    @Override
    protected void setupViews() {
        super.setupViews();
        inviteModeList = new ArrayList<>();
        inviteNumberSizeView = (TextView) findViewById(R.id.show_all_invite_people_list);
        inviteMoneyView = (TextView) findViewById(R.id.invite_money_view);
        findViewById(R.id.show_all_invite_people_list).setOnClickListener(this);
        findViewById(R.id.invite_now_button).setOnClickListener(this);
        showCodeLayout = (LinearLayout) findViewById(R.id.show_invite_code_layout);
        findViewById(R.id.copy_invite_code_button).setOnClickListener(this);
        inviteCodeView = (TextView) findViewById(R.id.invite_code_text_view);
    }

    @Override
    public void onClick(View v) {
        if (!Util.isNetworkAvailable(appContext)){
            UIHelper.ToastMessage(appContext, getString(R.string.net_work_invalid));
            return;
        }
        switch (v.getId()){
            case R.id.show_all_invite_people_list:
                showInviteListDialog();
                break;
            case R.id.invite_now_button:
                if (mUser == null){
                    MainRouter.getInstance(this).showActivity(ModuleID.USER_MODULE_ID, UserUI.LoginActivity);
                    return;
                }

                if (getSharePermissionSize(mSharePermissionList) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(InviteActivity.this,mSharePermissionList, 100);
                }else {
                    showShareDialog();
                }
                break;
            case R.id.copy_invite_code_button:
                if (mUser != null){
                    ClipboardManager clipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
                    ClipData myClip = ClipData.newPlainText("text", mUser.getInviteCode());
                    clipboard.setPrimaryClip(myClip);
                    UIHelper.ToastMessage(this, getString(R.string.copy_invite_code_success));
                }
                break;
        }
    }

    private void showShareDialog(){
        SharePopupWindow popupWindow = new SharePopupWindow(this);
        popupWindow.setInviteData(1, mUser.getInviteCode());
        popupWindow.showAtLocation(findViewById(R.id.invite_blank_line), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showShareDialog();
            } else {
                UIHelper.ToastMessage(this, getString(R.string.share_state_refuse));
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Subscribe
    public void onInviteStoreChange(Store.StoreChangeEvent event) {
        String type = event.getType();
        switch (type){
            case InviteAction.ACTION_REQUEST_START:
                showLoadingDialog();
                break;
            case InviteAction.ACTION_REQUEST_FINISH:
                dismissLoadingDialog();
                break;
            case InviteAction.ACTION_REQUEST_FAIL:
                UIHelper.ToastMessage(this, getString(R.string.net_work_error));
                break;
            case InviteAction.ACTION_REQUEST_ERROR_MESSAGE:
                String msg = event.getMessage();
                UIHelper.ToastMessage(this, msg);
                break;
            case InviteAction.ACTION_REQUEST_INVAITE_DATA:
                List<InviteMode> data = inviteStore.getInviteModes();
                if (data != null){
                    inviteModeList = data;
                    setViewData();
                }
                break;
            case InviteAction.ACTION_REQUEST_INVALID_TOKEN:
                MainRouter.getInstance(this).showActivity(ModuleID.USER_MODULE_ID, UserUI.LoginActivity);
                break;
        }
    }

    private void setViewData(){
        float moneySize = 0;
        for (int i = 0; i < inviteModeList.size(); i++){
            InviteMode mode = inviteModeList.get(i);
            moneySize += mode.getCouponMoney();
        }
        inviteMoneyView.setText(String.format(Locale.CHINESE, "%.0f", moneySize));
        inviteNumberSizeView.setText(String.format(Locale.CHINESE, getString(R.string.total_invite_people_number), inviteModeList.size()));
    }

    private void showInviteListDialog(){
        final Dialog dialog = new Dialog(this, R.style.DialogStyle);
        View rootView = LayoutInflater.from(this).inflate(R.layout.invite_people_list_dialog_layout, null);
        dialog.setContentView(rootView);
        rootView.findViewById(R.id.invite_dialog_dismiss_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.invite_dialog_list_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        InviteAdapter adapter = new InviteAdapter(this);
        adapter.setInviteData(inviteModeList);
        recyclerView.setAdapter(adapter);
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get( this ).onActivityResult( requestCode, resultCode, data);
    }
}
