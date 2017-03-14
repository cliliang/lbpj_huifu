package com.baluobo.user.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.baluobo.R;
import com.baluobo.app.AppContext;
import com.baluobo.app.flux.Store;
import com.baluobo.app.model.BaseModel;
import com.baluobo.app.ui.BaseToolBarActivity;
import com.baluobo.common.db.LuoBoDBM;
import com.baluobo.common.tools.UIHelper;
import com.baluobo.app.flux.AppActionsCreator;
import com.baluobo.common.tools.Util;
import com.baluobo.user.actions.UserInfoAction;
import com.baluobo.user.actions.WebAction;
import com.baluobo.user.model.User;
import com.baluobo.user.router.UserRouter;
import com.baluobo.user.router.UserUI;
import com.baluobo.user.stores.BankCardStore;
import com.baluobo.user.stores.UserInfoStore;
import com.squareup.otto.Subscribe;

import java.util.Locale;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/16.
 */
public class RechargeActivity extends BaseToolBarActivity {
    private UserInfoStore userInfoStore;
    private TextView usableView, bankCardView;
    private EditText inputView;
    private TextView button, showText, bankShowText;
    private String requestType;
    private static String enableString;
    private boolean updateInfo = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recharge_activity_layout);
        initDependencies();
        setupViews();
        setViewData();
    }

    @Override
    protected void initDependencies() {
        super.initDependencies();
        userInfoStore = UserInfoStore.getInstance();
        dispatcher.register(userInfoStore);
    }

    private void setViewData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            requestType = bundle.getString(WebAction.WEB_REQUEST_TYPE);
            if (WebAction.REQUEST_RECHARGE.equals(requestType)) {
                setTitle(getString(R.string.recharge));
                button.setText(getString(R.string.recharge));
                inputView.setInputType(InputType.TYPE_CLASS_NUMBER);
                inputView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
                findViewById(R.id.recharge_service_money_view).setVisibility(View.GONE);
            } else {
                setTitle(getString(R.string.withdraw));
                setPricePoint(inputView);
                showText.setText(getString(R.string.recharge_show_text));
                bankShowText.setText(getString(R.string.recharge_bank_card_show));
                inputView.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                inputView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(9)});
                button.setText(getString(R.string.withdraw));
            }
        }
    }

    public static void setPricePoint(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                resetInputView(editText, s.toString());
            }
        });
    }

    private static void resetInputView(EditText editText, String edit){
        float inputFloat = 0f;
        float enableFloat = 0f;
        try {
            inputFloat = Float.parseFloat(edit);
            enableFloat = Float.parseFloat(enableString);
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        if (Float.compare(inputFloat, enableFloat) > 0){
            editText.setText(enableString);
            editText.setSelection(enableString.length());
        }
    }

    @Override
    protected void setupViews() {
        super.setupViews();
        bankShowText = (TextView) findViewById(R.id.recharge_bank_show_text);
        showText = (TextView) findViewById(R.id.recharge_show_text);
        usableView = (TextView) findViewById(R.id.recharge_usable_money);
        bankCardView = (TextView) findViewById(R.id.recharge_bank_info);
        inputView = (EditText) findViewById(R.id.recharge_input_view);
        button = (TextView) findViewById(R.id.recharge_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Util.isNetworkAvailable(appContext)){
                    UIHelper.ToastMessage(appContext, getString(R.string.net_work_invalid));
                    return;
                }
                if (invalidInput()) {
                    Bundle bundle = new Bundle();
                    bundle.putDouble(WebAction.REQUEST_BOUND_MONEY, Double.parseDouble(inputView.getText().toString()));
                    bundle.putString(WebAction.WEB_REQUEST_TYPE, requestType);
                    UserRouter.getInstance(RechargeActivity.this).showActivity(UserUI.WebActivity, bundle);
                    updateInfo = true;
                }
            }
        });
        inputView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    button.setEnabled(false);
                } else {
                    button.setEnabled(true);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        userInfoStore.register(this);
        if (mUser != null) {
            setViewData(mUser);
        }
        if (updateInfo && mUser != null) {
            appActionsCreator.updateUserInfo(mUser.getUserId(), mUser.getToken());
            updateInfo = false;
        }
    }

    private void setViewData(User user) {
        if (user != null) {
            enableString = user.getEnAbleMoney();
            if (TextUtils.isEmpty(enableString)) {
                usableView.setText("0.0");
            } else {
                usableView.setText(enableString);
            }

            String bankString = user.getBankCard();
            if (!TextUtils.isEmpty(bankString) && bankString.length() > 4) {
                bankCardView.setText(String.format(Locale.US, getString(R.string.user_recharge_bank_card_name), user.getBankCardName(), bankString.substring(bankString.length() - 4, bankString.length())));
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        userInfoStore.unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dispatcher.unregister(userInfoStore);
    }

    private boolean invalidInput() {
        String input = inputView.getText().toString();
        if (TextUtils.isEmpty(input)) {
            if (requestType.equals(WebAction.REQUEST_RECHARGE)) {
                UIHelper.ToastMessage(this, getString(R.string.please_input_recharge_money));
            } else {
                UIHelper.ToastMessage(this, getString(R.string.please_input_redeem_money));
            }
            return false;
        }
        return true;
    }

    @Subscribe
    public void onLoginStoreChange(Store.StoreChangeEvent event) {
        String type = event.getType();
        switch (type) {
            case UserInfoAction.ACTION_REQUEST_START:
                showLoadingDialog();
                break;
            case UserInfoAction.ACTION_REQUEST_FINISH:
                dismissLoadingDialog();
                break;
            case UserInfoAction.ACTION_REQUEST_FAIL:
                UIHelper.ToastMessage(this, getString(R.string.net_work_error));
                break;
            case UserInfoAction.ACTION_REQUEST_ERROR_MESSAGE:
                String msg = event.getMessage();
                UIHelper.ToastMessage(this, msg);
                break;
            case UserInfoAction.ACTION_REQUEST_INVALID_TOKEN:
                UserRouter.getInstance(this).showActivity(UserUI.LoginActivity);
                finish();
                break;
            case UserInfoAction.ACTION_REQUEST_UPDATE_USER_INFO_SUCCESS:
                BaseModel baseModel = userInfoStore.getUser();
                if (baseModel != null) {
                    User user = (User) baseModel.getRows();
                    if (user != null) {
                        mUser = user;
                        appContext.setUser(mUser);
                        setViewData(mUser);
                        LuoBoDBM.getInstance(this).updateUserInfo(mUser);
                        updateUserToken(mUser.getUserId(), mUser.getToken(), baseModel.getToken());
                    }
                }
                break;
        }
    }
}
