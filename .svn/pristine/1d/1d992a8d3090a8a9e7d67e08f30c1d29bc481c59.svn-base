package com.baluobo.product.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baluobo.R;
import com.baluobo.common.tools.Util;
import com.baluobo.product.bean.Product;

import java.util.Locale;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/11/21.
 */
public class ProductOnlineItemView extends LinearLayout {
    private TextView productNameView;
    private TextView bankNameView;

    private TextView interestRateView;
    private TextView dayView;
    private TextView countDownView;
    private ProductItemCircleView circleView;

    public ProductOnlineItemView(Context context) {
        super(context);
        init(context);
    }

    public ProductOnlineItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ProductOnlineItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        LayoutInflater.from(context).inflate(R.layout.product_item_layout, this);
        productNameView = (TextView) findViewById(R.id.product_item_name);
        bankNameView = (TextView) findViewById(R.id.product_item_bank_name);
        interestRateView = (TextView) findViewById(R.id.product_item_interest_rate);
        dayView = (TextView) findViewById(R.id.product_item_day_number);
        circleView = (ProductItemCircleView) findViewById(R.id.product_item_circle_view);
        circleView.setVisibility(GONE);
        countDownView = (TextView) findViewById(R.id.product_item_count_down_view);
        findViewById(R.id.product_item_huoqi_icon).setVisibility(GONE);
    }

    public void setCountDown(long time){
        String string = Util.getDistanceSec(time);
        countDownView.setText(string);
    }

    public void setItemData(Product product){
        if (product == null){
            return;
        }
        productNameView.setText(product.getGoodName());

        String payLabel = product.getPayLabel();
        if (payLabel.length() > 8){
            payLabel = payLabel.substring(3, payLabel.length() - 5);
            if (payLabel.length() > 12){
                payLabel = payLabel.substring(0, 12) + "...";
            }
        }
        bankNameView.setText(payLabel);
        interestRateView.setText(String.format(Locale.US, "%.2f", product.getProceeds()));
        dayView.setText(String.valueOf(product.getInvestTime()));
    }
}
