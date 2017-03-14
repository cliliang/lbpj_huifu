package com.baluobo.user.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baluobo.R;
import com.baluobo.user.model.Order;

import java.util.ArrayList;
import java.util.Locale;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/25.
 */
public class RedeemAdapter extends BaseAdapter {
    private OnRedeemButtonClickListener listener;
    private ArrayList<Order> products;
    private Context mContext;
    public RedeemAdapter(Context cnt){
        this.mContext = cnt;
        products = new ArrayList<>();
    }

    public void setData(ArrayList<Order> data){
        if (data != null){
            this.products = data;
        }else {
            products.clear();
        }
    }

    public void setLoadData(ArrayList<Order> data){
        if (data != null){
            this.products.addAll(data);
        }
    }

    @Override
    public int getCount() {
        return products.size();
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
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.redeem_list_item_layout, null);
        }
        TextView nameView = (TextView) convertView.findViewById(R.id.redeem_item_product_name);
        TextView totalView = (TextView) convertView.findViewById(R.id.redeem_item_order_back);
        TextView benjinView = (TextView) convertView.findViewById(R.id.redeem_item_ben_jin);
        TextView button = (TextView) convertView.findViewById(R.id.redeem_item_redeem_button);
        final Order order = products.get(position);
        if (order != null){
            nameView.setText(order.getGoodName());
            totalView.setText(String.format(Locale.US, mContext.getString(R.string.redeem_item_total_money), (float)(order.getSpeedMoney() - order.getCountMoney())));
            benjinView.setText(String.format(Locale.US, mContext.getString(R.string.redeem_item_ben_jin), (float)order.getCountMoney()));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        listener.onRedeemButtonClickListener(order);
                    }
                }
            });
        }
        return convertView;
    }

    public void setOnRedeemButtonClickListener(OnRedeemButtonClickListener listener){
        this.listener = listener;
    }
}
