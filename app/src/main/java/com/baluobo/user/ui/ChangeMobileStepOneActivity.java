package com.baluobo.user.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.baluobo.R;
import com.baluobo.app.AppContext;
import com.baluobo.app.flux.Store;
import com.baluobo.app.ui.BaseToolBarActivity;
import com.baluobo.common.tools.UIHelper;
import com.baluobo.common.tools.Util;
import com.baluobo.user.actions.ChangeMobileOneAction;
import com.baluobo.user.actions.LoginAction;
import com.baluobo.user.actions.RegisterAction;
import com.baluobo.user.model.SendVerifyCodeType;
import com.baluobo.user.router.UserRouter;
import com.baluobo.user.router.UserUI;
import com.baluobo.user.stores.ChangeMobileOneStore;
import com.squareup.otto.Subscribe;

import java.util.Locale;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/16.
 */
public class ChangeMobileStepOneActivity extends BaseToolBarActivity {
    private ChangeMobileOneStore mobileStore;
    private EditText inputPsdView;
    private EditText inputPhoneView;
    private EditText inputCodeView;
    private TextView sendCodeView;
    private TextView button;
    private Handler mHandler = new Handler();
    private int number = 60;
    private final int SHOW_CHANGE_MOBILE_REQUEST = UserUI.ChangeMobileStepOne + 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_mobile_step_one_activity_layout);
        setTitle(getString(R.string.change_phone_number));
        setupViews();
        initDependencies();
    }

    @Override
    protected void setupViews() {
        super.setupViews();
        inputPsdView = (EditText) findViewById(R.id.change_mobile_input_login_psd);
        inputPhoneView = (EditText) findViewById(R.id.change_mobile_input_old_phone);
        inputCodeView = (EditText) findViewById(R.id.change_mobile_input_old_check);
        button = (TextView) findViewById(R.id.change_mobile_next_step_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Util.isNetworkAvailable(appContext)){
                    UIHelper.ToastMessage(appContext, getString(R.string.net_work_invalid));
                    return;
                }
                if (mUser != null){
                    String psd = inputPsdView.getText().toString();
                    if (TextUtils.isEmpty(psd)){
                        UIHelper.ToastMessage(ChangeMobileStepOneActivity.this, getString(R.string.please_input_password));
                        return;
                    }

                    String phone = inputPhoneView.getText().toString();
                    if (phone.length() != 11){
                        UIHelper.ToastMessage(ChangeMobileStepOneActivity.this, getString(R.string.please_input_valid_phone));
                        return;
                    }
                    String code = inputCodeView.getText().toString();
                    if (TextUtils.isEmpty(code)){
                        UIHelper.ToastMessage(ChangeMobileStepOneActivity.this, getString(R.string.please_input_check_code));
                        return;
                    }
                    appActionsCreator.changeMobileStepOne(inputPsdView.getText().toString(), inputPhoneView.getText().toString(), inputCodeView.getText().toString(), mUser.getUserId(), mUser.getToken());
                }
            }
        });
        sendCodeView = (TextView) findViewById(R.id.change_mobile_send_code);
        sendCodeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Util.isNetworkAvailable(appContext)){
                    UIHelper.ToastMessage(appContext, getString(R.string.net_work_invalid));
                    return;
                }
                String phone = inputPhoneView.getText().toString();
                if (phone.length() != 11){
                    UIHelper.ToastMessage(ChangeMobileStepOneActivity.this, getString(R.string.please_input_valid_phone));
                    return;
                }
                appActionsCreator.sendCheckCode(phone, SendVerifyCodeType.SEND_VERIFY_CODE_TYPE_CHANGE_MOBILE.getType());
            }
        });

        inputPhoneView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                updateButtonState();
            }
        });

        inputCodeView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                updateButtonState();
            }
        });

        inputPsdView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                updateButtonState();
            }
        });
    }

    private void updateButtonState(){
        button.setEnabled(false);
        String psdInput = inputPsdView.getText().toString();
        if (TextUtils.isEmpty(psdInput)){
            return;
        }

        String codeInput = inputCodeView.getText().toString();
        if (TextUtils.isEmpty(codeInput)){
            return;
        }

        String phoneInput = inputPhoneView.getText().toString();
        if (TextUtils.isEmpty(phoneInput)){
            return;
        }
        button.setEnabled(true);
    }

    @Override
    protected void initDependencies() {
        super.initDependencies();
        mobileStore = ChangeMobileOneStore.getInstance();
        dispatcher.register(mobileStore);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mobileStore.register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mobileStore.unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dispatcher.unregister(mobileStore);
        mHandler.removeCallbacks(codeRunnable);
    }

    @Subscribe
    public void onMobileStoreChange(Store.StoreChangeEvent event) {
        switch (event.getType()){
            case RegisterAction.ACTION_REQUEST_START:
            case ChangeMobileOneAction.ACTION_REQUEST_START:
                showLoadingDialog();
                break;
            case RegisterAction.ACTION_REQUEST_FINISH:
            case ChangeMobileOneAction.ACTION_REQUEST_FINISH:
                dismissLoadingDialog();
                break;
            case RegisterAction.ACTION_REQUEST_ERROR_MESSAGE:
            case ChangeMobileOneAction.ACTION_REQUEST_ERROR_MESSAGE:
                String msg = event.getMessage();
                UIHelper.ToastMessage(this, msg);
                break;
            case RegisterAction.ACTION_REQUEST_FAIL:
            case ChangeMobileOneAction.ACTION_REQUEST_FAIL:
                UIHelper.ToastMessage(this, getString(R.string.net_work_error));
                break;
            case ChangeMobileOneAction.ACTION_REQUEST_INVALID_TOKEN:
                UserRouter.getInstance(this).showActivity(UserUI.LoginActivity);
                finish();
                break;
            case RegisterAction.ACTION_REGISTER_SEND_CODE_START:
                mHandler.post(codeRunnable);
                sendCodeView.setClickable(false);
                break;
            case ChangeMobileOneAction.ACTION_MOBILE_STEP_ONE_SUCCESS:
                UserRouter.getInstance(this).showActivityForResult(this, UserUI.ChangeMobileStepTwo, SHOW_CHANGE_MOBILE_REQUEST, null);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SHOW_CHANGE_MOBILE_REQUEST && resultCode == RESULT_OK){
            finish();
        }
    }

    private Runnable codeRunnable = new Runnable() {
        @Override
        public void run() {
            number--;
            if (number > 0){
                sendCodeView.setText(String.format(Locale.US, getString(R.string.post_phone_check_code), number));
                mHandler.postDelayed(codeRunnable, 1000);
            }else {
                number = 60;
                sendCodeView.setClickable(true);
                sendCodeView.setText(getString(R.string.for_get_phone_check_code));
                mHandler.removeCallbacks(codeRunnable);
            }
        }
    };
}
