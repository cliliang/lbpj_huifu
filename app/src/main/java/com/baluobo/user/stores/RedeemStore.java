package com.baluobo.user.stores;

import com.baluobo.app.flux.Action;
import com.baluobo.app.flux.Store;
import com.baluobo.user.actions.RedeemAction;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/29.
 */
public class RedeemStore extends Store {
    private static RedeemStore instance;
    private RedeemStore(){}
    public static RedeemStore getInstance(){
        if (instance == null){
            instance = new RedeemStore();
        }
        return instance;
    }
    @Override
    public void onAction(Action action) {
        String type = action.getType();
        switch (type){
            case RedeemAction.ACTION_REQUEST_ERROR_MESSAGE:
                String msg = (String) action.getData();
                emitStoreChange(type, msg);
                break;
            case RedeemAction.ACTION_REQUEST_START:
            case RedeemAction.ACTION_REQUEST_FINISH:
            case RedeemAction.ACTION_REQUEST_FAIL:
            case RedeemAction.ACTION_REQUEST_INVALID_TOKEN:
            case RedeemAction.ACTION_REQUEST_REDEEM_BACK_SUCCESS:
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
