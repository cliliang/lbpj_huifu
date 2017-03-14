package com.baluobo.user.stores;

import com.baluobo.app.flux.Action;
import com.baluobo.app.flux.Store;
import com.baluobo.user.actions.RegisterAction;
import com.baluobo.user.model.User;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/13.
 */
public class RegisterStore extends Store {
    private static RegisterStore instance;
    private User user;

    private RegisterStore(){
        super();
    }

    public static RegisterStore getInstance(){
        if (instance == null){
            instance = new RegisterStore();
        }
        return instance;
    }

    public User getRegisterUser(){
        return user;
    }

    @Override
    public void onAction(Action action) {
        String type = action.getType();
        if (RegisterAction.ACTION_REQUEST_ERROR_MESSAGE.equals(type)){
            String msg = (String) action.getData();
            emitStoreChange(type, msg);
        }else {
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
