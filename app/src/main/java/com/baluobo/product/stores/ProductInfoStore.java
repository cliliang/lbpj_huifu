package com.baluobo.product.stores;

import android.util.Log;

import com.baluobo.app.flux.Action;
import com.baluobo.app.flux.Store;
import com.baluobo.app.model.BaseModel;
import com.baluobo.product.actions.ProductInfoAction;
import com.baluobo.product.bean.ProductInfo;
import com.baluobo.user.actions.UserInfoAction;
import com.baluobo.user.model.User;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/26.
 */
public class ProductInfoStore extends Store {
    private ProductInfo productInfo;
    private BaseModel userBaseModel;
    private static ProductInfoStore instance;

    private ProductInfoStore() {
    }

    public static ProductInfoStore getInstance() {
        if (instance == null) {
            instance = new ProductInfoStore();
        }
        return instance;
    }

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public BaseModel getUserInfo(){
        return userBaseModel;
    }

    @Override
    public void onAction(Action action) {
        String type = action.getType();
        switch (type){
            case ProductInfoAction.ACTION_REQUEST_ERROR_MESSAGE:
                String msg = (String) action.getData();
                emitStoreChange(type, msg);
                break;
            case ProductInfoAction.ACTION_REQUEST_PRODUCT_INFO_SUCCESS:
                if (action.getData() instanceof  ProductInfo){
                    ProductInfo productInfo = (ProductInfo) action.getData();
                    if (productInfo != null){
                        this.productInfo = productInfo;
                        emitStoreChange(type);
                    }
                }
                break;
            case UserInfoAction.ACTION_REQUEST_UPDATE_USER_INFO_SUCCESS:
                BaseModel model = (BaseModel) action.getData();
                if (model != null){
                    userBaseModel = model;
                    emitStoreChange(type);
                }
                break;
            case ProductInfoAction.ACTION_REQUEST_START:
            case ProductInfoAction.ACTION_REQUEST_FINISH:
            case ProductInfoAction.ACTION_REQUEST_FAIL:
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
