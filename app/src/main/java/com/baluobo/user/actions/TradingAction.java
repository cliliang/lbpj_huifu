package com.baluobo.user.actions;

import com.baluobo.app.flux.Action;
import com.baluobo.user.model.Order;
import com.baluobo.user.model.TradingRecord;

import java.util.List;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/18.
 */
public class TradingAction extends Action<List<TradingRecord>> {

    private List<TradingRecord> data;
    public static final String ACTION_REQUEST_FAIL = "trading_record_action_request_fail";
    public static final String ACTION_REQUEST_ERROR_MESSAGE = "trading_record_action_request_error_message";
    public static final String ACTION_REQUEST_INVALID_TOKEN = "trading_record_action_request_invalid_token";
    public static final String ACTION_REQUEST_REFRESH_TRADING_RECORD_SUCCESS = "trading_record_action_request_refresh_trading_record_success";
    public static final String ACTION_REQUEST_LOAD_TRADING_RECORD_SUCCESS = "trading_record_action_request_load_trading_record_success";
    public static final String ACTION_REQUEST_TRADING_RECORD_DATA_SIZE = "trading_record_action_request_trading_record_data_size";
    public TradingAction(String type, List<TradingRecord> data) {
        super(type, data);
        this.data = data;
    }

    public TradingAction(String type){
        super(type, null);
    }
}
