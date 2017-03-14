package com.baluobo.find.actions;

import com.baluobo.app.flux.Action;
import com.baluobo.find.model.SignRecord;

import java.util.ArrayList;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/9/21.
 */
public class SignRecordAction extends Action<ArrayList<SignRecord>> {
    private ArrayList<SignRecord> signRecords;
    public static final String ACTION_REQUEST_START = "action_request_sign_record_start";
    public static final String ACTION_REQUEST_FINISH = "action_request_sign_record_finish";
    public static final String ACTION_REQUEST_FAIL = "action_request_sign_record_fail";
    public static final String ACTION_REQUEST_INVALID_TOKEN = "action_request_sign_record_invalid_token";
    public static final String ACTION_REQUEST_ERROR_MESSAGE = "action_request_sign_record_error_message";
    public static final String ACTION_REQUEST_SIGN_RECORD_SUCCESS = "action_request_sign_record_success";
    public SignRecordAction(String type, ArrayList<SignRecord> data) {
        super(type, data);
        this.signRecords = data;
    }

    public SignRecordAction(String type) {
        super(type);
    }
}
