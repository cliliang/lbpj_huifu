package com.baluobo.common.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.baluobo.R;
import com.baluobo.find.model.RedTicket;

import java.util.List;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/8/26.
 */
public class VIPTicketView extends LinearLayout implements VIPBlockView.OnVIPBlockClickListener{
    private VIPBlockView block1View, block2View, block3View;
    private OnVIPTicketClickListener listener;
    public VIPTicketView(Context context) {
        super(context);
        init(context);
    }

    public VIPTicketView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public VIPTicketView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        LayoutInflater.from(context).inflate(R.layout.vip_ticket_view_layout, this);
        block1View = (VIPBlockView) findViewById(R.id.vip_ticket_block_view1);
        block2View = (VIPBlockView) findViewById(R.id.vip_ticket_block_view2);
        block3View = (VIPBlockView) findViewById(R.id.vip_ticket_block_view3);
        block1View.setOnVIPBlockClickListener(this);
        block2View.setOnVIPBlockClickListener(this);
        block3View.setOnVIPBlockClickListener(this);
    }

    public void setVIPTicketViewData(List<RedTicket> redTickets){
        if (redTickets == null || redTickets.size() == 0){
            return;
        }
        if (redTickets.size() == 1){
            block1View.setViewData(redTickets.get(0));
            block2View.setVisibility(INVISIBLE);
            block3View.setVisibility(INVISIBLE);
        }else if (redTickets.size() == 2){
            block1View.setViewData(redTickets.get(0));
            block2View.setViewData(redTickets.get(1));
            block3View.setVisibility(INVISIBLE);
        }else {
            block1View.setViewData(redTickets.get(0));
            block2View.setViewData(redTickets.get(1));
            block3View.setViewData(redTickets.get(2));
        }
    }

    @Override
    public void onVipBlockClickListener(int ticketId) {
        listener.onVipTicketClickListener(ticketId);
    }

    public void setOnVIPTicketClickListener(OnVIPTicketClickListener l){
        this.listener = l;
    }

    public interface OnVIPTicketClickListener{
        void onVipTicketClickListener(int id);
    }
}
