package com.baluobo.find.stores;

import com.baluobo.app.flux.Action;
import com.baluobo.app.flux.Store;
import com.baluobo.app.model.BaseModel;
import com.baluobo.find.actions.SignInAction;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/9/20.
 */
public class SignInStore extends Store {
    private BaseModel data;
    private static SignInStore instance;
    private SignInStore(){}
    public static SignInStore getInstance(){
        if (instance == null){
            instance = new SignInStore();
        }
        return instance;
    }
    public BaseModel getData(){
        return data;
    }
    @Override
    public void onAction(Action action) {
        String type = action.getType();
        switch (type){
            case SignInAction.ACTION_REQUEST_START:
            case SignInAction.ACTION_REQUEST_FINISH:
            case SignInAction.ACTION_REQUEST_ERROR_MESSAGE:
            case SignInAction.ACTION_REQUEST_INVALID_TOKEN:
            case SignInAction.ACTION_REQUEST_FAIL:
                emitStoreChange(type);
                break;
            case SignInAction.ACTION_REQUEST_SIGN_IN_SUCCESS:
                BaseModel baseModel = (BaseModel) action.getData();
                if (baseModel != null){
                    this.data = baseModel;
                    emitStoreChange(type);
                }
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
