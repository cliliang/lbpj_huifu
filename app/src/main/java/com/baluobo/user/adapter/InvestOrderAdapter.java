package com.baluobo.user.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baluobo.R;
import com.baluobo.common.tools.Util;
import com.baluobo.user.model.ExpOrder;
import com.baluobo.user.model.InvestOrder;
import com.baluobo.user.model.Order;
import com.baluobo.user.model.ProductEnum;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/21.
 */
public class InvestOrderAdapter extends BaseAdapter {
    private List<Order> normalOrders;
    private ExpOrder expOrder;
    private InvestOrder data;
    private Context mContext;
    public InvestOrderAdapter(Context cnt){
        this.mContext = cnt;
        normalOrders = new ArrayList<>();
    }

    public void setData(InvestOrder investOrder){
        this.data = investOrder;
        if (data != null){
            List<Order> orders = data.getBuyOrders();
            if (orders != null && orders.size() > 0){
                this.normalOrders = orders;
            }
            expOrder = data.getExperienceOrder();
        }
    }

    public InvestOrder getData(){
        return data;
    }

    public void setLoadingData(List<Order> list){
        if (list == null || list.size() == 0){
            return;
        }
        normalOrders.addAll(list);
    }

    public int getDataSize(){
        return normalOrders.size();
    }
    @Override
    public int getCount() {
        int expOrderSize = 0;
        if (expOrder != null){
            expOrderSize = 1;
        }
        return normalOrders.size() + expOrderSize;
    }

