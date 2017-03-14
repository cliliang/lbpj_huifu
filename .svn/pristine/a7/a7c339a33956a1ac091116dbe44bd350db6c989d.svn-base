package com.baluobo.user.router;

import android.content.Context;

import com.baluobo.common.module.RouterBase;
import com.baluobo.user.ui.AboutUsActivity;
import com.baluobo.user.ui.AccountSecurityActivity;
import com.baluobo.user.ui.BankCardManageActivity;
import com.baluobo.user.ui.ChangeLoginPsdActivity;
import com.baluobo.user.ui.ChangeMobileStepOneActivity;
import com.baluobo.user.ui.ChangeMobileStepTwoActivity;
import com.baluobo.user.ui.ContactUsActivity;
import com.baluobo.user.ui.FastRegisterActivity;
import com.baluobo.user.ui.FeedbackActivity;
import com.baluobo.user.ui.FeedbackSuccessActivity;
import com.baluobo.user.ui.FuLiActivity;
import com.baluobo.user.ui.GestureEditActivity;
import com.baluobo.user.ui.GestureVerifyActivity;
import com.baluobo.user.ui.InvestDetailActivity;
import com.baluobo.user.ui.LoginActivity;
import com.baluobo.user.ui.LuoboKuaiZhuanActivity;
import com.baluobo.user.ui.MessageInfoActivity;
import com.baluobo.user.ui.MoreActivity;
import com.baluobo.user.ui.MsgCenterActivity;
import com.baluobo.user.ui.MyInvestActivity;
import com.baluobo.user.ui.RechargeActivity;
import com.baluobo.user.ui.RedeemActivity;
import com.baluobo.user.ui.RedeemListActivity;
import com.baluobo.user.ui.StaticWebActivity;
import com.baluobo.user.ui.TotalAssetsActivity;
import com.baluobo.user.ui.TradingRecordActivity;
import com.baluobo.user.ui.WebActivity;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/10.
 */
public class UserRouter extends RouterBase {
    private static UserRouter instance;
    private UserRouter(Context context) {
        super(context);
        maps.put(UserUI.LoginActivity, LoginActivity.class);
//        maps.put(UserUI.RegisterActivity, RegisterActivity.class);
//        maps.put(UserUI.RetrieveStepOne, RetrieveStepOneActivity.class);
//        maps.put(UserUI.RetrieveStepTwo, RetrieveStepTwoActivity.class);
//        maps.put(UserUI.AccountInfo, AccountInfoActivity.class);
        maps.put(UserUI.More, MoreActivity.class);
        maps.put(UserUI.ContactUs, ContactUsActivity.class);
        maps.put(UserUI.AboutUs, AboutUsActivity.class);
        maps.put(UserUI.Feedback, FeedbackActivity.class);
        maps.put(UserUI.MsgCenter, MsgCenterActivity.class);
        maps.put(UserUI.WebActivity, WebActivity.class);
        maps.put(UserUI.RechargeActivity, RechargeActivity.class);
        maps.put(UserUI.AccountSecurity, AccountSecurityActivity.class);
        maps.put(UserUI.ChangeMobileStepOne, ChangeMobileStepOneActivity.class);
        maps.put(UserUI.ChangeMobileStepTwo, ChangeMobileStepTwoActivity.class);
        maps.put(UserUI.ChangeLoginPsd, ChangeLoginPsdActivity.class);
        maps.put(UserUI.TotalAssets, TotalAssetsActivity.class);
        maps.put(UserUI.TradingRecord, TradingRecordActivity.class);
        maps.put(UserUI.MyInvest, MyInvestActivity.class);
        maps.put(UserUI.LuoboKuaiZhuan, LuoboKuaiZhuanActivity.class);
        maps.put(UserUI.RedeemActivity, RedeemListActivity.class);
        maps.put(UserUI.MessageInfo, MessageInfoActivity.class);
        maps.put(UserUI.BankCardManager, BankCardManageActivity.class);
        maps.put(UserUI.InvestDetail, InvestDetailActivity.class);
        maps.put(UserUI.RedeemBackActivity, RedeemActivity.class);
        maps.put(UserUI.StaticWeb, StaticWebActivity.class);
        maps.put(UserUI.GestureEdit, GestureEditActivity.class);
        maps.put(UserUI.GestureVerify, GestureVerifyActivity.class);
        maps.put(UserUI.FuliCenter, FuLiActivity.class);
        maps.put(UserUI.FeedbackSuccess, FeedbackSuccessActivity.class);
        maps.put(UserUI.FastRegister, FastRegisterActivity.class);
    }

    public static UserRouter getInstance(Context cnt){
        if (instance == null){
            synchronized (UserRouter.class){
                instance = new UserRouter(cnt);
            }
        }
        return instance;
    }
}
