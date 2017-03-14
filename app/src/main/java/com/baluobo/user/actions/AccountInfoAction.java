package com.baluobo.user.actions;

import com.baluobo.app.flux.Action;
import com.baluobo.app.model.BaseModel;
import com.baluobo.user.model.User;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/6/22.
 */
public class AccountInfoAction extends Action<BaseModel> {
    public static final String ACTION_REQUEST_START = "account_info_action_request_start";
    public static final String ACTION_REQUEST_FINISH = "account_info_action_request_finish";
    public static final String ACTION_REQUEST_FAIL = "account_info_action_request_fail";
    public static final String ACTION_REQUEST_INVALID_TOKEN = "account_info_action_request_token";
    public static final String ACTION_REQUEST_ERROR_MESSAGE = "account_info_action_request_error_message";
    public static final String ACTION_REQUEST_ACCOUNT_INFO_REFRUSH_DATA_SUCCESS = "account_info_action_request_refresh_success";
    private BaseModel baseModel;
    public AccountInfoAction(String type, BaseModel data) {
        super(type, data);
        this.baseModel = data;
    }

    public AccountInfoAction(String type) {
        super(type);
    }
}
