package com.baluobo.find.actions;

import com.baluobo.app.flux.Action;
import com.baluobo.app.model.BaseModel;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/9/20.
 */
public class SignInAction extends Action<BaseModel> {
    private BaseModel data;
    public static final String ACTION_REQUEST_START = "sign_in_action_request_start";
    public static final String ACTION_REQUEST_FINISH = "sign_in_action_request_finish";
    public static final String ACTION_REQUEST_FAIL = "sign_in_action_request_fail";
    public static final String ACTION_REQUEST_ERROR_MESSAGE = "sign_in_action_request_error_message";
    public static final String ACTION_REQUEST_INVALID_TOKEN = "sign_in_action_request_invalid_token";
    public static final String ACTION_REQUEST_SIGN_IN_SUCCESS = "sign_in_action_request_success";
    public SignInAction(String type, BaseModel data) {
        super(type, data);
        this.data = data;
    }

    public SignInAction(String type) {
        super(type);
    }
}
