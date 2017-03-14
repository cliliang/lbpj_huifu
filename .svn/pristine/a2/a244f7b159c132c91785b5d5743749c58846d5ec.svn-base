package com.baluobo.home.stores;

import com.baluobo.app.flux.Action;
import com.baluobo.app.flux.Store;
import com.baluobo.home.actions.LoadPicAction;
import com.baluobo.home.model.Banner;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/12/15.
 */
public class LoadPicStore extends Store {
    private Banner picInfo;
    private static LoadPicStore instance;
    private LoadPicStore(){}
    public static LoadPicStore getInstance(){
        if (instance == null){
            instance = new LoadPicStore();
        }
        return instance;
    }
    public Banner getLoadPicInfo(){
        return picInfo;
    }
    @Override
    public void onAction(Action action) {
        String type = action.getType();
        switch (type){
            case LoadPicAction.LOAD_PIC_ACTION_SUCCESS:
                Banner banner = (Banner) action.getData();
                if (banner != null){
                    this.picInfo = banner;
                    emitStoreChange(type);
                }
                break;
            case LoadPicAction.DOWNLOAD_LOAD_PIC_FINISH:
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
