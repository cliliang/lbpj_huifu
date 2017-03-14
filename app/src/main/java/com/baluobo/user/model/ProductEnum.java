package com.baluobo.user.model;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/21.
 */
public enum ProductEnum {

    LUOBO_DING_TOU(10, "luobo_ding_tou"),
    LUOBO_KUAI_ZHUAN(13, "luobo_kuai_zhuan"),
    LUOBO_XIN_SHOU(11, "luobo_xin_shou"),
    LUOBO_YIN_PIAO(5, "luobo_yin_piao");
    private int productId;
    private String productName;

    ProductEnum(int id, String name) {
        this.productId = id;
        this.productName = name;

    }

    public int getProductTypeId() {
        return productId;
    }

    public static ProductEnum getProduct(int id){
        if (id == LUOBO_DING_TOU.getProductTypeId()){
            return LUOBO_DING_TOU;
        }else if (id == LUOBO_KUAI_ZHUAN.getProductTypeId()){
            return LUOBO_KUAI_ZHUAN;
        }else if (id == LUOBO_XIN_SHOU.getProductTypeId()){
            return LUOBO_XIN_SHOU;
        }else {
            return LUOBO_YIN_PIAO;
        }
    }
}
