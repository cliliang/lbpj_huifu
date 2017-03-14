package com.baluobo.home.stores;

import com.baluobo.app.flux.Action;
import com.baluobo.app.flux.Store;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/7/20.
 */
public class DeviceStore extends Store {
    private static DeviceStore instance;
    private DeviceStore(){};
    public static DeviceStore getInstance(){
        if (instance == null){
            instance = new DeviceStore();
        }
        return instance;
    }
    @Override
    public void onAction(Action action) {
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
