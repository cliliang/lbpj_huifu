package com.baluobo.product.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baluobo.R;
import com.baluobo.app.flux.Store;
import com.baluobo.app.router.MainRouter;
import com.baluobo.app.ui.BaseToolBarActivity;
import com.baluobo.common.config.AppConfig;
import com.baluobo.common.module.ModuleID;
import com.baluobo.common.tools.UIHelper;
import com.baluobo.common.tools.Util;
import com.baluobo.common.tools.WindowUtil;
import com.baluobo.common.views.ScaleMoneyView;
import com.baluobo.common.views.ScrollListenerHorizontalScrollView;
import com.baluobo.find.views.ChoiceRedTicketView;
import com.baluobo.product.actions.ProductBuyAction;
import com.baluobo.product.actions.ProductInfoAction;
import com.baluobo.product.actions.UsableCouponsAction;
import com.baluobo.product.bean.Product;
import com.baluobo.product.stores.ProductBuyStore;
import com.baluobo.product.stores.UsableCouponsStore;
import com.baluobo.user.actions.WebAction;
import com.baluobo.user.adapter.OnRedTicketCheckedListener;
import com.baluobo.user.model.Order;
import com.baluobo.user.model.ProductEnum;
import com.baluobo.user.model.RedPacket;
import com.baluobo.user.router.UserUI;
import com.squareup.otto.Subscribe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/20.
 */
public class ProductBuyActivity extends BaseToolBarActivity implements View.OnClickListener{
    private ProductBuyStore productBuyStore;
    private UsableCouponsStore couponsStore;
    private ScaleMoneyView ruleView;
    private TextView descView1, descView2;
    private TextView accountMoney;
    private TextView rechargeButton;
    private TextView investMoneyView;
    private TextView income30DayView;
    private EditText inputInvestView;
    private TextView couponMoneyView, couponRateView;
    private TextView couponMoneyAddView, couponRateAddView;
    private TextView canBuyView;
    private TextView investButton;
    private TextView preMoneyText;
    private TextView couponsTitleView;
    private LinearLayout couponsContainerLayout;
    private ScrollListenerHorizontalScrollView scrollView;
    private InvestTextWatcher textWatcher;
    private List<RedPacket> redPackets = new ArrayList<>();
    private Product product;
    private String enableMoney;
    float benJinMoney = 0;
    float xianJinMoney = 0;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            WindowUtil.showPopupWindow(getApplicationContext(), WindowUtil.TYPE_BUY);
            return false;
        }
    });

//    private boolean textChangeFromUser = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy_activity_layout);
        setTitle(getResources().getString(R.string.buy_now));
        initDependencies();
        setupViews();
//        if (!AppConfig.getInstance(this).isRulerShowed()){
//            mHandler.sendEmptyMessageDelayed(1, 300);
//            AppConfig.getInstance(this).setRulerShowed(true);
//        }
    }

    @Override
    protected void setupViews() {
        super.setupViews();
        ruleView = (ScaleMoneyView) findViewById(R.id.buy_money_scroll_view);

        scrollView = (ScrollListenerHorizontalScrollView) findViewById(R.id.product_rule_view_scroll_view);
        scrollView.setOnScrollViewListener(new ScrollListenerHorizontalScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {
                calculate(l);
            }
        });

