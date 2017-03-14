package com.baluobo.user.ui;

import android.os.Bundle;
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
import com.baluobo.common.db.LuoBoDBM;
import com.baluobo.common.tools.CountDownTimer;
import com.baluobo.common.tools.UIHelper;
import com.baluobo.common.tools.Util;
import com.baluobo.common.views.CustomInputView;
import com.baluobo.user.actions.FastRegisterAction;
import com.baluobo.user.actions.WebAction;
import com.baluobo.user.model.User;
import com.baluobo.user.router.UserRouter;
import com.baluobo.user.router.UserUI;
import com.baluobo.user.stores.FastRegisterStore;
import com.squareup.otto.Subscribe;

import java.util.Locale;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/12/26.
 */
public class FastRegisterActivity extends BaseToolBarActivity implements View.OnClickListener{
    private FastRegisterStore store;
    private CustomInputView inputPhoneView, inputCodeView, inputInvestView;
    private TextView haveInvestButton, fastRegisterButton, sendCodeButton;
    private CheckBox serviceBox;
    private CountDownTimer timer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fast_register_activity_layout);
        setRightTextMenu(getString(R.string.register_right_menu_text), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setTitle(getString(R.string.register));
        setupViews();
        initDependencies();
    }

    @Override
    protected void setupViews() {
        super.setupViews();
        inputPhoneView = (CustomInputView) findViewById(R.id.fast_register_input_phone_view);
        inputPhoneView.setInputType(InputType.TYPE_CLASS_NUMBER);
        inputPhoneView.setMaxInput(11);
        inputPhoneView.addCustomTextChangedListener(new CustomInputView.CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                inputIsEmpty();
            }
        });
        inputCodeView = (CustomInputView) findViewById(R.id.fast_register_input_phone_coe);
        inputCodeView.setInputType(InputType.TYPE_CLASS_NUMBER);
        inputCodeView.addCustomTextChangedListener(new CustomInputView.CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                inputIsEmpty();
            }
        });
        inputInvestView = (CustomInputView) findViewById(R.id.fast_register_input_invest_code);
        sendCodeButton = (TextView) findViewById(R.id.fast_register_send_check_code);
        sendCodeButton.setOnClickListener(this);
        haveInvestButton = (TextView) findViewById(R.id.fast_register_have_invest_code_button);
        haveInvestButton.setOnClickListener(this);
        fastRegisterButton = (TextView) findViewById(R.id.fast_register_button);
        fastRegisterButton.setOnClickListener(this);
        findViewById(R.id.fast_register_service_list_button).setOnClickListener(this);
        serviceBox = (CheckBox) findViewById(R.id.fast_register_accept_checkbox);
    }

    private void inputIsEmpty(){
        String phone = inputPhoneView.getInputText();
        if (TextUtils.isEmpty(phone)){
            fastRegisterButton.setEnabled(false);
            return;
        }
        String code = inputCodeView.getInputText();
        if (TextUtils.isEmpty(code)){
            fastRegisterButton.setEnabled(false);
            return;
        }
        fastRegisterButton.setEnabled(true);
    }

    @Override
    protected void initDependencies() {
        super.initDependencies();
        store = FastRegisterStore.getInstance();
        dispatcher.register(store);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.fast_register_send_check_code:
                if (!Util.isNetworkAvailable(appContext)){
                    UIHelper.ToastMessage(appContext, getString(R.string.net_work_invalid));
                    return;
                }
                String phoneString = inputPhoneView.getInputText();
                if (TextUtils.isEmpty(phoneString)){
                    UIHelper.ToastMessage(appContext, getString(R.string.input_empty_phone_number));
                    return;
                }
                if (phoneString.length() != 11){
                    UIHelper.ToastMessage(appContext, getString(R.string.please_input_valid_phone));
                    return;
                }
                sendCodeButton.setEnabled(false);
                timer = new CountDownTimer(60 * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        int millis = (int)(millisUntilFinished / 1000);
                        sendCodeButton.setText(String.format(Locale.CHINA, getString(R.string.post_phone_check_code), millis));
                    }

                    @Override
                    public void onFinish() {
                        sendCodeButton.setEnabled(true);
                        sendCodeButton.setText(getString(R.string.for_get_phone_check_code));
                    }
                }.start();
                appActionsCreator.sendFastRegisterCheckCode(phoneString);
                break;
            case R.id.fast_register_service_list_button:
                Bundle bundle = new Bundle();
                bundle.putString(WebAction.STATIC_WEB_BOUND_TYPE, WebAction.STATIC_WEB_TYPE_SERVICE);
                UserRouter.getInstance(appContext).showActivity(UserUI.StaticWeb, bundle);
                break;
            case R.id.fast_register_have_invest_code_button:
                haveInvestButton.setVisibility(View.INVISIBLE);
                inputInvestView.setVisibility(View.VISIBLE);
                break;
            case R.id.fast_register_button:
                if (!Util.isNetworkAvailable(appContext)){
                    UIHelper.ToastMessage(appContext, getString(R.string.net_work_invalid));
                    return;
                }
                String phone = inputPhoneView.getInputText();
                if (TextUtils.isEmpty(phone)){
                    UIHelper.ToastMessage(appContext, getString(R.string.input_empty_phone_number));
                    return;
                }
                if (phone.length() != 11){
                    UIHelper.ToastMessage(appContext, getString(R.string.please_input_valid_phone));
                    return;
                }
                String code = inputCodeView.getInputText();
                if (TextUtils.isEmpty(code)){
                    UIHelper.ToastMessage(appContext, getString(R.string.please_input_check_code));
                    return;
                }
                if (!serviceBox.isChecked()){
                    UIHelper.ToastMessage(appContext, getString(R.string.first_read_accept_service));
                    return;
                }
                appActionsCreator.fastRegister(phone, code, inputInvestView.getInputText());
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        store.register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        store.unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dispatcher.unregister(store);
        if (timer != null){
            timer.cancel();
        }
    }

    @Subscribe
    public void onFastRegisterStoreChange(Store.StoreChangeEvent event) {
        String type = event.getType();
        switch (type){
            case FastRegisterAction.FAST_REGISTER_REQUEST_START:
            case FastRegisterAction.FAST_REGISTER_SEND_CODE_START:
                showLoadingDialog();
                break;
            case FastRegisterAction.FAST_REGISTER_REQUEST_FINISH:
            case FastRegisterAction.FAST_REGISTER_SEND_CODE_FINISH:
                dismissLoadingDialog();
                break;
            case FastRegisterAction.FAST_REGISTER_REQUEST_FAIL:
            case FastRegisterAction.FAST_REGISTER_SEND_CODE_FAIL:
                UIHelper.ToastMessage(appContext, getString(R.string.net_work_error));
                break;
            case FastRegisterAction.FAST_REGISTER_REQUEST_ERROR_MESSAGE:
            case FastRegisterAction.FAST_REGISTER_SEND_CODE_ERROR_MESSAGE:
                String msg = event.getMessage();
                UIHelper.ToastMessage(appContext, msg);
                break;
            case FastRegisterAction.FAST_REGISTER_REQUEST_SUCCESS:
                User user = store.getUser();
                if (user != null){
                    LuoBoDBM.getInstance(this).insertUser(user);
                    appContext.setUser(user);
                }
                setResult(RESULT_OK);
                finish();
                break;
        }
    }
}
