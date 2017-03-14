package com.baluobo.home.ui;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.baluobo.R;
import com.baluobo.app.AppManager;
import com.baluobo.app.flux.Store;
import com.baluobo.app.model.BaseModel;
import com.baluobo.app.router.MainRouter;
import com.baluobo.app.ui.BaseStatusBarActivity;
import com.baluobo.common.config.AppConfig;
import com.baluobo.common.module.ModuleID;
import com.baluobo.common.tools.FileUtils;
import com.baluobo.common.tools.LuoboImageLoader;
import com.baluobo.common.tools.UIHelper;
import com.baluobo.common.tools.Util;
import com.baluobo.find.actions.SignInAction;
import com.baluobo.find.stores.SignInStore;
import com.baluobo.find.ui.VIPCenterActivity;
import com.baluobo.home.actions.DotAction;
import com.baluobo.home.actions.VersionAction;
import com.baluobo.home.model.VersionInfo;
import com.baluobo.home.router.HomeUI;
import com.baluobo.home.stores.DotStore;
import com.baluobo.home.stores.VersionStore;
import com.baluobo.user.actions.WebAction;
import com.baluobo.user.model.User;
import com.baluobo.user.router.UserUI;
import com.baluobo.user.ui.LoginActivity;
import com.baluobo.user.ui.MsgCenterActivity;
import com.baluobo.user.ui.WebActivity;
import com.squareup.otto.Subscribe;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengRegistrar;
import com.xiaomi.market.sdk.UpdateResponse;
import com.xiaomi.market.sdk.UpdateStatus;
import com.xiaomi.market.sdk.XiaomiUpdateAgent;
import com.xiaomi.market.sdk.XiaomiUpdateListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends BaseStatusBarActivity {
    private final String HOME_TAG = "home_tag";
    private final String MINE_TAG = "mine_tag";
    private final String FIND_TAG = "find_tag";
    private FragmentTabHost tabHost;

    private TextView userDotView;
    private int[] titleIds = null;
    private int PHONE_STATE_REQUEST = HomeUI.MainActivity + 1;
    private DotStore dotStore;
    private SignInStore signInStore;
    private VersionStore versionStore;
    private AppConfig appConfig;
    private Dialog updateDialog;
    private boolean downloadAPK = false;
    private boolean forceUpdate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String[] tags = {HOME_TAG, FIND_TAG, MINE_TAG};
        titleIds = new int[]{R.string.home_tag, R.string.find_tag, R.string.mine_tag};
        final int iconIds[] = {R.drawable.home_tab_icon_selector, R.drawable.find_tab_icon_selector, R.drawable.mine_tabl_icon_selector};
        final Class fragments[] = {HomeFragment.class, FindFragment.class, MineFragment.class};

        tabHost = (FragmentTabHost) findViewById(R.id.main_fragment_tabhost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.main_content_fragment);
        int count = fragments.length;
        for (int i = 0; i < count; i++) {
            View tabView = getTabItemView(titleIds[i], iconIds[i]);
            TabHost.TabSpec tabSpec = tabHost.newTabSpec(tags[i]).setIndicator(tabView);
            tabHost.addTab(tabSpec, fragments[i], null);
        }
        tabHost.getTabWidget().setDividerDrawable(null);
        String md5 = Util.MD5("abc");
        Log.i("chen", md5);
        appConfig = AppConfig.getInstance(this);
        //小米检查更新
//        XiaomiUpdateAgent.setUpdateAutoPopup(false);
//        XiaomiUpdateAgent.setUpdateListener(new XiaomiUpdateListener() {
//            @Override
//            public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
//                switch (updateStatus) {
//                    case UpdateStatus.STATUS_UPDATE:
//                        // 有更新， UpdateResponse为本次更新的详细信息
//                        // 其中包含更新信息，下载地址，MD5校验信息等，可自行处理下载安装
//                        // 如果希望 SDK继续接管下载安装事宜，可调用
//                        String date = String.format(Locale.CHINA, "%tF", new Date());
//                        String update = appConfig.getUpdateWindowDate();
//                        if (!date.equals(update) && MainActivity.this != null  && !isFinishing()){
//                            showUpdateDialog();
//                        }
//                        break;
//                    case UpdateStatus.STATUS_NO_UPDATE:
//                        // 无更新， UpdateResponse为null
//                        break;
//                    case UpdateStatus.STATUS_NO_WIFI:
//                        // 设置了只在WiFi下更新，且WiFi不可用时， UpdateResponse为null
//                        break;
//                    case UpdateStatus.STATUS_NO_NET:
//                        // 没有网络， UpdateResponse为null
//                        break;
//                    case UpdateStatus.STATUS_FAILED:
//                        // 检查更新与服务器通讯失败，可稍后再试， UpdateResponse为null
//                        break;
//                    case UpdateStatus.STATUS_LOCAL_APP_FAILED:
//                        // 检查更新获取本地安装应用信息失败， UpdateResponse为null
//                        break;
//                    default:
//                        break;
//                }
//            }
//        });
//        checkNewVersion();

        PushAgent pushAgent = PushAgent.getInstance(this);
        pushAgent.enable();

        initDependencies();
        processPushIntent(getIntent());
    }

    @Override
    protected void initDependencies() {
        super.initDependencies();
        dotStore = DotStore.getInstance();
        signInStore = SignInStore.getInstance();
        versionStore = VersionStore.getInstance();
        dispatcher.register(dotStore);
        dispatcher.register(signInStore);
        dispatcher.register(versionStore);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dotStore.register(this);
        String device_token = UmengRegistrar.getRegistrationId(this);
        if (mUser != null && !TextUtils.isEmpty(device_token)) {
            //上传最新的device_token
            appActionsCreator.updateDeviceToken(mUser.getUserId(), mUser.getToken(), device_token);
        }
        if (mUser != null) {
            appActionsCreator.getUnreadMessage(mUser.getUserId(), mUser.getToken());

            //自动签到
            Date nowDate = new Date(Calendar.getInstance().getTimeInMillis());
            String nowString = String.format(Locale.CHINESE, "%tF", nowDate);
            Date signDate = mUser.getGetScoreTime();
            String signString = String.format(Locale.CHINESE, "%tF", signDate);
            if (!nowString.equals(signString)) {
                appActionsCreator.signIn(mUser.getUserId(), mUser.getToken());
            }
        }

//        检查服务器上是否有最新版本
        appActionsCreator.checkVersionInfo();

    }

    public void checkNewVersion() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, PHONE_STATE_REQUEST);
        } else {
            XiaomiUpdateAgent.update(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PHONE_STATE_REQUEST) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                XiaomiUpdateAgent.update(this);
            } else {
                UIHelper.ToastMessage(this, getString(R.string.phone_state_refuse));
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onStop() {
        super.onStop();
        dotStore.unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dispatcher.unregister(dotStore);
        dispatcher.unregister(signInStore);
        dispatcher.unregister(versionStore);
    }

    @Subscribe
    public void onDotStoreChange(Store.StoreChangeEvent event) {
        switch (event.getType()) {
            case DotAction.MAIN_DOT_ACTION_HAVE_UNREAD_MESSAGE:
                userDotView.setVisibility(View.VISIBLE);
                break;
            case DotAction.MAIN_DOT_ACTION_DONT_HAVE_UNREAD_MESSAGE:
                userDotView.setVisibility(View.GONE);
                break;
            case SignInAction.ACTION_REQUEST_SIGN_IN_SUCCESS:
                BaseModel baseModel = signInStore.getData();
                if (baseModel != null) {
                    User newInfo = (User) baseModel.getRows();
                    if (newInfo != null) {
                        newInfo.setGetScoreTime(Calendar.getInstance().getTimeInMillis());
                        mUser = newInfo;
                        appContext.setUser(mUser);
                        updateUserToken(mUser.getUserId(), mUser.getToken(), baseModel.getToken());
                        showSignDialog(this, baseModel.getMessage());
                    }
                }
                break;
            case VersionAction.ACTION_REQUEST_VERSION_SUCCESS:
                VersionInfo info = versionStore.getVersionInfo();
                if (info != null) {
                    forceUpdate = info.getAndroid_version_upda();
                    int newCode = info.getAndroid_version_num();
                    PackageInfo packageInfo = getPackageInfo();
                    if (packageInfo != null) {
                        int nowCode = packageInfo.versionCode;
                        if (newCode > nowCode) {
                            File apkFile = FileUtils.getApkFile(appContext, info.getAndroid_version_file());
                            if (apkFile != null && apkFile.exists() && apkFile.isFile()) {
                                Log.i("chen", "有包");
                                String md5 = FileUtils.getFileMD5(apkFile);
                                String newMd5 = info.getAndroid_version_hash();
                                if (!TextUtils.isEmpty(md5) && !TextUtils.isEmpty(newMd5) && md5.equals(newMd5)) {
                                    String date = String.format(Locale.CHINA, "%tF", new Date());
                                    String update = appConfig.getUpdateWindowDate();
                                    if (MainActivity.this != null && !isFinishing()) {
                                        showUpdateDialog(apkFile);
                                    }
                                } else {
                                    Log.i("chen", "包不对");
                                    if (!downloadAPK && FileUtils.deleteNullFile(FileUtils.FILE_TYPE_APK, appContext, info.getAndroid_version_file())) {
                                        //删除错误包成功，下载apk
                                        Log.i("chen", "删除错误包成功");
                                        startDownload(info);
                                    }else {
                                        Log.i("chen", "删除失败");
                                    }
                                }
                            } else {
                                if (!downloadAPK){
                                    Log.i("chen", "无包");
                                    startDownload(info);
                                }
                            }
                        }
                    }
                }
                break;
            case VersionAction.ACTION_REQUEST_FINISH:
                Log.i("chen", "收到消息");
                downloadAPK = false;
                break;
        }
    }

    private void startDownload(VersionInfo info){
        //下载apk
        if (!Util.isWifi(appContext)){
            return;
        }

        if (!downloadAPK) {
            Log.i("chen", "开始下载");
            downloadAPK = true;
            appActionsCreator.downloadApk(appContext, info.getAndroid_version_file());
        }else {
            Log.i("chen", "正在下载，返回");
        }
    }

    private void installAPK(File apkFile) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        appContext.startActivity(intent);
    }

    private PackageInfo getPackageInfo() {
        PackageInfo info = null;
        PackageManager packageManager = appContext.getPackageManager();
        if (packageManager != null) {
            try {
                info = packageManager.getPackageInfo(appContext.getPackageName(), PackageManager.GET_CONFIGURATIONS);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return info;
    }

    @Override
    protected void onNewIntent(Intent newIntent) {
        super.onNewIntent(newIntent);
        processPushIntent(newIntent);
    }

    private void processPushIntent(Intent newIntent) {
        if (newIntent == null) {
            return;
        }
        String customString = newIntent.getStringExtra("pushMessage");
        if (!TextUtils.isEmpty(customString)) {
            Log.i("chen", "pushMessage:" + customString);
            try {
                JSONObject json = new JSONObject(customString);
                Intent pushIntent = new Intent();
                Bundle bundle = new Bundle();
                int key1 = json.optInt("key1");// 1(公告)、2(活动)、3(产品到期)、4（活动弹窗）、5（生日红包推送）
                if (key1 == 1) {
                    int key2 = json.optInt("key2");// 表示公告/ /产品的id
                    pushIntent.setClass(this, WebActivity.class);
                    bundle.putString(WebAction.WEB_REQUEST_TYPE, WebAction.REQUEST_HOME_DECLARATION);
                    bundle.putInt(WebAction.REQUEST_DECLARATION_ID, key2);
                    pushIntent.putExtras(bundle);
                    pushIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(pushIntent);
                } else if (key1 == 2) {
                    String key3 = json.optString("key3");//显示活动URL
                    pushIntent.setClass(this, WebActivity.class);
                    bundle.putString(WebAction.WEB_REQUEST_TYPE, WebAction.REQUEST_HOME_BANNER);
                    bundle.putString(WebAction.REQUEST_BANNER_URL, key3);
                    pushIntent.putExtras(bundle);
                    pushIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(pushIntent);
                } else if (key1 == 3) {
                    if (mUser != null) {
                        pushIntent.setClass(this, MsgCenterActivity.class);
                    } else {
                        pushIntent.setClass(this, LoginActivity.class);
                    }
                    pushIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(pushIntent);
                } else if (key1 == 4) {
                    String key2 = json.optString("key2"); //图片URL
                    String key3 = json.optString("key3"); //h5页面地址
                    if (MainActivity.this != null && !isFinishing()){
                        showActivityDialog(key2, key3);
                        Log.i("chen", "key2:" + key2 + " key3:" + key3);
                    }
                } else if (key1 == 5) {
                    pushIntent.setClass(this, VIPCenterActivity.class);
                    pushIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(pushIntent);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private View getTabItemView(int titleId, int iconId) {
        View view = getLayoutInflater().inflate(R.layout.main_tab_indicator_item, null);
        TextView textView = (TextView) view.findViewById(R.id.main_tab_title_id);
        textView.setText(titleId);
        TextView dotView = (TextView) view.findViewById(R.id.main_tab_dot_id);
        dotView.setVisibility(View.GONE);
        if (titleId == titleIds[2]) {
            userDotView = dotView;
        }
        textView.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(iconId), null, null);
        return view;
    }


    private long pressTime = 0;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - pressTime < 3 * 1000) {
            super.onBackPressed();
            AppManager.getAppManager().appTerminate(getApplicationContext());
        } else {
            pressTime = System.currentTimeMillis();
            UIHelper.ToastMessage(this, getResources().getString(R.string.out_of_app_once_click));
        }
    }

    private void showUpdateDialog(){
        final Dialog updateDialog = new Dialog(this, R.style.DialogStyle);
        View view = LayoutInflater.from(this).inflate(R.layout.show_new_version_layout, null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XiaomiUpdateAgent.arrange();
                if (MainActivity.this != null && !MainActivity.this.isFinishing()){
                    updateDialog.dismiss();
                }
            }
        });
        updateDialog.setContentView(view);
        updateDialog.show();
        updateDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                appConfig.setUpdateWindowDate(String.format(Locale.CHINA, "%tF", new Date()));
            }
        });
    }

    private void showUpdateDialog(final File apkFile) {
        if (updateDialog != null && updateDialog.isShowing()){
            return;
        }
        updateDialog = new Dialog(this, R.style.DialogStyle);
        View view = LayoutInflater.from(this).inflate(R.layout.show_new_version_layout, null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.this != null && !MainActivity.this.isFinishing()){
                    updateDialog.dismiss();
                }
                installAPK(apkFile);
            }
        });
        updateDialog.setContentView(view);
        updateDialog.show();
        if (forceUpdate){
            updateDialog.setCancelable(false);
        }
        updateDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                appConfig.setUpdateWindowDate(String.format(Locale.CHINA, "%tF", new Date()));
            }
        });
    }

    private void showActivityDialog(String imageURL, final String webURL) {
        final Dialog dialog = new Dialog(MainActivity.this, R.style.DialogStyle);
        View rootView = LayoutInflater.from(this).inflate(R.layout.show_push_activity_dialog_layout, null);
        ImageView image = (ImageView) rootView.findViewById(R.id.show_push_activity_image_view);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle dayBundle = new Bundle();
                dayBundle.putString(WebAction.WEB_REQUEST_TYPE, WebAction.GOOD_DAY_ACTIVITY);
                dayBundle.putString(WebAction.GOOD_DAY_WEB, webURL);
                MainRouter.getInstance(MainActivity.this).showActivity(ModuleID.USER_MODULE_ID, UserUI.WebActivity, dayBundle);
                if (MainActivity.this != null && !MainActivity.this.isFinishing()){
                    dialog.dismiss();
                }
            }
        });
        View close = rootView.findViewById(R.id.show_push_activity_close_layout);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.this != null && !MainActivity.this.isFinishing()){
                    dialog.dismiss();
                }
            }
        });
        LuoboImageLoader.getInstance().displayImage(imageURL, image);
        dialog.setContentView(rootView);
        dialog.show();
    }
}
