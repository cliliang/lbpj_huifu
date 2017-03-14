package com.baluobo.find.stores;

import com.baluobo.app.flux.Action;
import com.baluobo.app.flux.Store;
import com.baluobo.app.model.BaseModel;
import com.baluobo.find.actions.VIPCenterAction;
import com.baluobo.find.model.RedTicket;

import java.util.ArrayList;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/8/26.
 */
public class VIPCenterStore extends Store {
    private BaseModel vipData;
    private static VIPCenterStore instance;
    private VIPCenterStore(){}
    public static VIPCenterStore getInstance(){
        if (instance == null){
            instance = new VIPCenterStore();
        }
        return instance;
    }

    public BaseModel getVipData() {
        return vipData;
    }

    @Override
    public void onAction(Action action) {
        String type = action.getType();
        switch (type){
            case VIPCenterAction.ACTION_REQUEST_START:
            case VIPCenterAction.ACTION_REQUEST_FINISH:
            case VIPCenterAction.ACTION_REQUEST_FAIL:
            case VIPCenterAction.ACTION_REQUEST_INVALID_TOKEN:
            case VIPCenterAction.ACTION_REQUEST_TAKE_RED_TICKET_SUCCESS:
                emitStoreChange(type);
                break;
            case VIPCenterAction.ACTION_REQUEST_ERROR_MESSAGE:
                String msg = (String) action.getData();
                emitStoreChange(type, msg);
                break;
            case VIPCenterAction.ACTION_REQUEST_RED_TICKET_SUCCESS:
                BaseModel baseModel = (BaseModel) action.getData();
                if (baseModel != null){
                    this.vipData = baseModel;
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
