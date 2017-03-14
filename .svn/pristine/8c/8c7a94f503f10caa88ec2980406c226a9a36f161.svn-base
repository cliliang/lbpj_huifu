package com.baluobo.product.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baluobo.R;
import com.baluobo.app.flux.Store;
import com.baluobo.app.router.MainRouter;
import com.baluobo.app.ui.BaseToolBarActivity;
import com.baluobo.common.module.ModuleID;
import com.baluobo.common.tools.UIHelper;
import com.baluobo.common.tools.Util;
import com.baluobo.product.actions.KuaiZhuanInfoAction;
import com.baluobo.product.actions.ProductBuyAction;
import com.baluobo.product.bean.Product;
import com.baluobo.product.stores.KuaiZhuanInfoStore;
import com.baluobo.product.stores.ProductBuyStore;
import com.baluobo.product.views.KuaiZhuanProgress;
import com.baluobo.user.actions.WebAction;
import com.baluobo.user.model.Order;
import com.baluobo.user.router.UserUI;
import com.squareup.otto.Subscribe;

import java.util.Locale;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/12/20.
 */
public class KuaiZhuanInfoActivity extends BaseToolBarActivity implements View.OnClickListener{
    private KuaiZhuanInfoStore infoStore;
    private ProductBuyStore buyStore;
    private KuaiZhuanProgress selledView;
    private TextView sellingView;
    private TextView rateView, unitView, canBuyView;
    private TextView customerMoney, customerCount;
    private EditText inputView;
    private TextView accountView, bankIncomeView, luoboIncomeView, baoIncomeView;
    private ImageView sellIconView;

