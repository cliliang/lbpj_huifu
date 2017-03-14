package com.baluobo.user.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baluobo.R;
import com.baluobo.app.AppContext;
import com.baluobo.app.flux.Store;
import com.baluobo.app.model.BaseModel;
import com.baluobo.app.router.MainRouter;
import com.baluobo.app.ui.BaseToolBarActivity;
import com.baluobo.common.db.LuoBoDBM;
import com.baluobo.common.module.ModuleID;
import com.baluobo.common.tools.CountDownTimer;
import com.baluobo.common.tools.UIHelper;
import com.baluobo.common.tools.Util;
import com.baluobo.common.views.CustomInputView;
import com.baluobo.home.router.HomeUI;
import com.baluobo.user.actions.ChangeLoginPsdAction;
import com.baluobo.user.actions.LoginAction;
import com.baluobo.user.model.User;
import com.baluobo.user.router.UserRouter;
import com.baluobo.user.router.UserUI;
import com.baluobo.user.stores.LoginStore;
import com.squareup.otto.Subscribe;

import java.util.Locale;


/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/11.
 */
public class LoginActivity extends BaseToolBarActivity implements View.OnClickListener {
    private LoginStore loginStore;
    private CustomInputView phoneInputView, passwordInputView;
    private LinearLayout psdLoginLayout;
    private TextView loginViaPsdView, sendCodeButton;
    private String loginType;
    private String loginPhone;
    private int LOGIN_VIA_WHAT = 1;
    private final int LOGIN_VIA_CODE = 1;
    private final int LOGIN_VIA_PSD = 2;
    private CountDownTimer timer;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_layout);
        setTitle(getResources().getString(R.string.login));
        //账号被顶掉后
        appContext.setUser(null);
        LuoBoDBM.getInstance(this).logout();
        Intent intent = getIntent();
        if (intent != null) {
            //忘记手势密码时，带来用户的手机号
            loginType = intent.getStringExtra(LoginAction.ACTION_LOGIN_TYPE);
            loginPhone = intent.getStringExtra(LoginAction.ACTION_LOGIN_PHONE);

        }
        setupViews();
        initDependencies();
    }

    @Override
    protected void setupViews() {
        super.setupViews();
        phoneInputView = (CustomInputView) findViewById(R.id.login_input_phone_view);
        phoneInputView.setInputType(InputType.TYPE_CLASS_NUMBER);
        phoneInputView.setMaxInput(11);
        passwordInputView = (CustomInputView) findViewById(R.id.login_input_password_view);
        passwordInputView.setInputType(InputType.TYPE_CLASS_NUMBER);
        psdLoginLayout = (LinearLayout) findViewById(R.id.login_via_password_layout);
        loginViaPsdView = (TextView) findViewById(R.id.login_via_psd_button);
        loginViaPsdView.setOnClickListener(this);
        sendCodeButton = (TextView) findViewById(R.id.login_send_check_code);
        sendCodeButton.setOnClickListener(this);
        findViewById(R.id.login_via_check_code_button).setOnClickListener(this);
        findViewById(R.id.login_button).setOnClickListener(this);
        findViewById(R.id.login_missing_password).setOnClickListener(this);
        findViewById(R.id.login_register_free).setOnClickListener(this);

        if (!TextUtils.isEmpty(loginPhone)) {
            phoneInputView.setInputContent(loginPhone);
        }
    }

    @Override
    protected void initDependencies() {
        super.initDependencies();
        loginStore = LoginStore.getInstance((AppContext) getApplicationContext());
        dispatcher.register(loginStore);
    }

    @Override
    public void onClick(View v) {
        if (!Util.isNetworkAvailable(appContext)) {
            UIHelper.ToastMessage(appContext, getString(R.string.net_work_invalid));
            return;
        }
        int viewId = v.getId();
        switch (viewId) {
            case R.id.login_send_check_code:
                String phoneString = phoneInputView.getInputText();
                if (TextUtils.isEmpty(phoneString)) {
                    UIHelper.ToastMessage(appContext, getString(R.string.input_empty_phone_number));
                    return;
                }
                if (phoneString.length() != 11) {
                    UIHelper.ToastMessage(appContext, getString(R.string.please_input_valid_phone));
                    return;
                }

                sendCodeButton.setEnabled(false);
                timer = new CountDownTimer(60000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        int millis = (int) (millisUntilFinished / 1000);
                        sendCodeButton.setText(String.format(Locale.CHINA, getString(R.string.post_phone_check_code), millis));
                    }

                    @Override
                    public void onFinish() {
                        timer.cancel();
                        sendCodeButton.setText(getString(R.string.for_get_phone_check_code));
                        sendCodeButton.setEnabled(true);
                    }
                }.start();
                appActionsCreator.sendLoginCheckCode(phoneString);
                break;
            case R.id.login_via_psd_button:
                LOGIN_VIA_WHAT = LOGIN_VIA_PSD;
                loginViaPsdView.setVisibility(View.GONE);
                psdLoginLayout.setVisibility(View.VISIBLE);
                sendCodeButton.setVisibility(View.GONE);
                passwordInputView.setInputHint(getString(R.string.please_input_password));
                passwordInputView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                passwordInputView.setMaxInput(20);
                passwordInputView.setInputContent("");
                break;
            case R.id.login_via_check_code_button:
                LOGIN_VIA_WHAT = LOGIN_VIA_CODE;
                loginViaPsdView.setVisibility(View.VISIBLE);
                psdLoginLayout.setVisibility(View.GONE);
                sendCodeButton.setVisibility(View.VISIBLE);
                passwordInputView.setInputHint(getString(R.string.please_input_check_code));
                passwordInputView.setInputType(InputType.TYPE_CLASS_NUMBER);
                passwordInputView.setInputContent("");
                break;
            case R.id.login_button:
                String phoneInput = phoneInputView.getInputText();
                if (TextUtils.isEmpty(phoneInput)) {
                    UIHelper.ToastMessage(LoginActivity.this, getString(R.string.input_empty_phone_number));
                    return;
                }
                if (phoneInput.length() != 11) {
                    UIHelper.ToastMessage(LoginActivity.this, getString(R.string.please_input_valid_phone));
                    return;
                }

                if (LOGIN_VIA_WHAT == LOGIN_VIA_PSD) {
                    String psdInput = passwordInputView.getInputText();
                    if (psdInput.length() < 6) {
                        UIHelper.ToastMessage(LoginActivity.this, getString(R.string.login_error_input_min_6));
                        return;
                    }
                    appActionsCreator.userLogin(phoneInputView.getInputText(), passwordInputView.getInputText());
                } else {
                    String codeInput = passwordInputView.getInputText();
                    if (TextUtils.isEmpty(codeInput)) {
                        UIHelper.ToastMessage(appContext, getString(R.string.please_input_check_code));
                        return;
                    }
                    appActionsCreator.fastLogin(phoneInputView.getInputText(), passwordInputView.getInputText());
                }
                break;
            case R.id.login_missing_password:
                Bundle bundle = new Bundle();
                bundle.putString(ChangeLoginPsdAction.ACTION_CHANGE_PSD_TYPE, ChangeLoginPsdAction.ACTION_TYPE_RETRIEVE_PSD);
                UserRouter.getInstance(this).showActivity(UserUI.ChangeLoginPsd, bundle);
                break;
            case R.id.login_have_no_account:
            case R.id.login_register_free:
                UserRouter.getInstance(this).showActivityForResult(LoginActivity.this, UserUI.FastRegister, 1, null);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loginStore.register(this);
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
        if (timer != null) {
            timer.cancel();
        }
    }

    @Subscribe
    public void onLoginStoreChange(Store.StoreChangeEvent event) {
        String type = event.getType();
        switch (type) {
            case LoginAction.LOGIN_SEND_CODE_ACTION_START:
            case LoginAction.ACTION_REQUEST_START:
                showLoadingDialog();
                break;
            case LoginAction.LOGIN_SEND_CODE_ACTION_FINISH:
            case LoginAction.ACTION_REQUEST_FINISH:
                dismissLoadingDialog();
                break;
            case LoginAction.ACTION_LOGIN_SUCCESS:
            case LoginAction.LOGIN_SEND_CODE_ACTION_SUCCESS:
                BaseModel baseModel = loginStore.getLoginUser();
                if (baseModel != null && baseModel.isSuccess()) {
                    User user = (User) baseModel.getRows();
                    if (user != null) {
                        UIHelper.ToastMessage(this, getString(R.string.login_success));
                        Log.i("chen", "uid:" + user.getUserId() + " token:" + baseModel.getToken());
                        user.setToken(baseModel.getToken());
                        LuoBoDBM.getInstance(this).insertUser(user);
                        mUser = user;
                        appContext.setUser(user);
                        setResult(RESULT_OK);
                        if (LoginAction.ACTION_LOGIN_TYPE_GESTURE.equals(loginType)) {
                            MainRouter.getInstance(this).showActivity(ModuleID.HOME_MODULE_ID, HomeUI.MainActivity);
                        }
                        finish();
                    }
                }
                break;
            case LoginAction.LOGIN_SEND_CODE_ACTION_ERROR_MESSAGE:
            case LoginAction.ACTION_REQUEST_ERROR_MESSAGE:
                String msg = event.getMessage();
                UIHelper.ToastMessage(this, msg);
                break;
            case LoginAction.LOGIN_SEND_CODE_ACTION_FAIL:
                if (timer != null) {
                    timer.cancel();
                }
                sendCodeButton.setEnabled(true);
                sendCodeButton.setText(getString(R.string.for_get_phone_check_code));
                break;
            case LoginAction.ACTION_REQUEST_FAIL:
                UIHelper.ToastMessage(this, getString(R.string.net_work_error));
                break;
        }
    }

    @Override
    public void onBackClick() {
        if (LoginAction.ACTION_LOGIN_TYPE_GESTURE.equals(loginType)) {
            MainRouter.getInstance(this).showActivity(ModuleID.HOME_MODULE_ID, HomeUI.MainActivity);
        } else {
            setResult(RESULT_OK + 1);
        }
        finish();
    }

    @Override
    public void onBackPressed() {
        if (LoginAction.ACTION_LOGIN_TYPE_GESTURE.equals(loginType)) {
            MainRouter.getInstance(this).showActivity(ModuleID.HOME_MODULE_ID, HomeUI.MainActivity);
        } else {
            setResult(RESULT_OK + 1);
        }
        finish();
    }
}
