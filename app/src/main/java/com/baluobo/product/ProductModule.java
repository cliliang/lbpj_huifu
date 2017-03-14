package com.baluobo.product;

import android.content.Context;

import com.baluobo.common.module.ModBase;
import com.baluobo.common.module.ModuleID;
import com.baluobo.common.module.ModuleNames;
import com.baluobo.common.module.RouterBase;
import com.baluobo.product.router.ProductRouter;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/19.
 */
public class ProductModule extends ModBase {
    private static ProductModule instance;
    private ProductModule() {
        super(ModuleID.PRODUCT_MODULE_ID, ModuleNames.PRODUCT_MODULE_NAME);
    }

    public static ProductModule getInstance(){
        if (instance == null){
            instance = new ProductModule();
        }
        return instance;
    }

    @Override
    public RouterBase getRouter(Context context) {
        return ProductRouter.getInstance(context);
    }
}
