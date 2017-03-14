package com.baluobo.product.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baluobo.R;
import com.baluobo.product.bean.Product;
import com.baluobo.product.views.ProductItemCircleView;
import com.baluobo.user.model.ProductEnum;

import java.util.ArrayList;
import java.util.Locale;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/23.
 */
public class ProductItemAdapter extends BaseAdapter {
    private ArrayList<Product> products;
    private Context context;
    private int screenWidth;
    public ProductItemAdapter(Context context, int width){
        this.context = context;
        products = new ArrayList<>();
        this.screenWidth = width;
    }

    public void setRefreshData(ArrayList<Product> refreshData){
        if (refreshData != null && refreshData.size() > 0){
            this.products.clear();
            this.products = refreshData;
        }
    }
    public void setLoadData(ArrayList<Product> loadData){
        if (loadData != null && loadData.size() > 0){
            products.addAll(loadData);
        }
    }

    @Override
    public int getCount() {
        return products.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (position == 0){
            convertView = LayoutInflater.from(context).inflate(R.layout.product_list_top_banner_layout, null);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.product_info_top_banner);
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) imageView.getLayoutParams();
            params.height = 245 * screenWidth / 750;
            imageView.setLayoutParams(params);
            if (products.size() > 0){
                Product product = products.get(0);
                if (product.getGcId() == ProductEnum.LUOBO_DING_TOU.getProductTypeId()){
                    imageView.setImageResource(R.drawable.banner_ding_tou);
                }else if (product.getGcId() == ProductEnum.LUOBO_XIN_SHOU.getProductTypeId()){
                    imageView.setImageResource(R.drawable.banner_xin_shou);
                }else {
                    imageView.setImageResource(R.drawable.banner_yin_piao);
                }
            }
        }else {
            convertView = LayoutInflater.from(context).inflate(R.layout.product_item_layout, null);
            TextView productNameView = (TextView) convertView.findViewById(R.id.product_item_name);
            TextView bankNameView  = (TextView) convertView.findViewById(R.id.product_item_bank_name);
            TextView rateFloatView = (TextView) convertView.findViewById(R.id.product_item_interest_rate);
            TextView dayIntView = (TextView) convertView.findViewById(R.id.product_item_day_number);
            LinearLayout countLayout = (LinearLayout) convertView.findViewById(R.id.product_item_online_layout);
            ImageView newIconView = (ImageView) convertView.findViewById(R.id.product_item_new_icon);
            countLayout.setVisibility(View.INVISIBLE);
            final ProductItemCircleView circleView = (ProductItemCircleView) convertView.findViewById(R.id.product_item_circle_view);
            Product product = products.get(position - 1);
            if (product != null){
                productNameView.setText(product.getGoodName());
                String payLabel = product.getPayLabel();
                if (payLabel.length() > 8){
                    payLabel = payLabel.substring(3, payLabel.length() - 5);
                    if (payLabel.length() > 12){
                        payLabel = payLabel.substring(0, 12) + "...";
                    }
                }
                bankNameView.setText(payLabel);
                rateFloatView.setText(String.format(Locale.US, "%.2f", product.getProceeds()));
                dayIntView.setText(String.valueOf(product.getInvestTime()));

                int buyFlg = product.getBuyflg();
                if (buyFlg == 4){
                    newIconView.setVisibility(View.VISIBLE);
                    double moneyRadiu = (product.getBuyMoney() - product.getSurplusMoney()) / product.getBuyMoney();
                    circleView.setRadiu((int) product.getSurplusMoney(), (float) moneyRadiu);
                }else {
                    newIconView.setVisibility(View.GONE);
                    circleView.setRadiu(0, 1);
                }
            }
        }
        return convertView;
    }
}
