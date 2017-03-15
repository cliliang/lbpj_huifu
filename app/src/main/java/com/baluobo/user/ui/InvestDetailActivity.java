package com.baluobo.user.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baluobo.R;
import com.baluobo.app.ui.BaseToolBarActivity;
import com.baluobo.common.tools.Util;
import com.baluobo.user.actions.InvestAction;
import com.baluobo.user.model.Order;
import com.baluobo.user.model.ProductEnum;

import java.util.Locale;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/25.
 */
public class InvestDetailActivity extends BaseToolBarActivity {
    private TextView buyMoneyView;
    private TextView totalBackMoneyView;
    private TextView backMoneyView;
    private TextView aprView;
    private TextView preMoneyTextView;
    private View highView;
    private View buyLayout;
    private View backView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invest_detail_activity_layout);
        setTitle(getResources().getString(R.string.trading_detail));
        setupViews();
    }

    @Override
    protected void setupViews() {
        super.setupViews();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            Order order = bundle.getParcelable(InvestAction.BUNDLE_ORDER_DATA);
            if (order != null){
                TextView nameView = (TextView) findViewById(R.id.invest_detail_product_name);
                nameView.setText(order.getGoodName());
                TextView statusView = (TextView) findViewById(R.id.invest_detail_order_status);
                int buyFlg = order.getBuyflg();
//                3表示投资中、2表示还款中、1表示已还款
                if (buyFlg == 3) {
                    statusView.setText(getString(R.string.my_invest_investing));
                }else if (buyFlg == 2){
                    statusView.setText(getString(R.string.my_invest_repay));
                }else {
                    statusView.setText(getString(R.string.my_invest_complete));
                }
                preMoneyTextView = (TextView) findViewById(R.id.preMoney_money_view_text);
                buyMoneyView = (TextView) findViewById(R.id.invest_detail_invest_money_view);
                totalBackMoneyView = (TextView) findViewById(R.id.invest_detail_total_pay_back_money);
                backMoneyView = (TextView) findViewById(R.id.invest_detail_pay_back_money_view);
                buyLayout = findViewById(R.id.invest_detail_back_money_layout);
                highView = findViewById(R.id.invest_detail_total_pay_into_layout);
                backView = findViewById(R.id.invest_detail_pay_back_layout);

                if (order.getGcId() == ProductEnum.LUOBO_KUAI_ZHUAN.getProductTypeId()){
                    preMoneyTextView.setText(getString(R.string.invest_detail_pre_30_interest));
                    findViewById(R.id.invest_detail_ding_qi_layout).setVisibility(View.GONE);
                    findViewById(R.id.invest_detail_ding_qi_layout).setVisibility(View.GONE);
                    double buyMoney = order.getCountMoney();
                    double interestMoney = Util.getHuoQiPrdMoney(buyMoney + order.getPrincipalMoney(), order.getProceeds());
                    setBuyMoney((float) buyMoney, (float) interestMoney);
                }else {
                    double buyMoney = order.getCountMoney();
                    double interestMoney = order.getPreProceeds();
                    setBuyMoney((float) buyMoney, (float) interestMoney);
                }

                TextView backTimeView = (TextView) findViewById(R.id.invest_detail_pay_time_view);
                backTimeView.setText(order.getEndTime());
                TextView orderTimeView = (TextView) findViewById(R.id.invest_detail_take_a_order_time);
                orderTimeView.setText(order.getCreateTime());

                aprView = (TextView) findViewById(R.id.invest_detail_arp_view);
                aprView.setText(String.format(Locale.US, "%.2f", order.getProceeds()));
            }
        }
    }

    /**
     *
     * @param buyMoney 本金
     * @param backMoney 利息
     */
    private void setBuyMoney(final float buyMoney, float backMoney){
        long longDuration = 2000;
        long shortDuration = 347;

        //预计收益 数字变化
        final ValueAnimator backAnimator = ValueAnimator.ofFloat(backMoney);
        backAnimator.setDuration(shortDuration);
        backAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                backMoneyView.setText(String.format(Locale.US, "%.2f", value));
                totalBackMoneyView.setText(String.format(Locale.US, "%.2f", buyMoney + value));
            }
        });

        //预计收益 柱形变化
        int lowHeight = getResources().getDimensionPixelSize(R.dimen.invest_low_height);
        final  LinearLayout.LayoutParams lowParams = (LinearLayout.LayoutParams) backView.getLayoutParams();
        final ValueAnimator lowAnimator = ValueAnimator.ofInt(lowHeight);
        lowAnimator.setDuration(shortDuration);
        lowAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int lowHeight = (int) animation.getAnimatedValue();
                lowParams.height = lowHeight;
                backView.setLayoutParams(lowParams);
            }
        });

        //本金 数字变化
        ValueAnimator buyAnimator = ValueAnimator.ofFloat(buyMoney);
        buyAnimator.setDuration(longDuration);
        buyAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float)animation.getAnimatedValue();
                buyMoneyView.setText(String.format(Locale.US, "%.2f", value));
                totalBackMoneyView.setText(String.format(Locale.US, "%.2f", value));
                if (value == buyMoney){
                    buyLayout.setVisibility(View.VISIBLE);
                }
            }
        });
        buyAnimator.start();

        //本金 柱状变化
        final int highHeight = getResources().getDimensionPixelSize(R.dimen.invest_high_height);
        final LinearLayout.LayoutParams highParams = (LinearLayout.LayoutParams) highView.getLayoutParams();
        ValueAnimator highAnimator = ValueAnimator.ofInt(highHeight);
        highAnimator.setDuration(longDuration);
        highAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int highValue = (int) animation.getAnimatedValue();
                highParams.height = highValue;
                highView.setLayoutParams(highParams);
            }
        });
        highAnimator.start();



        highAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                backAnimator.start();
                lowAnimator.start();
            }
        });
    }
}
