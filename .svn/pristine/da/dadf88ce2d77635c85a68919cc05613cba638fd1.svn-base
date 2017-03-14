package com.baluobo.app.flux;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.baluobo.app.AppContext;
import com.baluobo.app.model.BaseModel;
import com.baluobo.common.config.AppConfig;
import com.baluobo.common.db.LuoBoDBM;
import com.baluobo.common.net.APIClient;
import com.baluobo.common.net.RedeemAPIClient;
import com.baluobo.common.tools.FileUtils;
import com.baluobo.common.tools.Util;
import com.baluobo.find.actions.SignInAction;
import com.baluobo.find.actions.SignInNoticeAction;
import com.baluobo.find.actions.SignRecordAction;
import com.baluobo.find.actions.VIPCenterAction;
import com.baluobo.find.model.RedTicket;
import com.baluobo.find.model.SignRecord;
import com.baluobo.home.actions.DotAction;
import com.baluobo.home.actions.InviteAction;
import com.baluobo.home.actions.LoadPicAction;
import com.baluobo.home.actions.VersionAction;
import com.baluobo.home.model.Banner;
import com.baluobo.home.model.InviteMode;
import com.baluobo.home.model.VersionInfo;
import com.baluobo.product.actions.ExpGoodAction;
import com.baluobo.product.actions.KuaiZhuanInfoAction;
import com.baluobo.product.actions.ProductBuyAction;
import com.baluobo.product.actions.ProductInfoAction;
import com.baluobo.product.actions.ProductListAction;
import com.baluobo.product.actions.ProductRecordAction;
import com.baluobo.product.actions.UsableCouponsAction;
import com.baluobo.product.bean.ExpGood;
import com.baluobo.product.bean.Product;
import com.baluobo.product.bean.ProductInfo;
import com.baluobo.user.actions.AccountInfoAction;
import com.baluobo.user.actions.BankCardAction;
import com.baluobo.user.actions.ChangeLoginPsdAction;
import com.baluobo.user.actions.ChangeMobileOneAction;
import com.baluobo.user.actions.ChangeMobileTowAction;
import com.baluobo.user.actions.FastRegisterAction;
import com.baluobo.user.actions.FeedbackAction;
import com.baluobo.user.actions.KuaiZhuanAction;
import com.baluobo.user.actions.LoginAction;
import com.baluobo.user.actions.MineAction;
import com.baluobo.user.actions.MsgAction;
import com.baluobo.user.actions.RedeemAction;
import com.baluobo.user.actions.RedeemListAction;
import com.baluobo.user.actions.RegisterAction;
import com.baluobo.user.actions.SecurityAction;
import com.baluobo.user.actions.TotalAssetsAction;
import com.baluobo.user.actions.TradingAction;
import com.baluobo.user.actions.UserInfoAction;
import com.baluobo.user.model.BankCardInfo;
import com.baluobo.user.model.KuaiZhuan;
import com.baluobo.user.model.Message;
import com.baluobo.user.model.Order;
import com.baluobo.user.model.RedPacket;
import com.baluobo.user.model.TradingRecord;
import com.baluobo.user.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/10.
 */
public class AppActionsCreator extends ActionsCreator {
    private static AppActionsCreator instance;
    private AppContext appContext;
    private AppActionsCreator(Dispatcher dispatcher, AppContext cnt) {
        super(dispatcher);
        this.appContext = cnt;
    }

    public static AppActionsCreator getInstance(Dispatcher dispatcher, AppContext cnt) {
        if (instance == null) {
            instance = new AppActionsCreator(dispatcher, cnt);
        }
        return instance;
    }

    /**
     * 登录请求
     * @param phone 输入的手机号
     * @param psd 输入的密码
     */
    public void userLogin(String phone, String psd) {
        dispatcher.dispatch(new LoginAction(LoginAction.ACTION_REQUEST_START));
        String psdMD5 = Util.MD5(psd);
        Log.i("chen", "psd:" + psdMD5);
        apiService.getLoginData(phone, psdMD5).enqueue(new Callback<BaseModel<User>>() {
            @Override
            public void onResponse(Call<BaseModel<User>> call, Response<BaseModel<User>> response) {
                dispatcher.dispatch(new LoginAction(LoginAction.ACTION_REQUEST_FINISH));
                BaseModel<User> baseModel = response.body();
                if (baseModel != null){
                    if (baseModel.isSuccess()){
                        dispatcher.dispatch(new LoginAction(LoginAction.ACTION_LOGIN_SUCCESS, baseModel));
                    }else {
                        String msg = baseModel.getMessage();
                        dispatcher.dispatch(new Action(LoginAction.ACTION_REQUEST_ERROR_MESSAGE, msg));
                    }

                }
            }

            @Override
            public void onFailure(Call<BaseModel<User>> call, Throwable t) {
                dispatcher.dispatch(new LoginAction(LoginAction.ACTION_REQUEST_FINISH));
                dispatcher.dispatch(new LoginAction(LoginAction.ACTION_REQUEST_FAIL));
            }
        });
    }

