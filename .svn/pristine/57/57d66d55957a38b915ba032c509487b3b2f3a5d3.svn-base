package com.baluobo.product.actions;

import com.baluobo.app.flux.Action;
import com.baluobo.product.bean.ExpGood;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/8/23.
 */
public class ExpGoodAction extends Action<ExpGood> {
    private ExpGood expGood;

    public static final String ACTION_REQUEST_START = "exp_good_action_request_start";
    public static final String ACTION_REQUEST_FINISH = "exp_good_action_request_finish";
    public static final String ACTION_REQUEST_FAIL = "exp_good_action_request_fail";
    public static final String ACTION_REQUEST_ERROR_MESSAGE = "exp_good_action_request_error_message";
    public static final String ACTION_REQUEST_INVALID_TOKEN = "exp_good_action_request_invalid_token";
    public static final String ACTION_REQUEST_EXP_GOOD_SUCCESS = "exp_good_action_request_success";
    public static final String BUY_EXP_ACTION_REQUEST_SUCCESS = "buy_exp_good_action_request_success";


    public ExpGoodAction(String type, ExpGood data) {
        super(type, data);
        this.expGood = data;
    }

    public ExpGoodAction(String type) {
        super(type);
    }
}
