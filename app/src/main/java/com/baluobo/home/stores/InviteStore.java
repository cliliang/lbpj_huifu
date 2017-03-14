package com.baluobo.home.stores;

import com.baluobo.app.flux.Action;
import com.baluobo.app.flux.Store;
import com.baluobo.home.actions.InviteAction;
import com.baluobo.home.model.InviteMode;

import java.util.List;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/8/22.
 */
public class InviteStore extends Store {
    private List<InviteMode> inviteModes;
    private static InviteStore instance;
    private InviteStore(){}
    public static InviteStore getInstance(){
        if (instance == null){
            instance = new InviteStore();
        }
        return instance;
    }

    public List<InviteMode> getInviteModes() {
        return inviteModes;
    }

    @Override
    public void onAction(Action action) {
        String type = action.getType();
        switch (type){
            case InviteAction.ACTION_REQUEST_ERROR_MESSAGE:
                String msg = (String) action.getData();
                emitStoreChange(type, msg);
                break;
            case InviteAction.ACTION_REQUEST_START:
            case InviteAction.ACTION_REQUEST_FINISH:
            case InviteAction.ACTION_REQUEST_INVALID_TOKEN:
            case InviteAction.ACTION_REQUEST_FAIL:
                emitStoreChange(type);
                break;
            case InviteAction.ACTION_REQUEST_INVAITE_DATA:
                List<InviteMode> data = (List<InviteMode>) action.getData();
                if (data != null){
                    inviteModes = data;
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
