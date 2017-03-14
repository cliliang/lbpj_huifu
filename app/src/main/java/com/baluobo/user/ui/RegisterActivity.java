package com.baluobo.user.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.baluobo.R;
import com.baluobo.app.flux.Store;
import com.baluobo.app.ui.BaseToolBarActivity;
import com.baluobo.common.tools.UIHelper;
import com.baluobo.common.tools.Util;
import com.baluobo.common.views.CustomInputView;
import com.baluobo.user.actions.RegisterAction;
import com.baluobo.app.flux.AppActionsCreator;
import com.baluobo.user.actions.WebAction;
import com.baluobo.user.router.UserRouter;
import com.baluobo.user.router.UserUI;
import com.baluobo.user.stores.RegisterStore;
import com.squareup.otto.Subscribe;

import java.util.Locale;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/13.
 */
public class RegisterActivity extends BaseToolBarActivity {
    private CustomInputView inputPhoneView, inputCodeView, inputPsdView, inputInviteView;
    private CheckBox acceptBox;
    private TextView registerButton;
    private TextView serviceView;
    private Handler mHandler = new Handler();
    private RegisterStore registerStore;
    private int number = 60;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity_layout);
        setTitle(getString(R.string.register));
        setRightTextMenu(getString(R.string.register_right_menu_text), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setupViews();
        initDependencies();

    }

    @Override
    protected void setupViews() {
        super.setupViews();
        inputPhoneView = (CustomInputView) findViewById(R.id.register_input_phone_view);
        inputPhoneView.setInputType(InputType.TYPE_CLASS_NUMBER);
        inputPhoneView.setMaxInput(11);
        inputPhoneView.setOnBtnClickListener(new CustomInputView.OnBtnClickListener() {
            @Override
            public void onBtnClickListener() {
//                appActionsCreator.sendCheckCode(inputPhoneView.getInputText());
            }
        });
        inputCodeView = (CustomInputView) findViewById(R.id.register_input_code_view);
        inputCodeView.setInputType(InputType.TYPE_CLASS_NUMBER);
        inputPsdView = (CustomInputView) findViewById(R.id.register_input_psd_view);
        inputPsdView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        inputPsdView.setMaxInput(20);

        inputInviteView = (CustomInputView) findViewById(R.id.register_input_invite_code_view);

        acceptBox = (CheckBox) findViewById(R.id.register_accept_checkbox);
        serviceView = (TextView) findViewById(R.id.register_service_list_button);
        serviceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(WebAction.STATIC_WEB_BOUND_TYPE, WebAction.STATIC_WEB_TYPE_SERVICE);
                UserRouter.getInstance(RegisterActivity.this).showActivity(UserUI.StaticWeb, bundle);
            }
        });

        registerButton = (TextView) findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Util.isNetworkAvailable(appContext)){
                    UIHelper.ToastMessage(appContext, getString(R.string.net_work_invalid));
                    return;
                }
                String inputPhone = inputPhoneView.getInputText();
                if (TextUtils.isEmpty(inputPhone)){
                    UIHelper.ToastMessage(RegisterActivity.this, getString(R.string.input_empty_phone_number));
                    return;
                }
                if (inputPhone.length() != 11){
                    UIHelper.ToastMessage(RegisterActivity.this, getString(R.string.please_input_valid_phone));
                    return;
                }
                String inputCode = inputCodeView.getInputText();
                if (TextUtils.isEmpty(inputCode)){
                    UIHelper.ToastMessage(RegisterActivity.this, getString(R.string.please_input_check_code));
                    return;
                }
                String inputPsd = inputPsdView.getInputText();
                if (inputPsd.length() < 6){
                    UIHelper.ToastMessage(RegisterActivity.this, getString(R.string.login_error_input_min_6));
                    return;
                }
                if (!acceptBox.isChecked()){
                    UIHelper.ToastMessage(RegisterActivity.this, getString(R.string.first_read_accept_service));
                    return;
                }
                appActionsCreator.userRegister(inputPhoneView.getInputText(), inputCodeView.getInputText(), inputPsdView.getInputText(), inputInviteView.getInputText());
            }
        });

        inputPhoneView.addCustomTextChangedListener(new CustomInputView.CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                inputIsEmpty();
            }
        });

        inputCodeView.addCustomTextChangedListener(new CustomInputView.CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                inputIsEmpty();
            }
        });

        inputPsdView.addCustomTextChangedListener(new CustomInputView.CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                inputIsEmpty();
            }
        });
    }

    private void inputIsEmpty(){
        String phone = inputPhoneView.getInputText();
        if (TextUtils.isEmpty(phone)){
            registerButton.setEnabled(false);
            return;
        }
        String code = inputCodeView.getInputText();
        if (TextUtils.isEmpty(code)){
            registerButton.setEnabled(false);
            return;
        }
        String psd = inputPsdView.getInputText();
        if (TextUtils.isEmpty(psd)){
            registerButton.setEnabled(false);
            return;
        }
        registerButton.setEnabled(true);
    }

    @Override
    protected void initDependencies() {
        super.initDependencies();
        registerStore = RegisterStore.getInstance();
        dispatcher.register(registerStore);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerStore.register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        registerStore.unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(codeRunnable);
        dispatcher.unregister(registerStore);
    }

    @Subscribe
    public void onRegisterStoreChange(Store.StoreChangeEvent event) {
        String type = event.getType();
        switch (type){
            case RegisterAction.ACTION_REQUEST_START:
                showLoadingDialog();
                break;
            case RegisterAction.ACTION_REQUEST_FINISH:
                dismissLoadingDialog();
                break;
            case RegisterAction.ACTION_REQUEST_ERROR_MESSAGE:
                String msg = event.getMessage();
                UIHelper.ToastMessage(this, msg);
                break;
            case RegisterAction.ACTION_REQUEST_FAIL:
                UIHelper.ToastMessage(this, getString(R.string.net_work_error));
                break;
            case RegisterAction.ACTION_REGISTER_SEND_CODE_START:
                mHandler.post(codeRunnable);
                inputPhoneView.setButtonClickable(false);
                break;
            case RegisterAction.ACTION_REGISTER_SUCCESS:
                finish();
                break;
        }
    }

    private Runnable codeRunnable = new Runnable() {
        @Override
        public void run() {
            number--;
            if (number > 0){
                inputPhoneView.setButtonText(String.format(Locale.US, getString(R.string.post_phone_check_code), number));
                mHandler.postDelayed(codeRunnable, 1000);
            }else {
                number = 60;
                inputPhoneView.setButtonClickable(true);
                inputPhoneView.setButtonText(getString(R.string.for_get_phone_check_code));
                mHandler.removeCallbacks(codeRunnable);
            }
        }
    };

}
