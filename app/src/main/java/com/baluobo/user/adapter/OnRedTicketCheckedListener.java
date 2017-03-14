package com.baluobo.user.adapter;

import com.baluobo.user.model.RedPacket;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/9/3.
 */
public interface OnRedTicketCheckedListener {
    void onRedTicketCheckedListener(boolean isChecked, RedPacket redPacket);
}
