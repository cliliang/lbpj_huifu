package com.baluobo.common.views;

import android.content.Context;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baluobo.R;
import com.baluobo.product.bean.Product;
import com.baluobo.product.views.ProductItemCircleView;
import com.baluobo.user.model.ProductEnum;

import java.util.Locale;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/19.
 */
public class ProductItemView extends LinearLayout {
    private TextView productNameView;
    private TextView bankNameView;

    private TextView interestRateView;
    private TextView dayView;
    private ProductItemCircleView circleView;
    private LinearLayout onlineLayout;
    private ImageView huoqiIcon, newIcon;
    private TextView dayDescView;
    private RelativeLayout huoqiLayout, dingqiLayout;

    public ProductItemView(Context context) {
        super(context);
        init(context, null);
    }

    public ProductItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ProductItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet){
        LayoutInflater.from(context).inflate(R.layout.product_item_layout, this);
        productNameView = (TextView) findViewById(R.id.product_item_name);
        bankNameView = (TextView) findViewById(R.id.product_item_bank_name);
        interestRateView = (TextView) findViewById(R.id.product_item_interest_rate);
        dayView = (TextView) findViewById(R.id.product_item_day_number);
        circleView = (ProductItemCircleView) findViewById(R.id.product_item_circle_view);
        onlineLayout = (LinearLayout) findViewById(R.id.product_item_online_layout);
        onlineLayout.setVisibility(INVISIBLE);
        huoqiIcon = (ImageView) findViewById(R.id.product_item_huoqi_icon);
        dayDescView = (TextView) findViewById(R.id.product_item_day_desc_view);
        huoqiLayout = (RelativeLayout) findViewById(R.id.product_item_huoqi_layout);
        dingqiLayout = (RelativeLayout) findViewById(R.id.product_item_dingqi_layout);
        newIcon = (ImageView) findViewById(R.id.product_item_new_icon);
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

        if (ProductEnum.getProduct(product.getGcId()) == ProductEnum.LUOBO_KUAI_ZHUAN){
            huoqiIcon.setVisibility(VISIBLE);
            circleView.setVisibility(GONE);

            huoqiLayout.setVisibility(VISIBLE);
            dingqiLayout.setVisibility(GONE);
            dayDescView.setVisibility(INVISIBLE);
        }else {
            huoqiIcon.setVisibility(GONE);
            circleView.setVisibility(VISIBLE);

            huoqiLayout.setVisibility(GONE);
            dingqiLayout.setVisibility(VISIBLE);
            dayDescView.setVisibility(VISIBLE);

            int buyFlg = product.getBuyflg();
            if (buyFlg == 4){
                newIcon.setVisibility(VISIBLE);
                double moneyRadiu = (product.getBuyMoney() - product.getSurplusMoney()) / product.getBuyMoney();
                circleView.setRadiu((int) product.getSurplusMoney(), (float) moneyRadiu);
            }else {
                newIcon.setVisibility(GONE);
                circleView.setRadiu(0, 1);
            }
        }
    }
}
