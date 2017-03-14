package com.baluobo.user.model;

/**
 * desc:
 * Created by:chenliliang
 * Created on:2017/3/14.
 */

public enum SendVerifyCodeType {
    SEND_VERIFY_CODE_TYPE_LOGIN("0"),
    SEND_VERIFY_CODE_TYPE_REGISTER("1"),
    SEND_VERIFY_CODE_TYPE_CHANGE_MOBILE("2"),
    SEND_VERIFY_CODE_TYPE_CHANGE_PSD("3");

    private String type;

    SendVerifyCodeType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }
}
