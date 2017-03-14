package com.baluobo.common.net;


import com.baluobo.app.model.BaseModel;
import com.baluobo.find.model.FindNews;
import com.baluobo.find.model.RedTicket;
import com.baluobo.find.model.SignRecord;
import com.baluobo.home.model.Banner;
import com.baluobo.home.model.Declaration;
import com.baluobo.home.model.InviteMode;
import com.baluobo.home.model.VersionInfo;
import com.baluobo.product.bean.ExpGood;
import com.baluobo.product.bean.HomeProduct;
import com.baluobo.product.bean.Product;
import com.baluobo.product.bean.ProductInfo;
import com.baluobo.user.model.BankCardInfo;
import com.baluobo.user.model.InvestOrder;
import com.baluobo.user.model.KuaiZhuan;
import com.baluobo.user.model.Message;
import com.baluobo.user.model.Order;
import com.baluobo.user.model.RedPacket;
import com.baluobo.user.model.RedPacketRecord;
import com.baluobo.user.model.TradingRecord;
import com.baluobo.user.model.User;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * desc: App所有的网络请求都在这里
 * Created by:chenliliang
 * Created on:16/5/6.
 */
public interface APIService {
    /**
     *  用户登录
     * @param phone 手机号
     * @param psd 加密后的密码
     */
    @FormUrlEncoded()
    @POST(APIClient.userLogin)
    Call<BaseModel<User>> getLoginData(@Field("mobile")String phone, @Field("passWord")String psd);

    /**
     * 发送手机验证码
     * @param mobile 手机号
     */
    @FormUrlEncoded()
    @POST(APIClient.sendPhoneCode)
    Call<BaseModel> sendCheckCode(@Field("mobile") String mobile);

    /**
     * 用户注册
     * @param mobile 手机号
     * @param passWord 密码
     * @param validCode 验证码
     */
    @FormUrlEncoded()
    @POST(APIClient.userRegister)
    Call<BaseModel<User>> getRegisterData(@Field("mobile") String mobile, @Field("passWord") String passWord, @Field("validCode") String validCode, @Field("beInviteCode") String inviteCode, @Field("deviceType")String deviceType);

    /**
     * 验证 验证码是否正确
     * @param mobile 手机号
     * @param validCode 验证码
     */
    @FormUrlEncoded()
    @POST(APIClient.validPhoneCode)
    Call<BaseModel> validCheckCode(@Field("mobile") String mobile, @Field("validCode") String validCode);

    /**
     * 用户找回密码
     * @param mobile 手机号
     * @param validCode 验证码
     * @param newPassword 新密码
     */
    @FormUrlEncoded()
    @POST(APIClient.getPsd)
    Call<BaseModel> resetPsd(@Field("mobile") String mobile, @Field("validCode") String validCode, @Field("newPassword")String newPassword);

    /**
     * 消息中心
     * @param page 页码
     * @param userId 用户id
     * @param token token
     */
    @FormUrlEncoded()
    @POST(APIClient.messageCenter)
    Call<BaseModel<List<Message>>> getMessageData(@Field("page") int page, @Field("userId")int userId, @Field("token")String token);

    /**
     * 一键消息已读
     * @param uId 用户id
     * @param token 用户token
     * @return
     */
    @FormUrlEncoded()
    @POST(APIClient.readAllMessage)
    Call<BaseModel> seenAllMessage(@Field("uId") int uId, @Field("token")String token);

    /**
     * 意见反馈
     * @param descContent descContent
     * @param uId uId
     * @param contactWay contactWay
     */
    @FormUrlEncoded()
    @POST(APIClient.userFeedback)
    Call<BaseModel> feedback(@Field("descContent")String descContent,  @Field("contactWay")String contactWay, @Field("uId")int uId);
    /**
     * 修改登录密码
     * @param mobile 手机号
     * @param validCode 验证码
     * @param newPassword 新密码
     * @return
     */
    @FormUrlEncoded()
    @POST(APIClient.resetLoginPsd)
    Call<BaseModel> changeLoginPsd(@Field("mobile") String mobile, @Field("validCode")String validCode, @Field("newPassword") String newPassword, @Field("token")String token);

    /**
     * 修改手机号第一步
     * @param passWord  passWord
     * @param mobile mobile
     * @param validCode validCode
     * @param userId userId
     * @param token token
     */
    @FormUrlEncoded()
    @POST(APIClient.resetPhoneStep1)
    Call<BaseModel> changeMobileStepOne(@Field("passWord")String passWord, @Field("mobile")String mobile, @Field("validCode")String validCode, @Field("userId")int userId, @Field("token")String token);

