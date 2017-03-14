package com.baluobo.user.actions;

import com.baluobo.app.flux.Action;
import com.baluobo.app.model.BaseModel;
import com.baluobo.user.model.User;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/30.
 */
public class TotalAssetsAction extends Action<BaseModel> {
    private BaseModel baseModel;
    public static final String ACTION_REQUEST_START = "total_assets_action_request_start";
    public static final String ACTION_REQUEST_FINISH = "total_assets_action_request_finish";
    public static final String ACTION_REQUEST_FAIL = "total_assets_action_request_fail";
    public static final String ACTION_REQUEST_ERROR_MESSAGE = "total_assets_action_request_error_message";
    public static final String ACTION_REQUEST_INVALID_TOKEN = "total_assets_action_request_invalid_token";
    public static final String ACTION_REQUEST_TOTAL_ASSETS_SUCCESS = "total_assets_action_request_success";
    public TotalAssetsAction(String type, BaseModel data) {
        super(type, data);
        this.baseModel = data;
    }

    public TotalAssetsAction(String type) {
        super(type);
    }
}
