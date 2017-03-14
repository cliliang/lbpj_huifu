package com.baluobo.user.stores;

import com.baluobo.app.flux.Action;
import com.baluobo.app.flux.Store;
import com.baluobo.user.actions.SecurityAction;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/7/19.
 */
public class GestureEditStore extends Store {
    private static GestureEditStore instance;
    private GestureEditStore(){};
    public static GestureEditStore getInstance(){
        if (instance == null){
            instance = new GestureEditStore();
        }
        return instance;
    }
    @Override
    public void onAction(Action action) {
        String type = action.getType();
        switch (type){
            case SecurityAction.ACTION_RESULT_ERROR_MESSAGE:
                String msg = (String) action.getData();
                emitStoreChange(type, msg);
                break;
            case SecurityAction.ACTION_RESULT_START:
            case SecurityAction.ACTION_RESULT_FINISH:
            case SecurityAction.ACTION_RESULT_SUCCESS:
            case SecurityAction.ACTION_RESULT_FAIL:
            case SecurityAction.ACTION_RESULT_VALID_TOKEN:
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
