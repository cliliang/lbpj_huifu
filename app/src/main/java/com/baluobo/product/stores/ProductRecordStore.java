package com.baluobo.product.stores;

import com.baluobo.app.flux.Action;
import com.baluobo.app.flux.Store;
import com.baluobo.product.actions.ProductRecordAction;
import com.baluobo.user.model.Order;

import java.util.ArrayList;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/27.
 */
public class ProductRecordStore extends Store {
    private ArrayList<Order> orders;
    private int totalSize;
    private static ProductRecordStore instance;
    private ProductRecordStore(){}
    public static ProductRecordStore getInstance(){
        if (instance == null){
            instance = new ProductRecordStore();
        }
        return instance;
    }
    public ArrayList<Order> getOrders(){
        return orders;
    }

    public int getTotalSize(){
        return totalSize;
    }

    public void clearMoreData(){
        orders.clear();
    }
    @Override
    public void onAction(Action action) {
        String type = action.getType();
        switch (type){
            case ProductRecordAction.ACTION_REQUEST_ERROR_MESSAGE:
                String msg = (String) action.getData();
                emitStoreChange(type, msg);
                break;
            case ProductRecordAction.ACTION_REQUEST_LOAD_ORDER_LIST_SUCCESS:
                ArrayList<Order> list = (ArrayList<Order>) action.getData();
                if (list != null){
                    this.orders = list;
                }
                emitStoreChange(type);
                break;
            case ProductRecordAction.ACTION_REQUEST_REFRESH_ORDER_LIST_SUCCESS:
                ArrayList<Order> refreshData = (ArrayList<Order>) action.getData();
                if (refreshData != null){
                    this.orders = refreshData;
                }
                emitStoreChange(type);
                break;
            case ProductRecordAction.ACTION_REQUEST_TOTAL_SIZE:
                totalSize = (int) action.getData();
                break;
            case ProductRecordAction.ACTION_REQUEST_FAIL:
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