    @Override
    public Object getItem(int position) {
        if (expOrder == null){
            return normalOrders.get(position - 1);
        }else {
            if (position == 1){
                return expOrder;
            }else {
                return normalOrders.get(position - 2);
            }
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.my_invest_order_item_adapter_layout, null);
        }
        TextView productNameView = (TextView) convertView.findViewById(R.id.my_invest_order_product_name);
        TextView principalView = (TextView) convertView.findViewById(R.id.my_invest_order_principle);
        TextView principalAndInterestView = (TextView) convertView.findViewById(R.id.my_invest_order_principle_and_interest);
        TextView symbolRedView = (TextView) convertView.findViewById(R.id.my_invest_money_from_red_packet);
        TextView redPacketView = (TextView) convertView.findViewById(R.id.my_invest_red_packet);
        TextView symbolAddView = (TextView) convertView.findViewById(R.id.my_invest_add_symbol);
        TextView redPacketMoney = (TextView) convertView.findViewById(R.id.my_invest_all_red_packet_money);
        TextView typeView = (TextView) convertView.findViewById(R.id.my_invest_order_type_view);
        if (expOrder == null){
            Order order = normalOrders.get(position);
            if (order != null){
                productNameView.setText(order.getGoodName());
                double principalMoney = order.getCountMoney();
                principalView.setText(String.format(Locale.US, mContext.getString(R.string.principal_money_text), principalMoney));

                double cashRedPacket = order.getCashMoney();
                double principalPacket = order.getPrincipalMoney();
                if (Double.compare(cashRedPacket+principalPacket, 0) > 0){
                    symbolRedView.setVisibility(View.VISIBLE);
                    symbolAddView.setVisibility(View.VISIBLE);
                    redPacketMoney.setVisibility(View.VISIBLE);
                    redPacketView.setVisibility(View.VISIBLE);
                    redPacketView.setText(Util.doubleMoveZero(cashRedPacket + principalPacket));
                }else {
                    symbolRedView.setVisibility(View.GONE);
                    symbolAddView.setVisibility(View.GONE);
                    redPacketMoney.setVisibility(View.GONE);
                    redPacketView.setVisibility(View.GONE);
                }
                if (order.getGcId() == ProductEnum.LUOBO_KUAI_ZHUAN.getProductTypeId()){
                    typeView.setText(mContext.getString(R.string.invest_detail_pre_30_interest));
                    principalAndInterestView.setText(String.format(Locale.US, "%.2f", Util.getHuoQiPrdMoney(order.getCountMoney(), order.getProceeds()) + order.getCountMoney()));
                    redPacketMoney.setText(String.format(Locale.CHINA, "%.2f", Util.getHuoQiPrdMoney(cashRedPacket + principalPacket, order.getProceeds()) + cashRedPacket));
                }else {
                    typeView.setText(mContext.getString(R.string.principal_and_interest_money_text));

                    String buyDay = order.getCreateTime();
                    String endDay = order.getEndTime();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
                    int investDay = 0;
                    try {
                        Date buyDate = format.parse(buyDay);
                        buyDay = format1.format(buyDate);
                        buyDate = format1.parse(buyDay);

                        Date endDate = format1.parse(endDay);
                        investDay = Util.dayFromEnd(buyDate.getTime(), endDate.getTime());
                        Log.i("chen", "days:" + investDay);
                    }catch (ParseException e){
                        e.printStackTrace();
                    }

                    principalAndInterestView.setText(String.format(Locale.US, "%.2f", Util.getDingQiPreMoney((float) order.getCountMoney(), investDay, order.getProceeds()) + order.getCountMoney()));
                    redPacketMoney.setText(String.format(Locale.CHINA, "%.2f", Util.getDingQiPreMoney((float)( cashRedPacket + principalPacket), investDay, order.getProceeds()) + cashRedPacket));
                }
            }
        }else {
            if (position == 0){
                symbolRedView.setVisibility(View.GONE);
                symbolAddView.setVisibility(View.GONE);
                redPacketMoney.setVisibility(View.GONE);
                redPacketView.setVisibility(View.GONE);

                typeView.setText(mContext.getString(R.string.exp_income_interest));
                productNameView.setText(expOrder.getGoodName());
                float benJin = expOrder.getCountMoney();
                principalView.setText(String.format(Locale.US, mContext.getString(R.string.principal_money_text), benJin));
                float liXi = expOrder.getPreProceeds();
                principalAndInterestView.setText(String.format(Locale.US, "%.2f", liXi));
            }else {
                Order order = normalOrders.get(position - 1);
                if (order != null){
                    productNameView.setText(order.getGoodName());
                    double principalMoney = order.getCountMoney();
                    principalView.setText(String.format(Locale.US, mContext.getString(R.string.principal_money_text), principalMoney));

                    double cashRedPacket = order.getCashMoney();
                    double principalPacket = order.getPrincipalMoney();
                    if (Double.compare(cashRedPacket+principalPacket, 0) > 0){
                        symbolRedView.setVisibility(View.VISIBLE);
                        symbolAddView.setVisibility(View.VISIBLE);
                        redPacketMoney.setVisibility(View.VISIBLE);
                        redPacketView.setVisibility(View.VISIBLE);
                        redPacketView.setText(Util.doubleMoveZero(cashRedPacket + principalPacket));
                    }else {
                        symbolRedView.setVisibility(View.GONE);
                        symbolAddView.setVisibility(View.GONE);
                        redPacketMoney.setVisibility(View.GONE);
                        redPacketView.setVisibility(View.GONE);
                    }
                    if (order.getGcId() == ProductEnum.LUOBO_KUAI_ZHUAN.getProductTypeId()){
                        typeView.setText(mContext.getString(R.string.invest_detail_pre_30_interest));
                        principalAndInterestView.setText(String.format(Locale.US, "%.2f", Util.getHuoQiPrdMoney(order.getCountMoney(), order.getProceeds()) + order.getCountMoney()));
                        redPacketMoney.setText(String.format(Locale.CHINA, "%.2f", Util.getHuoQiPrdMoney(cashRedPacket + principalPacket, order.getProceeds()) + cashRedPacket));
                    }else {
                        typeView.setText(mContext.getString(R.string.principal_and_interest_money_text));

                        String buyDay = order.getCreateTime();
                        String endDay = order.getEndTime();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
                        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
                        int investDay = 0;
                        try {
                            Date buyDate = format.parse(buyDay);
                            buyDay = format1.format(buyDate);
                            buyDate = format1.parse(buyDay);

                            Date endDate = format1.parse(endDay);
                            investDay = Util.dayFromEnd(buyDate.getTime(), endDate.getTime());
                        }catch (ParseException e){
                            e.printStackTrace();
                        }

                        principalAndInterestView.setText(String.format(Locale.US, "%.2f", Util.getDingQiPreMoney((float) order.getCountMoney(), investDay, order.getProceeds()) + order.getCountMoney()));
                        redPacketMoney.setText(String.format(Locale.CHINA, "%.2f", Util.getDingQiPreMoney((float)( cashRedPacket + principalPacket), investDay, order.getProceeds()) + cashRedPacket));
                    }
                }
            }
        }
        return convertView;
    }
}
