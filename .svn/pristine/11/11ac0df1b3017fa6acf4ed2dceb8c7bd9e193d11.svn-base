package com.baluobo.find.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.baluobo.R;
import com.baluobo.user.adapter.OnRedTicketCheckedListener;
import com.baluobo.user.model.RedPacket;

import java.util.Locale;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/8/27.
 */
public class ChoiceRedTicketView extends FrameLayout {
    private CheckBox checkBox;
    private RedPacket redPacket;
    private TextView moneyView, typeView;
    private Context mContext;
    private OnRedTicketCheckedListener listener;
    public ChoiceRedTicketView(Context context) {
        super(context);
        init(context);
    }

    public ChoiceRedTicketView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ChoiceRedTicketView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context cnt){
        this.mContext = cnt;
        LayoutInflater.from(cnt).inflate(R.layout.choice_red_ticket_view_layout, this);
        checkBox = (CheckBox) findViewById(R.id.choice_red_ticket_view);
        moneyView = (TextView) findViewById(R.id.choice_coupon_money);
        typeView = (TextView) findViewById(R.id.choice_coupon_type);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (listener != null && redPacket != null){
                    listener.onRedTicketCheckedListener(isChecked, redPacket);
                }
            }
        });
    }

    public void setViewData(RedPacket coupon){
        if (coupon == null){
            return;
        }
        this.redPacket = coupon;
        moneyView.setText(String.format(Locale.CHINESE, "%.0f", redPacket.getCouponMoney()));
        int couponType = redPacket.getCouponType();
        String type;
        if (couponType == 0){
            type = mContext.getResources().getString(R.string.cash_red_packet);
        }else if (couponType == 1){
            type = mContext.getResources().getString(R.string.principal_red_packet);
        }else {
            type = mContext.getResources().getString(R.string.experience_ticket);
        }
        typeView.setText(type);
    }

    public void setOnRedTicketCheckedListener(OnRedTicketCheckedListener l){
        this.listener = l;
    }

}
