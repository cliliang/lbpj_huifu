package com.baluobo.user.stores;

import com.baluobo.app.flux.Action;
import com.baluobo.app.flux.Store;
import com.baluobo.user.actions.ChangeMobileTowAction;
import com.baluobo.user.actions.RegisterAction;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/17.
 */
public class ChangeMobileTwoStore extends Store {
    private static ChangeMobileTwoStore instance;
    private ChangeMobileTwoStore(){}
    public static ChangeMobileTwoStore getInstance(){
        if (instance == null){
            instance = new ChangeMobileTwoStore();
        }
        return instance;
    }
    @Override
    public void onAction(Action action) {
        String type = action.getType();
        switch (type){
            case RegisterAction.ACTION_REQUEST_ERROR_MESSAGE:
            case ChangeMobileTowAction.ACTION_REQUEST_ERROR_MESSAGE:
                String msg = (String) action.getData();
                emitStoreChange(type, msg);
                break;
            case ChangeMobileTowAction.ACTION_REQUEST_SEND_CODE_SUCCESS:
            case ChangeMobileTowAction.ACTION_REQUEST_START:
            case ChangeMobileTowAction.ACTION_REQUEST_FINISH:
            case ChangeMobileTowAction.ACTION_MOBILE_STEP_TWO_SUCCESS:
            case ChangeMobileTowAction.ACTION_REQUEST_INVALID_TOKEN:
            case ChangeMobileTowAction.ACTION_REQUEST_FAIL:

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
