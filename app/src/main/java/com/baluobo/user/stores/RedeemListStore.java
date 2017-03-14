package com.baluobo.user.stores;

import com.baluobo.app.flux.Action;
import com.baluobo.app.flux.Store;
import com.baluobo.user.actions.RedeemListAction;
import com.baluobo.user.model.Order;

import java.util.ArrayList;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/25.
 */
public class RedeemListStore extends Store {
    private ArrayList<Order> products;
    private int totalSize;
    private static RedeemListStore instance;
    private RedeemListStore(){}
    public static RedeemListStore getInstance(){
        if (instance == null){
            instance = new RedeemListStore();
        }
        return instance;
    }

    public ArrayList<Order> getProducts(){
        return products;
    }

    public int getTotalSize(){
        return totalSize;
    }

    public void clearMoreData(){
        if (products != null){
            products.clear();
        }
    }
    @Override
    public void onAction(Action action) {
        String type = action.getType();
        switch (type){
            case RedeemListAction.ACTION_REQUEST_ERROR_MESSAGE:
                String msg = (String) action.getData();
                emitStoreChange(type, msg);
                break;
            case RedeemListAction.ACTION_REQUEST_REFRESH_DATA_SUCCESS:
                this.products = (ArrayList<Order>) action.getData();
                emitStoreChange(type);
                break;
            case RedeemListAction.ACTION_REQUEUST_LOAD_DATA_SUCCESS:
                this.products = (ArrayList<Order>) action.getData();
                emitStoreChange(type);
                break;
            case RedeemListAction.ACTION_LOAD_DATA_TOTAL_SIZE:
                this.totalSize = (int) action.getData();
                break;
            case RedeemListAction.ACTION_REQUEST_FAIL:
            case RedeemListAction.ACTION_REQUEST_INVALID_TOKEN:
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
