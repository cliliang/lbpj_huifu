package com.baluobo.find.stores;

import com.baluobo.app.flux.Action;
import com.baluobo.app.flux.Store;
import com.baluobo.find.actions.FindAction;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/8/29.
 */
public class FindStore extends Store {
    private static FindStore instance;
    private FindStore(){}
    public static FindStore getInstance(){
        if (instance == null){
            instance = new FindStore();
        }
        return instance;
    }
    @Override
    public void onAction(Action action) {
        String type = action.getType();
        switch (type){
            case FindAction.ACTION_REQUEST_FIND_UPDATE_DATE:
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
