package com.baluobo.user.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.baluobo.app.model.BaseModel;
import com.baluobo.app.model.BaseParcelableModel;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/18.
 */
public class Order extends BaseParcelableModel {
    //订单id
    private int buyOrderId;
    //订单编号
    private String buyOrderNo;
    //产品id
    private int gId;
    //产品名称
    private String goodName;
    //用户id
    private int uId;
    //本金+利息(活期专用)
    private double speedMoney;
    //本金
    private double countMoney;
    //优惠券金额
    private double couponMoney;
    //到账时间
    private String buyEndTime;
    //投资天数
    private int investTime;
    //处理时间
    private String buyOrderStateTime;
    //创建时间
    private String createTime;
    //是否删除0否1是
    private int isDelete;
    //备注
    private String remark;
    //用户手机号
    private String mobile;
    //预计收益 （定期专用）
    private double preProceeds;
    //手续费
    private double prCharge;
    //优惠券id
    private int cpId;
    //起息时间
    private String startTime;
    //产品分类0银承产品1商票产品
    private int gcId;
    //用户归属地
    private String userPlace;
    //产品类型0新手1普通2本息再投3vip
    private int gType;
    //用户客户号
    private String usrCustId;
    //借款人账户
    private String borrowerCustId;
    //应答返回码	-111默认未处理其他就是汇付返回码
    private String respCode;
    //应答描述
    private String respDesc;
    //累计收益
    private double sumEarn;
    //订单状态0未支付1已投标2已放款3已还款4处理失败
    private int buyOrder;

    private double benXiMoney;
    private int isBenXi;
    private String unFreeTime;
    //结息时间
    private String endTime;
    private double proceeds;
    private double productMoneys;
    private int buyflg;
    private double sumMoney;
    private double yesterdayIncome;
    private double surplusReturnMoney;
    //本金红包
    private double principalMoney;
    //现金红包
    private double cashMoney;

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

    public double getSpeedMoney() {
        return speedMoney;
    }

    public void setSpeedMoney(double speedMoney) {
        this.speedMoney = speedMoney;
    }

    public double getCountMoney() {
        return countMoney;
    }

    public void setCountMoney(double countMoney) {
        this.countMoney = countMoney;
    }

    public double getCouponMoney() {
        return couponMoney;
    }

    public void setCouponMoney(double couponMoney) {
        this.couponMoney = couponMoney;
    }

    public String getBuyEndTime() {
        return buyEndTime;
    }

    public void setBuyEndTime(String buyEndTime) {
        this.buyEndTime = buyEndTime;
    }

    public int getInvestTime() {
        return investTime;
    }

    public void setInvestTime(int investTime) {
        this.investTime = investTime;
    }

    public String getBuyOrderStateTime() {
        return buyOrderStateTime;
    }

