package com.baluobo.user.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baluobo.R;
import com.baluobo.app.AppContext;
import com.baluobo.app.flux.Store;
import com.baluobo.app.ui.BaseToolBarActivity;
import com.baluobo.common.db.LuoBoDBM;
import com.baluobo.common.tools.UIHelper;
import com.baluobo.common.tools.Util;
import com.baluobo.user.actions.ChangeLoginPsdAction;
import com.baluobo.user.actions.RegisterAction;
import com.baluobo.user.actions.SecurityAction;
import com.baluobo.app.flux.AppActionsCreator;
import com.baluobo.user.model.User;
import com.baluobo.user.router.UserRouter;
import com.baluobo.user.router.UserUI;
import com.baluobo.user.stores.ChangeLoginPsdStore;
import com.baluobo.user.stores.GestureEditStore;
import com.squareup.otto.Subscribe;

import java.util.Locale;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/16.
 */
public class ChangeLoginPsdActivity extends BaseToolBarActivity implements View.OnClickListener, View.OnFocusChangeListener{
    private ChangeLoginPsdStore changeStore;
    private GestureEditStore gestureEditStore;
    private EditText inputPhoneView, inputCodeView, inputPsdView, inputPsdAgainView;
    private ImageView deletePhoneView, deleteCodeView, deletePsdView, deletePsdAgainView;
    private TextView sendCodeButton;
    private TextView submitButton;
    private Handler mHandler = new Handler();
    private String type;
    private String changeLoginPsdType;
    private int number = 60;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_login_psd_activity_layout);
        setupViews();
        initDependencies();
    }

    @Override
    protected void setupViews() {
        super.setupViews();
        inputPhoneView = (EditText) findViewById(R.id.change_psd_input_phone_view);
        inputCodeView = (EditText) findViewById(R.id.change_psd_input_code_view);
        inputPsdView = (EditText) findViewById(R.id.change_psd_input_psd_view);
        inputPsdAgainView = (EditText) findViewById(R.id.change_psd_input_psd_again_view);

        deletePhoneView = (ImageView) findViewById(R.id.change_psd_input_phone_delete_view);
        deletePhoneView.setOnClickListener(this);
        deleteCodeView = (ImageView) findViewById(R.id.change_psd_input_code_delete_view);
        deleteCodeView.setOnClickListener(this);
        deletePsdView = (ImageView) findViewById(R.id.change_psd_input_psd_delete_view);
        deletePsdView.setOnClickListener(this);
        deletePsdAgainView = (ImageView) findViewById(R.id.change_psd_input_new_psd_delete_view);
        deletePsdAgainView.setOnClickListener(this);

        sendCodeButton = (TextView) findViewById(R.id.change_psd_send_code_button);
        sendCodeButton.setOnClickListener(this);
        submitButton = (TextView) findViewById(R.id.change_psd_submit_button);
        submitButton.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            type = bundle.getString(ChangeLoginPsdAction.ACTION_CHANGE_PSD_TYPE);
            changeLoginPsdType = bundle.getString(SecurityAction.SECURITY_GESTURE_VERIFY_ACTION);
            if (ChangeLoginPsdAction.ACTION_TYPE_CHANGE_LOGIN_PAS.equals(type)){
                setTitle(getString(R.string.change_login_psd));
            }else {
                setTitle(getString(R.string.retrieve_psd));
            }
        }

        setupInputView();
    }

    private void inputIsValid(){
        String phone = inputPhoneView.getText().toString();
        if (TextUtils.isEmpty(phone)){
            submitButton.setEnabled(false);
            return;
        }
        String code = inputCodeView.getText().toString();
        if (TextUtils.isEmpty(code)){
            submitButton.setEnabled(false);
            return;
        }
        String psd = inputPsdView.getText().toString();
        if (TextUtils.isEmpty(psd)){
            submitButton.setEnabled(false);
            return;
        }
        String newPsd = inputPsdAgainView.getText().toString();
        if (TextUtils.isEmpty(newPsd)){
            submitButton.setEnabled(false);
            return;
        }
        submitButton.setEnabled(true);
    }

    @Override
    protected void initDependencies() {
        super.initDependencies();
        changeStore = ChangeLoginPsdStore.getInstance();
        dispatcher.register(changeStore);
        if (SecurityAction.GESTURE_VERIFY_ACTION_CLOSE.equals(changeLoginPsdType)){
            gestureEditStore = GestureEditStore.getInstance();
            dispatcher.register(gestureEditStore);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        changeStore.register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        changeStore.unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dispatcher.unregister(changeStore);
        mHandler.removeCallbacks(codeRunnable);
        if (SecurityAction.GESTURE_VERIFY_ACTION_CLOSE.equals(changeLoginPsdType)){
            dispatcher.unregister(gestureEditStore);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.change_psd_input_phone_delete_view:
                inputPhoneView.setText("");
                break;
            case R.id.change_psd_input_code_delete_view:
                inputCodeView.setText("");
                break;
            case R.id.change_psd_input_psd_delete_view:
                inputPsdView.setText("");
                break;
            case R.id.change_psd_input_new_psd_delete_view:
                inputPsdAgainView.setText("");
                break;
            case R.id.change_psd_send_code_button:
                if (!Util.isNetworkAvailable(appContext)){
                    UIHelper.ToastMessage(appContext, getString(R.string.net_work_invalid));
                    return;
                }
                String inputPhone = inputPhoneView.getText().toString();
                if (inputPhone.length() != 11){
                    UIHelper.ToastMessage(ChangeLoginPsdActivity.this, getString(R.string.please_input_valid_phone));
                    return;
                }
                appActionsCreator.sendCheckCode(inputPhoneView.getText().toString());
                break;
            case R.id.change_psd_submit_button:
                if (!Util.isNetworkAvailable(appContext)){
                    UIHelper.ToastMessage(appContext, getString(R.string.net_work_invalid));
                    return;
                }
                String phone = inputPhoneView.getText().toString();
                if (phone.length() != 11){
                    UIHelper.ToastMessage(ChangeLoginPsdActivity.this, getString(R.string.please_input_valid_phone));
                    return;
                }
                String code = inputCodeView.getText().toString();
                if (TextUtils.isEmpty(code)){
                    UIHelper.ToastMessage(ChangeLoginPsdActivity.this, getString(R.string.please_input_check_code));
                    return;
                }

                String oldPsd = inputPsdView.getText().toString();
                if (oldPsd.length() < 6){
                    UIHelper.ToastMessage(ChangeLoginPsdActivity.this, getString(R.string.login_error_input_min_6));
                    return;
                }

                String newPsd = inputPsdAgainView.getText().toString();
                if (!newPsd.equals(oldPsd)){
                    UIHelper.ToastMessage(ChangeLoginPsdActivity.this, getString(R.string.change_psd_input_double_different));
                    return;
                }

                if (ChangeLoginPsdAction.ACTION_TYPE_CHANGE_LOGIN_PAS.equals(type)){
                    if (mUser != null){
                        appActionsCreator.changeLoginPsd(inputPhoneView.getText().toString(), inputCodeView.getText().toString(), inputPsdView.getText().toString() , mUser.getToken());
                    }
                }else {
                    appActionsCreator.retrievePsd(inputPhoneView.getText().toString(), inputCodeView.getText().toString(), inputPsdView.getText().toString());
                }
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        int id = v.getId();
        switch (id){
            case R.id.change_psd_input_phone_delete_view:
                String input = inputPhoneView.getText().toString();
                if (hasFocus){
                    if (TextUtils.isEmpty(input)){
                        deletePhoneView.setVisibility(View.GONE);
                    }else {
                        deletePhoneView.setVisibility(View.VISIBLE);
                    }
                }else {
                    deletePhoneView.setVisibility(View.GONE);
                }
                break;
            case R.id.change_psd_input_code_delete_view:
                if (hasFocus){
                    if (TextUtils.isEmpty(inputCodeView.getText().toString())){
                        deleteCodeView.setVisibility(View.GONE);
                    }else {
                        deleteCodeView.setVisibility(View.VISIBLE);
                    }
                }else {
                    deleteCodeView.setVisibility(View.GONE);
                }
                break;
            case R.id.change_psd_input_psd_delete_view:
                if (hasFocus){
                    if (TextUtils.isEmpty(inputPsdView.getText().toString())){
                        deletePsdView.setVisibility(View.GONE);
                    }else {
                        deletePsdView.setVisibility(View.VISIBLE);
                    }
                }else {
                    deletePsdView.setVisibility(View.GONE);
                }
                break;
            case R.id.change_psd_input_new_psd_delete_view:
                if (hasFocus){
                    if (TextUtils.isEmpty(inputPsdAgainView.getText().toString())){
                        deletePsdAgainView.setVisibility(View.GONE);
                    }else {
                        deletePsdAgainView.setVisibility(View.VISIBLE);
                    }
                }else {
                    deletePsdAgainView.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Subscribe
    public void onPsdStoreChange(Store.StoreChangeEvent event) {
        switch (event.getType()){
            case SecurityAction.ACTION_RESULT_START:
            case RegisterAction.ACTION_REQUEST_START:
            case ChangeLoginPsdAction.ACTION_REQUEST_START:
                showLoadingDialog();
                break;
            case SecurityAction.ACTION_RESULT_FINISH:
            case RegisterAction.ACTION_REQUEST_FINISH:
            case ChangeLoginPsdAction.ACTION_REQUEST_FINISH:
                dismissLoadingDialog();
                break;
            case SecurityAction.ACTION_RESULT_ERROR_MESSAGE:
            case RegisterAction.ACTION_REQUEST_ERROR_MESSAGE:
            case ChangeLoginPsdAction.ACTION_REQUEST_ERROR_MESSAGE:
                String msg = event.getMessage();
                UIHelper.ToastMessage(this, msg);
                break;
            case SecurityAction.ACTION_RESULT_FAIL:
            case RegisterAction.ACTION_REQUEST_FAIL:
            case ChangeLoginPsdAction.ACTION_REQUEST_FAIL:
                UIHelper.ToastMessage(this, getString(R.string.net_work_error));
                break;
            case SecurityAction.ACTION_RESULT_VALID_TOKEN:
            case ChangeLoginPsdAction.ACTION_REQUEST_INVALID_TOKEN:
                UserRouter.getInstance(this).showActivity(UserUI.LoginActivity);
                finish();
                break;
            case RegisterAction.ACTION_REGISTER_SEND_CODE_START:
                mHandler.post(codeRunnable);
                sendCodeButton.setClickable(false);
                break;
            case ChangeLoginPsdAction.ACTION_CHANGE_PSD_SUCCESS:
                if (SecurityAction.GESTURE_VERIFY_ACTION_OPEN.equals(changeLoginPsdType)){
                    //打开应用时找回
                    Bundle bundle = new Bundle();
                    bundle.putString(SecurityAction.SECURITY_GESTURE_EDIT_ACTION, SecurityAction.GESTURE_MODIFY_RESET);
                    UserRouter.getInstance(this).showActivity(UserUI.GestureEdit, bundle);
                    finish();
                }else if (SecurityAction.GESTURE_VERIFY_ACTION_MODIFY.equals(changeLoginPsdType)){
                    //修改手势密码时找回
                    Bundle bundle = new Bundle();
                    bundle.putString(SecurityAction.SECURITY_GESTURE_EDIT_ACTION, SecurityAction.GESTURE_MODIFY_CHANGE);
                    UserRouter.getInstance(this).showActivity(UserUI.GestureEdit, bundle);
                    finish();
                }else if (SecurityAction.GESTURE_VERIFY_ACTION_CLOSE.equals(changeLoginPsdType)){
                    //关闭手势时找回
                    appActionsCreator.updateUserGesture(mUser.getUserId(), mUser.getToken(), "");
                }else if (SecurityAction.GESTURE_VERIFY_ACTION_HOME.equals(changeLoginPsdType)){
                    //home返回时找回
                    Bundle bundle = new Bundle();
                    bundle.putString(SecurityAction.SECURITY_GESTURE_EDIT_ACTION, SecurityAction.GESTURE_MODIFY_HOME);
                    UserRouter.getInstance(this).showActivity(UserUI.GestureEdit, bundle);
                    finish();
                }else {
                    finish();
                }
                break;
            case SecurityAction.ACTION_RESULT_SUCCESS:
                LuoBoDBM dbm = LuoBoDBM.getInstance(this);
                mUser.setHandPassword("");
                dbm.updateUserGesture(mUser, "");
                appContext.setUser(mUser);
                finish();
                break;
        }
    }

    private Runnable codeRunnable = new Runnable() {
        @Override
        public void run() {
            number--;
            if (number > 0){
                sendCodeButton.setText(String.format(Locale.US, getString(R.string.post_phone_check_code), number));
                mHandler.postDelayed(codeRunnable, 1000);
            }else {
                number = 60;
                sendCodeButton.setClickable(true);
                sendCodeButton.setText(getString(R.string.for_get_phone_check_code));
                mHandler.removeCallbacks(codeRunnable);
            }
        }
    };

    private void setupInputView(){
        inputPhoneView.setOnFocusChangeListener(this);
        inputCodeView.setOnFocusChangeListener(this);
        inputPsdView.setOnFocusChangeListener(this);
        inputPsdAgainView.setOnFocusChangeListener(this);

        inputPhoneView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString();
                if (input.length() < 11){
                    sendCodeButton.setEnabled(false);
                }else {
                    sendCodeButton.setEnabled(true);
                }
                inputIsValid();

                if (TextUtils.isEmpty(input)){
                    deletePhoneView.setVisibility(View.GONE);
                }else {
                    deletePhoneView.setVisibility(View.VISIBLE);
                }
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
                inputIsValid();

                if (TextUtils.isEmpty(s.toString())){
                    deleteCodeView.setVisibility(View.GONE);
                }else {
                    deleteCodeView.setVisibility(View.VISIBLE);
                }
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
                inputIsValid();

                if (TextUtils.isEmpty(s.toString())){
                    deletePsdView.setVisibility(View.GONE);
                }else {
                    deletePsdView.setVisibility(View.VISIBLE);
                }
            }
        });

        inputPsdAgainView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inputIsValid();

                if (TextUtils.isEmpty(s.toString())){
                    deletePsdAgainView.setVisibility(View.GONE);
                }else {
                    deletePsdAgainView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

}
