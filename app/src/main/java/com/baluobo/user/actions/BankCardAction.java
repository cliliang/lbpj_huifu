package com.baluobo.user.actions;

import com.baluobo.app.flux.Action;
import com.baluobo.app.model.BaseModel;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/31.
 */
public class BankCardAction extends Action<BaseModel> {
    private BaseModel data;
    public static final String ACTION_REQUEST_START = "bank_card_action_request_start";
    public static final String ACTION_REQUEST_FINISH = "bank_card_action_request_finish";
    public static final String ACTION_REQUEST_FAIL = "bank_card_action_request_fail";
    public static final String ACTION_REQUEST_ERROR_MESSAGE = "bank_card_action_request_error_message";
    public static final String ACTION_REQUEST_INVALID_TOKEN = "bank_card_action_request_invalid_token";
    public static final String ACTION_REQUEST_BANK_CARD_SUCCESS = "bank_card_action_request_success";
    public BankCardAction(String type, BaseModel data) {
        super(type, data);
        this.data = data;
    }

    public BankCardAction(String type) {
        super(type);
    }
}
