package com.baluobo.user.ui;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.baluobo.R;
import com.baluobo.app.AppContext;
import com.baluobo.app.flux.Store;
import com.baluobo.app.model.BaseModel;
import com.baluobo.app.ui.BaseToolBarActivity;
import com.baluobo.common.db.LuoBoDBM;
import com.baluobo.common.module.ModuleID;
import com.baluobo.common.tools.UIHelper;
import com.baluobo.common.tools.Util;
import com.baluobo.common.views.AssetsItemView;
import com.baluobo.common.views.TotalAssetsView;
import com.baluobo.user.actions.TotalAssetsAction;
import com.baluobo.app.flux.AppActionsCreator;
import com.baluobo.user.actions.WebAction;
import com.baluobo.user.model.User;
import com.baluobo.user.router.UserRouter;
import com.baluobo.user.router.UserUI;
import com.baluobo.user.stores.TotalAssetsStore;
import com.squareup.otto.Subscribe;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/17.
 */
public class TotalAssetsActivity extends BaseToolBarActivity implements View.OnClickListener {
    private TotalAssetsView totalAssetsView;
    private TotalAssetsStore totalAssetsStore;
    private AppContext appContext;
    private TextView totalView;
    private AssetsItemView lbKuaiZhuanView, lbDinDouView, xinShouView, yinPiaoView, enableView, expView;
    private TextView tomorrowIncomeView, totalIncomeView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.total_assets_activity_layout);
        initDependencies();
        setTitle(getString(R.string.total_assets));
        setupViews();
    }

    @Override
    protected void initDependencies() {
        super.initDependencies();
        totalAssetsStore = TotalAssetsStore.getInstance();
        dispatcher.register(totalAssetsStore);
    }

    @Override
    protected void setupViews() {
        super.setupViews();
        appContext = (AppContext) getApplicationContext();
        totalView = (TextView) findViewById(R.id.assets_total_money);
        totalAssetsView = (TotalAssetsView) findViewById(R.id.total_assets_view);
        lbKuaiZhuanView = (AssetsItemView) findViewById(R.id.assets_luobo_kuai_zhuan_view);
        lbDinDouView = (AssetsItemView) findViewById(R.id.assets_luobo_ding_tou_view);
        xinShouView = (AssetsItemView) findViewById(R.id.assets_luobo_xin_shou_view);
        yinPiaoView = (AssetsItemView) findViewById(R.id.assets_luobo_yin_piao_button);
        enableView = (AssetsItemView) findViewById(R.id.assets_luobo_usable_view);
        expView = (AssetsItemView) findViewById(R.id.assets_luobo_exp_view);
        findViewById(R.id.assets_recharge_button).setOnClickListener(this);
        findViewById(R.id.assets_withdraw_button).setOnClickListener(this);
        tomorrowIncomeView = (TextView) findViewById(R.id.total_assets_tomorrow_money);
        totalIncomeView = (TextView) findViewById(R.id.total_assets_total_money);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            animAssetsView();

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        totalAssetsStore.register(this);
        if (mUser != null) {
            appActionsCreator.getTotalAssets(mUser.getUserId(), mUser.getToken());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        totalAssetsStore.unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dispatcher.unregister(totalAssetsStore);
    }

    @Override
    public void onClick(View v) {
        if (!Util.isNetworkAvailable(appContext)){
            UIHelper.ToastMessage(appContext, getString(R.string.net_work_invalid));
            return;
        }
        switch (v.getId()) {
            case R.id.assets_recharge_button:
                Bundle bundle = new Bundle();
                bundle.putString(WebAction.WEB_REQUEST_TYPE, WebAction.REQUEST_RECHARGE);
                UserRouter.getInstance(this).showActivity(UserUI.RechargeActivity, bundle);
                break;
            case R.id.assets_withdraw_button:
                if (mUser == null) {
                    return;
                }
                String bankCard = mUser.getBankCard();
                if (TextUtils.isEmpty(bankCard)) {
                    UIHelper.ToastMessage(this, getString(R.string.please_first_bound_bank_card));
                    Bundle bundleBank = new Bundle();
                    bundleBank.putString(WebAction.WEB_REQUEST_TYPE, WebAction.REQUEST_BUNDLE_BANK_CARD);
                    UserRouter.getInstance(this).showActivity(UserUI.WebActivity, bundleBank);
                    return;
                }
                Bundle withdrawBundle = new Bundle();
                withdrawBundle.putString(WebAction.WEB_REQUEST_TYPE, WebAction.REQUEST_WITHDRAW);
                UserRouter.getInstance(this).showActivity(UserUI.RechargeActivity, withdrawBundle);
                break;
        }
    }

    @Subscribe
    public void onTotalAssetsStoreChange(Store.StoreChangeEvent event) {
        String type = event.getType();
        switch (type) {
            case TotalAssetsAction.ACTION_REQUEST_START:
                showLoadingDialog();
                break;
            case TotalAssetsAction.ACTION_REQUEST_FINISH:
                dismissLoadingDialog();
                break;
            case TotalAssetsAction.ACTION_REQUEST_FAIL:
                UIHelper.ToastMessage(this, getString(R.string.net_work_error));
                break;
            case TotalAssetsAction.ACTION_REQUEST_ERROR_MESSAGE:
                String msg = event.getMessage();
                UIHelper.ToastMessage(this, msg);
                break;
            case TotalAssetsAction.ACTION_REQUEST_INVALID_TOKEN:
                UserRouter.getInstance(this).showActivity(UserUI.LoginActivity);
                finish();
                break;
            case TotalAssetsAction.ACTION_REQUEST_TOTAL_ASSETS_SUCCESS:
                BaseModel baseModel = totalAssetsStore.getUser();
                if (baseModel != null && baseModel.isSuccess()) {
                    User user = (User) baseModel.getRows();
                    if (user != null) {
                        mUser = user;
                        setViewData(user);
                        appContext.setUser(mUser);
                        LuoBoDBM.getInstance(this).updateUserInfo(user);
                        updateUserToken(user.getUserId(), user.getToken(), baseModel.getToken());
                    }
                }
                break;
        }
    }

    private void setViewData(User assetsUser) {
        float[] assets = new float[6];
        if (assetsUser != null) {
            tomorrowIncomeView.setText(String.format(Locale.US, getString(R.string.total_assets_tomorrow_income), assetsUser.getYesterdayIncome()));
            try {
                float totalMoney = Float.parseFloat(assetsUser.getSumProceeds());
                totalIncomeView.setText(String.format(Locale.US, getString(R.string.total_assets_total_income), totalMoney));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            assets[0] = assetsUser.getLbkzMoneys();
            assets[1] = assetsUser.getLbdtMoneys();
            assets[2] = assetsUser.getXsbMoneys();
            assets[3] = assetsUser.getYpmMoneys();
            assets[4] = assetsUser.getExperienceIncome();
            String enableString = assetsUser.getEnAbleMoney();
            if (TextUtils.isEmpty(enableString)) {
                assets[5] = 0;
            } else {
                float enable = Float.parseFloat(enableString);
                assets[5] = enable;
            }
        }
        totalAssetsView.setTotalAssets(assets);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessageDelayed(1, 100);
            }
        }, 200);
        float totalAssets = 0;
        String totalString = assetsUser.getCountMoney();
        if (!TextUtils.isEmpty(totalString)) {
            totalAssets = Float.parseFloat(totalString);
        }
        if (totalAssets == 0) {
            totalView.setText("0.0");
            lbKuaiZhuanView.setAssets("0.0%", "0.0");
            lbDinDouView.setAssets("0.0%", "0.0");
            xinShouView.setAssets("0.0%", "0.0");
            yinPiaoView.setAssets("0.0%", "0.0");
            expView.setAssets("0.0%", "0.0");
            enableView.setAssets("0.0%", "0.0");
        } else {
            totalView.setText(String.format(Locale.US, "%.2f", totalAssets));
            lbKuaiZhuanView.setAssets(String.format(Locale.US, "%.2f%%", calculate(assets[0], totalAssets)), String.format(Locale.US, "%.2f", assets[0]));
            lbDinDouView.setAssets(String.format(Locale.US, "%.2f%%", calculate(assets[1], totalAssets)), String.format(Locale.US, "%.2f", assets[1]));
            xinShouView.setAssets(String.format(Locale.US, "%.2f%%", calculate(assets[2], totalAssets)), String.format(Locale.US, "%.2f", assets[2]));
            yinPiaoView.setAssets(String.format(Locale.US, "%.2f%%", calculate(assets[3], totalAssets)), String.format(Locale.US, "%.2f", assets[3]));
            expView.setAssets(String.format(Locale.US, "%.2f%%", calculate(assets[4], totalAssets)), String.format(Locale.US, "%.2f", assets[4]));
            enableView.setAssets(String.format(Locale.US, "%.2f%%", calculate(assets[5], totalAssets)), String.format(Locale.US, "%.2f", assets[5]));
        }
    }

    private float calculate(float hand, float total) {
        return hand / total * 100;
    }

    private void animAssetsView() {
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 360f);
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float angle = (float) animation.getAnimatedValue();
                totalAssetsView.setMaxAngle(angle);
            }
        });
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
    }
}
