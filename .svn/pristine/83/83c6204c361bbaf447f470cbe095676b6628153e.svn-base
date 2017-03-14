package com.baluobo.common.net;


import com.baluobo.common.config.AppConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * desc: 网络框架采用retrofit
 * Created by:chenliliang
 * Created on:16/5/6.
 */
public class APIClient {
    protected static final String HTTP = "http://";
    private static final String HTTPS = "https://";

    protected static final String DEBUG_HOST_BASE = "test.luobopj.com:8080/";//baluobo-zxtc.imwork.net:59617/12   test.luobopj.com:8080/115
    protected static final String RELEASE_HOST_BASE = "www.luobopj.com/";//161
    protected static final String HUIFU = "huifu/";
    private static final String MOBILE = "mobile/";
    protected static final String CGT = "cgt/";

    private static final String debugBaseHost = HTTP + DEBUG_HOST_BASE;
    private static final String releaseBaseHost = HTTP + RELEASE_HOST_BASE;

    private static final String debugURL = debugBaseHost + MOBILE;
    private static final String releaseURL = releaseBaseHost + MOBILE;

    //使用汇付天下接口，用于购买，开户等进入H5的界面
    private static final String debugHuifuURL = HTTP + DEBUG_HOST_BASE + HUIFU;
    private static final String releaseHuifuURL = HTTP + RELEASE_HOST_BASE + HUIFU;

    //使用存管通接口，用于购买，开户等进入H5的界面
    private static final String debugCGTURL = HTTP + DEBUG_HOST_BASE + CGT;
    private static final String releaseCGTURL = HTTP + RELEASE_HOST_BASE + CGT;

    private static final String debugDeclaration = HTTP + DEBUG_HOST_BASE + "notice/";
    private static final String releaseDeclaration = HTTP + RELEASE_HOST_BASE + "notice/";

