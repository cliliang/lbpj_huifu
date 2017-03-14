package com.baluobo.user.stores;

import com.baluobo.app.flux.Action;
import com.baluobo.app.flux.Store;
import com.baluobo.app.model.BaseModel;
import com.baluobo.user.actions.KuaiZhuanAction;
import com.baluobo.user.model.KuaiZhuan;

import java.util.ArrayList;
import java.util.List;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/24.
 */
public class KuaiZhuanStore extends Store {
    private static KuaiZhuanStore  instance;
    private BaseModel<KuaiZhuan> data;
    private KuaiZhuanStore(){}
    public static KuaiZhuanStore getInstance(){
        if (instance == null){
            instance = new KuaiZhuanStore();
        }
        return instance;
    }
    public BaseModel<KuaiZhuan> getData(){
        return data;
    }
    @Override
    public void onAction(Action action) {
        String type = action.getType();
        switch (type){
            case KuaiZhuanAction.ACTION_REQUEST_KUAI_ZHUAN_SUCCESS:
                BaseModel<KuaiZhuan> baseModel = (BaseModel<KuaiZhuan>) action.getData();
                if (baseModel != null){
                    this.data = baseModel;
                }
                emitStoreChange(type);
                break;
            case KuaiZhuanAction.ACTION_REQUEST_ERROR_MESSAGE:
                String msg = (String) action.getData();
                emitStoreChange(type, msg);
                break;
            case KuaiZhuanAction.ACTION_REQUEST_START:
            case KuaiZhuanAction.ACTION_REQUEST_FINISH:
            case KuaiZhuanAction.ACTION_REQUEST_FAIL:
            case KuaiZhuanAction.ACTION_REQUEST_INVALID_TOKEN:
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
