package com.baluobo.product.stores;

import com.baluobo.app.flux.Action;
import com.baluobo.app.flux.Store;
import com.baluobo.product.actions.UsableCouponsAction;
import com.baluobo.user.model.RedPacket;

import java.util.ArrayList;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/8/29.
 */
public class UsableCouponsStore extends Store{
    private ArrayList<RedPacket> redPackets;
    private static UsableCouponsStore instance;
    private UsableCouponsStore(){}
    public static UsableCouponsStore getInstance(){
        if (instance == null){
            instance = new UsableCouponsStore();
        }
        return instance;
    }

    public ArrayList<RedPacket> getRedPackets() {
        return redPackets;
    }

    @Override
    public void onAction(Action action) {
        String type = action.getType();
        switch (type){
            case UsableCouponsAction.ACTION_REQUEST_START:
            case UsableCouponsAction.ACTION_REQUEST_FINISH:
            case UsableCouponsAction.ACTION_REQUEST_FAIL:
            case UsableCouponsAction.ACTION_REQUEST_INVALID_TOKEN:
                emitStoreChange(type);
                break;
            case UsableCouponsAction.ACTION_REQUEST_ERROR_MESSAGE:
                String msg = (String) action.getData();
                emitStoreChange(type, msg);
                break;
            case UsableCouponsAction.ACTION_RQEUST_USABLE_RED_PACKET_SUCCESS:
                ArrayList<RedPacket> list = (ArrayList<RedPacket>) action.getData();
                if (list != null){
                    this.redPackets = list;
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
