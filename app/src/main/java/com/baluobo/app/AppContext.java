package com.baluobo.app;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.baluobo.R;
import com.baluobo.app.flux.Action;
import com.baluobo.app.model.BaseModel;
import com.baluobo.app.router.MainRouter;
import com.baluobo.common.config.AppConfig;
import com.baluobo.common.db.LuoBoDBM;
import com.baluobo.common.db.LuoBoDatabaseOpenHelper;
import com.baluobo.common.module.ModBase;
import com.baluobo.common.module.ModuleID;
import com.baluobo.common.net.APIClient;
import com.baluobo.common.tools.LoadingDialog;
import com.baluobo.common.tools.LuoboImageLoader;
import com.baluobo.common.tools.UIHelper;
import com.baluobo.common.tools.Util;
import com.baluobo.common.views.gesture.GestureCallBack;
import com.baluobo.common.views.gesture.GestureContentView;
import com.baluobo.find.FindModule;
import com.baluobo.home.HomeModule;
import com.baluobo.home.router.HomeUI;
import com.baluobo.home.ui.MainActivity;
import com.baluobo.product.ProductModule;
import com.baluobo.user.UserModule;
import com.baluobo.user.actions.LoginAction;
import com.baluobo.user.actions.SecurityAction;
import com.baluobo.user.model.User;
import com.baluobo.user.router.UserRouter;
import com.baluobo.user.router.UserUI;
import com.baluobo.user.ui.GestureEditActivity;
import com.baluobo.user.ui.LoginActivity;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;
import com.umeng.socialize.PlatformConfig;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Stack;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/4.
 */
