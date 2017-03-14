package com.baluobo.find.stores;

import com.baluobo.app.flux.Action;
import com.baluobo.app.flux.Store;
import com.baluobo.find.actions.SignInNoticeAction;

/**
 * desc:
 * Created by:chenliliang
 * Created on:2016/10/10.
 */
public class SignInNoticeStore extends Store {
    private static SignInNoticeStore instance;
    private SignInNoticeStore(){}
    public static SignInNoticeStore getInstance(){
        if (instance == null){
            instance = new SignInNoticeStore();
        }
        return instance;
    }
    @Override
    public void onAction(Action action) {
        String type = action.getType();
        switch (type){
            case SignInNoticeAction.ACTION_SIGN_IN_NOTICE_CLOSE:
            case SignInNoticeAction.ACTION_SIGN_IN_NOTICE_OPEN:
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