    public void setBuyOrderStateTime(String buyOrderStateTime) {
        this.buyOrderStateTime = buyOrderStateTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public double getPreProceeds() {
        return preProceeds;
    }

    public void setPreProceeds(double preProceeds) {
        this.preProceeds = preProceeds;
    }

    public double getPrCharge() {
        return prCharge;
    }

    public void setPrCharge(double prCharge) {
        this.prCharge = prCharge;
    }

    public int getCpId() {
        return cpId;
    }

    public void setCpId(int cpId) {
        this.cpId = cpId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getGcId() {
        return gcId;
    }

    public void setGcId(int gcId) {
        this.gcId = gcId;
    }

    public String getUserPlace() {
        return userPlace;
    }

    public void setUserPlace(String userPlace) {
        this.userPlace = userPlace;
    }

    public int getgType() {
        return gType;
    }

    public void setgType(int gType) {
        this.gType = gType;
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

    public double getSumEarn() {
        return sumEarn;
    }

    public void setSumEarn(double sumEarn) {
        this.sumEarn = sumEarn;
    }

    public int getBuyOrder() {
        return buyOrder;
    }

    public void setBuyOrder(int buyOrder) {
        this.buyOrder = buyOrder;
    }


    public int getIsBenXi() {
        return isBenXi;
    }

    public void setIsBenXi(int isBenXi) {
        this.isBenXi = isBenXi;
    }

    public String getUnFreeTime() {
        return unFreeTime;
    }

    public void setUnFreeTime(String unFreeTime) {
        this.unFreeTime = unFreeTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }


    public int getBuyflg() {
        return buyflg;
    }

    public void setBuyflg(int buyflg) {
        this.buyflg = buyflg;
    }

    public double getBenXiMoney() {
        return benXiMoney;
    }

    public void setBenXiMoney(double benXiMoney) {
        this.benXiMoney = benXiMoney;
    }

    public double getProceeds() {
        return proceeds;
    }

    public void setProceeds(double proceeds) {
        this.proceeds = proceeds;
    }

    public double getProductMoneys() {
        return productMoneys;
    }

    public void setProductMoneys(double productMoneys) {
        this.productMoneys = productMoneys;
    }

    public double getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(double sumMoney) {
        this.sumMoney = sumMoney;
    }

    public double getYesterdayIncome() {
        return yesterdayIncome;
    }

    public void setYesterdayIncome(double yesterdayIncome) {
        this.yesterdayIncome = yesterdayIncome;
    }

    public double getSurplusReturnMoney() {
        return surplusReturnMoney;
    }

    public void setSurplusReturnMoney(double surplusReturnMoney) {
        this.surplusReturnMoney = surplusReturnMoney;
    }

    public double getPrincipalMoney() {
        return principalMoney;
    }

    public void setPrincipalMoney(double principalMoney) {
        this.principalMoney = principalMoney;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public double getCashMoney() {
        return cashMoney;
    }

    public void setCashMoney(double cashMoney) {
        this.cashMoney = cashMoney;
    }

    public Order(Parcel source) {
        buyOrderId = source.readInt();
        buyOrderNo = source.readString();
        gId = source.readInt();
        goodName = source.readString();
        uId = source.readInt();
        speedMoney = source.readDouble();
        countMoney = source.readDouble();
        couponMoney = source.readDouble();
        buyEndTime = source.readString();
        investTime = source.readInt();
        buyOrderStateTime = source.readString();
        createTime = source.readString();
        isDelete = source.readInt();
        remark = source.readString();
        mobile = source.readString();
        preProceeds = source.readDouble();
        prCharge = source.readDouble();
        cpId = source.readInt();
        startTime = source.readString();
        gcId = source.readInt();
        userPlace = source.readString();
        gType = source.readInt();
        usrCustId = source.readString();
        borrowerCustId = source.readString();
        respCode = source.readString();
        respDesc = source.readString();
        sumEarn = source.readDouble();
        buyOrder = source.readInt();
        benXiMoney = source.readDouble();
        isBenXi = source.readInt();
        unFreeTime = source.readString();
        endTime = source.readString();
        proceeds = source.readDouble();
        productMoneys = source.readDouble();
        buyflg = source.readInt();
        sumMoney = source.readDouble();
        yesterdayIncome = source.readDouble();
        surplusReturnMoney = source.readDouble();
        principalMoney = source.readDouble();
        cashMoney = source.readDouble();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(buyOrderId);
        dest.writeString(buyOrderNo);
        dest.writeInt(gId);
        dest.writeString(goodName);
        dest.writeInt(uId);
        dest.writeDouble(speedMoney);
        dest.writeDouble(countMoney);
        dest.writeDouble(couponMoney);
        dest.writeString(buyEndTime);
        dest.writeInt(investTime);
        dest.writeString(buyOrderStateTime);
        dest.writeString(createTime);
        dest.writeInt(isDelete);
        dest.writeString(remark);
        dest.writeString(mobile);
        dest.writeDouble(preProceeds);
        dest.writeDouble(prCharge);
        dest.writeInt(cpId);
        dest.writeString(startTime);
        dest.writeInt(gcId);
        dest.writeString(userPlace);
        dest.writeInt(gType);
        dest.writeString(usrCustId);
        dest.writeString(borrowerCustId);
        dest.writeString(respCode);
        dest.writeString(respDesc);
        dest.writeDouble(sumEarn);
        dest.writeInt(buyOrder);
        dest.writeDouble(benXiMoney);
        dest.writeInt(isBenXi);
        dest.writeString(unFreeTime);
        dest.writeString(endTime);
        dest.writeDouble(proceeds);
        dest.writeDouble(productMoneys);
        dest.writeInt(buyflg);
        dest.writeDouble(sumMoney);
        dest.writeDouble(yesterdayIncome);
        dest.writeDouble(surplusReturnMoney);
        dest.writeDouble(principalMoney);
        dest.writeDouble(cashMoney);

    }

    public static final Parcelable.Creator<Order> CREATOR = new Parcelable.Creator<Order>() {

        @Override
        public Order createFromParcel(Parcel source) {
            return new Order(source);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };
}
