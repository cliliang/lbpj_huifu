package com.baluobo.user.model;

import com.baluobo.app.model.BaseParcelableModel;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/8/17.
 */
public class RedPacket extends BaseParcelableModel{
    private int couponId;
    private String couponTitle;
    private String couponDesc;
    private float  couponMoney;
    private String createTime;
    private String endTime;
    private int scores; //所需积分数量
    private int couponType; //0：现金红包；1：本金红包；2：体验金券
    private int state; //0：未使用；1：已使用；2：已过期
    private int uId;
    private int originType; //红包来源类型 0 新手注册 1 邀请有礼 2 兑换 3 抽奖 4 惊喜红包 5 生日红包 6 会员手动领取
    private int vipRedPackId; //用户手动领取会员特权优惠券种类id


    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public String getCouponTitle() {
        return couponTitle;
    }

    public void setCouponTitle(String couponTitle) {
        this.couponTitle = couponTitle;
    }

    public String getCouponDesc() {
        return couponDesc;
    }

    public void setCouponDesc(String couponDesc) {
        this.couponDesc = couponDesc;
    }

    public float getCouponMoney() {
        return couponMoney;
    }

    public void setCouponMoney(float couponMoney) {
        this.couponMoney = couponMoney;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getScores() {
        return scores;
    }

    public void setScores(int scores) {
        this.scores = scores;
    }

    public int getCouponType() {
        return couponType;
    }

    public void setCouponType(int couponType) {
        this.couponType = couponType;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public int getOriginType() {
        return originType;
    }

    public void setOriginType(int originType) {
        this.originType = originType;
    }

    public int getVipRedPackId() {
        return vipRedPackId;
    }

    public void setVipRedPackId(int vipRedPackId) {
        this.vipRedPackId = vipRedPackId;
    }
}
