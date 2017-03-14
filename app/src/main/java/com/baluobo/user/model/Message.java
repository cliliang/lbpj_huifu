package com.baluobo.user.model;

import com.baluobo.app.model.BaseModel;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/15.
 */
public class Message extends BaseModel {
    //消息id
    private int messId;
    //消息标题
    private String title;
    //消息类型   1普通消息(前台点击不可跳转) 2用户投资 3会员特权礼遇 4红包到期
    private int messType;
    //创建时间
    private String createTime;
    //消息内容描述
    private String messDesc;
    //用户id
    private int uId;
    //消息是否阅读0否1是
    private int seenType;

    public int getMessId() {
        return messId;
    }

    public void setMessId(int messId) {
        this.messId = messId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMessType() {
        return messType;
    }

    public void setMessType(int messType) {
        this.messType = messType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMessDesc() {
        return messDesc;
    }

    public void setMessDesc(String messDesc) {
        this.messDesc = messDesc;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public boolean getSeenType() {
        return seenType == 1;
    }

    public void setSeenType(int seenType) {
        this.seenType = seenType;
    }
}
