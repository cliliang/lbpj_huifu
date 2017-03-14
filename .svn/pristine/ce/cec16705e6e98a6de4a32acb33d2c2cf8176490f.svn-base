package com.baluobo.find.actions;

import com.baluobo.app.flux.Action;
import com.baluobo.app.model.BaseModel;
import com.baluobo.find.model.RedTicket;

import java.util.List;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/8/26.
 */
public class VIPCenterAction extends Action<BaseModel> {
    private BaseModel vipData;
    public static final String ACTION_REQUEST_START = "vip_red_ticket_action_request_start_start";
    public static final String ACTION_REQUEST_FINISH = "vip_red_ticket_action_request_start_finish";
    public static final String ACTION_REQUEST_FAIL = "vip_red_ticket_action_request_start_fail";
    public static final String ACTION_REQUEST_INVALID_TOKEN = "vip_red_ticket_action_request_start_invalid_token";
    public static final String ACTION_REQUEST_ERROR_MESSAGE = "vip_red_ticket_action_request_start_error_message";
    public static final String ACTION_REQUEST_RED_TICKET_SUCCESS = "vip_red_ticket_action_request_start_success";
    public static final String ACTION_REQUEST_TAKE_RED_TICKET_SUCCESS = "take_vip_red_ticket_success";
    public VIPCenterAction(String type, BaseModel data) {
        super(type, data);
        this.vipData = data;
    }

    public VIPCenterAction(String type) {
        super(type);
    }
}