    private Product product;
    private final float bankRate = 2f;
    private final float baoRate = 3.03f;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kuai_zhuan_info_activity_layout);
        setTitle(getString(R.string.luobo_kuai_zhuan));
        initDependencies();
        setupViews();
    }

    @Override
    protected void initDependencies() {
        super.initDependencies();
        infoStore = KuaiZhuanInfoStore.getInstance();
        buyStore = ProductBuyStore.getInstance();
        dispatcher.register(infoStore);
        dispatcher.register(buyStore);
    }

    @Override
    protected void onResume() {
        super.onResume();
        infoStore.register(this);
        if (mUser != null){
            accountView.setText(mUser.getEnAbleMoney());
        }else {
            accountView.setText("0");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        infoStore.unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dispatcher.unregister(infoStore);
        dispatcher.unregister(buyStore);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == WebAction.BUY_PRODUCT_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
//            appActionsCreator.getKuaiZhuanData();
            KuaiZhuanInfoActivity.this.finish();
        }
    }

    @Override
    protected void setupViews() {
        super.setupViews();
        selledView = (KuaiZhuanProgress) findViewById(R.id.kuai_zhuan_selled_weight_view);
        sellingView = (TextView) findViewById(R.id.kuai_zhuan_selling_weight_view);
        sellIconView = (ImageView) findViewById(R.id.kuai_zhuan_progress_icon);

        rateView = (TextView) findViewById(R.id.kuai_zhuan_info_apr);
        unitView = (TextView) findViewById(R.id.kuai_zhuan_info_can_buy_view);
        canBuyView = (TextView) findViewById(R.id.kuai_zhuan_info_total_can_buy_view);
        customerMoney = (TextView) findViewById(R.id.kuai_zhuan_info_customer_total_buy);
        customerCount = (TextView) findViewById(R.id.kuai_zhuan_info_customer_total_people);

        inputView = (EditText) findViewById(R.id.kuai_zhuan_info_edit_view);
        accountView = (TextView) findViewById(R.id.kuai_zhuan_info_account_view);
        bankIncomeView = (TextView) findViewById(R.id.kuai_zhuan_info_bank_income);
        luoboIncomeView = (TextView) findViewById(R.id.kuai_zhuan_info_luobo_income);
        baoIncomeView = (TextView) findViewById(R.id.kuai_zhuan_info_bao_bao_income);

        showLoadingDialog();
        appActionsCreator.getKuaiZhuanData();

        findViewById(R.id.kuai_zhuan_info_buy_button).setOnClickListener(this);
    }

    private void setViewData(Product info){
        if (info == null){
            return;
        }
        this.product = info;
        progressIconLocation((float) (1 - (product.getSurplusMoney() / product.getBuyMoney())));
        rateView.setText(String.format(Locale.CHINA, "%.2f", product.getProceeds()));
        unitView.setText(String.format(Locale.CHINA, "%.0f", product.getInvestUnit()));
        canBuyView.setText(String.format(Locale.CHINA, getString(R.string.kuai_zhuan_can_buy_total), product.getSurplusMoney()));
        customerMoney.setText(Util.friendMoney(appContext, product.getSumMoney()));
        customerCount.setText(String.format(Locale.CHINA, "%d", product.getBuyUserCount()));
        inputView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String inputString = s.toString();
                if (!TextUtils.isEmpty(inputString)){
                    try {
                        double investMoney = Double.parseDouble(inputString);
                        setIncomeView(investMoney, product.getProceeds());
                    }catch (NumberFormatException e){
                        e.printStackTrace();
                    }
                }else {
                    setIncomeView(0, 0);
                }
            }
        });
    }

    private void setIncomeView(double investMoney, double process){
        double come = Util.getHuoQiPrdMoney(investMoney, process);
        luoboIncomeView.setText(String.format(Locale.CHINA, "%.2f", come));
        bankIncomeView.setText(String.format(Locale.CHINA, "%.2f", Util.getHuoQiPrdMoney(investMoney, 2f)));
        baoIncomeView.setText(String.format(Locale.CHINA, "%.2f", Util.getHuoQiPrdMoney(investMoney, 3.03f)));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.kuai_zhuan_info_buy_button:
                if (product == null){
                    return;
                }
                if (mUser == null){
                    MainRouter.getInstance(this).showActivity(ModuleID.USER_MODULE_ID, UserUI.LoginActivity);
                    return;
                }
                if (!mUser.isAutonym()){
                    UIHelper.ToastMessage(this, getString(R.string.please_first_register_in_huifu));
                    Bundle bundle = new Bundle();
                    bundle.putString(WebAction.WEB_REQUEST_TYPE, WebAction.REQUEST_OPEN_ACCOUNT);
                    MainRouter.getInstance(this).showActivity(ModuleID.USER_MODULE_ID, UserUI.WebActivity, bundle);
                    return;
                }
                String enableString = mUser.getEnAbleMoney();
                String inputString = inputView.getText().toString();
                if (TextUtils.isEmpty(inputString)){
                    UIHelper.ToastMessage(this, getString(R.string.product_buy_input_money));
                    return;
                }

                int inputMoney = 0;
                float enableMoney = 0f;
                if (TextUtils.isEmpty(enableString)){
                    enableMoney = 0f;
                }else {
                    try {
                        enableMoney = Float.parseFloat(enableString);
                    }catch (NumberFormatException e){
                        e.printStackTrace();
                    }
                }

                try {
                    inputMoney = Integer.parseInt(inputString);
                }catch (NumberFormatException e){
                    e.printStackTrace();
                }
                if (inputMoney > enableMoney){
                    UIHelper.ToastMessage(this, getString(R.string.product_buy_account_less_buy));
                    return;
                }
                showNoticeDialog(inputMoney);
                break;
        }
    }

    private void showNoticeDialog(final int money){
        final Dialog noticeDialog = new Dialog(this, R.style.DialogStyle);
        View view = LayoutInflater.from(this).inflate(R.layout.product_buy_dialog_notice_layout, null);
        view.findViewById(R.id.product_notice_dialog_ok_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noticeDialog.dismiss();
                if (mUser != null){
                    appActionsCreator.getPayOrder(mUser.getUserId(), mUser.getToken(), product.getGoodId(), money, "");
                }
            }
        });
        view.findViewById(R.id.product_notice_dialog_cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noticeDialog.dismiss();
            }
        });
        noticeDialog.setContentView(view);
        noticeDialog.show();
    }

    private void progressIconLocation(float progress){
        int screen[] = UIHelper.getScreenDisplay(appContext);
        int screenWidth = screen[0];
        int margin = getResources().getDimensionPixelSize(R.dimen.kuai_zhuan_progress_margin);
        int progressWidth = screenWidth - margin * 2;
        if (progressWidth > 0){
            LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) selledView.getLayoutParams();
            params1.weight = progress;
            LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) sellingView.getLayoutParams();
            params2.weight = 1- progress;
            selledView.setLayoutParams(params1);
            sellingView.setLayoutParams(params2);
            int iconMargin = (int) (progressWidth * progress) - 8;
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) sellIconView.getLayoutParams();
            params.leftMargin = iconMargin + margin;
            sellIconView.setLayoutParams(params);
        }
    }

    @Subscribe
    public void onKuaiZhuanInfoStoreChange(Store.StoreChangeEvent event) {
        String type = event.getType();
        switch (type){
            case ProductBuyAction.ACTION_REQUEST_START:
            case KuaiZhuanInfoAction.ACTION_REQUEST_START:
                showLoadingDialog();
                break;
            case ProductBuyAction.ACTION_REQUEST_FINISH:
            case KuaiZhuanInfoAction.ACTION_REQUEST_FINISH:
                dismissLoadingDialog();
                break;
            case ProductBuyAction.ACTION_REQUEST_ERROR_MESSAGE:
            case KuaiZhuanInfoAction.ACTION_REQUEST_ERROR_MESSAGE:
                String msg = event.getMessage();
                UIHelper.ToastMessage(appContext, msg);
                break;
            case ProductBuyAction.ACTION_REQUEST_FAIL:
            case KuaiZhuanInfoAction.ACTION_REQUEST_FAIL:
                UIHelper.ToastMessage(appContext, getString(R.string.net_work_error));
                break;
            case KuaiZhuanInfoAction.ACTION_REQUEST_KUAI_ZHUAN_INFO_SUCCESS:
                Product info = infoStore.getInfo();
                if (info != null){
                    setViewData(info);
                }
                break;
            case ProductBuyAction.ACTION_REQUEST_ORDER_SUCCESS:
                Order order = buyStore.getOrder();
                if (order != null){
                    Bundle buyBundle = new Bundle();
                    buyBundle.putString(WebAction.WEB_REQUEST_TYPE, WebAction.REQUEST_BUY);
                    buyBundle.putInt(WebAction.REQUEST_PAY_ORDER_ID, order.getBuyOrderId());
                    MainRouter.getInstance(appContext).showActivityForResult(ModuleID.USER_MODULE_ID, KuaiZhuanInfoActivity.this, UserUI.WebActivity, WebAction.BUY_PRODUCT_REQUEST_CODE, buyBundle);
                }
                break;
        }
    }
}
