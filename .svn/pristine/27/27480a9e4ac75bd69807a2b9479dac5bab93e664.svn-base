package com.baluobo.user.stores;

import com.baluobo.app.flux.Action;
import com.baluobo.app.flux.Store;
import com.baluobo.app.model.BaseModel;
import com.baluobo.user.actions.BankCardAction;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/31.
 */
public class BankCardStore extends Store {
    private BaseModel baseModel;
    private static BankCardStore instance;
    private BankCardStore (){}
    public static BankCardStore getInstance(){
        if (instance == null){
            instance = new BankCardStore();
        }
        return instance;
    }

    public BaseModel getData(){
        return baseModel;
    }

    @Override
    public void onAction(Action action) {
        String type = action.getType();
        switch (type){
            case BankCardAction.ACTION_REQUEST_ERROR_MESSAGE:
                String msg = (String) action.getData();
                emitStoreChange(type, msg);
                break;
            case BankCardAction.ACTION_REQUEST_BANK_CARD_SUCCESS:
                BaseModel data = (BaseModel) action.getData();
                if (data != null){
                    this.baseModel = data;
                    emitStoreChange(type);
                }
                break;
            case BankCardAction.ACTION_REQUEST_START:
            case BankCardAction.ACTION_REQUEST_FINISH:
            case BankCardAction.ACTION_REQUEST_FAIL:
            case BankCardAction.ACTION_REQUEST_INVALID_TOKEN:
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
