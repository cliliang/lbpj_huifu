package com.baluobo.user.stores;

import com.baluobo.app.flux.Action;
import com.baluobo.app.flux.Store;
import com.baluobo.user.actions.TradingAction;
import com.baluobo.user.model.Order;
import com.baluobo.user.model.TradingRecord;

import java.util.List;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/18.
 */
public class TradingStore extends Store {
    private static TradingStore instance;
    private List<TradingRecord> data;
    private int totalSize = 0;
    private TradingStore(){}

    public static TradingStore getInstance(){
        if (instance == null){
            instance = new TradingStore();
        }
        return instance;
    }
    public List<TradingRecord> getData(){
        return data;
    }

    public int getTotalSize(){
        return totalSize;
    }
    public void clearStoreData(){
        if (data != null){
            data.clear();
        }
    }
    @Override
    public void onAction(Action action) {
        String type = action.getType();
        switch (type){
            case TradingAction.ACTION_REQUEST_REFRESH_TRADING_RECORD_SUCCESS:
                List<TradingRecord> list = (List<TradingRecord>) action.getData();
                if (list != null){
                    this.data = list;
                    emitStoreChange(type);
                }
                break;
            case TradingAction.ACTION_REQUEST_LOAD_TRADING_RECORD_SUCCESS:
                List<TradingRecord> loadData = (List<TradingRecord>) action.getData();
                if (loadData != null){
                    this.data = loadData;
                    emitStoreChange(type);
                }
                break;
            case TradingAction.ACTION_REQUEST_TRADING_RECORD_DATA_SIZE:
                int size = (int) action.getData();
                this.totalSize = size;
                break;
            case TradingAction.ACTION_REQUEST_ERROR_MESSAGE:
                String msg = (String) action.getData();
                emitStoreChange(type, msg);
                break;
            case TradingAction.ACTION_REQUEST_FAIL:
            case TradingAction.ACTION_REQUEST_INVALID_TOKEN:
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
