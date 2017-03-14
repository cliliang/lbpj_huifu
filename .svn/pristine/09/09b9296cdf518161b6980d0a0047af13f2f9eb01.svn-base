package com.baluobo.product.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.baluobo.R;
import com.baluobo.app.AppContext;
import com.baluobo.app.flux.Store;
import com.baluobo.app.router.MainRouter;
import com.baluobo.app.ui.BaseToolBarActivity;
import com.baluobo.common.module.ModuleID;
import com.baluobo.common.tools.UIHelper;
import com.baluobo.common.tools.Util;
import com.baluobo.product.actions.ExpGoodAction;
import com.baluobo.product.bean.ExpGood;
import com.baluobo.product.stores.ExpGoodStore;
import com.baluobo.app.flux.AppActionsCreator;
import com.baluobo.user.model.User;
import com.baluobo.user.router.UserUI;
import com.squareup.otto.Subscribe;

import java.util.Locale;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/8/17.
 */
public class ExperienceGoldActivity extends BaseToolBarActivity {
    private ExpGoodStore expGoodStore;
    private TextView expRate, expDay, expMoney, buyButton;
    private ExpGood mExpGood;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.experience_gold_activity_layout);
        setTitle(getString(R.string.luobo_ti_yan));
        initDependencies();
        setupViews();
    }

    @Override
    protected void setupViews() {
        super.setupViews();
        expRate = (TextView) findViewById(R.id.experience_rate);
        expDay = (TextView) findViewById(R.id.experience_day);
        expMoney = (TextView) findViewById(R.id.experience_money);
        appContext = (AppContext) getApplicationContext();
        buyButton = (TextView) findViewById(R.id.experience_gold_join_button);
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Util.isNetworkAvailable(appContext)){
                    UIHelper.ToastMessage(appContext, getString(R.string.net_work_invalid));
                    return;
                }
                if (mUser != null && mExpGood != null){
                    buyButton.setEnabled(false);
                    appActionsCreator.buyExpGood(mUser.getUserId(), mUser.getToken());
                }else {
                    MainRouter.getInstance(ExperienceGoldActivity.this).showActivity(ModuleID.USER_MODULE_ID, UserUI.LoginActivity);
                }
            }
        });
    }

    private void setViewData(ExpGood expGood){
        expRate.setText(String.format(Locale.CHINESE, "%.2f", expGood.getProceeds()));
        expDay.setText(String.valueOf(expGood.getInvestTime()));
        expMoney.setText(String.valueOf(expGood.getGoodMoney()));
        if (expGood.getGoodFlg() == 0){
            buyButton.setEnabled(true);
        }else {
            buyButton.setEnabled(false);
        }
    }

    @Override
    protected void initDependencies() {
        super.initDependencies();
        expGoodStore = ExpGoodStore.getInstance();
        dispatcher.register(expGoodStore);
    }

    @Override
    protected void onResume() {
        super.onResume();
        expGoodStore.register(this);
        int uid = 0;
        String token = "";
        if (mUser != null){
            uid = mUser.getUserId();
            token = mUser.getToken();
        }
        appActionsCreator.getExpGoodInfo(uid, token);
    }

    @Override
    protected void onStop() {
        super.onStop();
        expGoodStore.unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dispatcher.unregister(expGoodStore);
    }

    @Subscribe
    public void onExpGoodStoreChange(Store.StoreChangeEvent event) {
        String type = event.getType();
        switch (type) {
            case ExpGoodAction.ACTION_REQUEST_START:
                showLoadingDialog();
                break;
            case ExpGoodAction.ACTION_REQUEST_FINISH:
                dismissLoadingDialog();
                break;
            case ExpGoodAction.ACTION_REQUEST_FAIL:
                UIHelper.ToastMessage(this, getString(R.string.net_work_error));
                break;
            case ExpGoodAction.ACTION_REQUEST_INVALID_TOKEN:
                MainRouter.getInstance(this).showActivity(ModuleID.USER_MODULE_ID, UserUI.LoginActivity);
                break;
            case ExpGoodAction.ACTION_REQUEST_ERROR_MESSAGE:
                String msg = event.getMessage();
                UIHelper.ToastMessage(this, msg);
                break;
            case ExpGoodAction.ACTION_REQUEST_EXP_GOOD_SUCCESS:
                ExpGood expGood = expGoodStore.getExpGood();
                if (expGood != null){
                    setViewData(expGood);
                    mExpGood = expGood;
                }
                break;
            case ExpGoodAction.BUY_EXP_ACTION_REQUEST_SUCCESS:
                UIHelper.ToastMessage(this, getString(R.string.buy_exp_good_success));
                finish();
                break;
        }
    }
}
