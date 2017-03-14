package com.baluobo.user.stores;

import com.baluobo.app.AppContext;
import com.baluobo.app.flux.Action;
import com.baluobo.app.flux.Store;
import com.baluobo.app.model.BaseModel;
import com.baluobo.user.actions.LoginAction;
import com.squareup.otto.Subscribe;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/10.
 */
public class LoginStore extends Store {
    private static LoginStore instance;
    private BaseModel baseModel;

    private LoginStore(AppContext cnt){
        super();
    }
    public static LoginStore getInstance(AppContext cnt){
        if (instance == null){
            instance = new LoginStore(cnt);
        }
        return instance;
    }

    public BaseModel getLoginUser(){
        return baseModel;
    }


    @Override
    @Subscribe
    public void onAction(Action action) {
        String type = action.getType();
        switch (type){
            case LoginAction.LOGIN_SEND_CODE_ACTION_ERROR_MESSAGE:
            case LoginAction.ACTION_REQUEST_ERROR_MESSAGE:
                String msg = (String) action.getData();
                emitStoreChange(LoginAction.ACTION_REQUEST_ERROR_MESSAGE, msg);
                break;
            case LoginAction.ACTION_LOGIN_SUCCESS:
                BaseModel model = (BaseModel) action.getData();
                if (model != null){
                    this.baseModel = model;
                    emitStoreChange(type);
                }
                break;
            case LoginAction.ACTION_REQUEST_START:
            case LoginAction.ACTION_REQUEST_FINISH:
            case LoginAction.ACTION_REQUEST_FAIL:
            case LoginAction.LOGIN_SEND_CODE_ACTION_START:
            case LoginAction.LOGIN_SEND_CODE_ACTION_FINISH:
            case LoginAction.LOGIN_SEND_CODE_ACTION_FAIL:
                emitStoreChange(type);
                break;

        }
    }

    @Override
    public StoreChangeEvent changeEvent(String type) {
        return new StoreChangeEvent(type);
    }

    public StoreChangeEvent changeEvent(String type, String msg){
        return new StoreChangeEvent(type, msg);
    }
}
