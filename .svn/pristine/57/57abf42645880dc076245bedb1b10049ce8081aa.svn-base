package com.baluobo.home.stores;

import com.baluobo.app.flux.Action;
import com.baluobo.app.flux.Store;
import com.baluobo.home.actions.RecommendAction;
import com.baluobo.product.bean.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/23.
 */
public class RecommendStore extends Store {
    private ArrayList<Product> products = new ArrayList<>();
    private static RecommendStore instance;
    private RecommendStore(){}
    public static RecommendStore getInstance(){
        if (instance == null){
            instance = new RecommendStore();
        }
        return instance;
    }

    public ArrayList<Product> getProducts(){
        return products;
    }
    @Override
    public void onAction(Action action) {
        String type = action.getType();
        if (RecommendAction.ACTION_REQUEST_RECOMMEND_DATA_SUCCESS.equals(type)){
            ArrayList<Product> list = (ArrayList<Product>) action.getData();
            if (list != null && list.size() > 0){
                this.products = list;
            }
            emitStoreChange(RecommendAction.ACTION_REQUEST_RECOMMEND_DATA_SUCCESS);
        }else {
            emitStoreChange(type);
        }
    }

    @Override
    public StoreChangeEvent changeEvent(String type) {
        return new StoreChangeEvent(type);
    }

    @Override
    public StoreChangeEvent changeEvent(String type, String msg) {
        return new StoreChangeEvent(type, msg);
    }
}
