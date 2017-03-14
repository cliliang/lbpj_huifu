package com.baluobo.product.actions;

import com.baluobo.app.flux.Action;
import com.baluobo.product.bean.Product;

import java.util.ArrayList;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/23.
 */
public class ProductListAction extends Action<ArrayList<Product>> {
    private ArrayList<Product> list;
    public static final String PRODUCT_TYPE = "product_type";
    public static final String ACTION_REQUEST_FAIL = "product_list_action_request_fail";
    public static final String ACTION_REQUEST_ERROR_MESSAGE = "product_list_action_request_error_message";
    public static final String PRODUCT_LIST_REQUEST_REFRESH_SUCCESS = "product_list_request_refresh_success";
    public static final String PRODUCT_LIST_REQUEST_LOAD_SUCCESS = "product_list_request_load_success";
    public static final String PRODUCT_LIST_DATA_TOTAL_SIZE = "product_list_data_total_size";
    public ProductListAction(String type, ArrayList<Product> data) {
        super(type, data);
        this.list = data;
    }
    public ProductListAction(String type){
        super(type, null);
    }
}
