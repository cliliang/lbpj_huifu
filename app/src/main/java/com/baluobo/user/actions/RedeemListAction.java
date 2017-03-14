package com.baluobo.user.actions;

import com.baluobo.app.flux.Action;
import com.baluobo.user.model.Order;

import java.util.ArrayList;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/25.
 */
public class RedeemListAction extends Action<ArrayList<Order>> {
    private ArrayList<Order> list;
    public static final String ACTION_REQUEST_FAIL = "redeem_list_action_request_fail";
    public static final String ACTION_REQUEST_ERROR_MESSAGE = "redeem_list_action_request_error_message";
    public static final String ACTION_REQUEST_INVALID_TOKEN = "redeem_list_action_request_token";
    public static final String ACTION_REQUEST_REFRESH_DATA_SUCCESS = "redeem_list_action_request_refresh_data_success";
    public static final String ACTION_REQUEUST_LOAD_DATA_SUCCESS = "action_request_load_data_success";
    public static final String ACTION_LOAD_DATA_TOTAL_SIZE = "redeem_list_action_request_load_data_total_size";
    public static final String BOUND_REDEEM_DATA = "bound_redeem_data";
    public RedeemListAction(String type, ArrayList<Order> data) {
        super(type, data);
        this.list = data;
    }
    public RedeemListAction(String type){
        super(type, null);
    }
}
