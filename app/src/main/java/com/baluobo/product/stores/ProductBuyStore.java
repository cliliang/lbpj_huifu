package com.baluobo.product.stores;

import com.baluobo.app.flux.Action;
import com.baluobo.app.flux.Store;
import com.baluobo.product.actions.ProductBuyAction;
import com.baluobo.user.model.Order;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/27.
 */
public class ProductBuyStore extends Store {
    private static ProductBuyStore instance;
    private Order order;
    private ProductBuyStore(){}
    public static ProductBuyStore getInstance(){
        if (instance == null){
            instance = new ProductBuyStore();
        }
        return instance;
    }
    public Order getOrder(){
        return order;
    }
    @Override
    public void onAction(Action action) {
        String type = action.getType();
        switch (type){
            case ProductBuyAction.ACTION_REQUEST_ERROR_MESSAGE:
                String msg = (String) action.getData();
                emitStoreChange(type, msg);
                break;
            case ProductBuyAction.ACTION_REQUEST_ORDER_SUCCESS:
                Order order = (Order) action.getData();
                if (order != null){
                   this.order = order;
                    emitStoreChange(type);
                }
                break;
            case ProductBuyAction.ACTION_REQUEST_START:
            case ProductBuyAction.ACTION_REQUEST_FINISH:
            case ProductBuyAction.ACTION_REQUEST_FAIL:
            case ProductBuyAction.ACTION_REQUEST_INVALID_TOKEN:
                emitStoreChange(type);
                break;
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
