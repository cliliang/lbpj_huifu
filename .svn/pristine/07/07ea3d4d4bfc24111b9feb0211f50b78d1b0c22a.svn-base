package com.baluobo.user.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.baluobo.R;
import com.baluobo.app.AppContext;
import com.baluobo.app.flux.Store;
import com.baluobo.app.ui.BaseToolBarActivity;
import com.baluobo.common.tools.UIHelper;
import com.baluobo.common.tools.Util;
import com.baluobo.user.actions.RedeemAction;
import com.baluobo.user.actions.RedeemListAction;
import com.baluobo.app.flux.AppActionsCreator;
import com.baluobo.user.model.Order;
import com.baluobo.user.model.User;
import com.baluobo.user.router.UserRouter;
import com.baluobo.user.router.UserUI;
import com.baluobo.user.stores.RedeemStore;
import com.squareup.otto.Subscribe;

import java.util.Date;
import java.util.Locale;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/25.
 */
public class RedeemActivity extends BaseToolBarActivity implements View.OnClickListener{
    private String orderNo;
    private RedeemStore redeemStore;
    private TextView redeemButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.redeem_back_activity_layout);
        setTitle(getString(R.string.redeem));
        setupViews();
    }

    @Override
    protected void setupViews() {
        super.setupViews();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            Order order = bundle.getParcelable(RedeemListAction.BOUND_REDEEM_DATA);
            if (order != null){
                orderNo = order.getBuyOrderNo();
                TextView backMoneyView = (TextView) findViewById(R.id.redeem_back_total_money);
                double redeemMoney = order.getSpeedMoney();
                backMoneyView.setText(String.format(Locale.US, "%.2f", (float)redeemMoney));
                TextView timeView = (TextView) findViewById(R.id.redeem_back_time);
                timeView.setText(String.format(Locale.CHINESE, "%tF", new Date()));
                findViewById(R.id.redeem_back_account_button).setOnClickListener(this);
                redeemButton = (TextView) findViewById(R.id.redeem_back_sure_button);
                redeemButton.setOnClickListener(this);
            }
        }
    }

    @Override
    protected void initDependencies() {
        super.initDependencies();
        redeemStore = RedeemStore.getInstance();
        dispatcher.register(redeemStore);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initDependencies();
        redeemStore.register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        redeemStore.unregister(this);
        dispatcher.unregister(redeemStore);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.redeem_back_account_button:
                onBackPressed();
                break;
            case R.id.redeem_back_sure_button:
                if (!Util.isNetworkAvailable(appContext)){
                    UIHelper.ToastMessage(appContext, getString(R.string.net_work_invalid));
                    return;
                }
                if (mUser != null){
                    redeemButton.setEnabled(false);
                    appActionsCreator.redeemBack(mUser.getUserId(), mUser.getToken(), orderNo);
                }
                break;
        }
    }

    @Subscribe
    public void onRedeemBackStoreChange(Store.StoreChangeEvent event) {
        String type = event.getType();
        switch (type){
            case RedeemAction.ACTION_REQUEST_REDEEM_BACK_SUCCESS:
                onBackPressed();
                break;
            case RedeemAction.ACTION_REQUEST_START:
                showLoadingDialog();
                break;
            case RedeemAction.ACTION_REQUEST_FINISH:
                dismissLoadingDialog();
                break;
            case RedeemAction.ACTION_REQUEST_FAIL:
                UIHelper.ToastMessage(this, getString(R.string.net_work_error));
                break;
            case RedeemAction.ACTION_REQUEST_INVALID_TOKEN:
                UserRouter.getInstance(this).showActivity(UserUI.LoginActivity);
                finish();
                break;
            case RedeemAction.ACTION_REQUEST_ERROR_MESSAGE:
                String msg = event.getMessage();
                UIHelper.ToastMessage(this, msg);
                break;
        }
    }
}
