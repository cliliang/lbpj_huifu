package com.baluobo.user.model;


import android.content.ContentValues;
import android.database.Cursor;

import com.baluobo.app.model.BaseModel;
import com.baluobo.common.tools.SingleButtonDialog;

import java.util.Date;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/10.
 */
public class User extends BaseModel {

    public static final String USER_TABLE= "user_table";

    public static final String TABLE_USER_ID = "userId";
    public static final String TABLE_USER_MOBILE = "mobile";
    public static final String TABLE_USER_PASS_WORD = "passWord";
    public static final String TABLE_IS_AUTONYM = "isAutonym";
    public static final String TABLE_USER_NAME = "userName";
    public static final String TABLE_IDCARD = "idCard";
    public static final String TABLE_HEADPIC = "headPic";
    public static final String TABLE_REMARK = "remark";
    public static final String TABLE_USERTYPE = "userType";
    public static final String TABLE_CREATE_TIME = "createTime";
    public static final String TABLE_IS_DELETE = "isDelete";
    public static final String TABLE_COUNT_NAME = "countName";
    public static final String TABLE_USER_CUSTID = "usrCustId";
    public static final String TABLE_TRXID = "trxId";
    public static final String TABLE_RESP_CODE = "respCode";
    public static final String TABLE_RESP_DESC = "respDesc";
    public static final String TABLE_USER_PLACE = "userPlace";
    public static final String TABLE_COUPON_FLG = "couponFlg";
    public static final String TABLE_USER_NO = "userNo";
    public static final String TABLE_COUNT_MONEY = "countMoney";
    public static final String TABLE_FREEZE_MONEY = "freezeMoney";
    public static final String TABLE_ENABLE_MONEY = "enAbleMoney";
    public static final String TABLE_SUM_PROCEEDS = "sumProceeds";
    public static final String TABLE_NOW_PROCEEDS = "nowProceeds";
    public static final String TABLE_REAL_TIME = "realTime";
    public static final String TABLE_INVITE_CODE = "inviteCode";
    public static final String TABLE_BANK_CARD = "bankCard";
    public static final String TABLE_BANK_CARD_NAME = "bankCardName";
    public static final String TABLE_TOKEN = "token";
    public static final String TABLE_YESTERDAY_INCOME = "yesterdayIncome";
    public static final String TABLE_GESTURE = "gesture";
    public static final String TABLE_BEINVITE_CODE = "beInviteCode";
    public static final String TABLE_USER_SCORE = "userScore";
    public static final String TABLE_EXP_INCOME = "expIncome";
    public static final String TABLE_SCORE_TIME = "scoreTime";
    public static final String TABLE_ISOPENSIGNIN = "isOpenSignIn";
    public static final String TABLE_IS_BIND_CARD = "isBindCard";
    public static final String TABLE_HUI_FU_MONEY = "withdrawMoney";
    public static final String TABLE_IS_ACTIVE_CGT = "isActiveCgt";

    // 用户id
    private int userId;
    // 手机号
    private String mobile;
    // 密码
    private String passWord;
    // 是否实名认证0否1是
    private int isAutonym;
    // 姓名
    private String userName;
    // 身份证
    private String idCard;
    // 头像
    private String headPic;
    // 备注
    private String remark;
    // 用户类别0新用户1普通用户2vip用户
    private int userType;
    // 创建时间
    private String createTime;
    // 是否删除0否1是
    private int isDelete;
    // 用户账户
    private String countName;
    // 用户客户号
    private String usrCustId;
    // 汇付平台返回交易码
    private String trxId;
    // 汇付平台返回交易码
    private String respCode;
    // 汇付平台返回交易描述
    private String respDesc;
    // 用户所在地
    private String userPlace;
    // 是否使用新手优惠券0否1是
    private int couponFlg;
    // 用户编号
    private String userNo;
    // 账户金额
    private String countMoney;
    // 账户冻结资金
    private String freezeMoney;
    // 账户可用资金
    private String enAbleMoney;
    // 累计收益
    private String sumProceeds;
    // 当前收益
    private String nowProceeds;

    private String realTime;
    //邀请码
    private String inviteCode;

    private String bankCard;
    private String bankCardName;

