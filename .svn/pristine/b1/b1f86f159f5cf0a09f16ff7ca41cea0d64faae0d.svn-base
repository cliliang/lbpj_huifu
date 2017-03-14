package com.baluobo.product.actions;

import com.baluobo.app.flux.Action;
import com.baluobo.user.model.RedPacket;

import java.util.ArrayList;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/8/29.
 */
public class UsableCouponsAction extends Action<ArrayList<RedPacket>> {
    public static final String ACTION_REQUEST_START = "action_request_usable_red_packet_start";
    public static final String ACTION_REQUEST_FINISH = "action_request_usable_red_packet_finish";
    public static final String ACTION_REQUEST_FAIL = "action_request_usable_red_packet_fail";
    public static final String ACTION_REQUEST_INVALID_TOKEN = "action_request_usable_red_packet_invalid_token";
    public static final String ACTION_REQUEST_ERROR_MESSAGE = "action_request_usable_red_packet_error_message";
    public static final String ACTION_RQEUST_USABLE_RED_PACKET_SUCCESS = "action_request_usable_red_packet_success";

    private ArrayList<RedPacket> redPackets;
    public UsableCouponsAction(String type, ArrayList<RedPacket> data) {
        super(type, data);
        this.redPackets = data;
    }

    public UsableCouponsAction(String type) {
        super(type);
    }
}
