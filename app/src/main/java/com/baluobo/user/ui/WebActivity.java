package com.baluobo.user.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.baluobo.R;
import com.baluobo.app.AppContext;
import com.baluobo.app.AppManager;
import com.baluobo.app.router.MainRouter;
import com.baluobo.app.ui.BaseToolBarActivity;
import com.baluobo.common.module.ModuleID;
import com.baluobo.common.net.APIClient;
import com.baluobo.common.tools.SharePopupWindow;
import com.baluobo.common.tools.UIHelper;
import com.baluobo.find.router.FindUI;
import com.baluobo.home.router.HomeRouter;
import com.baluobo.home.router.HomeUI;
import com.baluobo.product.actions.ProductInfoAction;
import com.baluobo.product.actions.ProductListAction;
import com.baluobo.product.router.ProductUI;
import com.baluobo.product.views.OnXfermodeTouchListener;
import com.baluobo.product.views.XfermodeView;
import com.baluobo.user.actions.WebAction;
import com.baluobo.user.model.ProductEnum;
import com.baluobo.user.model.User;
import com.baluobo.user.router.UserRouter;
import com.baluobo.user.router.UserUI;
import com.umeng.socialize.UMShareAPI;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Locale;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/16.
 */
public class WebActivity extends BaseToolBarActivity {
    private WebView webView;
    private ProgressBar progressBar;
    private String loginURL = "website/login.html";
    private String mallURL = "website/mall.html";
    private String turnplate = "website/turnplate.html";
    private String bannerURL;
    private boolean canGoBack = true; //有些界面，点击后退都退出web界面，默认是回到前一个web界面的
    private boolean loginViaBanner = false;  //从banner活动页面跳转到登录
    private boolean backMall = false; //直接返回商城首页
    private int shareType;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_activity_layout);
        setupViews();
        initData();
    }

    private void initData(){
        AppContext appContext = (AppContext) getApplicationContext();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            String requestType = bundle.getString(WebAction.WEB_REQUEST_TYPE);
            User user = appContext.getUser();
            int userId = -1;
            String token = "";
            if (user != null){
                userId = user.getUserId();
                token = user.getToken();
            }
            switch (requestType){
//                case WebAction.REQUEST_OPEN_ACCOUNT:
//                    canGoBack = false;
//                    String openAccountURL = APIClient.getCGTURL() + APIClient.cgtRegister + "?userId=" + userId + "&token=" + token + "&deviceType=0";
//                    webView.loadUrl(openAccountURL);
//                    break;
//                case WebAction.REQUEST_BUNDLE_BANK_CARD:
//                    canGoBack = false;
//                    String boundBankURL = APIClient.getCGTURL() + APIClient.cgtBoundCard + "?userId=" + userId + "&token=" + token + "&deviceType=0";
//                    webView.loadUrl(boundBankURL);
//                    break;
//                case WebAction.REQUEST_RECHARGE:
//                    canGoBack = false;
//                    double rechargeMoney = bundle.getDouble(WebAction.REQUEST_BOUND_MONEY);
//                    String rechargeURL = APIClient.getCGTURL() + APIClient.cgtRecharge + "?userId=" + userId + "&token=" + token + "&money=" + rechargeMoney + "&deviceType=0";
//                    webView.loadUrl(rechargeURL);
//                    break;
//                case WebAction.REQUEST_WITHDRAW:
//                    canGoBack = false;
//                    double withdrawMoney = bundle.getDouble(WebAction.REQUEST_BOUND_MONEY);
//                    String withdrawURL = APIClient.getCGTURL() + APIClient.cgtWithdraw + "?userId=" + userId + "&token=" + token + "&money=" + withdrawMoney + "&deviceType=0";
//                    webView.loadUrl(withdrawURL);
//                    break;
//                case WebAction.REQUEST_BUY:
//                    canGoBack = false;
//                    int orderId = bundle.getInt(WebAction.REQUEST_PAY_ORDER_ID);
//                    String buyURL = APIClient.getCGTURL() + APIClient.cgtReTransaction + "?userId=" + userId + "&token=" + token + "&buyOrderId=" + orderId + "&deviceType=0";
//                    webView.loadUrl(buyURL);
//                    break;
//                case WebAction.REQUEST_UNBOUND_BANK_CARD:
//                    canGoBack = false;
//                    String unBoundURL = APIClient.getCGTURL() + APIClient.cgtUnBindBankCard + "?userId=" + userId + "&deviceType=0";
//                    webView.loadUrl(unBoundURL);
//                    break;

                case WebAction.REQUEST_OPEN_ACCOUNT:
                    canGoBack = false;
                    String openAccountURL = APIClient.getHuifuURL() + APIClient.huifuRegister + "?userId=" + userId + "&token=" + token;
                    webView.loadUrl(openAccountURL);
                    break;
                case WebAction.REQUEST_BUNDLE_BANK_CARD:
                    canGoBack = false;
                    String boundBankURL = APIClient.getHuifuURL() + APIClient.huifuBank + "?userId=" + userId + "&token=" + token;
                    webView.loadUrl(boundBankURL);
                    break;
                case WebAction.REQUEST_RECHARGE:
                    canGoBack = false;
                    double rechargeMoney = bundle.getDouble(WebAction.REQUEST_BOUND_MONEY);
                    String rechargeURL = APIClient.getHuifuURL() + APIClient.huifuRecharge + "?userId=" + userId + "&token=" + token + "&money=" + rechargeMoney;
                    webView.loadUrl(rechargeURL);
                    break;
                case WebAction.REQUEST_WITHDRAW:
                    canGoBack = false;
                    double withdrawMoney = bundle.getDouble(WebAction.REQUEST_BOUND_MONEY);
                    String withdrawURL = APIClient.getHuifuURL() + APIClient.huifuWithdraw + "?userId=" + userId + "&token=" + token + "&money=" + withdrawMoney;
                    webView.loadUrl(withdrawURL);
                    break;
                case WebAction.REQUEST_BUY:
                    canGoBack = false;
                    int orderId = bundle.getInt(WebAction.REQUEST_PAY_ORDER_ID);
                    String buyURL = APIClient.getHuifuURL() + APIClient.huifuPay + "?userId=" + userId + "&token=" + token + "&buyOrderId=" + orderId;
                    webView.loadUrl(buyURL);
                    break;


                case WebAction.REQUEST_HOME_BANNER:
                    loginViaBanner = true;
                    canGoBack = false;
//                    setTitle(getString(R.string.activity_detail_title));
                    bannerURL = bundle.getString(WebAction.REQUEST_BANNER_URL);
                    if (user != null){
                        bannerURL += "?userId=" + user.getUserId();
                    }
                    webView.loadUrl(bannerURL);
                    break;
                case WebAction.REQUEST_UNWRAP_BANK_CARD:
//                    setTitle(getString(R.string.unwrap_bank_card));
                    String unwrapUrl = bundle.getString(WebAction.REQUEST_UNWRAP_CARD_URL);
                    webView.loadUrl(unwrapUrl);
                    break;
                case WebAction.REQUEST_ABOUT_US:
//                    setTitle(getString(R.string.about_us));
                    String aboutURL = APIClient.getBannerHost() + "baluobo/before/appH5/aboutUs.html";
                    webView.loadUrl(aboutURL);
                    break;
                case WebAction.REQUEST_HOME_DECLARATION:
//                    setTitle(getString(R.string.declaration_detail));
                    int declarationId = bundle.getInt(WebAction.REQUEST_DECLARATION_ID);
                    String declarationURL = APIClient.getBannerHost() + "notice/" + declarationId + ".html";
                    webView.loadUrl(declarationURL);
                    break;
                case WebAction.REQUEST_MALL:
//                    setTitle(getString(R.string.find_blank_ji_fen_title));
                    String alert = "";
                    if (user != null){
                        alert = "?userId=" + user.getUserId();
                    }
                    String mallUrl = APIClient.getBannerHost() + mallURL + alert;
                    webView.loadUrl(mallUrl);
                    break;
                case WebAction.REQUEST_HELP:
//                    setTitle(getString(R.string.find_blank_help_title));
                    String helpURL = APIClient.getBannerHost() + "wenti.html";
                    webView.loadUrl(helpURL);
                    break;
                case WebAction.ONLINE_SERVICE:
//                    setTitle(getString(R.string.online_service_title));
                    String onlineURL = "https://www.sobot.com/chat/oldh5/index.html?sysNum=4a452db0d290432ba19f469a0c8b1f15&source=2";
                    webView.loadUrl(onlineURL);
                    break;
                case WebAction.FIND_NEWS_DETAIL:
//                    setTitle(getString(R.string.find_news_title));
                    int newsId = bundle.getInt(WebAction.FIND_NEWS_ID);
                    String newsURL = APIClient.getBannerHost() + "story/" + newsId + ".html";
                    webView.loadUrl(newsURL);
                    break;
                case WebAction.GOOD_DAY_ACTIVITY:
//                    setTitle(getString(R.string.find_news_title));
                    String dayURL = bundle.getString(WebAction.GOOD_DAY_WEB);
                    webView.loadUrl(dayURL);
                    break;
                case WebAction.FULI_RULE_ACTIVITY:
//                    setTitle(getString(R.string.red_packet_rule));
                    String ruleURL = APIClient.getBannerHost() + "website/redRules.html";
                    webView.loadUrl(ruleURL);
                    break;
            }
        }
    }

    @Override
    protected void setupViews() {
        super.setupViews();
        progressBar = (ProgressBar) findViewById(R.id.web_activity_progress);
        webView = (WebView) findViewById(R.id.web_activity_view);
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        webView.clearCache(true);
        webView.clearHistory();
        webView.clearFormData();
        WebSettings webSettings = webView.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new LuoboJavaScriptInterface(this), "android");
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLoadWithOverviewMode(true);
        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                //产品购买成功，返回就直接退出
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK){
                    handleBack();
                    return true;
                }
                return false;
            }
        });
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);

            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
                if (!TextUtils.isEmpty(url) && (url.contains(mallURL) || url.contains(turnplate))){
                    webView.clearHistory();
                }

                String title = view.getTitle();
                if (!TextUtils.isEmpty(title)){
                    setTitle(title);
                    //实物商品兑换成功后和在立即兑换界面，返回商城首页
                    if (title.equals(getString(R.string.exchange_gift_now)) || title.equals(getString(R.string.exchange_gift_success))){
                        backMall = true;
                    }else{
                        backMall = false;
                    }
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (progressBar.getVisibility() != View.VISIBLE){
                    progressBar.setVisibility(View.VISIBLE);
                }

                //商城登录界面跳转
                if (!TextUtils.isEmpty(url) && url.contains(loginURL)){
                    UserRouter.getInstance(WebActivity.this).showActivityForResult(WebActivity.this, UserUI.LoginActivity, WebAction.MALL_TO_LOGIN, null);
                    return;
                }
                //产品购买成功，返回刮刮乐红包信息  url.contains("prizeMoney")
                if (url.contains("prizeName") && url.contains("prizeId")){
                    String[] splitURL = url.split("\\?");
                    if (splitURL.length == 2){
                        String resultExtra = splitURL[1];
                        getGuaGuaContent(resultExtra);
                    }
                }
                //转盘抽奖界面处理
                if (!TextUtils.isEmpty(url) && url.endsWith(turnplate)){
                    if (mUser != null){
                        url = url + "?userId=" + mUser.getUserId();
                        view.loadUrl(url);
                        return;
                    }
                }
            }
        });
    }

    private void handleBack(){
        if (backMall){
            User user = appContext.getUser();
            String stub = "";
            if (user != null){
                stub = "?userId=" + user.getUserId();
            }
            String url = APIClient.getBannerHost() + mallURL + stub;
            webView.loadUrl(url);
            backMall = false;
        }else {
            if (canGoBack){
                if (webView.canGoBack()){
                    webView.goBack();
                }else {
                    finishWeb();
                }
            }else {
                finishWeb();
            }
        }
    }

    @Override
    public void onBackClick() {
        handleBack();
    }

    private void finishWeb(){
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        AppManager.getAppManager().finishActivity(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get( this ).onActivityResult( requestCode, resultCode, data);
        //从积分商城或活动转盘页面跳转到登录，成功后返回
        if (requestCode == WebAction.MALL_TO_LOGIN && resultCode == RESULT_OK){
            User user = appContext.getUser();
            String stub = "";
            if (user != null){
                stub = "?userId=" + user.getUserId();
            }
            String url;

            if (loginViaBanner){//从banner活动页面跳转到登录
                url = bannerURL + stub;
            }else {//从积分商城登录返回，回去商城首页
                url = APIClient.getBannerHost() + mallURL + stub;
            }
            webView.loadUrl(url);
            //跳转到登录界面但未登录，返回
        }else if (requestCode  == WebAction.MALL_TO_LOGIN && resultCode == RESULT_OK + 1){
            if (webView.canGoBack()){
                webView.goBack();
            }
        }
    }

    private void getGuaGuaContent(String result){
        if (TextUtils.isEmpty(result)){
            return;
        }
        String prizeName = "";
        String prizeId = "";
        String prizeMoney = "";
        String[] resultSplit = result.split("&");
        if (resultSplit.length > 0){
            for (String code : resultSplit){
                String[] split = code.split("=");
                if (split.length == 2){
                    if (split[0].equals("prizeName")){
                        prizeName = split[1];
                    }else if (split[0].equals("prizeId")){
                        prizeId = split[1];
                    }else if (split[0].equals("prizeMoney")){
                        prizeMoney = split[1];
                    }
                }
            }
        }

        if (!TextUtils.isEmpty(prizeName) && !TextUtils.isEmpty(prizeId)){
            showGuaGuaDialog(prizeName, prizeMoney, prizeId);
        }
    }

    private void showGuaGuaDialog(String prizeName, String prizeMoney, final String prizeId){
        Dialog dialog = new Dialog(this, R.style.DialogStyle);
        View view = LayoutInflater.from(this).inflate(R.layout.show_gua_gua_dialog_layout, null);
        dialog.setContentView(view);
        TextView moneyView = (TextView) view.findViewById(R.id.gua_gua_money);
        TextView typeView = (TextView) view.findViewById(R.id.gua_gua_type);

        if (!TextUtils.isEmpty(prizeMoney) && !TextUtils.isEmpty(prizeId)){
            try {
                int prize =Integer.parseInt(prizeId);
                if (mUser != null){
                    initDependencies();
                    appActionsCreator.takeGuaRedTicket(mUser.getUserId(), prize, mUser.getToken());
                }
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
        }

        try {
            prizeName = URLDecoder.decode(prizeName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (!TextUtils.isEmpty(prizeMoney)){
            moneyView.setVisibility(View.VISIBLE);
            if (!prizeName.equals(getResources().getString(R.string.gua_gua_jifen))){
                moneyView.setText(String.format(Locale.US, getResources().getString(R.string.gua_gua_money), prizeMoney));
            }else {
                moneyView.setText(prizeMoney);
            }
        }else {
            moneyView.setVisibility(View.GONE);
        }
        typeView.setText(prizeName);
        dialog.show();
    }


    public class LuoboJavaScriptInterface {
        private Context mContext;
        public LuoboJavaScriptInterface(Context cnt){
            this.mContext = cnt;
        }

        //积分商城转盘页面分享，javascript调用此方法
        @JavascriptInterface
        public void shareResult() {
            shareType = 3;
            if (getSharePermissionSize(mSharePermissionList) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(WebActivity.this, mSharePermissionList, 100);
            }else {
                showShareWindow();
            }
        }

        //跳转到体验金界面
        @JavascriptInterface
        public void forExp(){
            MainRouter.getInstance(WebActivity.this).showActivity(ModuleID.PRODUCT_MODULE_ID, ProductUI.Experience);
        }

        //跳转到银票苗
        @JavascriptInterface
        public void skipYinPiaoMiao(){
            Bundle yinPiaoBundle = new Bundle();
            yinPiaoBundle.putInt(ProductListAction.PRODUCT_TYPE, ProductEnum.LUOBO_YIN_PIAO.getProductTypeId());
            MainRouter.getInstance(WebActivity.this).showActivity(ModuleID.PRODUCT_MODULE_ID, ProductUI.ProductList, yinPiaoBundle);
        }

        //跳转到首页
        @JavascriptInterface
        public void skipHomePage(){
//            MainRouter.getInstance(WebActivity.this).showActivity(ModuleID.HOME_MODULE_ID, HomeUI.MainActivity);
            AppManager.getAppManager().finishActivity(WebActivity.this);
        }

        //跳转到福利中心
        @JavascriptInterface
        public void skipFuliCenter(){
            MainRouter.getInstance(WebActivity.this).showActivity(ModuleID.USER_MODULE_ID, UserUI.FuliCenter);
            AppManager.getAppManager().finishActivity(WebActivity.this);
        }

        //跳转到邀请有礼
        @JavascriptInterface
        public void skipInvite(){
            if(mUser != null){
                HomeRouter.getInstance(WebActivity.this).showActivity(HomeUI.Invite);
            }else {
                MainRouter.getInstance(WebActivity.this).showActivity(ModuleID.USER_MODULE_ID, UserUI.LoginActivity);
            }
        }

        //跳转到萝卜快赚界面
        @JavascriptInterface
        public void skipLuoBoKuaiZhuan(){
            MainRouter.getInstance(appContext).showActivity(ModuleID.PRODUCT_MODULE_ID, ProductUI.KuaiZhuanInfo);
        }

        //跳转到积分商城
        @JavascriptInterface
        public void skipJiFenShangCheng(){
            String alert = "";
            if (mUser != null){
                alert = "?userId=" + mUser.getUserId();
            }
            String mallURL = APIClient.getBannerHost() + "website/mall.html" + alert;
            webView.loadUrl(mallURL);
        }

        //跳转到会员特权
        @JavascriptInterface
        public void skipVIPCenter(){
            MainRouter.getInstance(WebActivity.this).showActivity(ModuleID.FIND_MODULE_ID, FindUI.VIPCenter);
        }

        //跳转到帮助中心
        @JavascriptInterface
        public void skipHelpCenter(){
            String helpURL = APIClient.getBannerHost() + "wenti.html";
            webView.loadUrl(helpURL);
        }

        //弹出邀请弹窗
        @JavascriptInterface
        public void shareInvite(){
            shareType = 1;
            if (getSharePermissionSize(mSharePermissionList) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(WebActivity.this, mSharePermissionList, 100);
            }else {
                showShareWindow();
            }
        }
    }

    private void showShareWindow(){
        SharePopupWindow shareWindow = new SharePopupWindow(WebActivity.this);
        shareWindow.setMallShare(shareType);
        shareWindow.showAtLocation(findViewById(R.id.web_activity_block_view), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showShareWindow();
            } else {
                UIHelper.ToastMessage(this, getString(R.string.share_state_refuse));
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
