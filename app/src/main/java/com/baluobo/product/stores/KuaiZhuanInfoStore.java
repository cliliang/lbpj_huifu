package com.baluobo.product.stores;

import com.baluobo.app.flux.Action;
import com.baluobo.app.flux.Store;
import com.baluobo.product.actions.KuaiZhuanInfoAction;
import com.baluobo.product.bean.Product;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/12/23.
 */
public class KuaiZhuanInfoStore extends Store {
    private static KuaiZhuanInfoStore instance;
    private Product product;
    private KuaiZhuanInfoStore(){}
    public static KuaiZhuanInfoStore getInstance(){
        if (instance == null){
            instance = new KuaiZhuanInfoStore();
        }
        return instance;
    }

    public Product getInfo(){
        return product;
    }
    @Override
    public void onAction(Action action) {
        String type = action.getType();
        switch (type){
            case KuaiZhuanInfoAction.ACTION_REQUEST_START:
            case KuaiZhuanInfoAction.ACTION_REQUEST_FINISH:
            case KuaiZhuanInfoAction.ACTION_REQUEST_FAIL:
                emitStoreChange(type);
                break;
            case KuaiZhuanInfoAction.ACTION_REQUEST_KUAI_ZHUAN_INFO_SUCCESS:
                Product info = (Product) action.getData();
                if (info != null){
                    this.product = info;
                    emitStoreChange(type);
                }
                break;
            case KuaiZhuanInfoAction.ACTION_REQUEST_ERROR_MESSAGE:
                String msg = (String) action.getData();
                emitStoreChange(type, msg);
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
