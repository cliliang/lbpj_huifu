package com.baluobo.user.stores;

import com.baluobo.app.flux.Action;
import com.baluobo.app.flux.Store;
import com.baluobo.user.actions.RedPacketAction;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/8/18.
 */
public class RedPacketStore extends Store {
    private static RedPacketStore instance;
    private RedPacketStore(){}
    public static RedPacketStore getInstance(){
        if (instance == null){
            instance = new RedPacketStore();
        }
        return instance;
    }

    @Override
    public void onAction(Action action) {
        String type = action.getType();
        switch (type){
            case RedPacketAction.ACTION_REQUEST_START:
            case RedPacketAction.ACTION_REQUEST_FINISH:
            case RedPacketAction.ACTION_REQUEST_FAIL:
            case RedPacketAction.ACTION_REQUEST_INVALID_TOKEN:
                break;
            case RedPacketAction.ACTION_REQUEST_ERROR_MESSAGE:
                String msg = (String) action.getData();
                emitStoreChange(type, msg);
                break;
            case RedPacketAction.ACTION_REQUEST_LOAD_DATA_SUCCESS:
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