    /**
     * 发送手机验证码
     * @param phone 手机号
     */
    public void sendCheckCode(String phone){
        dispatcher.dispatch(new RegisterAction(RegisterAction.ACTION_REQUEST_START));
        apiService.sendCheckCode(phone).enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                dispatcher.dispatch(new RegisterAction(RegisterAction.ACTION_REQUEST_FINISH));
                BaseModel baseModel = response.body();
                if (baseModel.isSuccess()){
                    dispatcher.dispatch(new RegisterAction(RegisterAction.ACTION_REGISTER_SEND_CODE_START));
                }
                String msg = baseModel.getMessage();
                dispatcher.dispatch(new Action(RegisterAction.ACTION_REQUEST_ERROR_MESSAGE, msg));
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                dispatcher.dispatch(new RegisterAction(RegisterAction.ACTION_REQUEST_FINISH));
                dispatcher.dispatch(new RegisterAction(RegisterAction.ACTION_REQUEST_FAIL));
            }
        });
    }

    public void sendLoginCheckCode(String phone){
        dispatcher.dispatch(new LoginAction(LoginAction.LOGIN_SEND_CODE_ACTION_START));
        apiService.sendCheckCode(phone).enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                dispatcher.dispatch(new LoginAction(LoginAction.LOGIN_SEND_CODE_ACTION_FINISH));
                BaseModel baseModel = response.body();
                if (baseModel.isSuccess()){
                    dispatcher.dispatch(new LoginAction(LoginAction.LOGIN_SEND_CODE_ACTION_SUCCESS));
                }
                String msg = baseModel.getMessage();
                dispatcher.dispatch(new Action(LoginAction.LOGIN_SEND_CODE_ACTION_ERROR_MESSAGE, msg));
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                dispatcher.dispatch(new LoginAction(LoginAction.LOGIN_SEND_CODE_ACTION_FINISH));
                dispatcher.dispatch(new LoginAction(LoginAction.LOGIN_SEND_CODE_ACTION_FAIL));
            }
        });
    }


    public void changeMobileSendCheckCode(String phone){
        dispatcher.dispatch(new ChangeMobileTowAction(ChangeMobileTowAction.ACTION_REQUEST_START));
        apiService.sendCheckCode(phone).enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                dispatcher.dispatch(new ChangeMobileTowAction(ChangeMobileTowAction.ACTION_REQUEST_FINISH));
                BaseModel baseModel = response.body();
                if (baseModel.isSuccess()){
                    dispatcher.dispatch(new ChangeMobileTowAction(ChangeMobileTowAction.ACTION_REQUEST_SEND_CODE_SUCCESS));
                }
                String msg = baseModel.getMessage();
                dispatcher.dispatch(new Action(ChangeMobileTowAction.ACTION_REQUEST_ERROR_MESSAGE, msg));
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                dispatcher.dispatch(new ChangeMobileTowAction(ChangeMobileTowAction.ACTION_REQUEST_FINISH));
                dispatcher.dispatch(new ChangeMobileTowAction(ChangeMobileTowAction.ACTION_REQUEST_FAIL));
            }
        });
    }

    /**
     * 用户注册
     * @param phone 手机号
     * @param code 验证码
     * @param psd 密码
     */
    public void userRegister(String phone, String code, String psd, String inviteCode){
        psd = Util.MD5(psd);
        dispatcher.dispatch(new RegisterAction(RegisterAction.ACTION_REQUEST_START));
        apiService.getRegisterData(phone, psd, code, inviteCode, "android").enqueue(new Callback<BaseModel<User>>() {
            @Override
            public void onResponse(Call<BaseModel<User>> call, Response<BaseModel<User>> response) {
                dispatcher.dispatch(new RegisterAction(RegisterAction.ACTION_REQUEST_FINISH));
                BaseModel<User> baseModel = response.body();
                if (baseModel != null){
                    if (baseModel.isSuccess()){
                        dispatcher.dispatch(new RegisterAction(RegisterAction.ACTION_REGISTER_SUCCESS));
                    }
                    String msg = baseModel.getMessage();
                    dispatcher.dispatch(new Action(RegisterAction.ACTION_REQUEST_ERROR_MESSAGE, msg));
                }
            }

            @Override
            public void onFailure(Call<BaseModel<User>> call, Throwable t) {
                dispatcher.dispatch(new RegisterAction(RegisterAction.ACTION_REQUEST_FINISH));
                dispatcher.dispatch(new RegisterAction(RegisterAction.ACTION_REQUEST_FAIL));
            }
        });
    }

    public void submitFeedback(String feedbackString, String descContact,int uid){
        if (!invalidFeedbackInput(feedbackString)){
            return;
        }
        dispatcher.dispatch(new FeedbackAction(FeedbackAction.ACTION_REQUEST_START));
        apiService.feedback(feedbackString, descContact, uid).enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                dispatcher.dispatch(new FeedbackAction(FeedbackAction.ACTION_REQUEST_FINISH));
                BaseModel baseModel = response.body();
                if (baseModel != null){
                    String msg = baseModel.getMessage();
                    dispatcher.dispatch(new Action(FeedbackAction.ACTION_REQUEST_ERROR_MESSAGE, msg));
                    if (baseModel.isSuccess()){
                        dispatcher.dispatch(new FeedbackAction(FeedbackAction.ACTION_FEEDBACK_SUCCESS));
                    }else {
                        if (baseModel.getFlg() == AppConfig.INVALID_TOKEN){
                            dispatcher.dispatch(new FeedbackAction(FeedbackAction.ACTION_REQUEST_TOKEN_INVALID));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                dispatcher.dispatch(new FeedbackAction(FeedbackAction.ACTION_REQUEST_FINISH));
                dispatcher.dispatch(new FeedbackAction(FeedbackAction.ACTION_REQUEST_FAIL));

            }
        });
    }

    private boolean invalidFeedbackInput(String feedbackString){
        if (feedbackString == null || feedbackString.length() < 5){
            dispatcher.dispatch(new FeedbackAction(FeedbackAction.ACTION_FEEDBACK_INPUT_LESS_MIN));
            return false;
        }
        return true;
    }

    /**
     * 消息中心数据
     * @param page 页码
     * @param userId 用户id
     * @param token 用户token
     */
    public void getMessageData(final int page, final int userId, final String token){
        apiService.getMessageData(page, userId, token).enqueue(new Callback<BaseModel<List<Message>>>() {
            @Override
            public void onResponse(Call<BaseModel<List<Message>>> call, Response<BaseModel<List<Message>>> response) {
                BaseModel<List<Message>> baseModel = response.body();
                if (baseModel != null){
                    if (baseModel.isSuccess()){
                        updateUserToken(userId, token, baseModel.getToken());
                        List<Message> data = baseModel.getRows();
                        if (page == 1){
                            dispatcher.dispatch(new MsgAction(MsgAction.ACTION_MSG_CENTER_REFRESH_SUCCESS, data));
                        }else {
                            dispatcher.dispatch(new MsgAction(MsgAction.ACTION_MSG_CENTER_LOAD_MORE_SUCCESS, data));
                        }
                    }else {
                        dispatcher.dispatch(new Action(MsgAction.ACTION_REQUEST_ERROR_MESSAGE, baseModel.getMessage()));
                        if (baseModel.getFlg() == AppConfig.INVALID_TOKEN){
                            dispatcher.dispatch(new MsgAction(MsgAction.ACTION_REQUEST_INVALID_TOKEN));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel<List<Message>>> call, Throwable t) {
                dispatcher.dispatch(new MsgAction(MsgAction.ACTION_REQUEST_FAIL));
            }
        });
    }

    public void seenAllMessage(final int uid, final String token){
        apiService.seenAllMessage(uid, token).enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                BaseModel baseModel = response.body();
                if (baseModel != null){
                    if (baseModel.isSuccess()){
                        updateUserToken(uid, token, baseModel.getToken());
                        dispatcher.dispatch(new MsgAction(MsgAction.ACTION_MSG_CENTER_SEEN_ALL_SUCCESS));
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
            }
        });
    }


    /**
     * 修改登录密码
     * @param phone phone
     * @param code code
     * @param newLoginPsd newLoginPsd
     */
    public void changeLoginPsd(String phone, String code, String newLoginPsd, String token){
        newLoginPsd = Util.MD5(newLoginPsd);
        dispatcher.dispatch(new ChangeLoginPsdAction(ChangeLoginPsdAction.ACTION_REQUEST_START));
        apiService.changeLoginPsd(phone, code, newLoginPsd, token).enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                dispatcher.dispatch(new ChangeLoginPsdAction(ChangeLoginPsdAction.ACTION_REQUEST_FINISH));
                BaseModel baseModel = response.body();
                if (baseModel != null){
                    dispatcher.dispatch(new ChangeLoginPsdAction(ChangeLoginPsdAction.ACTION_REQUEST_ERROR_MESSAGE, baseModel.getMessage()));
                    if (baseModel.isSuccess()){
                        dispatcher.dispatch(new ChangeLoginPsdAction(ChangeLoginPsdAction.ACTION_CHANGE_PSD_SUCCESS));
                    }else {
                        if (baseModel.getFlg() == AppConfig.INVALID_TOKEN){
                            dispatcher.dispatch(new ChangeLoginPsdAction(ChangeLoginPsdAction.ACTION_REQUEST_INVALID_TOKEN));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                dispatcher.dispatch(new ChangeLoginPsdAction(ChangeLoginPsdAction.ACTION_REQUEST_FINISH));
                dispatcher.dispatch(new ChangeLoginPsdAction(ChangeLoginPsdAction.ACTION_REQUEST_FAIL));
            }
        });
    }

    public void retrievePsd(String phone, String code, String newPsd){
        newPsd = Util.MD5(newPsd);
        dispatcher.dispatch(new ChangeLoginPsdAction(ChangeLoginPsdAction.ACTION_REQUEST_START));
        apiService.resetPsd(phone, code, newPsd).enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                dispatcher.dispatch(new ChangeLoginPsdAction(ChangeLoginPsdAction.ACTION_REQUEST_FINISH));
                BaseModel baseModel = response.body();
                if (baseModel != null){
                    dispatcher.dispatch(new ChangeLoginPsdAction(ChangeLoginPsdAction.ACTION_REQUEST_ERROR_MESSAGE, baseModel.getMessage()));
                    if (baseModel.isSuccess()){
                        dispatcher.dispatch(new ChangeLoginPsdAction(ChangeLoginPsdAction.ACTION_CHANGE_PSD_SUCCESS));
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                dispatcher.dispatch(new ChangeLoginPsdAction(ChangeLoginPsdAction.ACTION_REQUEST_FINISH));
                dispatcher.dispatch(new ChangeLoginPsdAction(ChangeLoginPsdAction.ACTION_REQUEST_FAIL));
            }
        });
    }

    /**
     * 修改手机号码第一步
     * @param psd psd
     * @param phone phone
     * @param code code
     * @param userId userId
     * @param token token
     */
    public void changeMobileStepOne(String psd, String phone, String code, final int userId, final String token){
        psd = Util.MD5(psd);
        dispatcher.dispatch(new ChangeMobileOneAction(ChangeMobileOneAction.ACTION_REQUEST_START));
        apiService.changeMobileStepOne(psd, phone, code, userId, token).enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                dispatcher.dispatch(new ChangeMobileOneAction(ChangeMobileOneAction.ACTION_REQUEST_FINISH));
                BaseModel baseModel = response.body();
                if (baseModel != null){
                    if (baseModel.isSuccess()){
                        updateUserToken(userId, token, baseModel.getToken());
                        dispatcher.dispatch(new ChangeMobileOneAction(ChangeMobileOneAction.ACTION_MOBILE_STEP_ONE_SUCCESS));
                    }else {
                        String msg = baseModel.getMessage();
                        dispatcher.dispatch(new ChangeMobileOneAction(ChangeMobileOneAction.ACTION_REQUEST_ERROR_MESSAGE, msg));
                        if (baseModel.getFlg() == AppConfig.INVALID_TOKEN){
                            dispatcher.dispatch(new ChangeMobileOneAction(ChangeMobileOneAction.ACTION_REQUEST_INVALID_TOKEN));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                dispatcher.dispatch(new ChangeMobileOneAction(ChangeMobileOneAction.ACTION_REQUEST_FINISH));
                dispatcher.dispatch(new ChangeMobileOneAction(ChangeMobileOneAction.ACTION_REQUEST_FAIL));
            }
        });
    }

    /**
     * 修改手机号码第二步
     * @param newPhone newPhone
     * @param code code
     * @param userId userId
     * @param token token
     */
    public void changeMobile(String newPhone, String code, final int userId, final String token){
        dispatcher.dispatch(new ChangeMobileTowAction(ChangeMobileTowAction.ACTION_REQUEST_START));
        apiService.changeMobileStepTwo(newPhone, code, userId, token).enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                dispatcher.dispatch(new ChangeMobileTowAction(ChangeMobileTowAction.ACTION_REQUEST_FINISH));
                BaseModel baseModel = response.body();
                if (baseModel.isSuccess()){
                    updateUserToken(userId, token, baseModel.getToken());
                    dispatcher.dispatch(new ChangeMobileTowAction(ChangeMobileTowAction.ACTION_MOBILE_STEP_TWO_SUCCESS));
                }else {
                    String msg = baseModel.getMessage();
                    dispatcher.dispatch(new Action(ChangeMobileTowAction.ACTION_REQUEST_ERROR_MESSAGE, msg));
                    if (baseModel.getFlg() == AppConfig.INVALID_TOKEN){
                        dispatcher.dispatch(new ChangeMobileTowAction(ChangeMobileTowAction.ACTION_REQUEST_INVALID_TOKEN));
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                dispatcher.dispatch(new ChangeMobileTowAction(ChangeMobileTowAction.ACTION_REQUEST_FINISH));
                dispatcher.dispatch(new ChangeMobileTowAction(ChangeMobileTowAction.ACTION_REQUEST_FAIL));
            }
        });
    }

