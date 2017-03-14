package com.baluobo.user.stores;

import com.baluobo.app.flux.Action;
import com.baluobo.app.flux.Store;
import com.baluobo.app.model.BaseModel;
import com.baluobo.home.actions.DotAction;
import com.baluobo.user.actions.MineAction;
import com.baluobo.user.model.User;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/6/1.
 */
public class MineStore extends Store {
    private BaseModel baseModel;
    private static MineStore instance;
    private MineStore (){}
    public static MineStore getInstance(){
        if (instance == null){
            instance = new MineStore();
        }
        return instance;
    }
    public BaseModel getUserData(){
        return baseModel;
    }
    @Override
    public void onAction(Action action) {
        String type = action.getType();
        switch (type){
            case MineAction.MINE_ACTION_REQUEST_UPDATE_SUCCESS:
                BaseModel model = (BaseModel) action.getData();
                if (model != null){
                    this.baseModel = model;
                    emitStoreChange(type);
                }
                break;
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
