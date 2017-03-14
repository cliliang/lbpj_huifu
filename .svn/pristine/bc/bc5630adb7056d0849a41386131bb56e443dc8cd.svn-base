package com.baluobo.product.actions;

import com.baluobo.app.flux.Action;
import com.baluobo.product.bean.Product;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/12/23.
 */
public class KuaiZhuanInfoAction extends Action<Product> {
    private Product product;
    public static final String ACTION_REQUEST_START = "kuai_zhuan_info_action_request_start";
    public static final String ACTION_REQUEST_FINISH = "kuai_zhuan_info_action_request_finish";
    public static final String ACTION_REQUEST_FAIL = "kuai_zhuan_info_action_request_fail";
    public static final String ACTION_REQUEST_KUAI_ZHUAN_INFO_SUCCESS = "kuai_zhuan_info_action_request_success";
    public static final String ACTION_REQUEST_ERROR_MESSAGE = "kuai_zhuan_info_action_request_error_message";
    public KuaiZhuanInfoAction(String type, Product data) {
        super(type, data);
        this.product = data;
    }

    public KuaiZhuanInfoAction(String type) {
        super(type);
    }
}
