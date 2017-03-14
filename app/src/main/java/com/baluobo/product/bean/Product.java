package com.baluobo.product.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.baluobo.app.model.BaseModel;
import com.baluobo.app.model.BaseParcelableModel;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/23.
 */
public class Product extends BaseParcelableModel{
    /**
     * 产品id
     *
     * @pdOid e58392b6-0b56-43f5-9b44-17efd16da701
     */
    private int goodId;

    /**
     * 产品名称
     *
     * @pdOid 2f091bdb-a00e-4040-8019-58ef53536baa
     */
    private String goodName;

    /**
     * goodLabel:TODO（产品标签）
     *
     * @since 1.0.0
     */
    private String goodLabel;
    /**
     * payLabel:兑付银行
     *
     * @since 1.0.0
     */
    private String payLabel;
    /**
     * safeLabel:TODO（产品图片）
     *
     * @since 1.0.0
     */
    private String safeLabel;
    /**
     * 总金额（定期用的开放额度）
     *
     * @pdOid fcf0330e-84ce-4a22-936e-885723d1e797
     */
    private double buyMoney;
    /**
     * 募集天数  hyb 2016-02-14
     *
     * @pdOid 2ef27172-3d4a-4600-9c88-8d3676edfbf9
     */
    private int raiseTime;
    /**
     * 投资天数
     *
     * @pdOid 2ef27171-3d4a-4600-9c88-8d3676edfbf9
     */
    private int investTime;
    /**
     * 起息时间
     *
     * @pdOid edbfea36-a8e1-4682-a93f-ca8a93d0e1cd
     */
    private String valuesTime;
    /**
     * 结息时间
     *
     * @pdOid edbfea36-a8e1-4682-a93f-ca8a93d0e1cd
     */
    private String valueTime;
    /**
     * 到账时间
     *
     * @pdOid 034283bf-2654-4979-b24f-2ee5609d747a
     */
    private String valuedTime;
    /**
     * 起投金额
     *
     * @pdOid 10c5203e-a92d-48a3-a761-d654c57a95b2
     */
    private double investUnit;
    /**
     * 回款方式 0到期本息自动返还至账户 1客户提现
     *
     * @pdOid cb4833cb-758b-41de-bba1-c5670aaa111b
     */
    private int backType;
    /**
     * 产品详情
     *
     * @pdOid a3423c72-9fe5-4770-bc89-76fa5f88c202
     */
    private String goodDesc;

    /**
     * safeDesc:TODO（安全保障说明）
     *
     * @since 1.0.0
     */
    private String safeDesc;
    /**
     * 创建时间
     *
     * @pdOid 4a087811-424b-466d-b1c5-3245497588a9
     */
    private String createTime;
    /**
     * 年化利率
     *
     * @pdOid 8d656c62-ba96-4bdd-bedf-68d28a44cf40
     */
    private double proceeds;
    /**
     * 对比年化收益
     *
     * @pdOid 8d656c62-ba96-4bdd-bedf-68d28a44cf40
     */
    private double vipProceeds;
    /**
     * 产品类型1银票苗4新人标5定期6活期
     *
     * @pdOid a01ffea9-98aa-4349-91ee-f3eb2df0d220
     */
    private int goodType;
    /**
     * 产品分类
     *
     * @pdOid e99c7347-567b-4ef4-9df8-d94f72ca2c2c
     */
    private int gcId;
    /**
     * 是否删除0否1是
     *
     * @pdOid 1a4025fc-1c8f-4c42-83b6-6247e021d1c7
     */
    private int isDelete;
    /**
     * 是否设置为推荐0否1是
     *
     * @pdOid 0483e58e-2f13-4e33-b78d-228ccd8be2df
     */
    private int isHost;
    /**
     * usrId:TODO（融资者客户号）
     *
     * @since 1.0.0
     */
    private String usrId;
    //标录入返回码
    private String respCode;
    //标录入返回描述
    private String respDesc;
    //开放额度(活期用的可购金额)
    private double openMoney;
    //倒计时产品时间
    private String onLineTime;
    //倒计时产品时间戳
    private long onLineTimeStamp;

//非持久化对象
    /**
     * goodDescUrl:TODO（产品描述路径）
     *
     * @since 1.0.0
     */
    private String goodDescUrl;
    /**
     * safeDescUrl:TODO（产品安全保障路径）
     *
     * @since 1.0.0
     */
    private String safeDescUrl;

    /**
     * surplusMoney:TODO（剩余金额）
     *
     * @since 1.0.0
     */
    private double surplusMoney;
    /**
     * buyUserCount:TODO（申购人数）
     *
     * @since 1.0.0
     */
    private int buyUserCount;
    /**
     * buyUserCount:TODO（认购比）
     *
     * @since 1.0.0
     */
    private double buyPercent;

