package com.baluobo.user.actions;

import com.baluobo.app.flux.Action;
import com.baluobo.app.model.BaseModel;
import com.baluobo.user.model.User;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/10.
 */
public class LoginAction extends Action<BaseModel> {
    private BaseModel baseModel;
    public static final String ACTION_LOGIN_TYPE = "action_login_type";
    public static final String ACTION_LOGIN_PHONE = "action_login_phone";
    public static final String ACTION_LOGIN_TYPE_GESTURE = "action_login_type_gesture";

    public static final String ACTION_REQUEST_START = "login_action_request_start";
    public static final String ACTION_REQUEST_FINISH = "login_action_request_finish";
    public static final String ACTION_REQUEST_FAIL = "login_action_request_fail";
    public static final String ACTION_REQUEST_ERROR_MESSAGE = "login_action_request_error_message";
    public static final String ACTION_LOGIN_SUCCESS = "action_login_success";

    public static final String LOGIN_SEND_CODE_ACTION_START = "login_send_code_action_start";
    public static final String LOGIN_SEND_CODE_ACTION_FINISH = "login_send_code_action_finish";
    public static final String LOGIN_SEND_CODE_ACTION_FAIL = "login_send_code_action_fail";
    public static final String LOGIN_SEND_CODE_ACTION_ERROR_MESSAGE = "login_send_code_action_error_message";
    public static final String LOGIN_SEND_CODE_ACTION_SUCCESS = "login_send_code_action_success";
    public LoginAction(String type, BaseModel data){
        super(type, data);
        this.baseModel = data;
    }

    public LoginAction(String type){
        super(type, null);
    }
}