//    public void myInvestData(int investType, final int page, int uid, String token){
//        apiService.myInvestData(investType, page, uid, token).enqueue(new Callback<BaseModel<List<Order>>>() {
//            @Override
//            public void onResponse(Call<BaseModel<List<Order>>> call, Response<BaseModel<List<Order>>> response) {
//                BaseModel baseModel = response.body();
//                if (baseModel != null){
//                    if (baseModel.isSuccess()){
//                        List<Order> data = (List<Order>) baseModel.getRows();
//                        dispatcher.dispatch(new Action(Action.ACTION_DATA_SIZE, baseModel.getTotal()));
//                        if (page == 1){
//                            dispatcher.dispatch(new InvestAction(InvestAction.ACTION_REQUEST_REFRESH_INVEST_DATA_SUCCESS, data));
//                        }else {
//                            dispatcher.dispatch(new InvestAction(InvestAction.ACTION_REQUEST_LOAD_INVEST_DATA_SUCCESS, data));
//                        }
//                    }else {
//                        String msg = baseModel.getMessage();
//                        dispatcher.dispatch(new Action(Action.ACTION_REQUEST_MESSAGE, msg));
//                        if (baseModel.getFlg() == AppConfig.INVALID_TOKEN){
//                           dispatcher.dispatch(new InvestAction(Action.ACTION_INVALID_TOKEN));
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<BaseModel<List<Order>>> call, Throwable t) {
//                dispatcher.dispatch(new InvestAction(Action.ACTION_REQUEST_FAIL));
//            }
//        });
//    }

    /**
     * 萝卜快赚数据
     * @param uid uid
     * @param token token
     */
    public void getKuaiZhuanData(final int uid, final String token){
        dispatcher.dispatch(new KuaiZhuanAction(KuaiZhuanAction.ACTION_REQUEST_START));
        apiService.getKuaiZhuanData(uid, token).enqueue(new Callback<BaseModel<List<KuaiZhuan>>>() {
            @Override
            public void onResponse(Call<BaseModel<List<KuaiZhuan>>> call, Response<BaseModel<List<KuaiZhuan>>> response) {
                dispatcher.dispatch(new KuaiZhuanAction(KuaiZhuanAction.ACTION_REQUEST_FINISH));
                BaseModel baseModel = response.body();
                if (baseModel != null){
                    if (baseModel.isSuccess()){
                        updateUserToken(uid, token, baseModel.getToken());
                        dispatcher.dispatch(new KuaiZhuanAction(KuaiZhuanAction.ACTION_REQUEST_KUAI_ZHUAN_SUCCESS, baseModel));
                    }else {
                        dispatcher.dispatch(new Action(KuaiZhuanAction.ACTION_REQUEST_ERROR_MESSAGE, baseModel.getMessage()));
                        if (baseModel.getFlg() == AppConfig.INVALID_TOKEN){
                            dispatcher.dispatch(new KuaiZhuanAction(KuaiZhuanAction.ACTION_REQUEST_INVALID_TOKEN));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel<List<KuaiZhuan>>> call, Throwable t) {
                dispatcher.dispatch(new KuaiZhuanAction(KuaiZhuanAction.ACTION_REQUEST_FINISH));
                dispatcher.dispatch(new KuaiZhuanAction(KuaiZhuanAction.ACTION_REQUEST_FAIL));
            }
        });
    }

    /**
     * 交易记录
     * @param userId userId
     * @param token token
     * @param page page
     */
    public void tradingRecordingData(final int userId, final String token, final int page){
        apiService.getTradingRecord(userId, token, page).enqueue(new Callback<BaseModel<List<TradingRecord>>>() {
            @Override
            public void onResponse(Call<BaseModel<List<TradingRecord>>> call, Response<BaseModel<List<TradingRecord>>> response) {
                BaseModel baseModel = response.body();
                if (baseModel != null){
                    if (baseModel.isSuccess()){
                        updateUserToken(userId, token, baseModel.getToken());
                        dispatcher.dispatch(new Action(TradingAction.ACTION_REQUEST_TRADING_RECORD_DATA_SIZE, baseModel.getTotal()));
                        List<TradingRecord> list = (List<TradingRecord>) baseModel.getRows();
                        if (page == 1){
                            dispatcher.dispatch(new TradingAction(TradingAction.ACTION_REQUEST_REFRESH_TRADING_RECORD_SUCCESS, list));
                        }else {
                            dispatcher.dispatch(new TradingAction(TradingAction.ACTION_REQUEST_LOAD_TRADING_RECORD_SUCCESS, list));
                        }

                    }else{
                        String msg = baseModel.getMessage();
                        dispatcher.dispatch(new Action(TradingAction.ACTION_REQUEST_ERROR_MESSAGE, msg));
                        if (baseModel.getFlg() == AppConfig.INVALID_TOKEN){
                            dispatcher.dispatch(new TradingAction(TradingAction.ACTION_REQUEST_INVALID_TOKEN));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel<List<TradingRecord>>> call, Throwable t) {
                dispatcher.dispatch(new TradingAction(TradingAction.ACTION_REQUEST_FAIL));
            }
        });
    }

    /**
     * 萝卜可赎回订单列表
     * @param uid uid
     * @param token token
     * @param page page
     */
    public void getRedeemList(final int uid, final String token, final int page){
        apiService.getRedeemList(uid, token, page).enqueue(new Callback<BaseModel<ArrayList<Order>>>() {
            @Override
            public void onResponse(Call<BaseModel<ArrayList<Order>>> call, Response<BaseModel<ArrayList<Order>>> response) {
                BaseModel baseModel = response.body();
                if (baseModel != null){
                    if (baseModel.isSuccess()){
                        updateUserToken(uid, token, baseModel.getToken());
                        ArrayList<Order> orders = (ArrayList<Order>) baseModel.getRows();
                        dispatcher.dispatch(new Action(RedeemListAction.ACTION_LOAD_DATA_TOTAL_SIZE, baseModel.getTotal()));
                        if (page == 1){
                            dispatcher.dispatch(new RedeemListAction(RedeemListAction.ACTION_REQUEST_REFRESH_DATA_SUCCESS, orders));
                        }else {
                            dispatcher.dispatch(new RedeemListAction(RedeemListAction.ACTION_REQUEUST_LOAD_DATA_SUCCESS, orders));
                        }
                    }else {
                        String msg = baseModel.getMessage();
                        dispatcher.dispatch(new Action(RedeemListAction.ACTION_REQUEST_ERROR_MESSAGE, msg));
                        if (baseModel.getFlg() == AppConfig.INVALID_TOKEN){
                            dispatcher.dispatch(new RedeemListAction(RedeemListAction.ACTION_REQUEST_INVALID_TOKEN));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel<ArrayList<Order>>> call, Throwable t) {
                dispatcher.dispatch(new RedeemListAction(RedeemListAction.ACTION_REQUEST_FAIL));
            }
        });
    }

    public void redeemBack(final int id, final String token, String orderNo){
        dispatcher.dispatch(new RedeemAction(RedeemAction.ACTION_REQUEST_START));
        RedeemAPIClient.getInstance().redeemBackOrder(id, token, orderNo, "0").enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                dispatcher.dispatch(new RedeemAction(RedeemAction.ACTION_REQUEST_FINISH));
                BaseModel baseModel = response.body();
                if (baseModel != null){
                    String msg = baseModel.getMessage();
                    dispatcher.dispatch(new Action(RedeemAction.ACTION_REQUEST_ERROR_MESSAGE, msg));
                    if (baseModel.isSuccess()){
                        updateUserToken(id, token, baseModel.getToken());
                        dispatcher.dispatch(new RedeemAction(RedeemAction.ACTION_REQUEST_REDEEM_BACK_SUCCESS));
                    }else {
                        if (baseModel.getFlg() == AppConfig.INVALID_TOKEN){
                            dispatcher.dispatch(new RedeemAction(RedeemAction.ACTION_REQUEST_INVALID_TOKEN));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                dispatcher.dispatch(new RedeemAction(RedeemAction.ACTION_REQUEST_FINISH));
                dispatcher.dispatch(new RedeemAction(RedeemAction.ACTION_REQUEST_FAIL));
            }
        });
    }

    /**
     * 更新用户信息
     * @param userId userId
     * @param token token
     */
    public void updateUserInfo(int userId, String token){
        dispatcher.dispatch(new UserInfoAction(UserInfoAction.ACTION_REQUEST_START));
        apiService.updateUserInfo(userId, token).enqueue(new Callback<BaseModel<User>>() {
            @Override
            public void onResponse(Call<BaseModel<User>> call, Response<BaseModel<User>> response) {
                dispatcher.dispatch(new UserInfoAction(UserInfoAction.ACTION_REQUEST_FINISH));
                BaseModel baseModel = response.body();
                //不做token刷新
                if (baseModel != null){
                    if (baseModel.isSuccess()){
                        dispatcher.dispatch(new UserInfoAction(UserInfoAction.ACTION_REQUEST_UPDATE_USER_INFO_SUCCESS, baseModel));
                    }else {
                        String msg = baseModel.getMessage();
                        dispatcher.dispatch(new Action(UserInfoAction.ACTION_REQUEST_ERROR_MESSAGE, msg));
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel<User>> call, Throwable t) {
                dispatcher.dispatch(new UserInfoAction(UserInfoAction.ACTION_REQUEST_FINISH));
                dispatcher.dispatch(new UserInfoAction(UserInfoAction.ACTION_REQUEST_FAIL));
            }
        });
    }

    /**
     * 总资产分类
     * @param uid uid
     * @param token token
     */
    public void getTotalAssets(int uid, String token){
        dispatcher.dispatch(new TotalAssetsAction(TotalAssetsAction.ACTION_REQUEST_START));
        apiService.getTotalAssets(uid, token).enqueue(new Callback<BaseModel<User>>() {
            @Override
            public void onResponse(Call<BaseModel<User>> call, Response<BaseModel<User>> response) {
                dispatcher.dispatch(new TotalAssetsAction(TotalAssetsAction.ACTION_REQUEST_FINISH));
                BaseModel baseModel = response.body();
                if (baseModel != null){
                    //不做token刷新
                    if (baseModel.isSuccess()){
                        dispatcher.dispatch(new TotalAssetsAction(TotalAssetsAction.ACTION_REQUEST_TOTAL_ASSETS_SUCCESS, baseModel));
                    }else {
                        String msg = baseModel.getMessage();
                        dispatcher.dispatch(new Action(TotalAssetsAction.ACTION_REQUEST_ERROR_MESSAGE, msg));
                        if (baseModel.getFlg() == AppConfig.INVALID_TOKEN){
                            dispatcher.dispatch(new TotalAssetsAction(TotalAssetsAction.ACTION_REQUEST_INVALID_TOKEN));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel<User>> call, Throwable t) {
                dispatcher.dispatch(new TotalAssetsAction(TotalAssetsAction.ACTION_REQUEST_FINISH));
                dispatcher.dispatch(new TotalAssetsAction(TotalAssetsAction.ACTION_REQUEST_FAIL));
            }
        });
    }

    /**
     *  在银行卡管理界面拉取信息
     * @param uid uid
     * @param token token
     */
    public void getBankCardInfo(final int uid, final String token){
        dispatcher.dispatch(new BankCardAction(BankCardAction.ACTION_REQUEST_START));
        apiService.getBankInfo(uid, token).enqueue(new Callback<BaseModel<BankCardInfo>>() {
            @Override
            public void onResponse(Call<BaseModel<BankCardInfo>> call, Response<BaseModel<BankCardInfo>> response) {
                dispatcher.dispatch(new BankCardAction(BankCardAction.ACTION_REQUEST_FINISH));
                BaseModel baseModel = response.body();
                if (baseModel != null){
                    if (baseModel.isSuccess()){
                        updateUserToken(uid, token, baseModel.getToken());
                        dispatcher.dispatch(new BankCardAction(BankCardAction.ACTION_REQUEST_BANK_CARD_SUCCESS, baseModel));
                    }else {
                        String msg = baseModel.getMessage();
                        dispatcher.dispatch(new Action(BankCardAction.ACTION_REQUEST_ERROR_MESSAGE, msg));
                        if (baseModel.getFlg() == AppConfig.INVALID_TOKEN){
                            dispatcher.dispatch(new BankCardAction(BankCardAction.ACTION_REQUEST_INVALID_TOKEN));
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<BaseModel<BankCardInfo>> call, Throwable t) {
                dispatcher.dispatch(new BankCardAction(BankCardAction.ACTION_REQUEST_FINISH));
                dispatcher.dispatch(new BankCardAction(BankCardAction.ACTION_REQUEST_FAIL));
            }
        });
    }

    public void updateUserInfoInMine(final int userId, final String token){
        apiService.updateUserInfo(userId, token).enqueue(new Callback<BaseModel<User>>() {
            @Override
            public void onResponse(Call<BaseModel<User>> call, Response<BaseModel<User>> response) {
                BaseModel baseModel = response.body();
                if (baseModel != null){
                    if (baseModel.isSuccess()){
                        //不刷新token
//                        updateUserToken(userId, token, baseModel.getToken());
                        dispatcher.dispatch(new MineAction(MineAction.MINE_ACTION_REQUEST_UPDATE_SUCCESS, baseModel));
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel<User>> call, Throwable t) {
            }
        });
    }

    /**
     * 账号信息界面，刷新用户信息
     * @param userId userId
     * @param token token
     */
    public void updateUserInfoInAccountInfo(int userId, String token){
        dispatcher.dispatch(new AccountInfoAction(AccountInfoAction.ACTION_REQUEST_START));
        apiService.updateUserInfo(userId, token).enqueue(new Callback<BaseModel<User>>() {
            @Override
            public void onResponse(Call<BaseModel<User>> call, Response<BaseModel<User>> response) {
                dispatcher.dispatch(new AccountInfoAction(AccountInfoAction.ACTION_REQUEST_FINISH));
                BaseModel baseModel = response.body();
                if (baseModel != null){
                    if (baseModel.isSuccess()){
                        dispatcher.dispatch(new AccountInfoAction(AccountInfoAction.ACTION_REQUEST_ACCOUNT_INFO_REFRUSH_DATA_SUCCESS, baseModel));
                    }else {
                        String msg = baseModel.getMessage();
                        //不做token刷新
                        dispatcher.dispatch(new Action(AccountInfoAction.ACTION_REQUEST_ERROR_MESSAGE, msg));
                        if (baseModel.getFlg() == AppConfig.INVALID_TOKEN){
                            dispatcher.dispatch(new AccountInfoAction(AccountInfoAction.ACTION_REQUEST_INVALID_TOKEN));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel<User>> call, Throwable t) {
                dispatcher.dispatch(new AccountInfoAction(AccountInfoAction.ACTION_REQUEST_FINISH));
                dispatcher.dispatch(new AccountInfoAction(AccountInfoAction.ACTION_REQUEST_FAIL));
            }
        });
    }

    /**
     * 上传手势密码
     * @param uid uid
     * @param token token
     * @param gesture gesture
     */
    public void updateUserGesture(final int uid, final String token, String gesture){
        dispatcher.dispatch(new SecurityAction(SecurityAction.ACTION_RESULT_START));
        apiService.updateGesture(uid, token, gesture).enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                dispatcher.dispatch(new SecurityAction(SecurityAction.ACTION_RESULT_FINISH));
                BaseModel baseModel = response.body();
                if (baseModel != null){
                    if (baseModel.isSuccess()){
                        updateUserToken(uid, token, baseModel.getToken());
                        dispatcher.dispatch(new SecurityAction(SecurityAction.ACTION_RESULT_SUCCESS));
                    }else {
                        String msg = baseModel.getMessage();
                        dispatcher.dispatch(new SecurityAction(SecurityAction.ACTION_RESULT_ERROR_MESSAGE, msg));
                        if (baseModel.getFlg() == AppConfig.INVALID_TOKEN){
                            dispatcher.dispatch(new SecurityAction(SecurityAction.ACTION_RESULT_VALID_TOKEN));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                dispatcher.dispatch(new SecurityAction(SecurityAction.ACTION_RESULT_FINISH));
                dispatcher.dispatch(new SecurityAction(SecurityAction.ACTION_RESULT_FAIL));
            }
        });
    }

    public void updateUserToken(int uid, String oldToken, String newToken){
        if (TextUtils.isEmpty(newToken)){
            return;
        }
        if (oldToken.equals(newToken)){
            return;
        }
        appContext.getUser().setToken(newToken);
        Log.i("chen", "oldToken:" + oldToken + "  newToken:" + newToken);
        LuoBoDBM.getInstance(appContext).updateUserToken(uid, newToken);
        refreshToken(uid, newToken);
    }

    /**
     * 可领取的全部红包信息
     * @param uid uid
     */
    public void getVipRedTicket(final int uid, final String token){
        dispatcher.dispatch(new VIPCenterAction(VIPCenterAction.ACTION_REQUEST_START));
        apiService.getVipRedTicketInfo(uid, token).enqueue(new Callback<BaseModel<ArrayList<RedTicket>>>() {
            @Override
            public void onResponse(Call<BaseModel<ArrayList<RedTicket>>> call, Response<BaseModel<ArrayList<RedTicket>>> response) {
                dispatcher.dispatch(new VIPCenterAction(VIPCenterAction.ACTION_REQUEST_FINISH));
                BaseModel baseModel = response.body();
                if (baseModel != null){
                    if (baseModel.isSuccess()){
//                        ArrayList<RedTicket> tickets = (ArrayList<RedTicket>) baseModel.getRows();
                        dispatcher.dispatch(new VIPCenterAction(VIPCenterAction.ACTION_REQUEST_RED_TICKET_SUCCESS, baseModel));
                        updateUserToken(uid, token, baseModel.getToken());

                    }else {
                        dispatcher.dispatch(new Action(VIPCenterAction.ACTION_REQUEST_ERROR_MESSAGE, baseModel.getMessage()));
                        if (baseModel.getFlg() == AppConfig.INVALID_TOKEN){
                            dispatcher.dispatch(new VIPCenterAction(VIPCenterAction.ACTION_REQUEST_INVALID_TOKEN));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel<ArrayList<RedTicket>>> call, Throwable t) {
                dispatcher.dispatch(new VIPCenterAction(VIPCenterAction.ACTION_REQUEST_FINISH));
                dispatcher.dispatch(new VIPCenterAction(VIPCenterAction.ACTION_REQUEST_FAIL));
            }
        });
    }

    /**
     * 用户领取红包
     * @param uid uid
     * @param ticketId ticketId
     */
    public void getVipRedTicket(final int uid, int ticketId, final String token){
        dispatcher.dispatch(new VIPCenterAction(VIPCenterAction.ACTION_REQUEST_START));
        apiService.getRedTicket(uid, ticketId, token).enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                dispatcher.dispatch(new VIPCenterAction(VIPCenterAction.ACTION_REQUEST_FINISH));
                BaseModel baseModel = response.body();
                if (baseModel != null){
                    if (baseModel.isSuccess()){
                        dispatcher.dispatch(new VIPCenterAction(VIPCenterAction.ACTION_REQUEST_TAKE_RED_TICKET_SUCCESS));
                        updateUserToken(uid, token, baseModel.getToken());
                    }else {
                        dispatcher.dispatch(new Action(VIPCenterAction.ACTION_REQUEST_ERROR_MESSAGE, baseModel.getMessage()));
                        if (baseModel.getFlg() == AppConfig.INVALID_TOKEN){
                            dispatcher.dispatch(new VIPCenterAction(VIPCenterAction.ACTION_REQUEST_INVALID_TOKEN));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                dispatcher.dispatch(new VIPCenterAction(VIPCenterAction.ACTION_REQUEST_FINISH));
                dispatcher.dispatch(new VIPCenterAction(VIPCenterAction.ACTION_REQUEST_FAIL));
            }
        });
    }

    /**
     * 当刷新toekn时，立即调用此接口，让服务器刷新token
     */
    public void refreshToken(int uid, String refreshToken){
        apiService.refreshToken(uid, refreshToken).enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
//                BaseModel baseModel = response.body();
//                if (baseModel != null && baseModel.isSuccess()){
//                }
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {

            }
        });
    }

    /**
     * 上传用户的token
     * @param id id
     * @param token token
     * @param deviceToken deviceToken
     */
    public void updateDeviceToken(final int id, final String token, String deviceToken){
        apiService.uploadDeviceToken(id, token, "android,"+ deviceToken).enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                BaseModel baseModel = response.body();
                if (baseModel != null){
                    if (baseModel.isSuccess()){
                        updateUserToken(id, token, baseModel.getToken());
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
            }
        });
    }

    /**
     * 获取用户是否有未读信息
     * @param id id
     * @param token token
     */
    public void getUnreadMessage(final int id, final String token){
        apiService.getUnreadMessage(id, token).enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                BaseModel baseModel = response.body();
                if (baseModel != null){
                    if (baseModel.isSuccess()){
                        updateUserToken(id, token, baseModel.getToken());
                        dispatcher.dispatch(new DotAction(DotAction.MAIN_DOT_ACTION_HAVE_UNREAD_MESSAGE, baseModel));
                    }else {
                        dispatcher.dispatch(new DotAction(DotAction.MAIN_DOT_ACTION_DONT_HAVE_UNREAD_MESSAGE));
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {

            }
        });
    }

    public void signIn(int id, String token){
        dispatcher.dispatch(new SignInAction(SignInAction.ACTION_REQUEST_START));
        apiService.signIn(id, token).enqueue(new Callback<BaseModel<User>>() {
            @Override
            public void onResponse(Call<BaseModel<User>> call, Response<BaseModel<User>> response) {
                dispatcher.dispatch(new SignInAction(SignInAction.ACTION_REQUEST_FINISH));
                BaseModel baseModel = response.body();
                if (baseModel != null){
                    if (baseModel.isSuccess()){
                        dispatcher.dispatch(new SignInAction(SignInAction.ACTION_REQUEST_SIGN_IN_SUCCESS, baseModel));
                    }else {
                        dispatcher.dispatch(new Action(SignInAction.ACTION_REQUEST_ERROR_MESSAGE, baseModel.getMessage()));
                        if (baseModel.getFlg() == AppConfig.INVALID_TOKEN){
                            dispatcher.dispatch(new SignInAction(SignInAction.ACTION_REQUEST_INVALID_TOKEN));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel<User>> call, Throwable t) {
                dispatcher.dispatch(new SignInAction(SignInAction.ACTION_REQUEST_FINISH));
                dispatcher.dispatch(new SignInAction(SignInAction.ACTION_REQUEST_FAIL));
            }
        });
    }

    /**
     * 获取用户邀请列表
     * @param uid id
     * @param token token
     */
    public void getInviteModels(final int uid, final String token){
        dispatcher.dispatch(new InviteAction(InviteAction.ACTION_REQUEST_START));
        apiService.getInviteData(uid, token).enqueue(new Callback<BaseModel<ArrayList<InviteMode>>>() {
            @Override
            public void onResponse(Call<BaseModel<ArrayList<InviteMode>>> call, Response<BaseModel<ArrayList<InviteMode>>> response) {
                dispatcher.dispatch(new InviteAction(InviteAction.ACTION_REQUEST_FINISH));
                BaseModel baseModel = response.body();
                if (baseModel != null){
                    if (baseModel.isSuccess()){
                        updateUserToken(uid, token, baseModel.getToken());
                        List<InviteMode> modes = (List<InviteMode>) baseModel.getRows();
                        dispatcher.dispatch(new InviteAction(InviteAction.ACTION_REQUEST_INVAITE_DATA, modes));
                    }else {
                        dispatcher.dispatch(new Action(InviteAction.ACTION_REQUEST_ERROR_MESSAGE, baseModel.getMessage()));
                        if (baseModel.getFlg() == AppConfig.INVALID_TOKEN){
                            dispatcher.dispatch(new InviteAction(MsgAction.ACTION_REQUEST_INVALID_TOKEN));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel<ArrayList<InviteMode>>> call, Throwable t) {
                dispatcher.dispatch(new InviteAction(InviteAction.ACTION_REQUEST_FINISH));
                dispatcher.dispatch(new InviteAction(InviteAction.ACTION_REQUEST_FAIL));
            }
        });
    }

    public void getProductList(int gcId,final int page){
        apiService.getProductList(gcId, page).enqueue(new Callback<BaseModel<ArrayList<Product>>>() {
            @Override
            public void onResponse(Call<BaseModel<ArrayList<Product>>> call, Response<BaseModel<ArrayList<Product>>> response) {
                BaseModel baseModel = response.body();
                if (baseModel != null){
                    if (baseModel.isSuccess()){
                        ArrayList<Product> data = (ArrayList<Product>) baseModel.getRows();
                        dispatcher.dispatch(new Action(ProductListAction.PRODUCT_LIST_DATA_TOTAL_SIZE, baseModel.getTotal()));
                        if (page == 1){
                            dispatcher.dispatch(new ProductListAction(ProductListAction.PRODUCT_LIST_REQUEST_REFRESH_SUCCESS, data));
                        }else {
                            dispatcher.dispatch(new ProductListAction(ProductListAction.PRODUCT_LIST_REQUEST_LOAD_SUCCESS, data));
                        }
                    }else {
                        String msg = baseModel.getMessage();
                        dispatcher.dispatch(new Action(ProductListAction.ACTION_REQUEST_ERROR_MESSAGE, msg));
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel<ArrayList<Product>>> call, Throwable t) {
                dispatcher.dispatch(new ProductListAction(ProductListAction.ACTION_REQUEST_FAIL));
            }
        });
    }

    public void getProductInfo(int goodId){
        dispatcher.dispatch(new ProductInfoAction(ProductInfoAction.ACTION_REQUEST_START));
        apiService.getProductInfo(goodId).enqueue(new Callback<BaseModel<ProductInfo>>() {
            @Override
            public void onResponse(Call<BaseModel<ProductInfo>> call, Response<BaseModel<ProductInfo>> response) {
                dispatcher.dispatch(new ProductInfoAction(ProductInfoAction.ACTION_REQUEST_FINISH));
                BaseModel baseModel = response.body();
                if (baseModel != null){
                    if (baseModel.isSuccess()){
                        ProductInfo productInfo = (ProductInfo) baseModel.getRows();
                        dispatcher.dispatch(new ProductInfoAction(ProductInfoAction.ACTION_REQUEST_PRODUCT_INFO_SUCCESS, productInfo));
                    }else {
                        String msg = baseModel.getMessage();
                        dispatcher.dispatch(new Action(ProductInfoAction.ACTION_REQUEST_ERROR_MESSAGE, msg));
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel<ProductInfo>> call, Throwable t) {
                dispatcher.dispatch(new ProductInfoAction(ProductInfoAction.ACTION_REQUEST_FINISH));
                dispatcher.dispatch(new ProductInfoAction(ProductInfoAction.ACTION_REQUEST_FAIL));
            }
        });
    }

    public void getKuaiZhuanData(){
        dispatcher.dispatch(new KuaiZhuanInfoAction(KuaiZhuanInfoAction.ACTION_REQUEST_START));
        apiService.getKuaiZhuanData().enqueue(new Callback<BaseModel<Product>>() {
            @Override
            public void onResponse(Call<BaseModel<Product>> call, Response<BaseModel<Product>> response) {
                dispatcher.dispatch(new KuaiZhuanInfoAction(KuaiZhuanInfoAction.ACTION_REQUEST_FINISH));
                BaseModel baseModel = response.body();
                if (baseModel != null){
                    if (baseModel.isSuccess()){
                        Product product = (Product) baseModel.getRows();
                        dispatcher.dispatch(new KuaiZhuanInfoAction(KuaiZhuanInfoAction.ACTION_REQUEST_KUAI_ZHUAN_INFO_SUCCESS, product));
                    }else {
                        String msg = baseModel.getMessage();
                        dispatcher.dispatch(new Action(KuaiZhuanInfoAction.ACTION_REQUEST_ERROR_MESSAGE, msg));
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel<Product>> call, Throwable t) {
                dispatcher.dispatch(new KuaiZhuanInfoAction(KuaiZhuanInfoAction.ACTION_REQUEST_FINISH));
                dispatcher.dispatch(new KuaiZhuanInfoAction(KuaiZhuanInfoAction.ACTION_REQUEST_FAIL));
            }
        });
    }

    /**
     * 生成订单
     */

    public void getPayOrder(final int uId, final String token, int goodId, int money, String couponsId){
        dispatcher.dispatch(new ProductBuyAction(ProductBuyAction.ACTION_REQUEST_START));
        apiService.getProductOrder(uId, token, goodId, money, couponsId, "android").enqueue(new Callback<BaseModel<Order>>() {
            @Override
            public void onResponse(Call<BaseModel<Order>> call, Response<BaseModel<Order>> response) {
                dispatcher.dispatch(new ProductBuyAction(ProductBuyAction.ACTION_REQUEST_FINISH));
                BaseModel baseModel = response.body();
                if (baseModel != null){
                    if (baseModel.isSuccess()){
                        updateUserToken(uId, token, baseModel.getToken());
                        Order order = (Order) baseModel.getRows();
                        dispatcher.dispatch(new ProductBuyAction(ProductBuyAction.ACTION_REQUEST_ORDER_SUCCESS, order));
                    }else {
                        String msg = baseModel.getMessage();
                        dispatcher.dispatch(new Action(ProductBuyAction.ACTION_REQUEST_ERROR_MESSAGE, msg));
                        if (baseModel.getFlg() == AppConfig.INVALID_TOKEN){
                            dispatcher.dispatch(new ProductBuyAction(ProductBuyAction.ACTION_REQUEST_INVALID_TOKEN));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel<Order>> call, Throwable t) {
                dispatcher.dispatch(new ProductBuyAction(ProductBuyAction.ACTION_REQUEST_FINISH));
                dispatcher.dispatch(new ProductBuyAction(ProductBuyAction.ACTION_REQUEST_FAIL));
            }
        });
    }
    public void getMoreProductOrders(int goodId, final int page){
        apiService.getMoreProductOrder(goodId, page).enqueue(new Callback<BaseModel<ArrayList<Order>>>() {
            @Override
            public void onResponse(Call<BaseModel<ArrayList<Order>>> call, Response<BaseModel<ArrayList<Order>>> response) {
                BaseModel baseModel = response.body();
                if (baseModel != null){
                    if (baseModel.isSuccess()){
                        int totalSize = baseModel.getTotal();
                        dispatcher.dispatch(new Action(ProductRecordAction.ACTION_REQUEST_TOTAL_SIZE, totalSize));
                        ArrayList<Order> orders = (ArrayList<Order>) baseModel.getRows();
                        if (page == 1){
                            dispatcher.dispatch(new ProductRecordAction(ProductRecordAction.ACTION_REQUEST_REFRESH_ORDER_LIST_SUCCESS, orders));
                        }else {
                            dispatcher.dispatch(new ProductRecordAction(ProductRecordAction.ACTION_REQUEST_LOAD_ORDER_LIST_SUCCESS, orders));
                        }
                    }else {
                        String msg = baseModel.getMessage();
                        dispatcher.dispatch(new Action(ProductRecordAction.ACTION_REQUEST_ERROR_MESSAGE, msg));
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel<ArrayList<Order>>> call, Throwable t) {
                dispatcher.dispatch(new ProductRecordAction(ProductRecordAction.ACTION_REQUEST_FAIL));
            }
        });
    }

    /**
     * 更新用户信息
     * @param userId userId
     * @param token token
     */
    public void updateUserInfoInProductInfo(final int userId, final String token){
        apiService.updateUserInfo(userId, token).enqueue(new Callback<BaseModel<User>>() {
            @Override
            public void onResponse(Call<BaseModel<User>> call, Response<BaseModel<User>> response) {
                BaseModel baseModel = response.body();
                if (baseModel != null){
                    if (baseModel.isSuccess()){
                        //返回User信息时不在此处刷新token
                        dispatcher.dispatch(new UserInfoAction(UserInfoAction.ACTION_REQUEST_UPDATE_USER_INFO_SUCCESS, baseModel));
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel<User>> call, Throwable t) {
            }
        });
    }

    /**
     * 获取体验金产品信息
     */
    public void getExpGoodInfo(final int uid, final String token){
        dispatcher.dispatch(new ExpGoodAction(ExpGoodAction.ACTION_REQUEST_START));
        apiService.getExpGoodInfo(uid, token).enqueue(new Callback<BaseModel<ExpGood>>() {
            @Override
            public void onResponse(Call<BaseModel<ExpGood>> call, Response<BaseModel<ExpGood>> response) {
                dispatcher.dispatch(new ExpGoodAction(ExpGoodAction.ACTION_REQUEST_FINISH));
                BaseModel baseModel = response.body();
                if (baseModel != null){
                    if (baseModel.isSuccess()){
                        updateUserToken(uid, token, baseModel.getToken());
                        ExpGood expGood = (ExpGood) baseModel.getRows();
                        dispatcher.dispatch(new ExpGoodAction(ExpGoodAction.ACTION_REQUEST_EXP_GOOD_SUCCESS, expGood));
                    }else {
                        String msg = baseModel.getMessage();
                        dispatcher.dispatch(new Action(ExpGoodAction.ACTION_REQUEST_ERROR_MESSAGE, msg));
                        if (baseModel.getFlg() == AppConfig.INVALID_TOKEN){
                            dispatcher.dispatch(new ExpGoodAction(ExpGoodAction.ACTION_REQUEST_INVALID_TOKEN));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel<ExpGood>> call, Throwable t) {
                dispatcher.dispatch(new ExpGoodAction(ExpGoodAction.ACTION_REQUEST_FINISH));
                dispatcher.dispatch(new ExpGoodAction(ExpGoodAction.ACTION_REQUEST_FAIL));
            }
        });
    }

    /**
     * 购买体验金产品
     */
    public void buyExpGood(final int uid, final String token){
        dispatcher.dispatch(new ExpGoodAction(ExpGoodAction.ACTION_REQUEST_START));
        apiService.buyExpGood(uid, token).enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                dispatcher.dispatch(new ExpGoodAction(ExpGoodAction.ACTION_REQUEST_FINISH));
                BaseModel baseModel = response.body();
                if (baseModel != null){
                    if (baseModel.isSuccess()){
                        updateUserToken(uid, token, baseModel.getToken());
                        dispatcher.dispatch(new ExpGoodAction(ExpGoodAction.BUY_EXP_ACTION_REQUEST_SUCCESS));
                    }else {
                        String msg = baseModel.getMessage();
                        dispatcher.dispatch(new Action(ExpGoodAction.ACTION_REQUEST_ERROR_MESSAGE, msg));
                        if (baseModel.getFlg() == AppConfig.INVALID_TOKEN){
                            dispatcher.dispatch(new ExpGoodAction(ExpGoodAction.ACTION_REQUEST_INVALID_TOKEN));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                dispatcher.dispatch(new ExpGoodAction(ExpGoodAction.ACTION_REQUEST_FINISH));
                dispatcher.dispatch(new ExpGoodAction(ExpGoodAction.ACTION_REQUEST_FAIL));
            }
        });
    }

    /**
     * 购买时，所有可用红包
     * @param uid uid
     */
    public void getUsableRedPacket(final int uid, int gcId, final String token){
        dispatcher.dispatch(new UsableCouponsAction(UserInfoAction.ACTION_REQUEST_START));
        apiService.getUsableRedPacket(uid, gcId, token).enqueue(new Callback<BaseModel<ArrayList<RedPacket>>>() {
            @Override
            public void onResponse(Call<BaseModel<ArrayList<RedPacket>>> call, Response<BaseModel<ArrayList<RedPacket>>> response) {
                dispatcher.dispatch(new UsableCouponsAction(UserInfoAction.ACTION_REQUEST_FINISH));
                BaseModel baseModel = response.body();
                if (baseModel != null){
                    if (baseModel.isSuccess()){
                        updateUserToken(uid, token, baseModel.getToken());
                        ArrayList<RedPacket> redPackets = (ArrayList<RedPacket>) baseModel.getRows();
                        dispatcher.dispatch(new UsableCouponsAction(UsableCouponsAction.ACTION_RQEUST_USABLE_RED_PACKET_SUCCESS, redPackets));
                    }else {
                        dispatcher.dispatch(new Action(UsableCouponsAction.ACTION_REQUEST_ERROR_MESSAGE, baseModel.getMessage()));
                        if (baseModel.getFlg() == AppConfig.INVALID_TOKEN){
                            dispatcher.dispatch(new UsableCouponsAction(UsableCouponsAction.ACTION_REQUEST_INVALID_TOKEN));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel<ArrayList<RedPacket>>> call, Throwable t) {
                dispatcher.dispatch(new UsableCouponsAction(UserInfoAction.ACTION_REQUEST_FINISH));
                dispatcher.dispatch(new UsableCouponsAction(UserInfoAction.ACTION_REQUEST_FAIL));
            }
        });
    }

    /**
     * 领取刮刮乐红包
     * @param uid uid
     * @param prizeId prizeId
     */
    public void takeGuaRedTicket(final int uid, int prizeId, final String token){
        apiService.takeGuaTicket(uid, prizeId, token).enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                BaseModel baseModel = response.body();
                if (baseModel != null){
                    if (baseModel.isSuccess()){
                        updateUserToken(uid, token, baseModel.getToken());
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
            }
        });
    }

    /**
     * 查看签到记录
     * @param id id
     * @param token token
     * @param calendar calendar
     */
    public void signInRecord(final int id, final String token, Calendar calendar){
        String date = String.format(Locale.US, "%tF", calendar.getTime());
        dispatcher.dispatch(new SignRecordAction(SignRecordAction.ACTION_REQUEST_START));
        apiService.getSignRecord(id, token, date).enqueue(new Callback<BaseModel<ArrayList<SignRecord>>>() {
            @Override
            public void onResponse(Call<BaseModel<ArrayList<SignRecord>>> call, Response<BaseModel<ArrayList<SignRecord>>> response) {
                dispatcher.dispatch(new SignRecordAction(SignRecordAction.ACTION_REQUEST_FINISH));
                BaseModel baseModel = response.body();
                if (baseModel != null){
                    if (baseModel.isSuccess()){
                        updateUserToken(id, token, baseModel.getToken());
                        ArrayList<SignRecord> signRecords = (ArrayList<SignRecord>) baseModel.getRows();
                        dispatcher.dispatch(new SignRecordAction(SignRecordAction.ACTION_REQUEST_SIGN_RECORD_SUCCESS, signRecords));
                    }else {
                        String msg = baseModel.getMessage();
                        dispatcher.dispatch(new Action(SignRecordAction.ACTION_REQUEST_ERROR_MESSAGE, msg));
                        if (baseModel.getFlg() == AppConfig.INVALID_TOKEN){
                            dispatcher.dispatch(new SignRecordAction(SignRecordAction.ACTION_REQUEST_INVALID_TOKEN));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel<ArrayList<SignRecord>>> call, Throwable t) {
                dispatcher.dispatch(new SignRecordAction(SignRecordAction.ACTION_REQUEST_FINISH));
                dispatcher.dispatch(new SignRecordAction(SignRecordAction.ACTION_REQUEST_FAIL));
            }
        });
    }

    /**
     * 是否打开自动签到通知
     * @param uid uid
     * @param token token
     * @param isSignIn isSignIn
     */
    public void autoNoticeSignIn(final int uid, final String token, final int isSignIn){
        apiService.signInNotice(uid, token, isSignIn).enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                BaseModel baseModel = response.body();
                if (baseModel.isSuccess()){
                    updateUserToken(uid, token, baseModel.getToken());
                    if (isSignIn == 1){
                        dispatcher.dispatch(new SignInNoticeAction(SignInNoticeAction.ACTION_SIGN_IN_NOTICE_OPEN));
                    }else {
                        dispatcher.dispatch(new SignInNoticeAction(SignInNoticeAction.ACTION_SIGN_IN_NOTICE_CLOSE));
                    }
                }else {
                }
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
            }
        });
    }

    public void checkVersionInfo(){
        apiService.checkVersion().enqueue(new Callback<BaseModel<VersionInfo>>() {
            @Override
            public void onResponse(Call<BaseModel<VersionInfo>> call, Response<BaseModel<VersionInfo>> response) {
                BaseModel baseModel = response.body();
                if (baseModel != null){
                    if (baseModel.isSuccess()){
                        VersionInfo versionInfo = (VersionInfo) baseModel.getRows();
                        dispatcher.dispatch(new VersionAction(VersionAction.ACTION_REQUEST_VERSION_SUCCESS, versionInfo));
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel<VersionInfo>> call, Throwable t) {

            }
        });
    }

    public void downloadApk(final Context context, final String fileName){
        apiService.downloadApp(APIClient.downloadApk+fileName).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody body = response.body();
                Log.i("chen", "开始下载。。。");
                if (body != null){
                    try {
                        final InputStream inputStream = body.byteStream();
                        new Thread(){
                            @Override
                            public void run() {
                                super.run();
                                FileUtils.writeFile(FileUtils.FILE_TYPE_APK, context, inputStream, fileName);
                            }
                        }.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Log.i("chen", "下载完成，发送消息");
                dispatcher.dispatch(new VersionAction(VersionAction.ACTION_REQUEST_FINISH));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("chen", "下载失败，发送消息");
                dispatcher.dispatch(new VersionAction(VersionAction.ACTION_REQUEST_FINISH));
            }
        });
    }

    public void checkLoadPic(){
        apiService.checkLoadPic().enqueue(new Callback<BaseModel<Banner>>() {
            @Override
            public void onResponse(Call<BaseModel<Banner>> call, Response<BaseModel<Banner>> response) {
                BaseModel baseModel = response.body();
                if (baseModel != null){
                    if (baseModel.isSuccess()){
                        Banner banner = (Banner) baseModel.getRows();
                        dispatcher.dispatch(new LoadPicAction(LoadPicAction.LOAD_PIC_ACTION_SUCCESS, banner));
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel<Banner>> call, Throwable t) {

            }
        });
    }

    public void downloadLoadPic(final Context context, final String filePath, final String fileName){
        apiService.downloadLoadPic(APIClient.downloadLoadPic + filePath).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                dispatcher.dispatch(new LoadPicAction(LoadPicAction.DOWNLOAD_LOAD_PIC_FINISH));
                ResponseBody body = response.body();
                if (body != null){
                    try {
                        final InputStream inputStream = body.byteStream();
                        new Thread(){
                            @Override
                            public void run() {
                                super.run();
                                FileUtils.writeFile(FileUtils.FILE_TYPE_LOAD_PIC, context, inputStream, fileName);
                            }
                        }.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                dispatcher.dispatch(new LoadPicAction(LoadPicAction.DOWNLOAD_LOAD_PIC_FINISH));
            }
        });
    }

    public void fastLogin(String phone, String code){
        dispatcher.dispatch(new LoginAction(LoginAction.ACTION_REQUEST_START));
        apiService.fastLogin(phone, code).enqueue(new Callback<BaseModel<User>>() {
            @Override
            public void onResponse(Call<BaseModel<User>> call, Response<BaseModel<User>> response) {
                dispatcher.dispatch(new LoginAction(LoginAction.ACTION_REQUEST_FINISH));
                BaseModel baseModel = response.body();
                if (baseModel != null){
                    if (baseModel.isSuccess()){
                        dispatcher.dispatch(new LoginAction(LoginAction.ACTION_LOGIN_SUCCESS, baseModel));
                    }
                    String msg = baseModel.getMessage();
                    dispatcher.dispatch(new Action(LoginAction.ACTION_REQUEST_ERROR_MESSAGE, msg));
                }
            }

            @Override
            public void onFailure(Call<BaseModel<User>> call, Throwable t) {
                dispatcher.dispatch(new LoginAction(LoginAction.ACTION_REQUEST_FINISH));
                dispatcher.dispatch(new LoginAction(LoginAction.ACTION_REQUEST_FAIL));
            }
        });
    }

    public void sendFastRegisterCheckCode(String phone){
        dispatcher.dispatch(new FastRegisterAction(FastRegisterAction.FAST_REGISTER_SEND_CODE_START));
        apiService.sendCheckCode(phone).enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                dispatcher.dispatch(new FastRegisterAction(FastRegisterAction.FAST_REGISTER_SEND_CODE_FINISH));
                BaseModel baseModel = response.body();
                if (baseModel.isSuccess()){
                    dispatcher.dispatch(new FastRegisterAction(FastRegisterAction.FAST_REGISTER_SEND_CODE_SUCCESS));
                }
                String msg = baseModel.getMessage();
                dispatcher.dispatch(new Action(FastRegisterAction.FAST_REGISTER_SEND_CODE_ERROR_MESSAGE, msg));
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                dispatcher.dispatch(new FastRegisterAction(FastRegisterAction.FAST_REGISTER_SEND_CODE_FINISH));
                dispatcher.dispatch(new FastRegisterAction(FastRegisterAction.FAST_REGISTER_SEND_CODE_FAIL));
            }
        });
    }

    public void fastRegister(String mobile, String validCode, String beInviteCode){
        dispatcher.dispatch(new FastRegisterAction(FastRegisterAction.FAST_REGISTER_REQUEST_START));
        apiService.fastRegister(mobile, validCode, beInviteCode, "android").enqueue(new Callback<BaseModel<User>>() {
            @Override
            public void onResponse(Call<BaseModel<User>> call, Response<BaseModel<User>> response) {
                dispatcher.dispatch(new FastRegisterAction(FastRegisterAction.FAST_REGISTER_REQUEST_FINISH));
                BaseModel baseModel = response.body();
                if (baseModel != null){
                    if (baseModel.isSuccess()){
                        User user = (User) baseModel.getRows();
                        dispatcher.dispatch(new FastRegisterAction(FastRegisterAction.FAST_REGISTER_REQUEST_SUCCESS, user));
                    }
                    String msg = baseModel.getMessage();
                    dispatcher.dispatch(new Action(FastRegisterAction.FAST_REGISTER_REQUEST_ERROR_MESSAGE, msg));
                }
            }

            @Override
            public void onFailure(Call<BaseModel<User>> call, Throwable t) {
                dispatcher.dispatch(new FastRegisterAction(FastRegisterAction.FAST_REGISTER_REQUEST_FINISH));
                dispatcher.dispatch(new FastRegisterAction(FastRegisterAction.FAST_REGISTER_REQUEST_FAIL));
            }
        });
    }
}
