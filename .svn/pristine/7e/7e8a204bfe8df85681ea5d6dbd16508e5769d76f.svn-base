package com.baluobo.user.actions;

import com.baluobo.app.flux.Action;
import com.baluobo.user.model.RedPacket;

import java.util.List;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/8/18.
 */
public class RedPacketAction extends Action<List<RedPacket>> {
    private List<RedPacket> lists;
    public static final String ACTION_REQUEST_START = "red_packet_list_action_request_start";
    public static final String ACTION_REQUEST_FINISH = "red_packet_list_action_request_finish";
    public static final String ACTION_REQUEST_FAIL = "red_packet_list_action_request_fail";
    public static final String ACTION_REQUEST_INVALID_TOKEN = "red_packet_list_action_request_invalid_token";
    public static final String ACTION_REQUEST_ERROR_MESSAGE = "red_packet_list_action_request_error_message";
    public static final String ACTION_REQUEST_LOAD_DATA_SUCCESS = "red_packet_list_action_request_load_data_success";
    public RedPacketAction(String type, List<RedPacket> data) {
        super(type, data);
        this.lists = data;
    }

    public RedPacketAction(String type) {
        super(type);
    }
}