    private double sumMoney;
    //分类名
    private String gcName;
    // 购买标识 4：立即抢购 3：已售罄 2：还款中 1：已还款
    private int buyflg1;
    //是否结束0否1是（到帐时间和当前时间比较）
    private int endflg;

    //放款总金额
    private double giveMoney;
    //应还款总金额
    private double refunMoney;
    //0未还款1已还款
    private int refundFlg;

    //计息天数
    private int manageTime;
    //系统时间
    private String serviceTime;

    public int getGoodId() {
        return goodId;
    }

    public void setGoodId(int goodId) {
        this.goodId = goodId;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getGoodLabel() {
        return goodLabel;
    }

    public void setGoodLabel(String goodLabel) {
        this.goodLabel = goodLabel;
    }

    public String getPayLabel() {
        return payLabel;
    }

    public void setPayLabel(String payLabel) {
        this.payLabel = payLabel;
    }

    public String getSafeLabel() {
        return safeLabel;
    }

    public void setSafeLabel(String safeLabel) {
        this.safeLabel = safeLabel;
    }

    public double getBuyMoney() {
        return buyMoney;
    }

    public void setBuyMoney(double buyMoney) {
        this.buyMoney = buyMoney;
    }

    public int getRaiseTime() {
        return raiseTime;
    }

    public void setRaiseTime(int raiseTime) {
        this.raiseTime = raiseTime;
    }

    public int getInvestTime() {
        return investTime;
    }

    public void setInvestTime(int investTime) {
        this.investTime = investTime;
    }

    public String getValuesTime() {
        return valuesTime;
    }

    public void setValuesTime(String valuesTime) {
        this.valuesTime = valuesTime;
    }

    public String getValueTime() {
        return valueTime;
    }

    public void setValueTime(String valueTime) {
        this.valueTime = valueTime;
    }

    public String getValuedTime() {
        return valuedTime;
    }

    public void setValuedTime(String valuedTime) {
        this.valuedTime = valuedTime;
    }

    public double getInvestUnit() {
        return investUnit;
    }

    public void setInvestUnit(double investUnit) {
        this.investUnit = investUnit;
    }

    public int getBackType() {
        return backType;
    }

    public void setBackType(int backType) {
        this.backType = backType;
    }

    public String getGoodDesc() {
        return goodDesc;
    }

    public void setGoodDesc(String goodDesc) {
        this.goodDesc = goodDesc;
    }

    public String getSafeDesc() {
        return safeDesc;
    }

    public void setSafeDesc(String safeDesc) {
        this.safeDesc = safeDesc;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public double getProceeds() {
        return proceeds;
    }

    public void setProceeds(double proceeds) {
        this.proceeds = proceeds;
    }

    public double getVipProceeds() {
        return vipProceeds;
    }

    public void setVipProceeds(double vipProceeds) {
        this.vipProceeds = vipProceeds;
    }

    public int getGoodType() {
        return goodType;
    }

    public void setGoodType(int goodType) {
        this.goodType = goodType;
    }

    public int getGcId() {
        return gcId;
    }

    public void setGcId(int gcId) {
        this.gcId = gcId;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public int getIsHost() {
        return isHost;
    }

    public void setIsHost(int isHost) {
        this.isHost = isHost;
    }

    public String getUsrId() {
        return usrId;
    }

    public void setUsrId(String usrId) {
        this.usrId = usrId;
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

    public double getOpenMoney() {
        return openMoney;
    }

    public void setOpenMoney(double openMoney) {
        this.openMoney = openMoney;
    }

    public String getGoodDescUrl() {
        return goodDescUrl;
    }

    public void setGoodDescUrl(String goodDescUrl) {
        this.goodDescUrl = goodDescUrl;
    }

    public String getSafeDescUrl() {
        return safeDescUrl;
    }

    public void setSafeDescUrl(String safeDescUrl) {
        this.safeDescUrl = safeDescUrl;
    }

    public double getSurplusMoney() {
        return surplusMoney;
    }

    public void setSurplusMoney(double surplusMoney) {
        this.surplusMoney = surplusMoney;
    }

    public int getBuyUserCount() {
        return buyUserCount;
    }

    public void setBuyUserCount(int buyUserCount) {
        this.buyUserCount = buyUserCount;
    }

    public double getBuyPercent() {
        return buyPercent;
    }

    public void setBuyPercent(double buyPercent) {
        this.buyPercent = buyPercent;
    }

    public double getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(double sumMoney) {
        this.sumMoney = sumMoney;
    }

    public String getGcName() {
        return gcName;
    }

    public void setGcName(String gcName) {
        this.gcName = gcName;
    }

    public int getBuyflg() {
        return buyflg1;
    }

    public void setBuyflg(int buyflg) {
        this.buyflg1 = buyflg;
    }

    public int getEndflg() {
        return endflg;
    }

    public void setEndflg(int endflg) {
        this.endflg = endflg;
    }

    public double getGiveMoney() {
        return giveMoney;
    }

    public void setGiveMoney(double giveMoney) {
        this.giveMoney = giveMoney;
    }

    public double getRefunMoney() {
        return refunMoney;
    }

    public void setRefunMoney(double refunMoney) {
        this.refunMoney = refunMoney;
    }

    public int getRefundFlg() {
        return refundFlg;
    }

    public void setRefundFlg(int refundFlg) {
        this.refundFlg = refundFlg;
    }

    public int getManageTime() {
        return manageTime;
    }

    public void setManageTime(int manageTime) {
        this.manageTime = manageTime;
    }

    public String getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }

    public String getOnLineTime() {
        return onLineTime;
    }

    public void setOnLineTime(String onLineTime) {
        this.onLineTime = onLineTime;
    }

    public long getOnLineTimeStamp() {
        return onLineTimeStamp;
    }

    public void setOnLineTimeStamp(long onLineTimeStamp) {
        this.onLineTimeStamp = onLineTimeStamp;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(goodId);
        dest.writeString(goodName);
        dest.writeString(goodLabel);
        dest.writeString(payLabel);
        dest.writeString(safeLabel);
        dest.writeDouble(buyMoney);
        dest.writeInt(raiseTime);
        dest.writeInt(investTime);
        dest.writeString(valuesTime);
        dest.writeString(valueTime);
        dest.writeString(valuedTime);
        dest.writeDouble(investUnit);
        dest.writeInt(backType);
        dest.writeString(goodDesc);
        dest.writeString(safeDesc);
        dest.writeString(createTime);
        dest.writeDouble(proceeds);
        dest.writeDouble(vipProceeds);
        dest.writeInt(goodType);
        dest.writeInt(gcId);
        dest.writeInt(isDelete);
        dest.writeInt(isHost);
        dest.writeString(usrId);
        dest.writeString(respCode);
        dest.writeString(respDesc);
        dest.writeDouble(openMoney);
        dest.writeString(goodDescUrl);
        dest.writeString(safeDescUrl);
        dest.writeDouble(surplusMoney);
        dest.writeInt(buyUserCount);
        dest.writeDouble(buyPercent);
        dest.writeDouble(sumMoney);
        dest.writeString(gcName);
        dest.writeInt(buyflg1);
        dest.writeInt(endflg);
        dest.writeDouble(giveMoney);
        dest.writeDouble(refunMoney);
        dest.writeInt(refundFlg);
        dest.writeInt(manageTime);
        dest.writeString(serviceTime);
        dest.writeString(onLineTime);
        dest.writeLong(onLineTimeStamp);
    }

    public Product(Parcel source){
        goodId = source.readInt();
        goodName = source.readString();
        goodLabel = source.readString();
        payLabel = source.readString();
        safeLabel = source.readString();
        buyMoney = source.readDouble();
        raiseTime = source.readInt();
        investTime = source.readInt();
        valuesTime = source.readString();
        valueTime = source.readString();
        valuedTime = source.readString();
        investUnit = source.readDouble();
        backType = source.readInt();
        goodDesc = source.readString();
        safeDesc = source.readString();
        createTime = source.readString();
        proceeds = source.readDouble();
        vipProceeds = source.readDouble();
        goodType = source.readInt();
        gcId = source.readInt();
        isDelete = source.readInt();
        isHost = source.readInt();
        usrId = source.readString();
        respCode = source.readString();
        respDesc = source.readString();
        openMoney = source.readDouble();
        goodDescUrl = source.readString();
        safeDescUrl = source.readString();
        surplusMoney = source.readDouble();
        buyUserCount = source.readInt();
        buyPercent = source.readDouble();
        sumMoney = source.readDouble();
        gcName = source.readString();
        buyflg1 = source.readInt();
        endflg = source.readInt();
        giveMoney = source.readDouble();
        refunMoney = source.readDouble();
        refundFlg = source.readInt();
        manageTime = source.readInt();
        serviceTime = source.readString();
        onLineTime = source.readString();
        onLineTimeStamp = source.readLong();
    }

    public static final Parcelable.Creator<BaseModel> CREATOR = new Parcelable.Creator<BaseModel>(){

        @Override
        public BaseModel createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public BaseModel[] newArray(int size) {
            return new BaseModel[size];
        }
    };
}
