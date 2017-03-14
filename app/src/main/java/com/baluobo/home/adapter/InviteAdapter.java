package com.baluobo.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baluobo.R;
import com.baluobo.home.model.InviteMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/8/10.
 */
public class InviteAdapter extends RecyclerView.Adapter<InviteAdapter.InviteViewHolder>{
    private Context mContext;
    private List<InviteMode> modes;
    public InviteAdapter(Context cnt){
        this.mContext = cnt;
        modes = new ArrayList<>();
    }

    public void setInviteData(List<InviteMode> modeList){
        if (modeList != null){
            this.modes = modeList;
        }
    }
    @Override
    public InviteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.invite_list_item_layout, null);
        return new InviteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InviteViewHolder holder, int position) {
        InviteMode mode = modes.get(position);
        if (mode == null){
            return;
        }
//        holder.moneyView.setText(String.format(Locale.US, mContext.getString(R.string.user_invite_money), mode.getCouponMoney()));
        holder.moneyView.setText(mode.getInviteDesc());
        holder.stateView.setText(String.format(Locale.US, mContext.getString(R.string.invite_user_date), mode.getCreateTime()));
        holder.accountView.setText(String.format(Locale.US, mContext.getString(R.string.invite_use_account), mode.getInvitedMobile()));
    }

    @Override
    public int getItemCount() {
        return modes.size();
    }

    class InviteViewHolder extends RecyclerView.ViewHolder{
        private TextView stateView, moneyView, accountView;
        public InviteViewHolder(View itemView) {
            super(itemView);
            stateView = (TextView) itemView.findViewById(R.id.invite_state_view);
            moneyView = (TextView) itemView.findViewById(R.id.invite_get_money_view);
            accountView = (TextView) itemView.findViewById(R.id.invite_state_account);
        }
    }
}
