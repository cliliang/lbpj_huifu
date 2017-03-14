package com.baluobo.user.stores;

import com.baluobo.app.flux.Action;
import com.baluobo.app.flux.Store;
import com.baluobo.user.actions.FeedbackAction;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/15.
 */
public class FeedbackStore extends Store {
    private static FeedbackStore instance;
    private FeedbackStore(){

    }

    public static FeedbackStore getInstance(){
        if (instance == null){
            instance = new FeedbackStore();
        }
        return instance;
    }

    @Override
    public void onAction(Action action) {
        String type = action.getType();
        if (FeedbackAction.ACTION_REQUEST_ERROR_MESSAGE.equals(type)){
            String msg = (String) action.getData();
            emitStoreChange(type, msg);
        }else {
            emitStoreChange(action.getType());
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