    //获取用户红包消息
    public static final String getRedPacket = "coupon/queryCouponPage.do";
    //用户邀请列表
    public static final String getInviteData = "inviteRecord/queryInviteReTotal.do";
    //体验金信息
    public static final String getExpGoodInfo = "experienceGood/queryExperienceGood.do";
    //购买体验金
    public static final String buyExpGood = "experienceOrder/save.do";
    //红包使用记录
    public static final String queryRedPacketRecord = "couponRecord/queryCouponRecordPage.do";
    //用户签到
    public static final String signIn = "user/signIn.do";
    //特权红包可领取列表
    public static final String showTakeRedPacket = "vipRedPack/queryAllVipRedPack.do";
    //领取红包
    public static final String getVipRedTicket = "vipRedPack/getVipRedPack.do";
    //发现新闻
    public static final String getFindNews = "news/queryNewsByTypePage.do";
    //购买时获取所有可用红包
    public static final String getUsableRedPacket = "coupon/queryCouponsByUserId.do";
    //领取刮刮乐红包
    public static final String takeGuaRedTicket = "coupon/getScratch.do";
    //刷新新的token
    public static final String refreshToken = "user/judgeToken.do";
    //用户登录
    public static final String userLogin = "user/login.do";
    //发送手机验证码
    public static final String sendPhoneCode = "user/queryMobileVaild.do";
    //用户注册
    public static final String userRegister = "user/regist.do";
    //验证收到的手机验证码是否正确
    public static final String validPhoneCode = "user/sendValidCodeByMobile.do";
    //找回密码接口
    public static final String getPsd = "user/sendPassByMobile.do";
    //消息中心
    public static final String messageCenter = "mess/queryPage.do";
    //一键读取所有消息
    public static final String readAllMessage = "mess/seenAllMess.do";
    //用户反馈
    public static final String userFeedback = "feedBack/saveFeedBack.do";
    //修改登录密码
    public static final String resetLoginPsd = "user/sendPassByMobile.do";
    //修改手机号第一步
    public static final String resetPhoneStep1 = "user/vaildUserForUpdateMobile1.do";
    //修改手机号
    public static final String resetPhone = "user/updateUserMobile.do";
    //我的理财
    public static final String mineInvest = "buyOrder/queryPageForMyOrders.do";
    //App banner数据
    public static final String bannerData = "activity/queryImgForApp.do";
    //首页推荐产品列表
    public static final String homeRecommendData = "good/queryPageForAppIndex.do";
    //首页推荐产品2.0
    public static final String getHomeProduct = "good/queryPageForAppIndexNew2.do";
    //按种类查寻产品列表
    public static final String queryProductList = "good/queryPage.do";
    //得到萝卜快赚的数据
    public static final String getKuaiZhuanData = "historyIncome/queryByPage.do";
    //得到交易记录
    public static final String getTradingRecord = "transactionRecord/queryRecordByPage.do";
    //得到首页公告列表
    public static final String getDeclarations = "news/queryNewsByPage.do";
    //得到所有可赎回的萝卜快赚列表
    public static final String getKuaiZhuanList = "buyOrder/queryPageForCurrent.do";
    //赎回萝卜快慊
    public static final String returnKuaiZhuan = "doReturnMoney2.do";
//    public static final String returnKuaiZhuan = "doCgtRefund.do";
    //获取产品具体信息
    public static final String getProductInfo = "good/queryById.do";
    //购买萝卜快赚信息界面
    public static final String getKuaiZhuanInfo = "good/queryCurrentGoodDetail.do";
    //购买产品时，生成定单
    public static final String getProductOrder = "buyOrder/saveNotBenXi.do";
    //在产品信息界面，加载更多购买此产品的订单信息
    public static final String getMoreProductOrder = "buyOrder/queryByOrderRecords.do";
    //更新用户信息
    public static final String updateUserInfo = "user/queryById.do";
    //用户总资产信息
    public static final String userTotalAssets = "user/queryCountMoneyForProduct.do";
    //用户银行卡信息
    public static final String getBankCardInfo = "bankCard/queryByUser.do";
    //上传用户手势密码
    public static final String updateGesturePsd = "user/saveOrUpdateHandPassword.do";
    //上传用户的deviceToken用于消息推送
    public static final String updateDeviceToken = "user/saveYmDeviceToken.do";
    //用户是否有未读消息
    public static final String getUnreadMessage = "mess/queryMessByNotRead.do";
    //在汇付开户
    public static final String huifuRegister = "doRegistHuiFu2.do";
    //汇付银行开户
    public static final String huifuBank = "doBankCardHuiFu2.do";
    //汇付冲值
    public static final String huifuRecharge = "doRecharge2.do";
    //汇付赎回
    public static final String huifuWithdraw = "doWithdraw2.do";
    //汇付付款
    public static final String huifuPay = "doPay2.do";
    //查询自己的签到记录
    public static final String signRecord = "signInRecord/queryPageByDate.do";
    //每天自动通知签到
    public static final String noticeSign = "user/isOpenSignIn.do";
    //查询是否有萝卜币
    public static final String queryTurnips = "turnips/queryTurnipsSum.do";
    //将萝卜币兑换成积分
    public static final String goConvertScore = "turnips/goConvertScore.do";
    //检查官网是否有新版本
    public static final String checkVersion = "android/getVer.do";
    //下载最新apk包
    public static final String downloadApk = "android/downloadApk.do?fileName=";
    //查看App加载页图片
    public static final String checkAppLoadPic = "app/queryStartImage.do";
    //下载加载页图片
    public static final String downloadLoadPic = "app/downloadStartImage.do?fileName=";
    //用验证码快速登录
    public static final String fastLogin = "user/fastLogin.do";
    //用验证码快速注册
    public static final String fastRegister = "user/fastRegist.do";
    //存管通注册界面
    public static final String cgtRegister = "doPersonalRegister.do";
    //存管通绑定银行卡
    public static final String cgtBoundCard = "doPersonalBindbankCard.do";
    //存管通充值
    public static final String cgtRecharge = "doRecharge.do";
    //存管通提现
    public static final String cgtWithdraw = "doWithdraw.do";
    //存管通解绑银行卡
    public static final String cgtUnBindBankCard = "doUnbindBankCardDirect.do";
    //存管通预外理投标
    public static final String cgtReTransaction = "doUserRreTransaction.do";

    private static APIService apiService;
    public static APIService getInstance(){
        if (apiService == null){
            synchronized (APIClient.class){
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(getURL())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                apiService = retrofit.create(APIService.class);
            }
        }
        return apiService;
    }

    private static String getURL(){
        return AppConfig.isDebugVersion ? debugURL : releaseURL;
    }

    public static String getHuifuURL(){
        return AppConfig.isDebugVersion ? debugHuifuURL : releaseHuifuURL;
    }

    public static String getCGTURL(){
        return AppConfig.isDebugVersion ? debugCGTURL : releaseCGTURL;
    }

    public static String getBannerHost(){
        return AppConfig.isDebugVersion ? debugBaseHost : releaseBaseHost;
    }
}
