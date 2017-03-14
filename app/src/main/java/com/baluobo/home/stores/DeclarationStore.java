package com.baluobo.home.stores;

import com.baluobo.app.flux.Action;
import com.baluobo.app.flux.Store;
import com.baluobo.home.actions.DeclarationAction;
import com.baluobo.home.model.Declaration;

import java.util.ArrayList;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/24.
 */
public class DeclarationStore extends Store {
    private ArrayList<Declaration> list = new ArrayList<>();
    private static DeclarationStore instance;
    private DeclarationStore(){}
    public static DeclarationStore getInstance(){
        if (instance == null){
            instance = new DeclarationStore();
        }
        return instance;
    }

    public ArrayList<Declaration> getDeclarations(){
        return list;
    }
    @Override
    public void onAction(Action action) {
        String type = action.getType();
        if (DeclarationAction.ACTION_REQUEST_DECLARATION_SUCCESS.equals(type)){
            ArrayList<Declaration> data = (ArrayList<Declaration>) action.getData();
            if (data != null){
                this.list = data;
                emitStoreChange(type);
            }
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