    private float xsbMoneys;
    private float ypmMoneys;
    private float lbdtMoneys;
    private float lbkzMoneys;
    private float yesterdayIncome;
    //手势密码
    private String handPassword;
    //用户受邀码
    private String beInviteCode;
    //用户积分总数
    private int userScore;
    //体验金累计收益
    private float experienceIncome;
    //签到时间，每天签到一次更新一次
    private long getScoreTime;
    //是否自动提醒签到
    private int isOpenSignIn;
    private String isBindCard;
    //汇付提现金额
    private float withdrawMoney;
    //是否激活0表示没有激活，1表示已经激活
    private String isActiveCgt;


    public User() {
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public boolean isAutonym() {
        return isAutonym == 1;
    }

    public void setAutonym(boolean autonym) {
        isAutonym = autonym ? 1 : 0;
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

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public boolean isDelete() {
        return isDelete == 1;
    }

    public void setDelete(boolean delete) {
        isDelete = delete ? 1 : 0;
    }

    public String getCountName() {
        return countName;
    }

    public void setCountName(String countName) {
        this.countName = countName;
    }

    public String getUsrCustId() {
        return usrCustId;
    }

    public void setUsrCustId(String usrCustId) {
        this.usrCustId = usrCustId;
    }

    public String getTrxId() {
        return trxId;
    }

    public void setTrxId(String trxId) {
        this.trxId = trxId;
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

    public String getUserPlace() {
        return userPlace;
    }

    public void setUserPlace(String userPlace) {
        this.userPlace = userPlace;
    }

    public boolean isCouponFlg() {
        return couponFlg == 1;
    }

    public void setCouponFlg(boolean couponFlg) {
        this.couponFlg = couponFlg ? 1 : 0;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getCountMoney() {
        return countMoney;
    }

    public void setCountMoney(String countMoney) {
        this.countMoney = countMoney;
    }

    public String getFreezeMoney() {
        return freezeMoney;
    }

    public void setFreezeMoney(String freezeMoney) {
        this.freezeMoney = freezeMoney;
    }

    public String getEnAbleMoney() {
        return enAbleMoney;
    }

    public void setEnAbleMoney(String enAbleMoney) {
        this.enAbleMoney = enAbleMoney;
    }

    public String getSumProceeds() {
        return sumProceeds;
    }

    public void setSumProceeds(String sumProceeds) {
        this.sumProceeds = sumProceeds;
    }

    public String getNowProceeds() {
        return nowProceeds;
    }

    public void setNowProceeds(String nowProceeds) {
        this.nowProceeds = nowProceeds;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getRealTime() {
        return realTime;
    }

    public void setRealTime(String realTime) {
        this.realTime = realTime;
    }

    public float getXsbMoneys() {
        return xsbMoneys;
    }

    public void setXsbMoneys(float xsbMoneys) {
        this.xsbMoneys = xsbMoneys;
    }

    public float getYpmMoneys() {
        return ypmMoneys;
    }

    public void setYpmMoneys(float ypmMoneys) {
        this.ypmMoneys = ypmMoneys;
    }

    public float getLbdtMoneys() {
        return lbdtMoneys;
    }

    public void setLbdtMoneys(float lbdtMoneys) {
        this.lbdtMoneys = lbdtMoneys;
    }

    public float getLbkzMoneys() {
        return lbkzMoneys;
    }

    public void setLbkzMoneys(float lbkzMoneys) {
        this.lbkzMoneys = lbkzMoneys;
    }

    public String getBankCardName() {
        return bankCardName;
    }

    public void setBankCardName(String bankCardName) {
        this.bankCardName = bankCardName;
    }

    public float getYesterdayIncome() {
        return yesterdayIncome;
    }

    public void setYesterdayIncome(float yesterdayIncome) {
        this.yesterdayIncome = yesterdayIncome;
    }

    public String getHandPassword() {
        return handPassword;
    }

    public void setHandPassword(String handPassword) {
        this.handPassword = handPassword;
    }

    public Date getGetScoreTime() {
        return new Date(getScoreTime);
    }

    public void setGetScoreTime(long getScoreTime) {
        this.getScoreTime = getScoreTime;
    }

    public float getExperienceIncome() {
        return experienceIncome;
    }

    public void setExperienceIncome(float experienceIncome) {
        this.experienceIncome = experienceIncome;
    }

    public int getUserScore() {
        return userScore;
    }

    public void setUserScore(int userScore) {
        this.userScore = userScore;
    }

    public String getBeInviteCode() {
        return beInviteCode;
    }

    public void setBeInviteCode(String beInviteCode) {
        this.beInviteCode = beInviteCode;
    }

    public int getIsOpenSignIn() {
        return isOpenSignIn;
    }

    public void setIsOpenSignIn(int isOpenSignIn) {
        this.isOpenSignIn = isOpenSignIn;
    }

    public boolean getIsBindCard() {
        return "1".equals(isBindCard);
    }

    public void setIsBindCard(String isBindCard) {
        this.isBindCard = isBindCard;
    }

    public boolean getIsActiveCgt() {
        return "1".equals(isActiveCgt);
    }

    public void setIsActiveCgt(String isActiveCgt) {
        this.isActiveCgt = isActiveCgt;
    }

    public float getWithdrawMoney() {
        return withdrawMoney;
    }

    public void setWithdrawMoney(float withdrawMoney) {
        this.withdrawMoney = withdrawMoney;
    }

    public static String genUserDatabaseTable(){
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE IF NOT EXISTS ");
        builder.append(USER_TABLE);
        builder.append("(").append("_id INTEGER primary key autoincrement,");
        builder.append(TABLE_USER_ID).append(" INTEGER,");
        builder.append(TABLE_USER_MOBILE).append(" TEXT,");
        builder.append(TABLE_USER_PASS_WORD).append(" TEXT,");
        builder.append(TABLE_IS_AUTONYM).append(" INTEGER,");
        builder.append(TABLE_USER_NAME).append(" TEXT,");
        builder.append(TABLE_IDCARD).append(" TEXT,");
        builder.append(TABLE_HEADPIC).append(" TEXT,");
        builder.append(TABLE_REMARK).append(" TEXT,");
        builder.append(TABLE_USERTYPE).append(" INTEGER,");
        builder.append(TABLE_CREATE_TIME).append(" TEXT,");
        builder.append(TABLE_IS_DELETE).append(" INTEGER,");
        builder.append(TABLE_COUNT_NAME).append(" TEXT,");
        builder.append(TABLE_USER_CUSTID).append(" TEXT,");
        builder.append(TABLE_TRXID).append(" TEXT,");
        builder.append(TABLE_RESP_CODE).append(" TEXT,");
        builder.append(TABLE_RESP_DESC).append(" TEXT,");
        builder.append(TABLE_USER_PLACE).append(" TEXT,");
        builder.append(TABLE_COUPON_FLG).append(" INTEGER,");
        builder.append(TABLE_USER_NO).append(" TEXT,");
        builder.append(TABLE_COUNT_MONEY).append(" TEXT,");
        builder.append(TABLE_FREEZE_MONEY).append(" TEXT,");
        builder.append(TABLE_ENABLE_MONEY).append(" TEXT,");
        builder.append(TABLE_SUM_PROCEEDS).append(" TEXT,");
        builder.append(TABLE_NOW_PROCEEDS).append(" TEXT,");
        builder.append(TABLE_REAL_TIME).append(" TEXT,");
        builder.append(TABLE_INVITE_CODE).append(" TEXT,");
        builder.append(TABLE_BANK_CARD).append(" TEXT,");
        builder.append(TABLE_BANK_CARD_NAME).append(" TEXT,");
        builder.append(TABLE_TOKEN).append(" TEXT,");
        builder.append(TABLE_GESTURE).append(" TEXT,");
        builder.append(TABLE_YESTERDAY_INCOME).append(" FLOAT,");
        builder.append(TABLE_BEINVITE_CODE).append(" TEXT,");
        builder.append(TABLE_USER_SCORE).append(" INTEGER,");
        builder.append(TABLE_EXP_INCOME).append(" FLOAT,");
        builder.append(TABLE_SCORE_TIME).append(" LONG,");
        builder.append(TABLE_IS_BIND_CARD).append(" TEXT,");
        builder.append(TABLE_IS_ACTIVE_CGT).append(" TEXT,");
        builder.append(TABLE_HUI_FU_MONEY).append(" FLOAT,");
        builder.append(TABLE_ISOPENSIGNIN).append(" INTEGER);");

        return builder.toString();
    }

    public static String updateDB1To2(){
        return "ALTER TABLE " + USER_TABLE + " ADD " + TABLE_GESTURE + " TEXT;";
    }

    public static String updateDB2To3dot1(){
        return "ALTER TABLE " + USER_TABLE + " ADD " + TABLE_BEINVITE_CODE + " TEXT;";
    }
    public static String updateDB2To3dot2(){
        return "ALTER TABLE " + USER_TABLE + " ADD " + TABLE_USER_SCORE + " INTEGER;";
    }
    public static String updateDB2To3dot3(){
        return "ALTER TABLE " + USER_TABLE + " ADD " + TABLE_EXP_INCOME + " FLOAT;";
    }

    public static String updateDB2To3dot4(){
        return "ALTER TABLE " + USER_TABLE + " ADD " + TABLE_SCORE_TIME + " LONG;";
    }

    public static String updateDB3To4Dot1(){
        return "ALTER TABLE " + USER_TABLE + " ADD " + TABLE_ISOPENSIGNIN + " INTEGER;";
    }

    public static String updateDB3To4Dot2(){
        return "ALTER TABLE " + USER_TABLE + " ADD " + TABLE_YESTERDAY_INCOME + " FLOAT;";
    }

    public static String updateDB4To5Dot1(){
        return "ALTER TABLE " + USER_TABLE + " ADD " + TABLE_IS_BIND_CARD + " TEXT;";
    }

    public static String updateDB4To5Dot2(){
        return "ALTER TABLE " + USER_TABLE + " ADD " + TABLE_IS_ACTIVE_CGT + " TEXT;";
    }

    public static String updateDB4To5Dot3(){
        return "ALTER TABLE " + USER_TABLE + " ADD " + TABLE_HUI_FU_MONEY + " FLOAT;";
    }




    public ContentValues toValues(){
        ContentValues values = new ContentValues();
        values.put(TABLE_USER_ID, userId);
        values.put(TABLE_USER_MOBILE, mobile);
        values.put(TABLE_USER_PASS_WORD, passWord);
        values.put(TABLE_IS_AUTONYM, isAutonym);
        values.put(TABLE_USER_NAME, userName);
        values.put(TABLE_IDCARD, idCard);
        values.put(TABLE_HEADPIC, headPic);
        values.put(TABLE_REMARK, remark);
        values.put(TABLE_USERTYPE, userType);
        values.put(TABLE_CREATE_TIME, createTime);
        values.put(TABLE_IS_DELETE, isDelete);
        values.put(TABLE_COUNT_NAME, countName);
        values.put(TABLE_USER_CUSTID, usrCustId);
        values.put(TABLE_TRXID, trxId);
        values.put(TABLE_RESP_CODE, respCode);
        values.put(TABLE_RESP_DESC, respDesc);
        values.put(TABLE_USER_PLACE, userPlace);
        values.put(TABLE_COUPON_FLG, couponFlg);
        values.put(TABLE_USER_NO, userNo);
        values.put(TABLE_COUNT_MONEY, countMoney);
        values.put(TABLE_ENABLE_MONEY, enAbleMoney);
        values.put(TABLE_FREEZE_MONEY, freezeMoney);
        values.put(TABLE_SUM_PROCEEDS, sumProceeds);
        values.put(TABLE_NOW_PROCEEDS, nowProceeds);
        values.put(TABLE_REAL_TIME, realTime);
        values.put(TABLE_INVITE_CODE, inviteCode);
        values.put(TABLE_BANK_CARD, bankCard);
        values.put(TABLE_BANK_CARD_NAME, bankCardName);
        values.put(TABLE_TOKEN, token);
        values.put(TABLE_YESTERDAY_INCOME, yesterdayIncome);
        values.put(TABLE_GESTURE, handPassword);
        values.put(TABLE_BEINVITE_CODE, beInviteCode);
        values.put(TABLE_USER_SCORE, userScore);
        values.put(TABLE_EXP_INCOME, experienceIncome);
        values.put(TABLE_SCORE_TIME, getScoreTime);
        values.put(TABLE_ISOPENSIGNIN, isOpenSignIn);
        values.put(TABLE_IS_BIND_CARD, isBindCard);
        values.put(TABLE_IS_ACTIVE_CGT, isActiveCgt);
        values.put(TABLE_HUI_FU_MONEY, withdrawMoney);
        return values;
    }

    public static User fromCursor(Cursor cursor){
        if (cursor == null){
            return null;
        }
        User user = new User();
        int columnIndex = cursor.getColumnIndexOrThrow(TABLE_USER_ID);
        if (columnIndex > -1){
            user.setUserId(cursor.getInt(columnIndex));
        }
        columnIndex = cursor.getColumnIndexOrThrow(TABLE_USER_MOBILE);
        if (columnIndex > -1){
            user.setMobile(cursor.getString(columnIndex));
        }
        columnIndex = cursor.getColumnIndexOrThrow(TABLE_USER_PASS_WORD);
        if (columnIndex > -1){
            user.setPassWord(cursor.getString(columnIndex));
        }
        columnIndex = cursor.getColumnIndexOrThrow(TABLE_IS_AUTONYM);
        if (columnIndex > -1){
            user.setAutonym(cursor.getInt(columnIndex) == 1);
        }
        columnIndex = cursor.getColumnIndexOrThrow(TABLE_USER_NAME);
        if (columnIndex > -1){
            user.setUserName(cursor.getString(columnIndex));
        }
        columnIndex = cursor.getColumnIndexOrThrow(TABLE_IDCARD);
        if (columnIndex > -1){
            user.setIdCard(cursor.getString(columnIndex));
        }
        columnIndex = cursor.getColumnIndexOrThrow(TABLE_HEADPIC);
        if (columnIndex > -1){
            user.setHeadPic(cursor.getString(columnIndex));
        }
        columnIndex = cursor.getColumnIndexOrThrow(TABLE_REMARK);
        if (columnIndex > -1){
            user.setRemark(cursor.getString(columnIndex));
        }
        columnIndex = cursor.getColumnIndexOrThrow(TABLE_USERTYPE);
        if (columnIndex > -1){
            user.setUserType(cursor.getInt(columnIndex));
        }
        columnIndex = cursor.getColumnIndexOrThrow(TABLE_CREATE_TIME);
        if (columnIndex > -1){
            user.setCreateTime(cursor.getString(columnIndex));
        }
        columnIndex = cursor.getColumnIndexOrThrow(TABLE_IS_DELETE);
        if (columnIndex > -1){
            user.setDelete(cursor.getInt(columnIndex) == 1);
        }
        columnIndex = cursor.getColumnIndexOrThrow(TABLE_COUNT_NAME);
        if (columnIndex > -1){
            user.setCountName(cursor.getString(columnIndex));
        }
        columnIndex = cursor.getColumnIndexOrThrow(TABLE_USER_CUSTID);
        if (columnIndex > -1){
            user.setUsrCustId(cursor.getString(columnIndex));
        }
        columnIndex = cursor.getColumnIndexOrThrow(TABLE_TRXID);
        if (columnIndex > -1){
            user.setTrxId(cursor.getString(columnIndex));
        }
        columnIndex = cursor.getColumnIndexOrThrow(TABLE_RESP_CODE);
        if (columnIndex > -1){
            user.setRespCode(cursor.getString(columnIndex));
        }
        columnIndex = cursor.getColumnIndexOrThrow(TABLE_RESP_DESC);
        if (columnIndex > -1){
            user.setRespDesc(cursor.getString(columnIndex));
        }
        columnIndex = cursor.getColumnIndexOrThrow(TABLE_USER_PLACE);
        if (columnIndex > -1){
            user.setUserPlace(cursor.getString(columnIndex));
        }
        columnIndex = cursor.getColumnIndexOrThrow(TABLE_COUPON_FLG);
        if (columnIndex > -1){
            user.setCouponFlg(cursor.getInt(columnIndex) == 1);
        }
        columnIndex = cursor.getColumnIndexOrThrow(TABLE_USER_NO);
        if (columnIndex > -1){
            user.setUserNo(cursor.getString(columnIndex));
        }
        columnIndex = cursor.getColumnIndexOrThrow(TABLE_COUNT_MONEY);
        if (columnIndex > -1){
            user.setCountMoney(cursor.getString(columnIndex));
        }
        columnIndex = cursor.getColumnIndexOrThrow(TABLE_FREEZE_MONEY);
        if (columnIndex > -1){
            user.setFreezeMoney(cursor.getString(columnIndex));
        }
        columnIndex = cursor.getColumnIndexOrThrow(TABLE_ENABLE_MONEY);
        if (columnIndex > -1){
            user.setEnAbleMoney(cursor.getString(columnIndex));
        }
        columnIndex = cursor.getColumnIndexOrThrow(TABLE_SUM_PROCEEDS);
        if (columnIndex > -1){
            user.setSumProceeds(cursor.getString(columnIndex));
        }
        columnIndex = cursor.getColumnIndexOrThrow(TABLE_NOW_PROCEEDS);
        if (columnIndex > -1){
            user.setNowProceeds(cursor.getString(columnIndex));
        }
        columnIndex = cursor.getColumnIndexOrThrow(TABLE_REAL_TIME);
        if (columnIndex > -1){
            user.setRealTime(cursor.getString(columnIndex));
        }
        columnIndex = cursor.getColumnIndexOrThrow(TABLE_INVITE_CODE);
        if (columnIndex > -1){
            user.setInviteCode(cursor.getString(columnIndex));
        }
        columnIndex = cursor.getColumnIndexOrThrow(TABLE_BANK_CARD);
        if (columnIndex > -1){
            user.setBankCard(cursor.getString(columnIndex));
        }
        columnIndex = cursor.getColumnIndexOrThrow(TABLE_BANK_CARD_NAME);
        if (columnIndex > -1){
            user.setBankCardName(cursor.getString(columnIndex));
        }
        columnIndex = cursor.getColumnIndexOrThrow(TABLE_TOKEN);
        if (columnIndex > -1){
            user.setToken(cursor.getString(columnIndex));
        }
        columnIndex = cursor.getColumnIndexOrThrow(TABLE_GESTURE);
        if (columnIndex > -1){
            user.setHandPassword(cursor.getString(columnIndex));
        }
        columnIndex = cursor.getColumnIndexOrThrow(TABLE_BEINVITE_CODE);
        if (columnIndex > -1){
            user.setBeInviteCode(cursor.getString(columnIndex));
        }
        columnIndex = cursor.getColumnIndexOrThrow(TABLE_USER_SCORE);
        if (columnIndex > -1){
            user.setUserScore(cursor.getInt(columnIndex));
        }
        columnIndex = cursor.getColumnIndexOrThrow(TABLE_EXP_INCOME);
        if (columnIndex > -1){
            user.setExperienceIncome(cursor.getFloat(columnIndex));
        }
        columnIndex = cursor.getColumnIndexOrThrow(TABLE_SCORE_TIME);
        if (columnIndex > -1){
            user.setGetScoreTime(cursor.getLong(columnIndex));
        }
        columnIndex = cursor.getColumnIndexOrThrow(TABLE_ISOPENSIGNIN);
        if (columnIndex > -1){
            user.setIsOpenSignIn(cursor.getInt(columnIndex));
        }
        columnIndex = cursor.getColumnIndexOrThrow(TABLE_YESTERDAY_INCOME);
        if (columnIndex > -1){
            user.setYesterdayIncome(cursor.getFloat(columnIndex));
        }
        columnIndex = cursor.getColumnIndexOrThrow(TABLE_IS_BIND_CARD);
        if (columnIndex > -1){
            user.setIsBindCard(cursor.getString(columnIndex));
        }
        columnIndex = cursor.getColumnIndexOrThrow(TABLE_IS_ACTIVE_CGT);
        if (columnIndex > -1){
            user.setIsActiveCgt(cursor.getString(columnIndex));
        }
        columnIndex = cursor.getColumnIndexOrThrow(TABLE_HUI_FU_MONEY);
        if (columnIndex > -1){
            user.setWithdrawMoney(cursor.getFloat(columnIndex));
        }
        return user;
    }
}
