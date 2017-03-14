package com.baluobo.product.router;

import android.content.Context;

import com.baluobo.common.module.RouterBase;
import com.baluobo.product.ui.ExperienceGoldActivity;
import com.baluobo.product.ui.KuaiZhuanInfoActivity;
import com.baluobo.product.ui.ProductBuyActivity;
import com.baluobo.product.ui.ProductListActivity;
import com.baluobo.product.ui.ProductInfoActivity;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/19.
 */
public class ProductRouter extends RouterBase {
    private static ProductRouter instance;
    private ProductRouter(Context context) {
        super(context);
        maps.put(ProductUI.ProductList, ProductListActivity.class);
        maps.put(ProductUI.ProductInfo, ProductInfoActivity.class);
        maps.put(ProductUI.BuyActivity, ProductBuyActivity.class);
        maps.put(ProductUI.Experience, ExperienceGoldActivity.class);
        maps.put(ProductUI.KuaiZhuanInfo, KuaiZhuanInfoActivity.class);
    }

    public static ProductRouter getInstance(Context cnt){
        if (instance == null){
            synchronized (ProductRouter.class){
                instance = new ProductRouter(cnt);
            }
        }
        return instance;
    }
}
