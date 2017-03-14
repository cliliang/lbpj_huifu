package com.baluobo.user.model;

import com.baluobo.app.model.BaseParcelableModel;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/8/29.
 */
public class ExpOrder extends BaseParcelableModel{
    private int buyOrderId;
    private String buyOrderNo;
    private int gId;
    private int cId;
    private String goodName;
    private int uId;
    private String mobile;
    private String usrCustId;
    private String borrowerCustId;
    private float countMoney;
    private int investTime;
    private String createTime;
    private String endTime;
    private String buyEndTime;
    private  String buyOrderStateTime; //: "2016-08-29",
    private int buyOrder; //: 1,
    private String respCode;//: "-111",
    private String respDesc;//: "下单成功",
    private float proceeds; //: 5.18,
    private float preProceeds; //: 37.84,
    private float yesterdayIncome; //: 0,
    private int buyflg; //: 3

    public int getBuyOrderId() {
        return buyOrderId;
    }

    public void setBuyOrderId(int buyOrderId) {
        this.buyOrderId = buyOrderId;
    }

    public String getBuyOrderNo() {
        return buyOrderNo;
    }

    public void setBuyOrderNo(String buyOrderNo) {
        this.buyOrderNo = buyOrderNo;
    }

    public int getgId() {
        return gId;
    }

    public void setgId(int gId) {
        this.gId = gId;
    }

    public int getcId() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUsrCustId() {
        return usrCustId;
    }

    public void setUsrCustId(String usrCustId) {
        this.usrCustId = usrCustId;
    }

    public String getBorrowerCustId() {
        return borrowerCustId;
    }

    public void setBorrowerCustId(String borrowerCustId) {
        this.borrowerCustId = borrowerCustId;
    }

    public float getCountMoney() {
        return countMoney;
    }

    public void setCountMoney(float countMoney) {
        this.countMoney = countMoney;
    }

    public int getInvestTime() {
        return investTime;
    }

    public void setInvestTime(int investTime) {
        this.investTime = investTime;
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

    public String getBuyEndTime() {
        return buyEndTime;
    }

    public void setBuyEndTime(String buyEndTime) {
        this.buyEndTime = buyEndTime;
    }

    public String getBuyOrderStateTime() {
        return buyOrderStateTime;
    }

    public void setBuyOrderStateTime(String buyOrderStateTime) {
        this.buyOrderStateTime = buyOrderStateTime;
    }

    public int getBuyOrder() {
        return buyOrder;
    }

    public void setBuyOrder(int buyOrder) {
        this.buyOrder = buyOrder;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespDesc() {
        return respDesc;
    }

    public void setRespDesc(String respDesc) {
        this.respDesc = respDesc;
    }

    public float getProceeds() {
        return proceeds;
    }

    public void setProceeds(float proceeds) {
        this.proceeds = proceeds;
    }

    public float getPreProceeds() {
        return preProceeds;
    }

    public void setPreProceeds(float preProceeds) {
        this.preProceeds = preProceeds;
    }

    public float getYesterdayIncome() {
        return yesterdayIncome;
    }

    public void setYesterdayIncome(float yesterdayIncome) {
        this.yesterdayIncome = yesterdayIncome;
    }

    public int getBuyflg() {
        return buyflg;
    }

    public void setBuyflg(int buyflg) {
        this.buyflg = buyflg;
    }
}
