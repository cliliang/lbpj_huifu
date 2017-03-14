package com.baluobo.user.ui;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baluobo.R;
import com.baluobo.app.AppContext;
import com.baluobo.app.flux.Store;
import com.baluobo.app.model.BaseModel;
import com.baluobo.app.ui.BaseToolBarActivity;
import com.baluobo.common.tools.UIHelper;
import com.baluobo.common.tools.Util;
import com.baluobo.common.views.KuaiZhuanMapView;
import com.baluobo.user.actions.KuaiZhuanAction;
import com.baluobo.app.flux.AppActionsCreator;
import com.baluobo.user.actions.WebAction;
import com.baluobo.user.model.KuaiZhuan;
import com.baluobo.user.model.User;
import com.baluobo.user.router.UserRouter;
import com.baluobo.user.router.UserUI;
import com.baluobo.user.stores.KuaiZhuanStore;
import com.squareup.otto.Subscribe;

import java.util.List;
import java.util.Locale;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/18.
 */
public class LuoboKuaiZhuanActivity extends BaseToolBarActivity {
    private KuaiZhuanStore kuaiZhuanStore;
    private List<KuaiZhuan> kuaiZhuanList;
    private KuaiZhuanMapView mapView;
    private TextView button;
    private TextView totalIncomeMoneyView;
    private TextView enableRedeemMoneyView;
    private TextView yesterdayView;
    private TextView topView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.luobo_kuai_zhuan_activity_layout);
        setTitle(getString(R.string.luobo_kuai_zhuan));
        setupViews();
    }

    @Override
    protected void setupViews() {
        super.setupViews();
        mapView = (KuaiZhuanMapView) findViewById(R.id.kuai_zhuan_map_view);
        totalIncomeMoneyView = (TextView) findViewById(R.id.kuai_zhuan_total_income_money);
        enableRedeemMoneyView = (TextView) findViewById(R.id.kuai_zhuan_total_redeem_money);
        yesterdayView = (TextView) findViewById(R.id.luobo_kuai_zhuan_tomorrow_money);
        topView = (TextView) findViewById(R.id.kuai_zhuan_zhe_view);
        button = (TextView) findViewById(R.id.luobo_kuai_zhuan_redeem_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Util.isNetworkAvailable(appContext)){
                    UIHelper.ToastMessage(appContext, getString(R.string.net_work_invalid));
                    return;
                }
                if (mUser == null){
                    return;
                }
                String userIdString = mUser.getIdCard();
                if (TextUtils.isEmpty(userIdString)){
                    UIHelper.ToastMessage(LuoboKuaiZhuanActivity.this, getString(R.string.please_first_register_in_huifu));
                    Bundle openAccountBundle = new Bundle();
                    openAccountBundle.putString(WebAction.WEB_REQUEST_TYPE, WebAction.REQUEST_OPEN_ACCOUNT);
                    UserRouter.getInstance(LuoboKuaiZhuanActivity.this).showActivity(UserUI.WebActivity, openAccountBundle);
                    return;
                }

                if (kuaiZhuanList != null && kuaiZhuanList.size() > 0){
                    UserRouter.getInstance(LuoboKuaiZhuanActivity.this).showActivity(UserUI.RedeemActivity);
                }
            }
        });
    }

    @Override
    protected void initDependencies() {
        super.initDependencies();
        kuaiZhuanStore = KuaiZhuanStore.getInstance();
        dispatcher.register(kuaiZhuanStore);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initDependencies();
        kuaiZhuanStore.register(this);
        if (mUser != null){
            appActionsCreator.getKuaiZhuanData(mUser.getUserId(), mUser.getToken());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        kuaiZhuanStore.unregister(this);
        dispatcher.unregister(kuaiZhuanStore);
    }

    @Subscribe
    public void onKuaiZhuanStoreChange(Store.StoreChangeEvent event) {
        String type = event.getType();
        switch (type){
            case KuaiZhuanAction.ACTION_REQUEST_START:
                showLoadingDialog();
                break;
            case KuaiZhuanAction.ACTION_REQUEST_FINISH:
                dismissLoadingDialog();
                break;
            case KuaiZhuanAction.ACTION_REQUEST_FAIL:
                UIHelper.ToastMessage(this, getString(R.string.net_work_error));
                break;
            case KuaiZhuanAction.ACTION_REQUEST_INVALID_TOKEN:
                UserRouter.getInstance(this).showActivity(UserUI.LoginActivity);
                finish();
                break;
            case KuaiZhuanAction.ACTION_REQUEST_ERROR_MESSAGE:
                String errorMessage = event.getMessage();
                UIHelper.ToastMessage(this, errorMessage);
                break;
            case KuaiZhuanAction.ACTION_REQUEST_KUAI_ZHUAN_SUCCESS:
                BaseModel<KuaiZhuan> baseModel = kuaiZhuanStore.getData();
                String msg = baseModel.getMessage();
                if (!TextUtils.isEmpty(msg) && msg.contains(",")){
                    String[] split = msg.split(",");
                    if (split.length == 3){
                        String yesterday = split[0];
                        if (!TextUtils.isEmpty(yesterday) ){
                            yesterdayView.setText(yesterday);
                        }
                        totalIncomeMoneyView.setText(String.format(Locale.US, getString(R.string.kuai_zhuan_total_income_money), split[1]));
                        enableRedeemMoneyView.setText(String.format(Locale.US, getString(R.string.kuai_zhuan_can_reback_money), split[2]));
                        String enableRedeem = split[2];
                        if (!TextUtils.isEmpty(enableRedeem)){
                            float redeem;
                            try {
                                redeem = Float.parseFloat(enableRedeem);
                                if (redeem > 0){
                                    button.setEnabled(true);
                                }else {
                                    button.setEnabled(false);
                                }
                            }catch (NumberFormatException e){
                                e.printStackTrace();
                                button.setEnabled(false);
                            }
                        }else {
                            button.setEnabled(false);
                        }
                    }
                }

                List<KuaiZhuan> dateList = (List<KuaiZhuan>) baseModel.getRows();
                if (dateList != null && dateList.size() > 0){
                    kuaiZhuanList = dateList;
                    mapView.setMapData(kuaiZhuanList);
                    showTrendMap();
                }
                break;
        }
    }

    private void showTrendMap(){
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenWidth = metrics.widthPixels;
        int viewWidth = screenWidth - 2 * getResources().getDimensionPixelSize(R.dimen.kuai_zhuan_map_margin);
        final LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) topView.getLayoutParams();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, viewWidth);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int width = (int) animation.getAnimatedValue();
                params.width = width;
                topView.setLayoutParams(params);
            }
        });
        valueAnimator.start();
    }
}
