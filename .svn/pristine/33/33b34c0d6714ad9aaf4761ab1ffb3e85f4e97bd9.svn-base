package com.baluobo.common.config;

import android.content.Context;

/**
 * desc: 定义App的一些全局配置
 * Created by:chenliliang
 * Created on:16/5/4.
 */
public class AppConfig {
    //发布正式版本时，配置isDebugVersion为false
    public static boolean isDebugVersion = false;
    //发布新版本时，配置是否展示引导页面
    public static final boolean NEW_VERSION_SHOW_GUIDE = false;
    //账号在别处登录后，token失效Flag
    public static final int INVALID_TOKEN = -111;

    //当前版本是否已经显示过引导页面了
    private final String VERSION_SHOW_GUIDE = "version_showed_guide";
    //判断产品购买界面新手引导图
    private final String SHOWED_RULER_GUIDER = "showed_ruler_guider";
    //判断我的界面新手引导图
    private final String SHOWED_TOTAL_ASSETS_GUIDER = "showed_total_assets_guider";
    //判断我的理财界面新手引导图
    private final String SHOWED_INVEST_GUIDER = "showed_invest_guider";
    //解锁手势密码错误的次数，5次后跳转到登录界面
    private final String GESTURE_ERROR_NUMBER = "gesture_error_number";
    //更新提醒弹窗每天只显示一次
    private final String SHOW_UPDATE_WINDOW = "show_update_window";

    /**
     * 微信appid 真实环境和测试环境都是使用正式的微信appid
     */
    public static final String WX_APP_ID = "wx2645bf2111c29719";
    public static final String WX_APP_SECRET = "bc27b586caa70abaea5f9c3b686495b9";
    /**
     * QQappid 真实环境和测试环境都是使用正式的QQappid
     */
    public static final String QZONE_APP_ID = "1104866018";
    public static final String QZONE_APP_KEY = "k8tBbYHfkCCnu6Ht";
    /**
     * 新浪app_key
     */
    public static final String SINA_APP_KEY = "1602997150";
    public static final String SINA_APP_SECRET = "d618df513da660cc0771c6546cfca0f5";

    private SharePreferenceManager preferenceManager;
    private static AppConfig appConfig;
    private AppConfig(Context cnt){
        preferenceManager = SharePreferenceManager.newInstance(cnt);
    }
    public static AppConfig getInstance(Context context){
        if (appConfig == null){
            appConfig = new AppConfig(context);
        }
        return appConfig;
    }

    /**
     * 获取当前版本是否已经显示过引导页面
     * @return true:已经显示过了  false:还没有显示
     */
    public boolean hadShowGuide(){
        return preferenceManager.getBooeanValue(VERSION_SHOW_GUIDE);
    }

    public void setShowGuide(boolean show){
        preferenceManager.setValue(VERSION_SHOW_GUIDE, show);
    }

    public boolean isRulerShowed(){
        return preferenceManager.getBooeanValue(SHOWED_RULER_GUIDER);
    }

    public void setRulerShowed(boolean isShowed){
        preferenceManager.setValue(SHOWED_RULER_GUIDER, isShowed);
    }

    public boolean isTotalAssetsShowed(){
        return preferenceManager.getBooeanValue(SHOWED_TOTAL_ASSETS_GUIDER);
    }

    public void setTotalAssetsShowed(boolean isShowed){
        preferenceManager.setValue(SHOWED_TOTAL_ASSETS_GUIDER, isShowed);
    }

    public boolean isInvestShowed(){
        return preferenceManager.getBooeanValue(SHOWED_INVEST_GUIDER);
    }

    public void setInvestShowed(boolean isShowed){
        preferenceManager.setValue(SHOWED_INVEST_GUIDER, isShowed);
    }

    public void setGestureErrorNumber(int number){
        preferenceManager.setValue(GESTURE_ERROR_NUMBER, number);
    }

    public int getGestureErrorNumber(){
        return preferenceManager.getIntValue(GESTURE_ERROR_NUMBER);
    }

    public void setUpdateWindowDate(String date){
        preferenceManager.setValue(SHOW_UPDATE_WINDOW, date);
    }

    public String getUpdateWindowDate(){
        return preferenceManager.getStringValue(SHOW_UPDATE_WINDOW);
    }
}