    /**
     * 修改手机号第二步
     * @param mobile mobile
     * @param validCode validCode
     * @param userId userId
     * @param token token
     */
    @FormUrlEncoded()
    @POST(APIClient.resetPhone)
    Call<BaseModel> changeMobileStepTwo(@Field("mobile")String mobile, @Field("validCode")String validCode, @Field("userId")int userId, @Field("token")String token);

    /**
     * 查询用户的投资定单，按订单状态  投资中、还款中、已还款 分类
     * @param buyflg  订单状态，3表示投资中、2表示还款中、1表示已还款
     * @param page page
     * @param userId userId
     * @param token token
     */
    @FormUrlEncoded()
    @POST(APIClient.mineInvest)
    Call<BaseModel<InvestOrder>> myInvestData(@Field("buyflg") int buyflg, @Field("page")int page, @Field("userId") int userId, @Field("token")String token);

    /**
     * app首页上BANNER图
     */
    @FormUrlEncoded()
    @POST(APIClient.bannerData)
    Call<BaseModel<ArrayList<Banner>>> getHomeBanners(@Field("activityType")int activityType);

    /**
     * app首页推荐商品
     * @return
     */
    @POST(APIClient.homeRecommendData)
    Call<BaseModel<ArrayList<Product>>> getRecommendProducts();

    /**
     * app首页推荐商品
     * @return
     */
    @POST(APIClient.getHomeProduct)
    Call<BaseModel<HomeProduct>> getHomeProduct();


    /**
     * 按产品类别查询产品列表
     * @param gcId gcid
     * @param page page
     * @return
     */
    @FormUrlEncoded()
    @POST(APIClient.queryProductList)
    Call<BaseModel<ArrayList<Product>>> getProductList(@Field("gcId")int gcId, @Field("page")int page);

    /**
     * 查询我的萝卜快赚投资数据
     * @param userId userId
     * @param token token
     */
    @FormUrlEncoded()
    @POST(APIClient.getKuaiZhuanData)
    Call<BaseModel<List<KuaiZhuan>>> getKuaiZhuanData(@Field("userId")int userId, @Field("token")String token);

    /**
     * 查询交易记录
     * @param userId
     * @param token
     * @param page
     * @return
     */
    @FormUrlEncoded()
    @POST(APIClient.getTradingRecord)
    Call<BaseModel<List<TradingRecord>>> getTradingRecord(@Field("userId")int userId, @Field("token")String token, @Field("page")int page);

    /**
     * 得到首页的公告列表
     * @return
     */
    @POST(APIClient.getDeclarations)
    Call<BaseModel<ArrayList<Declaration>>> getDeclarationData();

    /**
     * 得到可赎回写单列表
     * @param userId
     * @param token
     * @param page
     * @return
     */
    @FormUrlEncoded()
    @POST(APIClient.getKuaiZhuanList)
    Call<BaseModel<ArrayList<Order>>> getRedeemList(@Field("userId")int userId, @Field("token")String token, @Field("page")int page);

    /**
     * 赎回定单
     * @param userId
     * @param token
     * @param orderNo
     * @return
     */
    @FormUrlEncoded()
    @POST(APIClient.returnKuaiZhuan)
    Call<BaseModel> redeemBackOrder(@Field("userId")int userId, @Field("token")String token, @Field("ordId")String orderNo, @Field("deviceType")String deviceType);

    /**
     * 产品信息列表
     * @param goodId goodId
     */
    @FormUrlEncoded()
    @POST(APIClient.getProductInfo)
    Call<BaseModel<ProductInfo>> getProductInfo(@Field("goodId")int goodId);

    /**
     * 购买萝卜快赚界面
     * @return
     */
    @POST(APIClient.getKuaiZhuanInfo)
    Call<BaseModel<Product>> getKuaiZhuanData();

    /**
     * 购买时生成订单
     * @param uId
     * @param token
     * @param goodId
     * @param money
     * @return
     */
    @FormUrlEncoded()
    @POST(APIClient.getProductOrder)
    Call<BaseModel<Order>> getProductOrder(@Field("uId")int uId, @Field("token")String token, @Field("goodId")int goodId, @Field("money")int money, @Field("couponIds")String couponIds, @Field("deviceType")String deviceType);

