package com.baluobo.user.model;

import com.baluobo.app.model.BaseParcelableModel;

import java.util.Date;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/8/18.
 */
public class RedPacketRecord extends BaseParcelableModel {
    private int couponReId;
    private int couponId;
    private int uId;
    private int couponReType;  //0已使用  1已过期
    private String createTime;
    private String couponReDesc;
    private int couponType; //0：现金红包；1：本金红包；2：体验金券
    private float couponMoney;

    public int getCouponReId() {
        return couponReId;
    }

    public void setCouponReId(int couponReId) {
        this.couponReId = couponReId;
    }

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public int getCouponReType() {
        return couponReType;
    }

    public void setCouponReType(int couponReType) {
        this.couponReType = couponReType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCouponReDesc() {
        return couponReDesc;
    }

    public void setCouponReDesc(String couponReDesc) {
        this.couponReDesc = couponReDesc;
    }

    public int getCouponType() {
        return couponType;
    }

    public void setCouponType(int couponType) {
        this.couponType = couponType;
    }

    public float getCouponMoney() {
        return couponMoney;
    }

    public void setCouponMoney(float couponMoney) {
        this.couponMoney = couponMoney;
    }
}
