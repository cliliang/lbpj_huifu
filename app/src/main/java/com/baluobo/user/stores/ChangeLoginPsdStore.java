package com.baluobo.user.stores;

import com.baluobo.app.flux.Action;
import com.baluobo.app.flux.Store;
import com.baluobo.user.actions.ChangeLoginPsdAction;
import com.baluobo.user.actions.RegisterAction;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/16.
 */
public class ChangeLoginPsdStore extends Store {
    private static ChangeLoginPsdStore instance;
    private ChangeLoginPsdStore(){
    }
    public static ChangeLoginPsdStore getInstance(){
        if (instance == null){
            instance = new ChangeLoginPsdStore();
        }
        return instance;
    }
    @Override
    public void onAction(Action action) {
        String type = action.getType();
        switch (type){
            case RegisterAction.ACTION_REQUEST_ERROR_MESSAGE:
            case ChangeLoginPsdAction.ACTION_REQUEST_ERROR_MESSAGE:
                String msg = (String) action.getData();
                emitStoreChange(type, msg);
                break;
            case RegisterAction.ACTION_REQUEST_START:
            case RegisterAction.ACTION_REQUEST_FINISH:
            case RegisterAction.ACTION_REGISTER_SEND_CODE_START:
            case RegisterAction.ACTION_REQUEST_FAIL:
            case ChangeLoginPsdAction.ACTION_REQUEST_START:
            case ChangeLoginPsdAction.ACTION_REQUEST_FINISH:
            case ChangeLoginPsdAction.ACTION_CHANGE_PSD_SUCCESS:
            case ChangeLoginPsdAction.ACTION_REQUEST_INVALID_TOKEN:
            case ChangeLoginPsdAction.ACTION_REQUEST_FAIL:
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
