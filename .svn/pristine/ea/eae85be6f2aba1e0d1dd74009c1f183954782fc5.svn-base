package com.baluobo.home.actions;

import com.baluobo.app.flux.Action;
import com.baluobo.product.bean.Product;

import java.util.List;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/23.
 */
public class RecommendAction extends Action<List<Product>> {
    private List<Product> list;
    public static final String ACTION_REQUEST_RECOMMEND_DATA_SUCCESS = "action_request_recommend_data_success";
    public RecommendAction(String type, List<Product> data) {
        super(type, data);
        this.list = data;
    }

    public RecommendAction(String type){
        super(type, null);
    }
}
