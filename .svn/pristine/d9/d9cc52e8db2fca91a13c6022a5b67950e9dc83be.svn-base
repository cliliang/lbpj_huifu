package com.baluobo.user.stores;

import com.baluobo.app.flux.Action;
import com.baluobo.app.flux.Store;
import com.baluobo.app.model.BaseModel;
import com.baluobo.user.actions.UserInfoAction;
import com.baluobo.user.model.User;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/30.
 */
public class UserInfoStore extends Store {
    private BaseModel baseModel;
    private static UserInfoStore instance;
    private UserInfoStore (){}
    public static UserInfoStore getInstance(){
        if (instance == null){
            instance = new UserInfoStore();
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
            case UserInfoAction.ACTION_REQUEST_ERROR_MESSAGE:
                String msg = (String) action.getData();
                emitStoreChange(type, msg);
                break;
            case UserInfoAction.ACTION_REQUEST_UPDATE_USER_INFO_SUCCESS:
                BaseModel model = (BaseModel) action.getData();
                if (model != null){
                    this.baseModel = model;
                    emitStoreChange(type);
                }
                break;
            default:
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
