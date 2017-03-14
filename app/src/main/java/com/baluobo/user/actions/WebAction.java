package com.baluobo.user.actions;

import com.baluobo.app.flux.Action;
import com.baluobo.user.router.UserUI;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/16.
 */
public class WebAction extends Action<String> {
    public static final String WEB_REQUEST_TYPE = "web_request_type";
    public static final String REQUEST_OPEN_ACCOUNT = "request_open_account";
    public static final String REQUEST_BUNDLE_BANK_CARD = "request_bundle_bank_card";
    public static final String REQUEST_UNBOUND_BANK_CARD = "request_unbound_bank_card";
    public static final String REQUEST_RECHARGE = "request_recharge";
    public static final String REQUEST_WITHDRAW = "request_withdraw";
    public static final String REQUEST_BUY = "request_buy";
    public static final String REQUEST_BOUND_MONEY = "request_bound_money";
    public static final String REQUEST_PAY_ORDER_ID = "request_pay_order_id";
    public static final String REQUEST_REDEEM = "request_redeem";
    public static final String REQUEST_HOME_DECLARATION = "request_home_declaration";
    public static final String REQUEST_DECLARATION_ID = "request_declaration_id";
    public static final String REQUEST_HOME_BANNER = "request_home_banner";
    public static final String REQUEST_BANNER_URL = "request_banner_url";
    public static final String STATIC_WEB_BOUND_TYPE = "static_web_bound_type";
    public static final String STATIC_WEB_TYPE_SERVICE = "static_web_type_service";
    public static final String STATIC_WEB_TYPE_ABOUT_US = "static_web_type_about_us";
    public static final String REQUEST_UNWRAP_BANK_CARD = "request_unwrap_bank_card";
    public static final String REQUEST_UNWRAP_CARD_URL = "request_unwrap_card_url";
    public static final String REQUEST_MALL = "request_load_mall_url";
    public static final String REQUEST_HELP = "request_load_help_url";
    public static final String RESULT_INTENT_EXTRA = "result_intent_extra";
    public static final String ONLINE_SERVICE = "online_service";
    public static final String FIND_NEWS_DETAIL = "find_news_detail";
    public static final String FIND_NEWS_ID = "find_news_id";
    public static final String GOOD_DAY_ACTIVITY = "good_day_activity";
    public static final String FULI_RULE_ACTIVITY = "fuli_rule_activity";
    public static final String GOOD_DAY_WEB = "good_day_web_url";
    public static final int BUY_PRODUCT_REQUEST_CODE = UserUI.WebActivity + 10;
    public static final int MALL_TO_LOGIN = UserUI.WebActivity + 11;
    public static final int MALL_TO_LOGIN_FROM_REGISTER = UserUI.WebActivity + 12;

    public static final String REQUEST_ABOUT_US = "request_about_us";
    public WebAction(String type, String data) {
        super(type, data);
    }
    public WebAction(String type){
        super(type, null);
    }
}