//        scrollView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                int action = event.getAction();
//                if (action == MotionEvent.ACTION_DOWN){
//                    textChangeFromUser = false;
//                }
//                return false;
//            }
//        });

        descView1 = (TextView) findViewById(R.id.product_buy_desc1);
        descView2 = (TextView) findViewById(R.id.product_buy_desc2);
        accountMoney = (TextView) findViewById(R.id.product_buy_account_money);
        canBuyView = (TextView) findViewById(R.id.product_buy_can_max_money);
        preMoneyText = (TextView) findViewById(R.id.preProcess_money);
        couponMoneyView = (TextView) findViewById(R.id.product_buy_coupon_money);
        couponRateView = (TextView) findViewById(R.id.product_buy_coupon_rate);
        couponMoneyAddView = (TextView) findViewById(R.id.product_buy_coupon_money_add);
        couponRateAddView = (TextView) findViewById(R.id.product_buy_coupon_rate_add);
        rechargeButton = (TextView) findViewById(R.id.product_buy_recharge_button);
        rechargeButton.setOnClickListener(this);
        findViewById(R.id.buy_all_product_money_button).setOnClickListener(this);

        investButton = (TextView) findViewById(R.id.product_buy_button);
        investButton.setOnClickListener(this);
        income30DayView = (TextView) findViewById(R.id.product_buy_30_day_income);
        investMoneyView = (TextView) findViewById(R.id.product_buy_invest_money);
        inputInvestView = (EditText) findViewById(R.id.product_buy_input_invest_count);
        textWatcher = new InvestTextWatcher();
        inputInvestView.addTextChangedListener(textWatcher);

        couponsContainerLayout = (LinearLayout) findViewById(R.id.choice_red_ticket_container);
        couponsTitleView = (TextView) findViewById(R.id.choice_red_ticket_title);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            Product p = bundle.getParcelable(ProductInfoAction.BOUND_FRAGMENT_INFO_DATA);
            if (p != null){
                this.product = p;
                canBuyView.setText(String.format(Locale.US, "%.0f", product.getSurplusMoney()));
                ruleView.setData((float) product.getSurplusMoney());
                if (product.getGcId() != ProductEnum.LUOBO_KUAI_ZHUAN.getProductTypeId()){
                    preMoneyText.setText(getString(R.string.product_buy_ding_qi_pre_money));
                    ViewStub dingQiStub = (ViewStub) findViewById(R.id.product_buy_ding_qi_stub);
                    dingQiStub.inflate();
                    TextView aprView = (TextView) findViewById(R.id.product_info_apr);
                    aprView.setText(String.format(Locale.US, "%.2f", product.getProceeds()));
                    TextView dayView = (TextView) findViewById(R.id.product_info_invest_day);
                    dayView.setText(String.valueOf(product.getInvestTime()));
                    TextView canBuyView = (TextView) findViewById(R.id.product_info_can_buy_money);
                    canBuyView.setText(String.format(Locale.US, "%.0f", product.getSurplusMoney()));
                    setInterestMoney(100f);
                }else {
                    inputInvestView.setHint(getString(R.string.product_info_input_buy_money_100_hint));
                    ViewStub huoQiStub = (ViewStub) findViewById(R.id.product_buy_kuai_zhuan_stub);
                    huoQiStub.inflate();
                    TextView aprView = (TextView) findViewById(R.id.product_buy_apr);
                    aprView.setText(String.format(Locale.US, "%.2f", product.getProceeds()));
                }
            }
        }
    }

    private class InvestTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            inputInvestView.setSelection(s.toString().length());
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String input = s.toString();
            inputInvestView.setSelection(input.length());
        }

        @Override
        public void afterTextChanged(Editable s) {
            String input = s.toString();
            if (!TextUtils.isEmpty(input)){
                investMoneyView.setText(input);
                try {
                    int money = Integer.parseInt(input);
                    double canBuy = product.getSurplusMoney();
                    if (money > canBuy){
                        inputInvestView.setText(String.format(Locale.CHINA, "%.0f", canBuy));
                        money = (int)canBuy;
                    }
                    setInterestMoney(money);
                }catch (NumberFormatException e){
                    e.printStackTrace();
                }
            }else {
                investMoneyView.setText("0");
                setInterestMoney(0);
            }
        }
    }

    @Override
    protected void initDependencies() {
        super.initDependencies();
        Log.i("chen", ProductBuyActivity.this.getClass().getName() + ":onCreate");
        productBuyStore = ProductBuyStore.getInstance();
        couponsStore = UsableCouponsStore.getInstance();
        dispatcher.register(productBuyStore);
        dispatcher.register(couponsStore);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("chen", ProductBuyActivity.this.getClass().getName() + ":onResume");
        productBuyStore.register(this);
        if (mUser != null){
            enableMoney = mUser.getEnAbleMoney();
            if (TextUtils.isEmpty(enableMoney)){
                accountMoney.setText("0.0");
                }else {
                accountMoney.setText(mUser.getEnAbleMoney());
            }
            appActionsCreator.getUsableRedPacket(mUser.getUserId(), product.getGcId(), mUser.getToken());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("chen", ProductBuyActivity.this.getClass().getName() + ":onStop");
        productBuyStore.unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("chen", ProductBuyActivity.this.getClass().getName() + ":onDestroy");
        dispatcher.unregister(productBuyStore);
        dispatcher.unregister(couponsStore);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.buy_all_product_money_button:
                if (product != null){
                    float userMoney = 0f;
                    float surplusMoney = (float)product.getSurplusMoney();
                    if (TextUtils.isEmpty(enableMoney)){
                        userMoney = 0f;
                    }else {
                        try{
                            userMoney = Float.parseFloat(enableMoney);
                        }catch (NumberFormatException e){
                            e.printStackTrace();
                        }
                    }

                    if (userMoney >= surplusMoney){
                        inputInvestView.setText(String.valueOf((int)surplusMoney));
                    }else {
                        inputInvestView.setText(String.valueOf((int)userMoney));
                    }
                }
                break;
            case R.id.product_buy_recharge_button:
                if (mUser == null){
                    return;
                }
                if (!Util.isNetworkAvailable(appContext)){
                    UIHelper.ToastMessage(appContext, getString(R.string.net_work_invalid));
                    return;
                }
                if (!mUser.isAutonym()){
                    UIHelper.ToastMessage(this, getString(R.string.please_first_register_in_huifu));
                    Bundle bundle = new Bundle();
                    bundle.putString(WebAction.WEB_REQUEST_TYPE, WebAction.REQUEST_OPEN_ACCOUNT);
                    MainRouter.getInstance(this).showActivity(ModuleID.USER_MODULE_ID, UserUI.WebActivity, bundle);
                    return;
                }

                Bundle bundle = new Bundle();
                bundle.putString(WebAction.WEB_REQUEST_TYPE, WebAction.REQUEST_RECHARGE);
                MainRouter.getInstance(this).showActivity(ModuleID.USER_MODULE_ID, UserUI.RechargeActivity, bundle);
                break;
            case R.id.product_buy_button:
                if (!Util.isNetworkAvailable(appContext)){
                    UIHelper.ToastMessage(appContext, getString(R.string.net_work_invalid));
                    return;
                }
                handNotice();
                break;
        }
    }

    @Subscribe
    public void onProductBuyStoreChange(Store.StoreChangeEvent event) {
        String type = event.getType();
        switch (type){
            case ProductBuyAction.ACTION_REQUEST_START:
            case UsableCouponsAction.ACTION_REQUEST_START:
                showLoadingDialog();
                break;
            case UsableCouponsAction.ACTION_REQUEST_FINISH:
            case ProductBuyAction.ACTION_REQUEST_FINISH:
                dismissLoadingDialog();
                break;
            case UsableCouponsAction.ACTION_REQUEST_FAIL:
            case ProductBuyAction.ACTION_REQUEST_FAIL:
                UIHelper.ToastMessage(this, getString(R.string.net_work_error));
                break;
            case UsableCouponsAction.ACTION_REQUEST_INVALID_TOKEN:
            case ProductBuyAction.ACTION_REQUEST_INVALID_TOKEN:
                MainRouter.getInstance(this).showActivity(ModuleID.USER_MODULE_ID, UserUI.LoginActivity);
                finish();
                break;
            case UsableCouponsAction.ACTION_REQUEST_ERROR_MESSAGE:
            case ProductBuyAction.ACTION_REQUEST_ERROR_MESSAGE:
                String msg = event.getMessage();
                UIHelper.ToastMessage(this, msg);
                break;
            case ProductBuyAction.ACTION_REQUEST_ORDER_SUCCESS:
                Order order = productBuyStore.getOrder();
                if (order != null){
                    Bundle buyBundle = new Bundle();
                    buyBundle.putString(WebAction.WEB_REQUEST_TYPE, WebAction.REQUEST_BUY);
                    buyBundle.putInt(WebAction.REQUEST_PAY_ORDER_ID, order.getBuyOrderId());
                    MainRouter.getInstance(ProductBuyActivity.this).showActivityForResult(ModuleID.USER_MODULE_ID, ProductBuyActivity.this, UserUI.WebActivity, WebAction.BUY_PRODUCT_REQUEST_CODE, buyBundle);
                }
                break;
            case UsableCouponsAction.ACTION_RQEUST_USABLE_RED_PACKET_SUCCESS:
                ArrayList<RedPacket> coupons = couponsStore.getRedPackets();
                if (coupons != null && coupons.size() > 0){
                    setCouponsData(coupons);
                    couponsTitleView.setVisibility(View.VISIBLE);
                }else {
                    couponsTitleView.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("chen", ProductBuyActivity.this.getClass().getName() + ":onActivityResult");
        if (requestCode == WebAction.BUY_PRODUCT_REQUEST_CODE && resultCode == RESULT_OK){
            setResult(RESULT_OK, data);
            finish();
        }
    }

    private void setCouponsData(ArrayList<RedPacket> redPackets){
        if (redPackets == null || redPackets.size() == 0){
            return;
        }
        couponsContainerLayout.removeAllViews();
        for (RedPacket redPacket : redPackets){
            ChoiceRedTicketView view = new ChoiceRedTicketView(this);
            view.setViewData(redPacket);
            view.setOnRedTicketCheckedListener(new OnRedTicketCheckedListener() {
                @Override
                public void onRedTicketCheckedListener(boolean isChecked, RedPacket coupon) {
                    resetMoneyData(isChecked, coupon);
                }
            });
            couponsContainerLayout.addView(view);
        }
    }

    //当选择红包时，重计算利息及所得
    private void resetMoneyData(boolean isChecked, RedPacket coupon){
        if (coupon == null){
            return;
        }
        if (isChecked){
            redPackets.add(coupon);
        }else {
            redPackets.remove(coupon);
        }
        xianJinMoney = 0;
        benJinMoney = 0;
        for (RedPacket redPacket : redPackets){
            if (redPacket.getCouponType() == 0){
                xianJinMoney += redPacket.getCouponMoney();
            }else if (redPacket.getCouponType() == 1){
                benJinMoney += redPacket.getCouponMoney();
            }
        }
        float couponMoney = xianJinMoney + benJinMoney;
        if (couponMoney == 0){
            couponMoneyView.setVisibility(View.GONE);
            couponMoneyAddView.setVisibility(View.GONE);
        }else {
            couponMoneyView.setVisibility(View.VISIBLE);
            couponMoneyAddView.setVisibility(View.VISIBLE);
            couponMoneyView.setText(String.format(Locale.US, "%.0f", couponMoney));
        }
        String inputInvest = inputInvestView.getText().toString();
        if (!TextUtils.isEmpty(inputInvest)){
            float invest = Float.parseFloat(inputInvest);
            setInterestMoney(invest);
        }else {
            setInterestMoney(0);
        }
    }

    //生成定单前，得到所有所选红包id
    private String getCouponsId(){
        StringBuilder builder = new StringBuilder();
        for (RedPacket redPacket : redPackets){
            builder.append(String.valueOf(redPacket.getCouponId()));
            builder.append(",");
        }
        String result = builder.toString();
        if (result.length() > 0){
            return result.substring(0, result.length() - 1);
        }
        return "";
    }

    //从买入金额计算利息
    private void setInterestMoney(float investMoney){
        if (product == null){
            return;
        }
        double preInterestMoney;
        double preCouponMoney;
        if (product.getGcId() == ProductEnum.LUOBO_KUAI_ZHUAN.getProductTypeId()){
            preInterestMoney = Util.getHuoQiPrdMoney(investMoney, product.getProceeds());
            preCouponMoney = Util.getHuoQiPrdMoney(benJinMoney, product.getProceeds());
        }else {
            int investTime = product.getInvestTime();
            String endTime = product.getValueTime();
            Calendar todayCalendar = Calendar.getInstance();
            todayCalendar.set(Calendar.HOUR_OF_DAY, 0);
            todayCalendar.set(Calendar.MINUTE, 0);
            todayCalendar.set(Calendar.SECOND, 0);
            todayCalendar.set(Calendar.MILLISECOND, 0);
            long todayTimeLong = todayCalendar.getTimeInMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Calendar cal = Calendar.getInstance();
            try {
                cal.setTime(sdf.parse(endTime));
                long endTimeLong = cal.getTimeInMillis();
                investTime = (int) ((endTimeLong - todayTimeLong) / (24 * 60 * 60L * 1000));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            preInterestMoney = Util.getDingQiPreMoney(investMoney, investTime, product.getProceeds());
            preCouponMoney = Util.getDingQiPreMoney(benJinMoney + xianJinMoney, investTime, product.getProceeds());
            preCouponMoney += xianJinMoney;
        }
        income30DayView.setText(String.format(Locale.US, "%.2f", preInterestMoney));
        if (preCouponMoney == 0){
            couponRateAddView.setVisibility(View.GONE);
            couponRateView.setVisibility(View.GONE);
        }else {
            couponRateAddView.setVisibility(View.VISIBLE);
            couponRateView.setVisibility(View.VISIBLE);
            couponRateView.setText(String.format(Locale.US, "%.2f", preCouponMoney));
        }
    }

    //当尺子移动时，计算投入资金
    private void calculate(int distance){
        int blockWidth = getResources().getDimensionPixelSize(R.dimen.buy_rule_height_line_height);
        int scrollBlock = distance / blockWidth;
        int money = 100 + scrollBlock * 100;
        investMoneyView.setText(String.valueOf(money));
        setInterestMoney(money);
        inputInvestView.setText(String.valueOf(money));
    }

    private void showNoticeDialog(final int money){
        final Dialog noticeDialog = new Dialog(this, R.style.DialogStyle);
        View view = LayoutInflater.from(this).inflate(R.layout.product_buy_dialog_notice_layout, null);
        view.findViewById(R.id.product_notice_dialog_ok_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noticeDialog.dismiss();
                if (mUser != null){
                    appActionsCreator.getPayOrder(mUser.getUserId(), mUser.getToken(), product.getGoodId(), money, getCouponsId());
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

    private void handNotice(){
        if (mUser == null || product == null){
            return;
        }

        if (!mUser.isAutonym()){
            UIHelper.ToastMessage(this, getString(R.string.please_first_register_in_huifu));
            Bundle bundle = new Bundle();
            bundle.putString(WebAction.WEB_REQUEST_TYPE, WebAction.REQUEST_OPEN_ACCOUNT);
            MainRouter.getInstance(this).showActivity(ModuleID.USER_MODULE_ID, UserUI.WebActivity, bundle);
            return;
        }

        String input = inputInvestView.getText().toString();
        if (TextUtils.isEmpty(input)){
            UIHelper.ToastMessage(this, getString(R.string.product_buy_input_money));
            return;
        }
        int investMoney = 0;
        try {
            investMoney = Integer.parseInt(input);
        }catch (NumberFormatException e){
            e.printStackTrace();
        }

        float accountEnableMoney = 0;
        if (!TextUtils.isEmpty(enableMoney)){
            accountEnableMoney = Float.parseFloat(enableMoney);
        }

        if (accountEnableMoney < investMoney){
            UIHelper.ToastMessage(this, getString(R.string.product_buy_account_less_buy));
            return;
        }
        showNoticeDialog(investMoney);
    }
}
