package com.baluobo.user.actions;

import com.baluobo.app.flux.Action;
import com.baluobo.app.model.BaseModel;
import com.baluobo.user.model.KuaiZhuan;

import java.util.List;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/24.
 */
public class KuaiZhuanAction extends Action<BaseModel> {
    private BaseModel data;
    public static final String ACTION_REQUEST_START = "kuai_zhuan_action_request_start";
    public static final String ACTION_REQUEST_FINISH = "kuai_zhuan_action_request_finish";
    public static final String ACTION_REQUEST_FAIL = "kuai_zhuan_action_request_fail";
    public static final String ACTION_REQUEST_ERROR_MESSAGE = "kuai_zhuan_action_request_message";
    public static final String ACTION_REQUEST_INVALID_TOKEN = "kuai_zhuan_action_request_invalid_token";
    public static final String ACTION_REQUEST_KUAI_ZHUAN_SUCCESS = "action_request_kuai_zhuan_success";
    public KuaiZhuanAction(String type, BaseModel data) {
        super(type, data);
        this.data = data;
    }

    public KuaiZhuanAction(String type){
        super(type, null);
    }
}