    /**
     * 获取更多产品订单
     * @param goodId
     * @param page
     * @return
     */
    @FormUrlEncoded()
    @POST(APIClient.getMoreProductOrder)
    Call<BaseModel<ArrayList<Order>>> getMoreProductOrder(@Field("goodId")int goodId, @Field("page")int page);

    /**
     * 更新用户信息
     * @param userId userId
     * @param token token
     */
    @FormUrlEncoded()
    @POST(APIClient.updateUserInfo)
    Call<BaseModel<User>> updateUserInfo(@Field("userId")int userId, @Field("token")String token);

    /**
     * 获取用户资产详情
     * @param userId
     * @param token
     * @return
     */
    @FormUrlEncoded()
    @POST(APIClient.userTotalAssets)
    Call<BaseModel<User>> getTotalAssets(@Field("userId")int userId, @Field("token")String token);

    /**
     * 查询银行卡信息
     * @param userId
     * @param token
     * @return
     */
    @FormUrlEncoded()
    @POST(APIClient.getBankCardInfo)
    Call<BaseModel<BankCardInfo>> getBankInfo(@Field("userId")int userId, @Field("token")String token);

    /**
     * 上传用户手势密码
     * @param userId
     * @param token
     * @param handPassword
     * @return
     */
    @FormUrlEncoded()
    @POST(APIClient.updateGesturePsd)
    Call<BaseModel> updateGesture(@Field("userId")int userId, @Field("token")String token, @Field("handPassword")String handPassword);

    /**
     * 上传DeviceToken
     * @param userId userId
     * @param token token
     * @param deviceToken deviceToken
     * @return null
     */
    @FormUrlEncoded()
    @POST(APIClient.updateDeviceToken)
    Call<BaseModel> uploadDeviceToken(@Field("userId")int userId, @Field("token")String token, @Field("deviceToken")String deviceToken);

    /**
     * 获取是否有未读消息
     * @param userId userId
     * @param token token
     * @return null
     */
    @FormUrlEncoded()
    @POST(APIClient.getUnreadMessage)
    Call<BaseModel> getUnreadMessage(@Field("userId")int userId, @Field("token")String token);

    /**
     * 获取用户红包消息
     * @param uId uId
     * @param couponType couponType
     * @param page page
     * @return null
     */
    @FormUrlEncoded()
    @POST(APIClient.getRedPacket)
    Call<BaseModel<ArrayList<RedPacket>>> getRedPacketData(@Field("uId")int uId, @Field("couponType")int couponType, @Field("page")int page, @Field("token")String token);

    /**
     * 红包使用记录
     * @param uId uId
     * @param couponReType couponReType
     * @param page page
     * @return null
     */
    @FormUrlEncoded()
    @POST(APIClient.queryRedPacketRecord)
    Call<BaseModel<ArrayList<RedPacketRecord>>> getRedPacketRecordData(@Field("uId")int uId, @Field("couponReType")int couponReType, @Field("page")int page, @Field("token")String token);

    /**
     * 用户邀请列表
     * @param invitesUserId invitesUserId
     * @return null
     */
    @FormUrlEncoded()
    @POST(APIClient.getInviteData)
    Call<BaseModel<ArrayList<InviteMode>>> getInviteData(@Field("invitesUserId")int invitesUserId, @Field("token")String token);

    /**
     * 获取体验金信息
     * @return null
     */
    @FormUrlEncoded()
    @POST(APIClient.getExpGoodInfo)
    Call<BaseModel<ExpGood>> getExpGoodInfo(@Field("userId")int userId, @Field("token")String token);

    /**
     * 购买体验金
     * @return null
     */
    @FormUrlEncoded()
    @POST(APIClient.buyExpGood)
    Call<BaseModel> buyExpGood(@Field("uId")int uId, @Field("token")String token);

    /**
     * 用户签到
     * rows：user对象
     success：true/false 成功/失败
     message：签到所得积分数(因为后台message为String类型，app端需要将message转换为int类型)
     * @param userId userId
     * @return null
     */
    @FormUrlEncoded()
    @POST(APIClient.signIn)
    Call<BaseModel<User>> signIn(@Field("userId")int userId, @Field("token")String token);

    /**
     * vip红包列表
     * @param userId
     * @return
     */
    @FormUrlEncoded()
    @POST(APIClient.showTakeRedPacket)
    Call<BaseModel<ArrayList<RedTicket>>> getVipRedTicketInfo(@Field("userId")int userId, @Field("token")String token);

