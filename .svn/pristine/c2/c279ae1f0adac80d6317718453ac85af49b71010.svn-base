package com.baluobo.home.stores;

import com.baluobo.app.flux.Action;
import com.baluobo.app.flux.Store;
import com.baluobo.home.actions.DotAction;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/7/26.
 */
public class DotStore extends Store {
    private static DotStore instance;
    private DotStore(){}
    public static DotStore getInstance(){
        if (instance == null){
            instance = new DotStore();
        }
        return instance;
    }
    @Override
    public void onAction(Action action) {
        String type = action.getType();
        switch (type){
            case DotAction.MAIN_DOT_ACTION_DONT_HAVE_UNREAD_MESSAGE:
            case DotAction.MAIN_DOT_ACTION_HAVE_UNREAD_MESSAGE:
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
