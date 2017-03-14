package com.baluobo.user.stores;

import com.baluobo.app.flux.Action;
import com.baluobo.app.flux.Store;
import com.baluobo.app.model.BaseModel;
import com.baluobo.user.actions.AccountInfoAction;
import com.baluobo.user.model.User;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/6/22.
 */
public class AccountInfoStore extends Store {
    private BaseModel baseModel;
    private static AccountInfoStore instance;
    private AccountInfoStore(){}
    public static AccountInfoStore getInstance(){
        if (instance == null){
            instance = new AccountInfoStore();
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
             case AccountInfoAction.ACTION_REQUEST_ERROR_MESSAGE:
                 String msg = (String) action.getData();
                 emitStoreChange(type, msg);
                 break;
             case AccountInfoAction.ACTION_REQUEST_ACCOUNT_INFO_REFRUSH_DATA_SUCCESS:
                 BaseModel model = (BaseModel) action.getData();
                 if (model != null){
                     this.baseModel = model;
                     emitStoreChange(type);
                 }
                 break;
             case AccountInfoAction.ACTION_REQUEST_START:
             case AccountInfoAction.ACTION_REQUEST_FINISH:
             case AccountInfoAction.ACTION_REQUEST_FAIL:
             case AccountInfoAction.ACTION_REQUEST_INVALID_TOKEN:
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
