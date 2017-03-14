package com.baluobo.user.stores;

import com.baluobo.app.flux.Action;
import com.baluobo.app.flux.Store;
import com.baluobo.user.actions.MsgAction;
import com.baluobo.user.model.Message;

import java.util.List;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/15.
 */
public class MsgStore extends Store {
    private List<Message> msgData;
    private static MsgStore instance;
    private MsgStore(){
    }
    public static MsgStore getInstance(){
        if (instance == null){
            instance = new MsgStore();
        }
        return instance;
    }

    public List<Message> getMsgData(){
        return msgData;
    }
    @Override
    public void onAction(Action action) {
        String type = action.getType();
        switch (type){
            case MsgAction.ACTION_REQUEST_ERROR_MESSAGE:
                String msg = (String) action.getData();
                emitStoreChange(type, msg);
                break;
            case MsgAction.ACTION_MSG_CENTER_REFRESH_SUCCESS:
                msgData = (List<Message>) action.getData();
                emitStoreChange(type);
                break;
            case MsgAction.ACTION_MSG_CENTER_LOAD_MORE_SUCCESS:
                msgData = (List<Message>) action.getData();
                emitStoreChange(type);
                break;
            case MsgAction.ACTION_REQUEST_FAIL:
            case MsgAction.ACTION_REQUEST_INVALID_TOKEN:
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
