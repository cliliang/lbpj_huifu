package com.baluobo.product.actions;

import com.baluobo.app.flux.Action;
import com.baluobo.user.model.Order;

import java.util.ArrayList;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/27.
 */
public class ProductRecordAction extends Action<ArrayList<Order>> {
    private ArrayList<Order> orders;
    public static final String ACTION_REQUEST_FAIL = "product_record_action_request_fail";
    public static final String ACTION_REQUEST_ERROR_MESSAGE = "product_record_action_request_error_message";
    public static final String ACTION_REQUEST_TOTAL_SIZE = "product_record_action_total_size";
    public static final String ACTION_REQUEST_REFRESH_ORDER_LIST_SUCCESS = "action_request_order_list_refresh_success";
    public static final String ACTION_REQUEST_LOAD_ORDER_LIST_SUCCESS = "action_request_order_list_success";
    public ProductRecordAction(String type, ArrayList<Order> data) {
        super(type, data);
        this.orders = data;
    }
    public ProductRecordAction(String type){
        super(type, null);
    }
}
