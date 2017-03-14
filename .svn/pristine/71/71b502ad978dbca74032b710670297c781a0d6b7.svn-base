package com.baluobo.user.actions;

import com.baluobo.app.flux.Action;
import com.baluobo.app.model.BaseModel;
import com.baluobo.user.model.User;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/30.
 */
public class UserInfoAction extends Action<BaseModel> {
    private BaseModel baseModel;
    public static final String ACTION_REQUEST_START = "update_user_info_action_request_start";
    public static final String ACTION_REQUEST_FINISH = "update_user_info_action_request_finish";
    public static final String ACTION_REQUEST_FAIL = "update_user_info_action_request_fail";
    public static final String ACTION_REQUEST_ERROR_MESSAGE = "update_user_info_action_request_error_message";
    public static final String ACTION_REQUEST_INVALID_TOKEN = "update_user_info_action_request_invalid_token";
    public static final String ACTION_REQUEST_UPDATE_USER_INFO_SUCCESS = "update_user_info_action_request_success";
    public UserInfoAction(String type, BaseModel data) {
        super(type, data);
        this.baseModel = data;
    }

    public UserInfoAction(String type) {
        super(type);
    }
}
