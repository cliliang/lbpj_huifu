package com.baluobo.product.stores;

import com.baluobo.app.flux.Action;
import com.baluobo.app.flux.Store;
import com.baluobo.common.tools.UIHelper;
import com.baluobo.product.actions.ProductListAction;
import com.baluobo.product.bean.Product;

import java.util.ArrayList;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/23.
 */
public class ProductListStore extends Store {
    private static ProductListStore instance;
    private int dataTotalSize = 0;
    private ArrayList<Product> products = new ArrayList<>();
    private ProductListStore(){}
    public static ProductListStore getInstance(){
        if (instance == null){
            instance = new ProductListStore();
        }
        return instance;
    }

    public ArrayList<Product> getProducts(){
        return (ArrayList<Product>) products.clone();
    }


    public int getDataTotalSize(){
        return dataTotalSize;
    }

    public void clearCache(){
        products.clear();
    }
    @Override
    public void onAction(Action action) {
        String type = action.getType();
        switch (type){
            case ProductListAction.ACTION_REQUEST_ERROR_MESSAGE:
                String msg = (String) action.getData();
                emitStoreChange(type, msg);
                break;
            case ProductListAction.PRODUCT_LIST_REQUEST_LOAD_SUCCESS:
                ArrayList<Product> loadData = (ArrayList<Product>) action.getData();
                if (loadData != null && loadData.size() > 0){
                    this.products.clear();
                    this.products = (ArrayList<Product>) loadData.clone();
                }
                emitStoreChange(type);
                break;
            case ProductListAction.PRODUCT_LIST_REQUEST_REFRESH_SUCCESS:
                ArrayList<Product> refreshData = (ArrayList<Product>) action.getData();
                if (refreshData != null && refreshData.size() > 0){
                    this.products.clear();
                    this.products = (ArrayList<Product>) refreshData.clone();
                }
                emitStoreChange(type);
                break;
            case ProductListAction.PRODUCT_LIST_DATA_TOTAL_SIZE:
                dataTotalSize = (int) action.getData();
                break;
            case ProductListAction.ACTION_REQUEST_FAIL:
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
