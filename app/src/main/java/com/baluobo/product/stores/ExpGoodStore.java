package com.baluobo.product.stores;

import com.baluobo.app.flux.Action;
import com.baluobo.app.flux.Store;
import com.baluobo.product.actions.ExpGoodAction;
import com.baluobo.product.bean.ExpGood;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/8/23.
 */
public class ExpGoodStore extends Store {
    private ExpGood expGood;
    private static ExpGoodStore instance;
    private ExpGoodStore(){}
    public static ExpGoodStore getInstance(){
        if (instance == null){
            instance = new ExpGoodStore();
        }
        return instance;
    }

    public ExpGood getExpGood() {
        return expGood;
    }

    @Override
    public void onAction(Action action) {
        String type = action.getType();
        switch (type){
            case ExpGoodAction.ACTION_REQUEST_START:
            case ExpGoodAction.ACTION_REQUEST_FINISH:
            case ExpGoodAction.ACTION_REQUEST_FAIL:
            case ExpGoodAction.ACTION_REQUEST_INVALID_TOKEN:
            case ExpGoodAction.BUY_EXP_ACTION_REQUEST_SUCCESS:
                emitStoreChange(type);
                break;
            case ExpGoodAction.ACTION_REQUEST_ERROR_MESSAGE:
                String msg = (String) action.getData();
                emitStoreChange(type, msg);
                break;
            case ExpGoodAction.ACTION_REQUEST_EXP_GOOD_SUCCESS:
                ExpGood exp = (ExpGood) action.getData();
                if (exp != null){
                    this.expGood = exp;
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
