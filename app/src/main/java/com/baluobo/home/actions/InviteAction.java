package com.baluobo.home.actions;

import com.baluobo.app.flux.Action;
import com.baluobo.home.model.InviteMode;

import java.util.List;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/8/22.
 */
public class InviteAction extends Action<List<InviteMode>> {
    private List<InviteMode> inviteModes;

    public static final String ACTION_REQUEST_START = "invite_data_action_request_start";
    public static final String ACTION_REQUEST_FAIL = "invite_data_action_request_fail";
    public static final String ACTION_REQUEST_FINISH = "invite_data_action_request_finish";
    public static final String ACTION_REQUEST_INVALID_TOKEN = "invite_data_action_request_invalid_token";
    public static final String ACTION_REQUEST_ERROR_MESSAGE = "invite_data_action_request_error_message";
    public static final String ACTION_REQUEST_INVAITE_DATA = "invite_data_action_request_invalid_data";

    public InviteAction(String type, List<InviteMode> data) {
        super(type, data);
        this.inviteModes = data;
    }

    public InviteAction(String type) {
        super(type);
    }
}
