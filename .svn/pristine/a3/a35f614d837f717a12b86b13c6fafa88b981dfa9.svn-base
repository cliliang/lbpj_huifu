package com.baluobo.user.stores;

import com.baluobo.app.flux.Action;
import com.baluobo.app.flux.Store;
import com.baluobo.user.actions.InvestAction;
import com.baluobo.user.model.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/21.
 */
public class InvestStore extends Store {
    private ArrayList<Order> data = new ArrayList<>();
    private int dataSize;
//    private static InvestStore instance;
    public InvestStore(){}
//    public static InvestStore getInstance(){
//        if (instance == null){
//            instance = new InvestStore();
//        }
//        return instance;
//    }
    public ArrayList<Order> getData(){
        return data;
    }
    public int getDataSize(){
        return dataSize;
    }
    @Override
    public void onAction(Action action) {
//        String type = action.getType();
//        switch (type){
//            case Action.ACTION_REQUEST_MESSAGE:
//                String msg = (String) action.getData();
//                emitStoreChange(Action.ACTION_REQUEST_MESSAGE, msg);
//                break;
//            case InvestAction.ACTION_REQUEST_REFRESH_INVEST_DATA_SUCCESS:
//                ArrayList<Order> refreshData = (ArrayList<Order>) action.getData();
//                if (refreshData != null){
//                    this.data = refreshData;
//                }
//                emitStoreChange(InvestAction.ACTION_REQUEST_REFRESH_INVEST_DATA_SUCCESS);
//                break;
//            case InvestAction.ACTION_REQUEST_LOAD_INVEST_DATA_SUCCESS:
//                ArrayList<Order> loadData = (ArrayList<Order>) action.getData();
//                if (loadData != null){
//                    this.data = loadData;
//                }
//                emitStoreChange(InvestAction.ACTION_REQUEST_LOAD_INVEST_DATA_SUCCESS);
//                break;
//            case Action.ACTION_DATA_SIZE:
//                dataSize = data.size();
//                emitStoreChange(Action.ACTION_DATA_SIZE);
//                break;
//            default:
//                emitStoreChange(type);
//                break;
//        }
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