    /**
     * vip用户领取红包
     * @param userId userId
     * @param vipId vipId
     * @return
     */
    @FormUrlEncoded()
    @POST(APIClient.getVipRedTicket)
    Call<BaseModel> getRedTicket(@Field("userId")int userId, @Field("vipId")int vipId, @Field("token")String token);

    /**
     * 发现新闻列表
     * @param contentType contentType
     * @param page page
     * @return
     */
    @FormUrlEncoded()
    @POST(APIClient.getFindNews)
    Call<BaseModel<ArrayList<FindNews>>> getFindNews(@Field("contentType")int contentType, @Field("page")int page);

    /**
     * 购买时所有可用的红包列表
     * @param userId userId
     * @return
     */
    @FormUrlEncoded()
    @POST(APIClient.getUsableRedPacket)
    Call<BaseModel<ArrayList<RedPacket>>> getUsableRedPacket(@Field("userId")int userId, @Field("gcId")int gcId, @Field("token")String token);

    /**
     * 领取刮刮乐红包
     * @param uId
     * @param prizeId
     * @return
     */
    @FormUrlEncoded()
    @POST(APIClient.takeGuaRedTicket)
    Call<BaseModel> takeGuaTicket(@Field("uId")int uId, @Field("prizeId")int prizeId, @Field("token")String token);

    /**
     * 当请求的接口返回的token与现在保存的token不一至时，调用此接口，让服务器刷新token
     * @param userId
     * @param token
     * @return
     */
    @FormUrlEncoded()
    @POST(APIClient.refreshToken)
    Call<BaseModel> refreshToken(@Field("userId")int userId, @Field("token")String token);

    /**
     * 查看签到记录
     * @param userId userId
     * @param token token
     * @param date date
     * @return
     */
    @FormUrlEncoded()
    @POST(APIClient.signRecord)
    Call<BaseModel<ArrayList<SignRecord>>> getSignRecord(@Field("userId")int userId, @Field("token")String token, @Field("date")String date);

    /**
     * 自动签到通知
     * @param userId
     * @param token
     * @param isOpenSignIn
     * @return
     */
    @FormUrlEncoded()
    @POST(APIClient.noticeSign)
    Call<BaseModel> signInNotice(@Field("userId")int userId, @Field("token")String token, @Field("isOpenSignIn")int isOpenSignIn);

    /**
     * 查询是否有可兑换的萝卜币
     * @param uId
     * @param token
     * @return
     */
    @FormUrlEncoded()
    @POST(APIClient.queryTurnips)
    Call<BaseModel<Integer>> queryTurnips(@Field("uId")int uId, @Field("token")String token);

    /**
     * 将萝卜币兑换成积分
     * @param uId
     * @param token
     * @return
     */
    @FormUrlEncoded()
    @POST(APIClient.goConvertScore)
    Call<BaseModel> goConvertScore(@Field("uId")int uId, @Field("token")String token);

    /**
     * 检查服务器上是否有新版本
     * @return
     */
    @POST(APIClient.checkVersion)
    Call<BaseModel<VersionInfo>> checkVersion();

    /**
     * 下载app包
     * @return
     */
    @GET
    Call<ResponseBody> downloadApp(@Url String fileURL);

    /**
     * 查询App加载页图片
     * @return
     */
    @POST(APIClient.checkAppLoadPic)
    Call<BaseModel<Banner>> checkLoadPic();

    /**
     * 下载App加载页图片
     * @param picURL 图片地址
     * @return
     */
    @GET
    Call<ResponseBody> downloadLoadPic(@Url String picURL);

    /**
     * 快速登录
     * @param mobile
     * @param validCode
     * @return
     */
    @FormUrlEncoded()
    @POST(APIClient.fastLogin)
    Call<BaseModel<User>> fastLogin(@Field("mobile")String mobile, @Field("validCode")String validCode);

    /**
     * 快速注册
     * @param mobile
     * @param validCode
     * @param beInviteCode
     * @param deviceType
     * @return
     */
    @FormUrlEncoded()
    @POST(APIClient.fastRegister)
    Call<BaseModel<User>> fastRegister(@Field("mobile")String mobile, @Field("validCode")String validCode, @Field("beInviteCode")String beInviteCode, @Field("deviceType")String deviceType);

}
