package com.baluobo.common.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baluobo.R;
import com.baluobo.find.model.RedTicket;

import java.util.Locale;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/8/26.
 */
public class VIPBlockView extends LinearLayout {
    private ImageView enableRedTicket, takableRedTicket;
    private TextView moneyView, typeview, takeButton;
    private Context mContext;
    private OnVIPBlockClickListener listener;
    public VIPBlockView(Context context) {
        super(context);
        init(context);
    }

    public VIPBlockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public VIPBlockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        this.mContext = context;
        LayoutInflater.from(context).inflate(R.layout.vip_block_view_layout, this);
        enableRedTicket = (ImageView) findViewById(R.id.vip_ticket_is_usable_image);
        takableRedTicket = (ImageView) findViewById(R.id.vip_red_ticket_is_takable_image);
        moneyView = (TextView) findViewById(R.id.vip_red_ticket_money_id);
        typeview = (TextView) findViewById(R.id.vip_red_ticket_type_id);
        takeButton = (TextView) findViewById(R.id.vip_take_red_ticket_button);
    }

    public void setViewData(final RedTicket redTicket){
        if (redTicket == null){
            return;
        }
        moneyView.setText(String.format(Locale.CHINESE, mContext.getResources().getString(R.string.red_ticket_money), redTicket.getVmoney()));
        int vType = redTicket.getVtype();
        if (vType == 0){
            typeview.setText(mContext.getString(R.string.cash_red_packet));
        }else if (vType == 1){
            typeview.setText(mContext.getString(R.string.principal_red_packet));
        }else {
            typeview.setText(mContext.getString(R.string.experience_ticket));
        }
        int gType = redTicket.getGtype();
        if (gType == 0){
            //0未领取
            enableRedTicket.setImageResource(R.drawable.red_ticket_usable);
            takableRedTicket.setVisibility(GONE);
            takeButton.setEnabled(true);
            takeButton.setText(mContext.getString(R.string.press_take_red_ticket));
        }else if (gType == 1){
            //1已领取
            enableRedTicket.setImageResource(R.drawable.red_ticket_disable);
            takableRedTicket.setVisibility(VISIBLE);
            takeButton.setEnabled(false);
            takeButton.setText(mContext.getString(R.string.red_ticket_taked));
        }else {
            //2等级不够
            enableRedTicket.setImageResource(R.drawable.red_ticket_disable);
            takableRedTicket.setVisibility(GONE);
            takeButton.setEnabled(false);
            int vlevel = redTicket.getVlevel();
            if (vlevel == 0){
                takeButton.setText(mContext.getString(R.string.red_packet_take_level0));
            }else if (vlevel == 1){
                takeButton.setText(mContext.getString(R.string.red_packet_take_level1));
            }else if (vlevel == 2){
                takeButton.setText(mContext.getString(R.string.red_packet_take_level2));
            }else if (vlevel == 3){
                takeButton.setText(mContext.getString(R.string.red_packet_take_level3));
            }else if (vlevel == 4){
                takeButton.setText(mContext.getString(R.string.red_packet_take_level4));
            }else if (vlevel == 5){
                takeButton.setText(mContext.getString(R.string.red_packet_take_level5));
            }
        }
        takeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    takeButton.setEnabled(false);
                    listener.onVipBlockClickListener(redTicket.getVid());
                }
            }
        });
    }

    public void setOnVIPBlockClickListener(OnVIPBlockClickListener l){
        this.listener = l;
    }

    public interface OnVIPBlockClickListener{
        void onVipBlockClickListener(int ticketId);
    }
}
