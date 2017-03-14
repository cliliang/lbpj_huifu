package com.baluobo.user.model;

import com.baluobo.app.model.BaseModel;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/31.
 */
public class BankCardInfo extends BaseModel {
    private int backCardId;
    //card No
    private String bankCard;
    private String mobile;
    private String userName;
    private String idCard;
    private String createTime;
    private String remark;
    private int bankCardState;
    private int uId;
    private String bankCardCode;
    private String respCode;
    private String respDesc;
    private int bankType;
    private int orderType;
    private String usrId;

    public int getBackCardId() {
        return backCardId;
    }

    public void setBackCardId(int backCardId) {
        this.backCardId = backCardId;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getBankCardState() {
        return bankCardState;
    }

    public void setBankCardState(int bankCardState) {
        this.bankCardState = bankCardState;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public String getBankCardCode() {
        return bankCardCode;
    }

    public void setBankCardCode(String bankCardCode) {
        this.bankCardCode = bankCardCode;
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

    public int getBankType() {
        return bankType;
    }

    public void setBankType(int bankType) {
        this.bankType = bankType;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public String getUsrId() {
        return usrId;
    }

    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }
}
