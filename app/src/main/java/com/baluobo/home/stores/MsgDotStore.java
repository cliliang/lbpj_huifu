package com.baluobo.home.stores;

import com.baluobo.app.flux.Action;
import com.baluobo.app.flux.Store;
import com.baluobo.app.model.BaseModel;
import com.baluobo.home.actions.DotAction;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/7/26.
 */
public class MsgDotStore extends Store {
    private BaseModel baseModel;
    private static MsgDotStore instance;
    private MsgDotStore(){}
    public static MsgDotStore getInstance(){
        if (instance == null){
            instance = new MsgDotStore();
        }
        return instance;
    }
    public BaseModel getMsg(){
        return baseModel;
    }
    @Override
    public void onAction(Action action) {
        String type = action.getType();
        switch (type){
            case DotAction.MAIN_DOT_ACTION_DONT_HAVE_UNREAD_MESSAGE:
                emitStoreChange(type);
                break;
            case DotAction.MAIN_DOT_ACTION_HAVE_UNREAD_MESSAGE:
                BaseModel model = (BaseModel) action.getData();
                if (model != null){
                    this.baseModel = model;
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
