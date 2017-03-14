package com.baluobo.user.actions;

import com.baluobo.app.flux.Action;
import com.baluobo.user.model.Order;

import java.util.List;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/21.
 */
public class InvestAction extends Action<List<Order>> {

    private List<Order> data;
    public static final String ACTION_REQUEST_REFRESH_INVEST_DATA_SUCCESS = "action_request_refresh_invest_data_success";
    public static final String ACTION_REQUEST_LOAD_INVEST_DATA_SUCCESS = "action_request_load_invest_data_success";
    public static final String BUNDLE_ORDER_DATA = "bundle_order_data";
    public static final int ORDER_STATE_INVESTING = 3;
    public static final int ORDER_STATE_REPAY = 2;
    public static final int ORDER_STATE_COMPLETE = 1;
    public InvestAction(String type, List<Order> data) {
        super(type, data);
        this.data = data;
    }
    public InvestAction(String type){
        super(type, null);
    }
}
