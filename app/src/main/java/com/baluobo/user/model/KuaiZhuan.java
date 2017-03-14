package com.baluobo.user.model;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/24.
 */
public class KuaiZhuan {
    private int hid;
    private int ordId;
    private int uId;
    private int gcId;
    private float incomeDay;
    private float incomeSum;
    private String createTime;

    public int getHid() {
        return hid;
    }

    public void setHid(int hid) {
        this.hid = hid;
    }

    public int getOrdId() {
        return ordId;
    }

    public void setOrdId(int ordId) {
        this.ordId = ordId;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public int getGcId() {
        return gcId;
    }

    public void setGcId(int gcId) {
        this.gcId = gcId;
    }

    public float getIncomeDay() {
        return incomeDay;
    }

    public void setIncomeDay(float incomeDay) {
        this.incomeDay = incomeDay;
    }

    public float getIncomeSum() {
        return incomeSum;
    }

    public void setIncomeSum(float incomeSum) {
        this.incomeSum = incomeSum;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
