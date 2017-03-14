package com.baluobo.user.actions;

import com.baluobo.app.flux.Action;
import com.baluobo.user.model.User;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/12/26.
 */
public class FastRegisterAction extends Action<User> {
    private User model;
    public static final String FAST_REGISTER_SEND_CODE_START = "fast_register_send_code_start";
    public static final String FAST_REGISTER_SEND_CODE_FINISH = "fast_register_send_code_finish";
    public static final String FAST_REGISTER_SEND_CODE_FAIL = "fast_register_send_code_fail";
    public static final String FAST_REGISTER_SEND_CODE_SUCCESS = "fast_register_send_code_success";
    public static final String FAST_REGISTER_SEND_CODE_ERROR_MESSAGE = "fast_register_send_code_error_message";

    public static final String FAST_REGISTER_REQUEST_START = "fast_register_request_start";
    public static final String FAST_REGISTER_REQUEST_FINISH = "fast_register_request_finish";
    public static final String FAST_REGISTER_REQUEST_FAIL = "fast_register_request_fail";
    public static final String FAST_REGISTER_REQUEST_SUCCESS = "fast_register_request_success";
    public static final String FAST_REGISTER_REQUEST_ERROR_MESSAGE = "fast_register_request_error_message";
    public FastRegisterAction(String type, User data) {
        super(type, data);
        this.model = data;
    }

    public FastRegisterAction(String type) {
        super(type);
    }
}
