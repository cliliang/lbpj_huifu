package com.baluobo.user.actions;

import com.baluobo.app.flux.Action;
import com.baluobo.user.model.User;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/13.
 */
public class RegisterAction extends Action<User> {
    private User user;

    public static final String ACTION_REQUEST_START = "register_action_request_start";
    public static final String ACTION_REQUEST_FINISH = "register_action_request_finish";
    public static final String ACTION_REQUEST_FAIL = "register_action_request_fail";
    public static final String ACTION_REQUEST_ERROR_MESSAGE = "register_action_request_error_message";
    public static final String ACTION_REGISTER_SUCCESS = "register_action_register_success";
    public static final String ACTION_REGISTER_SEND_CODE_START = "register_action_send_code_start";
    public RegisterAction(String type, User data) {
        super(type, data);
        this.user = data;
    }

    public RegisterAction(String type){
        super(type, null);
    }

    public User getUser() {
        return user;
    }
}