public class AppContext extends Application {
    private List<ModBase> mods = new ArrayList<>();
    private SQLiteDatabase database;
    private User user;
    private Dialog dialog;
    private Stack<Activity> activityStack = new Stack<>();
    private GestureContentView gestureView;
    private boolean validGesture = false;
    private Activity activeActivity;
    private LoadingDialog loadingDialog;
    private AppConfig appConfig;
    private DismissGestureDialogReceiver receiver;
    private class DismissGestureDialogReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            validGesture = true;
            if (dialog != null && !activeActivity.isFinishing() && dialog.isShowing()){
                dialog.dismiss();
            }
        }
    }
    @Override
    public void onCreate() {
        super.onCreate();
        initAccount();
        getMods();
        initImageLoader(this);
        pushMessageHandler();
        configUMengShare();
        handBackFromPressHome();

        receiver = new DismissGestureDialogReceiver();
        IntentFilter intentFilter = new IntentFilter(SecurityAction.RECEIVER_ACTION_DISMISS_GESTURE_DIALOG);
        registerReceiver(receiver, intentFilter);
        appConfig = AppConfig.getInstance(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        unregisterReceiver(receiver);
    }

    private void configUMengShare(){
        PlatformConfig.setWeixin(AppConfig.WX_APP_ID, AppConfig.WX_APP_SECRET);
        PlatformConfig.setSinaWeibo(AppConfig.SINA_APP_KEY, AppConfig.SINA_APP_SECRET);
        PlatformConfig.setQQZone(AppConfig.QZONE_APP_ID, AppConfig.QZONE_APP_KEY);
    }

    //App增加模块时，都要在这里注册
    private void fillMods() {
        if (mods.size() == 0) {
            synchronized (AppContext.class) {
                mods.add(HomeModule.getInstance());
                mods.add(UserModule.getInstance());
                mods.add(ProductModule.getInstance());
                mods.add(FindModule.getInstance());
            }
        }
    }

    public List<ModBase> getMods() {
        fillMods();
        return mods;
    }

    private static void initImageLoader(Context context) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageForEmptyUri(R.drawable.error_image)
                .showImageOnFail(R.drawable.error_image)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app
                .defaultDisplayImageOptions(options)
                .build();
        LuoboImageLoader.getInstance().init(config);
    }

    private void handBackFromPressHome(){
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {
                if (activityStack.size() == 0 && !activity.getLocalClassName().equals("home.ui.StartActivity")){
                    if (dialog != null && !activity.isFinishing()){
                        dialog.dismiss();
                    }
                    if (user != null && !TextUtils.isEmpty(user.getHandPassword())){
                        showGestureDialog(activity);
                    }
                }
                activityStack.add(activity);
            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
                activityStack.remove(activity);
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    private void pushMessageHandler(){
        PushAgent mPushAgent = PushAgent.getInstance(this);
        mPushAgent.setDebugMode(true);
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler(){
            @Override
            public void dealWithCustomAction(Context context, UMessage uMessage) {
                super.dealWithCustomAction(context, uMessage);
                String customString = uMessage.custom;
                if (!TextUtils.isEmpty(customString)){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("pushMessage", customString);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        };
        mPushAgent.setNotificationClickHandler(notificationClickHandler);
    }

    private void initAccount(){
        LuoBoDatabaseOpenHelper openHelper = LuoBoDatabaseOpenHelper.getInstance(this);
        database = openHelper.getWritableDatabase();
        user = LuoBoDBM.getInstance(this).getUser();
    }

    public User getUser(){
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SQLiteDatabase getDatabase(){
        return database;
    }

    private void showGestureDialog(Activity activity){
        activeActivity = activity;
        dialog = new Dialog(activity, R.style.GestureDialogStyle);
        Window win = dialog.getWindow();
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        win.setAttributes(lp);

        View view = LayoutInflater.from(activity).inflate(R.layout.gesture_verify_activity_layout, null);
        setGestureDialogData(view);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        validGesture = false;
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (!validGesture){
                    AppManager.getAppManager().appTerminate(getApplicationContext());
                }
            }
        });
    }

    private void setGestureDialogData(View rootView){
        TextView dayView = (TextView) rootView.findViewById(R.id.gesture_day_view);
        TextView monthView = (TextView) rootView.findViewById(R.id.gesture_month_view);
        TextView weekView = (TextView) rootView.findViewById(R.id.gesture_week_view);
        Calendar calendar = Calendar.getInstance();
        dayView.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
        monthView.setText(String.format(Locale.CHINESE, "%tB", new Date()));
        weekView.setText(String.format(Locale.CHINESE, "%tA", new Date()));

        TextView verifyLoginPsd = (TextView) rootView.findViewById(R.id.gesture_verify_login_psd);
        verifyLoginPsd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showVerifyLoginPsdDialog();
            }
        });
        TextView loseLoginPsd = (TextView) rootView.findViewById(R.id.gesture_verify_lose_login_psd_view);
        loseLoginPsd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(SecurityAction.SECURITY_GESTURE_VERIFY_ACTION, SecurityAction.GESTURE_VERIFY_ACTION_HOME);
                MainRouter.getInstance(activeActivity).showActivity(ModuleID.USER_MODULE_ID, UserUI.ChangeLoginPsd, bundle);
            }
        });

        TextView helloView = (TextView) rootView.findViewById(R.id.gesture_verify_hello_view);
        FrameLayout gestureRootView = (FrameLayout) rootView.findViewById(R.id.gesture_verify_root_view);
        String gesture = "";
        if (user != null){
            gesture = user.getHandPassword();
            String phone = user.getMobile();
            if (!TextUtils.isEmpty(phone) && phone.length() == 11){
                phone = phone.substring(0, 3) + "****" + phone.substring(7, phone.length());
                helloView.setText(String.format(Locale.US, getString(R.string.gesture_verify_hello_world), phone));
            }
        }

        if (TextUtils.isEmpty(gesture)){
            if (dialog != null){
                dialog.dismiss();
            }
        }

        gestureView = new GestureContentView(this, true, gesture, new GestureCallBack() {
            @Override
            public void onGestureCodeInput(String inputCode) {

            }

            @Override
            public void checkedSuccess() {
                appConfig.setGestureErrorNumber(0);
                validGesture = true;
                gestureView.clearDrawlineState(0L);
                dialog.dismiss();
            }

            @Override
            public void checkedFail() {
                gestureView.clearDrawlineState(500L);
                int number = appConfig.getGestureErrorNumber();
                appConfig.setGestureErrorNumber(number + 1);
                if (number >= 4){
                    appConfig.setGestureErrorNumber(0);
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    intent.putExtra(LoginAction.ACTION_LOGIN_TYPE, LoginAction.ACTION_LOGIN_TYPE_GESTURE);
                    intent.putExtra(LoginAction.ACTION_LOGIN_PHONE, user.getMobile());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else {
                    UIHelper.ToastMessage(getApplicationContext(), String.format(Locale.US, getString(R.string.gesture_error_number), 4 - number));
                }
            }
        });
        gestureView.setParentView(gestureRootView);
    }

    private void showVerifyLoginPsdDialog(){
        final Dialog psdDialog = new Dialog(activeActivity, R.style.DialogStyle);
        View view = LayoutInflater.from(this).inflate(R.layout.verify_login_dialog_layout, null);
        final EditText editText = (EditText) view.findViewById(R.id.verify_input_psd_view);
        view.findViewById(R.id.verify_cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                psdDialog.dismiss();
            }
        });
        view.findViewById(R.id.verify_ok_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String psd = editText.getText().toString();
                if (TextUtils.isEmpty(psd)){
                    UIHelper.ToastMessage(getApplicationContext(), getString(R.string.please_input_password));
                    return;
                }
                if (user != null){
                    userLogin(user.getMobile(), psd);
                }
                psdDialog.dismiss();
            }
        });
        psdDialog.setContentView(view);
        psdDialog.show();
    }

    //手势密码错误时，可以用登录密码操作
    public void userLogin(String phone, String psd) {
        loadingDialog = new LoadingDialog.Build(activeActivity).create();
        showLoadingDialog();
        psd = Util.MD5(psd);
        APIClient.getInstance().getLoginData(phone, psd).enqueue(new Callback<BaseModel<User>>() {
            @Override
            public void onResponse(Call<BaseModel<User>> call, Response<BaseModel<User>> response) {
                dismissLoadingDialog();
                BaseModel<User> baseModel = response.body();
                if (baseModel != null){
                    if (baseModel.isSuccess()){
                        User loadUser = baseModel.getRows();
                        if (loadUser != null){
                            loadUser.setToken(baseModel.getToken());
                            user = loadUser;
                            LuoBoDBM luoBoDBM = LuoBoDBM.getInstance(getApplicationContext());
                            luoBoDBM.insertUser(loadUser);
                            //验证登录密码成功
                            Bundle bundle = new Bundle();
                            bundle.putString(SecurityAction.SECURITY_GESTURE_EDIT_ACTION, SecurityAction.GESTURE_MODIFY_HOME);
                            MainRouter.getInstance(activeActivity).showActivity(ModuleID.USER_MODULE_ID, UserUI.GestureEdit, bundle);
                        }
                    }else {
                        String msg = baseModel.getMessage();
                        UIHelper.ToastMessage(getApplicationContext(), msg);
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel<User>> call, Throwable t) {
                dismissLoadingDialog();
                UIHelper.ToastMessage(getApplicationContext(), getString(R.string.net_work_error));
            }
        });
    }

    public void showLoadingDialog(){
        if (loadingDialog != null && !loadingDialog.isShowing()){
            loadingDialog.show();
        }
    }

    public void dismissLoadingDialog(){
        if (!activeActivity.isFinishing()){
            if (loadingDialog != null && loadingDialog.isShowing()){
                loadingDialog.dismiss();
            }
        }
    }
}
