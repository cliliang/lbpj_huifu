package com.baluobo.user.stores;

import com.baluobo.app.flux.Action;
import com.baluobo.app.flux.Store;
import com.baluobo.user.actions.SecurityAction;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/7/14.
 */
public class GestureStore extends Store {
    private static GestureStore instance;
    private GestureStore(){}
    public static GestureStore getInstance(){
        if (instance == null){
            instance =new GestureStore();
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
