package com.baluobo.home.stores;

import com.baluobo.app.flux.Action;
import com.baluobo.app.flux.Store;
import com.baluobo.home.actions.BannerAction;
import com.baluobo.home.model.Banner;

import java.util.ArrayList;
import java.util.List;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/23.
 */
public class BannerStore extends Store {
    private ArrayList<Banner> banners = new ArrayList<>();
    private static BannerStore instance;
    private BannerStore(){}
    public static BannerStore getInstance(){
        if (instance == null){
            instance = new BannerStore();
        }
        return instance;
    }

    public ArrayList<Banner> getBanners(){
        return banners;
    }
    @Override
    public void onAction(Action action) {
        String type = action.getType();
        if (BannerAction.ACTION_REQUEST_BANNER_SUCCESS.equals(type)){
            ArrayList<Banner> data = (ArrayList<Banner>) action.getData();
            if (data != null){
                this.banners = data;
                emitStoreChange(type);
            }
        }else {
            emitStoreChange(type);
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
