package com.baluobo.user.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.baluobo.R;
import com.baluobo.app.AppContext;
import com.baluobo.app.flux.Store;
import com.baluobo.app.router.MainRouter;
import com.baluobo.app.ui.BaseToolBarActivity;
import com.baluobo.common.config.AppConfig;
import com.baluobo.common.db.LuoBoDBM;
import com.baluobo.common.module.ModuleID;
import com.baluobo.common.tools.UIHelper;
import com.baluobo.common.tools.Util;
import com.baluobo.common.views.gesture.GestureCallBack;
import com.baluobo.common.views.gesture.GestureContentView;
import com.baluobo.home.router.HomeUI;
import com.baluobo.user.actions.LoginAction;
import com.baluobo.user.actions.SecurityAction;
import com.baluobo.user.router.UserRouter;
import com.baluobo.user.router.UserUI;
import com.baluobo.user.stores.GestureStore;
import com.baluobo.user.stores.LoginStore;
import com.squareup.otto.Subscribe;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/7/8.
 */
public class GestureVerifyActivity extends BaseToolBarActivity implements View.OnClickListener{
    private FrameLayout rootView;
    private GestureContentView gestureView;
    private TextView helloView;
    private String verifyType;
    private LoginStore loginStore;
    private GestureStore gestureStore;
    private AppConfig appConfig;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gesture_verify_activity_layout);
        setTitle(getString(R.string.verify_gesture));
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            verifyType = bundle.getString(SecurityAction.SECURITY_GESTURE_VERIFY_ACTION);
        }
        initDependencies();
        setupViews();
    }

    @Override
    protected void initDependencies() {
        super.initDependencies();
        loginStore = LoginStore.getInstance((AppContext) getApplicationContext());
        dispatcher.register(loginStore);
        gestureStore = GestureStore.getInstance();
        dispatcher.register(gestureStore);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loginStore.register(this);
        String gesture = "";
        if (mUser != null){
            gesture = mUser.getHandPassword();
            String phone = mUser.getMobile();
            if (!TextUtils.isEmpty(phone) && phone.length() == 11){
                phone = phone.substring(0, 3) + "****" + phone.substring(7, phone.length());
                helloView.setText(String.format(Locale.US, getString(R.string.gesture_verify_hello_world), phone));
            }
        }
        if (TextUtils.isEmpty(gesture)){
            finish();
        }
        gestureView = new GestureContentView(this, true, gesture, new GestureCallBack() {
            @Override
            public void onGestureCodeInput(String inputCode) {

            }

            @Override
            public void checkedSuccess() {
                appConfig.setGestureErrorNumber(0);

                gestureView.clearDrawlineState(0L);
                if (SecurityAction.GESTURE_VERIFY_ACTION_OPEN.equals(verifyType)){
                    //打开应用时验证
                    MainRouter.getInstance(GestureVerifyActivity.this).showActivity(ModuleID.HOME_MODULE_ID, HomeUI.MainActivity);
                    finish();
                }else if (SecurityAction.GESTURE_VERIFY_ACTION_CLOSE.equals(verifyType)){
                    //关闭手势时验证
                    if (mUser != null){
                        appActionsCreator.updateUserGesture(mUser.getUserId(), mUser.getToken(), "");
                    }
                }else if (SecurityAction.GESTURE_VERIFY_ACTION_MODIFY.equals(verifyType)){
                    //修改手势密码时验证
                    Bundle bundle = new Bundle();
                    bundle.putString(SecurityAction.SECURITY_GESTURE_EDIT_ACTION, SecurityAction.GESTURE_MODIFY_CHANGE);
                    UserRouter.getInstance(GestureVerifyActivity.this).showActivityForResult(GestureVerifyActivity.this, UserUI.GestureEdit, 100, bundle);
                }
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
                    intent.putExtra(LoginAction.ACTION_LOGIN_PHONE, mUser.getMobile());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else {
                    UIHelper.ToastMessage(getApplicationContext(), String.format(Locale.US, getString(R.string.gesture_error_number), 4 - number));
                }
            }
        });
        gestureView.setParentView(rootView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK){
            finish();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        loginStore.unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dispatcher.unregister(loginStore);
        dispatcher.unregister(gestureStore);
    }

    @Override
    protected void setupViews() {
        super.setupViews();
        appConfig = AppConfig.getInstance(this);
        TextView dayView = (TextView) findViewById(R.id.gesture_day_view);
        TextView monthView = (TextView) findViewById(R.id.gesture_month_view);
        TextView weekView = (TextView) findViewById(R.id.gesture_week_view);
        helloView = (TextView) findViewById(R.id.gesture_verify_hello_view);
        Calendar calendar = Calendar.getInstance();
        dayView.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
        monthView.setText(String.format(Locale.CHINESE, "%tB", new Date()));
        weekView.setText(String.format(Locale.CHINESE, "%tA", new Date()));

        TextView verifyLoginPsd = (TextView) findViewById(R.id.gesture_verify_login_psd);
        verifyLoginPsd.setOnClickListener(this);
        TextView loseLoginPsd = (TextView) findViewById(R.id.gesture_verify_lose_login_psd_view);
        loseLoginPsd.setOnClickListener(this);
        rootView = (FrameLayout) findViewById(R.id.gesture_verify_root_view);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.gesture_verify_login_psd:
                showVerifyLoginPsdDialog();
                break;
            case R.id.gesture_verify_lose_login_psd_view:
                Bundle bundle = new Bundle();
                bundle.putString(SecurityAction.SECURITY_GESTURE_VERIFY_ACTION, verifyType);
                UserRouter.getInstance(this).showActivity(UserUI.ChangeLoginPsd, bundle);
                finish();
                break;
        }
    }

    @Subscribe
    public void onVerifyStoreChange(Store.StoreChangeEvent event) {
        switch (event.getType()){
            case LoginAction.ACTION_LOGIN_SUCCESS:
                //打开应用时验证密码后，修改手势密码
                if (SecurityAction.GESTURE_VERIFY_ACTION_OPEN.equals(verifyType)){
                    Bundle bundle = new Bundle();
                    bundle.putString(SecurityAction.SECURITY_GESTURE_EDIT_ACTION, SecurityAction.GESTURE_MODIFY_RESET);
                    UserRouter.getInstance(this).showActivity(UserUI.GestureEdit, bundle);
                    finish();
                }else if (SecurityAction.GESTURE_VERIFY_ACTION_CLOSE.equals(verifyType)){
                    //关闭手势密码时验证后
                    if (mUser != null){
                        appActionsCreator.updateUserGesture(mUser.getUserId(), mUser.getToken(), "");
                    }
                }else if (SecurityAction.GESTURE_VERIFY_ACTION_MODIFY.equals(verifyType)){
                    //修改手势密码时验证
                    Bundle bundle = new Bundle();
                    bundle.putString(SecurityAction.SECURITY_GESTURE_EDIT_ACTION, SecurityAction.GESTURE_MODIFY_CHANGE);
                    UserRouter.getInstance(GestureVerifyActivity.this).showActivity(UserUI.GestureEdit, bundle);
                    finish();
                }
                break;
            case LoginAction.ACTION_REQUEST_START:
            case SecurityAction.ACTION_RESULT_START:
                showLoadingDialog();
                break;
            case LoginAction.ACTION_REQUEST_FINISH:
            case SecurityAction.ACTION_RESULT_FINISH:
                dismissLoadingDialog();
                break;
            case LoginAction.ACTION_REQUEST_FAIL:
            case SecurityAction.ACTION_RESULT_FAIL:
                UIHelper.ToastMessage(this, getString(R.string.net_work_error));
                break;
            case SecurityAction.ACTION_RESULT_ERROR_MESSAGE:
            case LoginAction.ACTION_REQUEST_ERROR_MESSAGE:
                String error = event.getMessage();
                UIHelper.ToastMessage(this, error);
                break;
            case SecurityAction.ACTION_RESULT_VALID_TOKEN:
                UserRouter.getInstance(this).showActivity(UserUI.LoginActivity);
                break;
            case SecurityAction.ACTION_RESULT_SUCCESS:
                LuoBoDBM dbm = LuoBoDBM.getInstance(this);
                if (dbm.updateUserGesture(mUser, "")){
                    mUser.setHandPassword("");
                    appContext.setUser(mUser);
                }
                finish();
                break;
        }
    }

    private void showVerifyLoginPsdDialog(){
        final Dialog dialog = new Dialog(this, R.style.DialogStyle);
        View view = LayoutInflater.from(this).inflate(R.layout.verify_login_dialog_layout, null);
        final EditText editText = (EditText) view.findViewById(R.id.verify_input_psd_view);
        view.findViewById(R.id.verify_cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.verify_ok_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String psd = editText.getText().toString();
                if (TextUtils.isEmpty(psd)){
                    UIHelper.ToastMessage(GestureVerifyActivity.this, getString(R.string.please_input_password));
                    return;
                }
                if (!Util.isNetworkAvailable(appContext)){
                    UIHelper.ToastMessage(appContext, getString(R.string.net_work_invalid));
                    return;
                }
                appActionsCreator.userLogin(mUser.getMobile(), psd);
                dialog.dismiss();
            }
        });
        dialog.setContentView(view);
        dialog.show();
    }
}
