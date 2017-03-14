package com.baluobo.user.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baluobo.R;
import com.baluobo.user.model.RedPacket;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import uk.co.senab.photoview.Compat;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/8/18.
 */
public class RedPacketAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<RedPacket> packets;
    public RedPacketAdapter(Context cnt){
        this.mContext = cnt;
        packets = new ArrayList<>();
    }

    public void setData(ArrayList<RedPacket> data){
        if (data != null){
            this.packets = data;
        }
    }

    public void setLoadData(List<RedPacket> loadData){
        if (loadData == null || loadData.size() == 0){
            return;
        }
        packets.addAll(loadData);
    }

    public ArrayList<RedPacket> getData(){
        return packets;
    }

    @Override
    public int getCount() {
        return packets.size();
    }

    @Override
    public Object getItem(int position) {
        return packets.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fuli_list_item_layout, null);
        }
        TextView moneyView = (TextView) convertView.findViewById(R.id.red_packet_money_id);
        TextView typeView = (TextView) convertView.findViewById(R.id.red_packet_type_view);
        TextView contentView = (TextView) convertView.findViewById(R.id.red_packet_content_view);
        TextView endTimeView = (TextView) convertView.findViewById(R.id.red_packet_end_time);
        TextView iconView = (TextView) convertView.findViewById(R.id.red_packet_icon_id);
        TextView lineView = (TextView) convertView.findViewById(R.id.red_packet_line_view);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.red_packet_pass_type_mark_image);
        TextView ruleView = (TextView) convertView.findViewById(R.id.red_packet_ben_jin_rule);
        RedPacket packet = packets.get(position);
        if (packet != null){
            //0：未使用；1：已使用；2：已过期
            int colorBase = ContextCompat.getColor(mContext, R.color.colorBase);
            int color40 = ContextCompat.getColor(mContext, R.color.color_40);
            int state = packet.getState();
            if (state == 0){
                moneyView.setTextColor(colorBase);
                typeView.setTextColor(colorBase);
                endTimeView.setTextColor(colorBase);
                iconView.setTextColor(colorBase);
                ruleView.setTextColor(colorBase);
            }else {
                moneyView.setTextColor(color40);
                typeView.setTextColor(color40);
                endTimeView.setTextColor(color40);
                iconView.setTextColor(color40);
                ruleView.setTextColor(color40);
            }
            if (state == 0){
                endTimeView.setText(String.format(Locale.CHINESE, mContext.getString(R.string.red_ticket_can_use_end), packet.getEndTime()));
                imageView.setImageResource(R.drawable.banner_dot_normal);
            }else if (state == 1){
                endTimeView.setText(String.format(Locale.CHINESE, mContext.getString(R.string.used_ticket_date), packet.getEndTime()));
                imageView.setImageResource(R.drawable.icon_data_used);
            }else if (state == 2){
                endTimeView.setText(String.format(Locale.CHINESE, mContext.getString(R.string.red_ticket_can_use_end), packet.getEndTime()));
                imageView.setImageResource(R.drawable.icon_data_pass);
            }

            moneyView.setText(String.format(Locale.CHINESE, "%.0f", packet.getCouponMoney()));
            contentView.setText(packet.getCouponDesc());

            if (packet.getCouponType() == 0){
                typeView.setText(mContext.getString(R.string.cash_red_packet));
                ruleView.setVisibility(View.GONE);
            }else if (packet.getCouponType() == 1){
                typeView.setText(mContext.getString(R.string.principal_red_packet));
                ruleView.setVisibility(View.VISIBLE);
            }else {
                typeView.setText(mContext.getString(R.string.experience_ticket));
                ruleView.setVisibility(View.GONE);
            }

            if (position + 1 < getCount()){
                RedPacket nextPacket = packets.get(position + 1);
                if (nextPacket != null && state == 0 && nextPacket.getState() != 0){
                    lineView.setVisibility(View.VISIBLE);
                }else {
                    lineView.setVisibility(View.GONE);
                }
            }
        }
        return convertView;
    }
}
