package com.baluobo.user.stores;

import com.baluobo.app.flux.Action;
import com.baluobo.app.flux.Store;
import com.baluobo.user.actions.FastRegisterAction;
import com.baluobo.user.model.User;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/12/26.
 */
public class FastRegisterStore extends Store {
    private User userData;
    private static FastRegisterStore instance;
    private FastRegisterStore(){}
    public static FastRegisterStore getInstance(){
        if (instance == null){
            instance = new FastRegisterStore();
        }
        return instance;
    }
    public User getUser(){
        return userData;
    }
    @Override
    public void onAction(Action action) {
        String type = action.getType();
        switch (type){
            case FastRegisterAction.FAST_REGISTER_REQUEST_START:
            case FastRegisterAction.FAST_REGISTER_SEND_CODE_START:
            case FastRegisterAction.FAST_REGISTER_REQUEST_FINISH:
            case FastRegisterAction.FAST_REGISTER_SEND_CODE_FINISH:
            case FastRegisterAction.FAST_REGISTER_REQUEST_FAIL:
            case FastRegisterAction.FAST_REGISTER_SEND_CODE_FAIL:
            case FastRegisterAction.FAST_REGISTER_SEND_CODE_SUCCESS:
                emitStoreChange(type);
                break;
            case FastRegisterAction.FAST_REGISTER_REQUEST_ERROR_MESSAGE:
            case FastRegisterAction.FAST_REGISTER_SEND_CODE_ERROR_MESSAGE:
                String msg = (String) action.getData();
                emitStoreChange(type, msg);
                break;
            case FastRegisterAction.FAST_REGISTER_REQUEST_SUCCESS:
                User data = (User) action.getData();
                if (data != null){
                    this.userData = data;
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
