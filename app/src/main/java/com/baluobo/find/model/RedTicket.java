package com.baluobo.find.model;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/8/26.
 */
public class RedTicket {
    private int vid;
    private float vmoney;
    private int vdays;
    private int vlevel; //最小可领取等级
    private int vtype;  //会员特权红包类型0 现金红包 1 本金红包 2 体验金券
    private String vdesc;
    private String vdate;
    private int gtype;  //0未领取 1已领取 2等级不够

    public int getVid() {
        return vid;
    }

    public void setVid(int vid) {
        this.vid = vid;
    }

    public float getVmoney() {
        return vmoney;
    }

    public void setVmoney(float vmoney) {
        this.vmoney = vmoney;
    }

    public int getVdays() {
        return vdays;
    }

    public void setVdays(int vdays) {
        this.vdays = vdays;
    }

    public int getVlevel() {
        return vlevel;
    }

    public void setVlevel(int vlevel) {
        this.vlevel = vlevel;
    }

    public int getVtype() {
        return vtype;
    }

    public void setVtype(int vtype) {
        this.vtype = vtype;
    }

    public String getVdesc() {
        return vdesc;
    }

    public void setVdesc(String vdesc) {
        this.vdesc = vdesc;
    }

    public String getVdate() {
        return vdate;
    }

    public void setVdate(String vdate) {
        this.vdate = vdate;
    }

    public int getGtype() {
        return gtype;
    }

    public void setGtype(int gtype) {
        this.gtype = gtype;
    }
}
