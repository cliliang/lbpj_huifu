package com.baluobo.home.stores;

import com.baluobo.app.flux.Action;
import com.baluobo.app.flux.Store;
import com.baluobo.home.actions.VersionAction;
import com.baluobo.home.model.VersionInfo;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/11/30.
 */
public class VersionStore extends Store {
    private VersionInfo info;
    private static VersionStore instance;
    private VersionStore(){}
    public static VersionStore getInstance(){
        if (instance == null){
            instance = new VersionStore();
        }
        return instance;
    }

    public VersionInfo getVersionInfo(){
        return info;
    }

    @Override
    public void onAction(Action action) {
        String type = action.getType();
        switch (type){
            case VersionAction.ACTION_REQUEST_VERSION_SUCCESS:
                VersionInfo versionInfo = (VersionInfo) action.getData();
                if (versionInfo != null){
                    this.info = versionInfo;
                    emitStoreChange(type);
                }
                break;
            case VersionAction.ACTION_REQUEST_FINISH:
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
