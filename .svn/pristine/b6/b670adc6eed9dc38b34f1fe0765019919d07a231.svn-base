package com.baluobo.product.adapter;

import android.content.Context;
import android.text.TextUtils;
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
 * Created on:16/5/27.
 */
public class ProductRecordAdapter extends BaseAdapter {
    private ArrayList<Order> productRecord;
    public Context mContext;
    public ProductRecordAdapter(Context context){
        this.mContext = context;
        productRecord = new ArrayList<>();
    }

    public void setProductRefreshData(ArrayList<Order> orders){
        if (orders != null && orders.size() > 0){
            this.productRecord = orders;
        }
    }

    public void setProductLoadData(ArrayList<Order> record){
        if (record != null){
            productRecord.addAll(record);
        }
    }

    @Override
    public int getCount() {
        return productRecord.size();
    }

    @Override
    public Object getItem(int position) {
        return productRecord.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.product_info_record_list_item, null);
        }
        TextView orderIdView = (TextView) convertView.findViewById(R.id.product_order_id);
        TextView orderMoneyView = (TextView) convertView.findViewById(R.id.product_order_money);
        TextView orderTimeView = (TextView) convertView.findViewById(R.id.product_order_time);
        Order order = productRecord.get(position);
        if (order != null){
            String orderNo = order.getBuyOrderNo();
            if (!TextUtils.isEmpty(orderNo) && orderNo.length() >= 7){
                orderNo = orderNo.substring(0, 3) + "****" + orderNo.substring(orderNo.length() - 4, orderNo.length());
            }
            orderIdView.setText(orderNo);
            orderMoneyView.setText(String.format(Locale.US, "%.0f", order.getCountMoney()));
            orderTimeView.setText(order.getCreateTime());
        }

        return convertView;
    }
}
