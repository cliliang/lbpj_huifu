package com.baluobo.user.ui;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.baluobo.R;
import com.baluobo.app.flux.Store;
import com.baluobo.app.model.BaseModel;
import com.baluobo.app.ui.BaseToolBarActivity;
import com.baluobo.common.tools.UIHelper;
import com.baluobo.common.tools.Util;
import com.baluobo.user.actions.BankCardAction;
import com.baluobo.user.actions.WebAction;
import com.baluobo.user.model.BankCardInfo;
import com.baluobo.user.router.UserRouter;
import com.baluobo.user.router.UserUI;
import com.baluobo.user.stores.BankCardStore;
import com.squareup.otto.Subscribe;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/18.
 */
public class BankCardManageActivity extends BaseToolBarActivity {
    private BankCardStore bankCardStore;
    private TextView BankNameView;
    private TextView bankCardView;
    private TextView nameView;
    private View addRootView, unwrapRootView;
    private boolean canUnwrap = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bank_card_manager_layout);
        setTitle(getString(R.string.bank_card_manager));
        initDependencies();
        setupViews();
    }

    @Override
    protected void initDependencies() {
        super.initDependencies();
        bankCardStore = BankCardStore.getInstance();
        dispatcher.register(bankCardStore);
    }

    @Override
    protected void setupViews() {
        super.setupViews();
        addRootView = findViewById(R.id.bank_card_manager_add_root);
        unwrapRootView = findViewById(R.id.bank_card_manager_unwrap_root);
        findViewById(R.id.add_bank_card_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Util.isNetworkAvailable(appContext)){
                    UIHelper.ToastMessage(appContext, getString(R.string.net_work_invalid));
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString(WebAction.WEB_REQUEST_TYPE, WebAction.REQUEST_BUNDLE_BANK_CARD);
                UserRouter.getInstance(BankCardManageActivity.this).showActivity(UserUI.WebActivity, bundle);
            }
        });

        nameView = (TextView) findViewById(R.id.bank_card_info_name);
        BankNameView = (TextView) findViewById(R.id.bank_card_info_bank_name);
        bankCardView = (TextView) findViewById(R.id.bank_card_view_id);
        findViewById(R.id.unwrap_bank_card_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Util.isNetworkAvailable(appContext)){
                    UIHelper.ToastMessage(appContext, getString(R.string.net_work_invalid));
                    return;
                }
                if (canUnwrap){
                    Bundle bundle = new Bundle();
                    bundle.putString(WebAction.WEB_REQUEST_TYPE, WebAction.REQUEST_UNBOUND_BANK_CARD);
                    UserRouter.getInstance(appContext).showActivity(UserUI.WebActivity, bundle);
//                    showUnwrapDialog();
                }else {
                    showInvalidDialog();
                }

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        bankCardStore.register(this);
        if (mUser != null){
            String bankCard = mUser.getBankCard();
            if (!TextUtils.isEmpty(bankCard)){
                addRootView.setVisibility(View.GONE);
                unwrapRootView.setVisibility(View.VISIBLE);
                appActionsCreator.getBankCardInfo(mUser.getUserId(), mUser.getToken());
            }else {
                addRootView.setVisibility(View.VISIBLE);
                unwrapRootView.setVisibility(View.GONE);
            }
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        bankCardStore.unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dispatcher.unregister(bankCardStore);
    }

    @Subscribe
    public void onBankCardStoreChange(Store.StoreChangeEvent event) {
        String type = event.getType();
        switch (type){
            case BankCardAction.ACTION_REQUEST_START:
                showLoadingDialog();
                break;
            case BankCardAction.ACTION_REQUEST_FINISH:
                dismissLoadingDialog();
                break;
            case BankCardAction.ACTION_REQUEST_FAIL:
                UIHelper.ToastMessage(this, getString(R.string.net_work_error));
                break;
            case BankCardAction.ACTION_REQUEST_ERROR_MESSAGE:
                String msg = event.getMessage();
                UIHelper.ToastMessage(this, msg);
                break;
            case BankCardAction.ACTION_REQUEST_INVALID_TOKEN:
                UserRouter.getInstance(this).showActivity(UserUI.LoginActivity);
                finish();
                break;
            case BankCardAction.ACTION_REQUEST_BANK_CARD_SUCCESS:
                BaseModel data = bankCardStore.getData();
                if (data != null){
                    BankCardInfo bankCardInfo = (BankCardInfo) data.getRows();
                    if (bankCardInfo != null){
                        setViewData(bankCardInfo);
                        canUnwrap = (data.getFlg() == 1);
                    }
                }
                break;
        }
    }

    private void setViewData(BankCardInfo bankCard){
        if (bankCard == null){
            return;
        }
        String bankCardString = bankCard.getBankCard();
        String nameString = bankCard.getUserName();
        if (!TextUtils.isEmpty(bankCardString) && bankCardString.length() > 7){
            bankCardString = bankCardString.substring(0, 4)+ " **** **** **** " + bankCardString.substring(bankCardString.length() - 3, bankCardString.length());
            bankCardView.setText(bankCardString);
        }
        if (!TextUtils.isEmpty(nameString) && nameString.length() > 1){
            nameString = "*" + nameString.substring(1, nameString.length());
            nameView.setText(nameString);
        }
    }

    private void showInvalidDialog(){
        final Dialog dialog = new Dialog(this, R.style.DialogStyle);
        View view = LayoutInflater.from(this).inflate(R.layout.bank_manager_invalid_dialog_layout, null);
        view.findViewById(R.id.bank_card_i_know_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(view);
        dialog.show();
    }
    private void showUnwrapDialog(){
        if (mUser == null){
            return;
        }

        final Dialog dialog = new Dialog(this, R.style.DialogStyle);
        View view = LayoutInflater.from(this).inflate(R.layout.unwrap_bank_card_dialog_layout, null);
        TextView accountNameView = (TextView) view.findViewById(R.id.huifu_account_view);
        accountNameView.setText(mUser.getCountName());
        view.findViewById(R.id.copy_huifu_name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cmb = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("text", mUser.getCountName());
                cmb.setPrimaryClip(clip);
                UIHelper.ToastMessage(BankCardManageActivity.this, getString(R.string.copy_huifu_name_already));
            }
        });
        view.findViewById(R.id.unwrap_dialog_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.unwrap_dialog_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (!Util.isNetworkAvailable(appContext)){
                    UIHelper.ToastMessage(appContext, getString(R.string.net_work_invalid));
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString(WebAction.WEB_REQUEST_TYPE, WebAction.REQUEST_UNWRAP_BANK_CARD);
                bundle.putString( WebAction.REQUEST_UNWRAP_CARD_URL, "https://c.chinapnr.com/p2puser/");
                UserRouter.getInstance(BankCardManageActivity.this).showActivity(UserUI.WebActivity, bundle);
            }
        });
        dialog.setContentView(view);
        dialog.show();
    }
}
