package com.baluobo.product.bean;

import java.util.List;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/11/18.
 */
public class HomeProduct {
    private List<Product> goodList;
    private List<Product> onLineGoods;

    public List<Product> getDtYpmGoods() {
        return goodList;
    }

    public void setDtYpmGoods(List<Product> dtYpmGoods) {
        this.goodList = dtYpmGoods;
    }

    public List<Product> getOnLineGoods() {
        return onLineGoods;
    }

    public void setOnLineGoods(List<Product> onLineGoods) {
        this.onLineGoods = onLineGoods;
    }
}
