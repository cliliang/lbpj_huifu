package com.baluobo.user.actions;

import com.baluobo.app.flux.Action;
import com.baluobo.user.model.Message;

import java.util.List;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/15.
 */
public class MsgAction extends Action<List<Message>> {
    private List<Message> data;
    public static final String ACTION_REQUEST_FAIL = "msg_action_request_fail";
    public static final String ACTION_REQUEST_INVALID_TOKEN = "msg_action_request_invalid_token";
    public static final String ACTION_REQUEST_ERROR_MESSAGE = "msg_request_error_message";
    public static final String ACTION_MSG_CENTER_REFRESH_SUCCESS = "action_msg_center_refresh_success";
    public static final String ACTION_MSG_CENTER_LOAD_MORE_SUCCESS = "action_msg_center_load_more_success";
    public static final String ACTION_MSG_CENTER_SEEN_ALL_SUCCESS = "action_msg_center_seen_all";
    public MsgAction(String type, List<Message> data) {
        super(type, data);
        this.data = data;
    }

    public MsgAction(String type){
        super(type, null);
    }

    @Override
    public List<Message> getData() {
        return data;
    }
}
