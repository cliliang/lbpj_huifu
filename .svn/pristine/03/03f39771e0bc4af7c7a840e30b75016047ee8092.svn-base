package com.baluobo.product.actions;

import android.util.Log;

import com.baluobo.app.flux.Action;
import com.baluobo.app.flux.ActionsCreator;
import com.baluobo.app.flux.Dispatcher;
import com.baluobo.app.model.BaseModel;
import com.baluobo.common.config.AppConfig;
import com.baluobo.product.bean.ExpGood;
import com.baluobo.product.bean.Product;
import com.baluobo.product.bean.ProductInfo;
import com.baluobo.user.actions.UserInfoAction;
import com.baluobo.user.model.Order;
import com.baluobo.user.model.RedPacket;
import com.baluobo.user.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/23.
 */
public class ProductActionsCreator extends ActionsCreator {
    private static ProductActionsCreator actionsCreator;

    private ProductActionsCreator(Dispatcher dispatcher) {
        super(dispatcher);
    }

    public static ProductActionsCreator getInstance(Dispatcher dispatcher){
        if (actionsCreator == null){
            actionsCreator = new ProductActionsCreator(dispatcher);
        }
        return actionsCreator;
    }

    
}
