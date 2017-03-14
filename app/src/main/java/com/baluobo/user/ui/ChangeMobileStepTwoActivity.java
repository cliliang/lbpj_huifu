package com.baluobo.user.ui;

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
import com.baluobo.common.db.LuoBoDBM;
import com.baluobo.common.tools.UIHelper;
import com.baluobo.common.tools.Util;
import com.baluobo.user.actions.ChangeMobileTowAction;
import com.baluobo.user.model.User;
import com.baluobo.user.router.UserRouter;
import com.baluobo.user.router.UserUI;
import com.baluobo.user.stores.ChangeMobileTwoStore;
import com.squareup.otto.Subscribe;

import java.util.Locale;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/16.
 */
public class ChangeMobileStepTwoActivity extends BaseToolBarActivity {
    private ChangeMobileTwoStore mobileStore;
    private EditText inputPhoneView;
    private EditText inputCodeView;
    private TextView sendCodeView;
    private TextView button;
    private Handler mHandler = new Handler();
    private int number = 60;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_mobile_step_two_activity_layout);
        setTitle(getString(R.string.change_phone_number));
        setupViews();
        initDependencies();
    }

    @Override
    protected void setupViews() {
        super.setupViews();
        inputPhoneView = (EditText) findViewById(R.id.change_mobile_input_new_phone);
        inputCodeView = (EditText) findViewById(R.id.change_mobile_input_new_code);
        sendCodeView = (TextView) findViewById(R.id.change_mobile_send_new_code);
        sendCodeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Util.isNetworkAvailable(appContext)){
                    UIHelper.ToastMessage(appContext, getString(R.string.net_work_invalid));
                    return;
                }
                String phone = inputPhoneView.getText().toString();
                if (phone.length() != 11){
                    UIHelper.ToastMessage(ChangeMobileStepTwoActivity.this, getString(R.string.please_input_valid_phone));
                    return;
                }
                appActionsCreator.changeMobileSendCheckCode(inputPhoneView.getText().toString());
            }
        });
        button = (TextView) findViewById(R.id.change_mobile_complete_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Util.isNetworkAvailable(appContext)){
                    UIHelper.ToastMessage(appContext, getString(R.string.net_work_invalid));
                    return;
                }
                if (mUser != null){
                    String phone = inputPhoneView.getText().toString();
                    if (phone.length() != 11){
                        UIHelper.ToastMessage(ChangeMobileStepTwoActivity.this, getString(R.string.please_input_valid_phone));
                        return;
                    }
                    String code = inputCodeView.getText().toString();
                    if (TextUtils.isEmpty(code)){
                        UIHelper.ToastMessage(ChangeMobileStepTwoActivity.this, getString(R.string.please_input_check_code));
                        return;
                    }
                    appActionsCreator.changeMobile(inputPhoneView.getText().toString(), inputCodeView.getText().toString(), mUser.getUserId(), mUser.getToken());
                }
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
    }

    private void updateButtonState(){
        button.setEnabled(false);
        String phoneInput = inputPhoneView.getText().toString();
        if (TextUtils.isEmpty(phoneInput)){
            return;
        }
        String codeInput = inputCodeView.getText().toString();
        if (TextUtils.isEmpty(codeInput)){
            return;
        }
        button.setEnabled(true);
    }

    @Override
    protected void initDependencies() {
        super.initDependencies();
        mobileStore = ChangeMobileTwoStore.getInstance();
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
    public void onChangeMobileChange(Store.StoreChangeEvent event) {
        switch (event.getType()) {
            case ChangeMobileTowAction.ACTION_REQUEST_START:
                showLoadingDialog();
                break;
            case ChangeMobileTowAction.ACTION_REQUEST_FINISH:
                dismissLoadingDialog();
                break;
            case ChangeMobileTowAction.ACTION_REQUEST_ERROR_MESSAGE:
                String msg = event.getMessage();
                UIHelper.ToastMessage(this, msg);
                break;
            case ChangeMobileTowAction.ACTION_REQUEST_FAIL:
                UIHelper.ToastMessage(this, getString(R.string.net_work_error));
                break;
            case ChangeMobileTowAction.ACTION_REQUEST_INVALID_TOKEN:
                UserRouter.getInstance(this).showActivity(UserUI.LoginActivity);
                finish();
                break;
            case ChangeMobileTowAction.ACTION_REQUEST_SEND_CODE_SUCCESS:
                mHandler.post(codeRunnable);
                sendCodeView.setClickable(false);
                break;
            case ChangeMobileTowAction.ACTION_MOBILE_STEP_TWO_SUCCESS:
                User user = ((AppContext)getApplicationContext()).getUser();
                user.setMobile(inputPhoneView.getText().toString());
                LuoBoDBM luoBoDBM = LuoBoDBM.getInstance(this);
                luoBoDBM.updateUserInfo(user);
                setResult(RESULT_OK);
                finish();
                break;
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
