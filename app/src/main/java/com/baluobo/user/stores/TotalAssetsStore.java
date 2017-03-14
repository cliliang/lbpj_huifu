package com.baluobo.user.stores;

import com.baluobo.app.flux.Action;
import com.baluobo.app.flux.Store;
import com.baluobo.app.model.BaseModel;
import com.baluobo.user.actions.TotalAssetsAction;
import com.baluobo.user.model.User;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/30.
 */
public class TotalAssetsStore extends Store {
    private static TotalAssetsStore instance;
    private BaseModel baseModel;
    private TotalAssetsStore (){}
    public static TotalAssetsStore getInstance(){
        if (instance == null){
            instance = new TotalAssetsStore();
        }
        return instance;
    }
    public BaseModel getUser(){
        return baseModel;
    }
    @Override
    public void onAction(Action action) {
        String type = action.getType();
        switch (type){
            case TotalAssetsAction.ACTION_REQUEST_ERROR_MESSAGE:
                String msg = (String) action.getData();
                emitStoreChange(type, msg);
                break;
            case TotalAssetsAction.ACTION_REQUEST_TOTAL_ASSETS_SUCCESS:
                BaseModel model = (BaseModel) action.getData();
                if (model != null){
                    this.baseModel = model;
                    emitStoreChange(type);
                }
                break;
            default:
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
