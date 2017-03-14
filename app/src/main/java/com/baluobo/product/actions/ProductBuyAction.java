package com.baluobo.product.actions;

import com.baluobo.app.flux.Action;
import com.baluobo.user.model.Order;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/27.
 */
public class ProductBuyAction extends Action<Order> {
    private Order data;

    public static final String ACTION_REQUEST_START = "product_pay_action_request_start";
    public static final String ACTION_REQUEST_FINISH = "product_pay_action_request_finish";
    public static final String ACTION_REQUEST_FAIL = "product_pay_action_request_fail";
    public static final String ACTION_REQUEST_ERROR_MESSAGE = "product_pay_action_request_error_message";
    public static final String ACTION_REQUEST_INVALID_TOKEN = "product_pay_action_request_invalid_token";
    public static final String ACTION_REQUEST_ORDER_SUCCESS = "action_request_order_success";
    public ProductBuyAction(String type, Order data) {
        super(type, data);
        this.data = data;
    }

    public ProductBuyAction(String type){
        super(type, null);
    }
}
