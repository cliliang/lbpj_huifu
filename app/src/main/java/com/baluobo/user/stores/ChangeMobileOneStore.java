package com.baluobo.user.stores;

import com.baluobo.app.flux.Action;
import com.baluobo.app.flux.Store;
import com.baluobo.user.actions.ChangeMobileOneAction;
import com.baluobo.user.actions.RegisterAction;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/29.
 */
public class ChangeMobileOneStore extends Store {
    private static ChangeMobileOneStore instance;
    private ChangeMobileOneStore(){}
    public static ChangeMobileOneStore getInstance(){
        if (instance == null){
            instance = new ChangeMobileOneStore();
        }
        return instance;
    }
    @Override
    public void onAction(Action action) {
        String type = action.getType();
        switch (type){
            case RegisterAction.ACTION_REQUEST_ERROR_MESSAGE:
            case ChangeMobileOneAction.ACTION_REQUEST_ERROR_MESSAGE:
                String msg = (String) action.getData();
                emitStoreChange(type, msg);
                break;
            case RegisterAction.ACTION_REQUEST_FAIL:
            case RegisterAction.ACTION_REQUEST_START:
            case RegisterAction.ACTION_REQUEST_FINISH:
            case ChangeMobileOneAction.ACTION_REQUEST_START:
            case ChangeMobileOneAction.ACTION_REQUEST_FINISH:
            case ChangeMobileOneAction.ACTION_REQUEST_FAIL:
            case ChangeMobileOneAction.ACTION_REQUEST_INVALID_TOKEN:
            case RegisterAction.ACTION_REGISTER_SEND_CODE_START:
            case ChangeMobileOneAction.ACTION_MOBILE_STEP_ONE_SUCCESS:
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
