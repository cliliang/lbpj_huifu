package com.baluobo.find.stores;

import com.baluobo.app.flux.Action;
import com.baluobo.app.flux.Store;
import com.baluobo.find.actions.SignRecordAction;
import com.baluobo.find.model.SignRecord;

import java.util.ArrayList;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/9/21.
 */
public class SignRecordStore extends Store {
    private ArrayList<SignRecord> signRecords;
    private static SignRecordStore instance;
    private SignRecordStore(){}
    public static SignRecordStore getInstance(){
        if (instance == null){
            instance = new SignRecordStore();
        }
        return instance;
    }

    public ArrayList<SignRecord> getSignRecords(){
        return signRecords;
    }
    @Override
    public void onAction(Action action) {
        String type = action.getType();
        switch (type){
            case SignRecordAction.ACTION_REQUEST_START:
            case SignRecordAction.ACTION_REQUEST_FINISH:
            case SignRecordAction.ACTION_REQUEST_FAIL:
            case SignRecordAction.ACTION_REQUEST_INVALID_TOKEN:
                emitStoreChange(type);
                break;
            case SignRecordAction.ACTION_REQUEST_ERROR_MESSAGE:
                String msg = (String) action.getData();
                emitStoreChange(type, msg);
                break;
            case SignRecordAction.ACTION_REQUEST_SIGN_RECORD_SUCCESS:
                ArrayList<SignRecord> signRecords = (ArrayList<SignRecord>) action.getData();
                if (signRecords != null){
                    this.signRecords = signRecords;
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
