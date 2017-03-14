package com.baluobo.product.actions;

import com.baluobo.app.flux.Action;
import com.baluobo.product.bean.ProductInfo;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/26.
 */
public class ProductInfoAction extends Action<ProductInfo> {
    private ProductInfo data;

    public static final String ACTION_REQUEST_START = "product_info_action_request_start";
    public static final String ACTION_REQUEST_FINISH = "product_info_action_request_finish";
    public static final String ACTION_REQUEST_FAIL = "product_info_action_request_fail";
    public static final String ACTION_REQUEST_ERROR_MESSAGE = "product_info_action_request_error_message";
    public static final String ACTION_REQUEST_PRODUCT_INFO_SUCCESS = "product_info_action_request_product_info_success";

    public static final String BOUND_PRODUCT_DATA_ID = "bound_product_data_id";
    public static final String BOUND_FRAGMENT_INFO_DATA = "bound_fragment_info_data";
    public static final String BOUND_FRAGMENT_SHEN_HE_DATA = "bound_fragment_shen_he_data";
    public static final String BOUND_FRAGMENT_RECORD_DATA = "bound_fragment_record_data";
    public static final String BOUND_FRAGMENT_SHEN_HE_GOOD_TYPE = "bound_fragment_shen_he_type";

    public ProductInfoAction(String type, ProductInfo data) {
        super(type, data);
        this.data = data;
    }

    public ProductInfoAction(String type){
        super(type);
    }
}
