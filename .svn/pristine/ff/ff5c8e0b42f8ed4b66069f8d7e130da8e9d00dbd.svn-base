package com.baluobo.user.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.baluobo.R;
import com.baluobo.app.flux.Store;
import com.baluobo.app.model.BaseModel;
import com.baluobo.app.ui.BaseToolBarActivity;
import com.baluobo.common.db.LuoBoDBM;
import com.baluobo.common.tools.UIHelper;
import com.baluobo.common.tools.Util;
import com.baluobo.common.views.CustomItemView;
import com.baluobo.common.views.SecurityView;
import com.baluobo.user.actions.AccountInfoAction;
import com.baluobo.user.actions.ChangeLoginPsdAction;
import com.baluobo.user.actions.SecurityAction;
import com.baluobo.user.actions.WebAction;
import com.baluobo.user.model.User;
import com.baluobo.user.router.UserRouter;
import com.baluobo.user.router.UserUI;
import com.baluobo.user.stores.AccountInfoStore;
import com.squareup.otto.Subscribe;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/16.
 */
public class AccountSecurityActivity extends BaseToolBarActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private CustomItemView openAccountItem, phoneItem, bankItem;
    private SecurityView securityView;
    private TextView changeGestureView;
    private Switch gestureSwitch;
    private AccountInfoStore infoStore;
    private boolean refreshInfo = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_security_activity_layout);
        setTitle(getString(R.string.account_info));
        setupViews();
        initDependencies();
    }

    @Override
    protected void setupViews() {
        super.setupViews();
        openAccountItem = (CustomItemView) findViewById(R.id.account_security_open_account);
        bankItem = (CustomItemView) findViewById(R.id.account_security_bound_bank_view);
        bankItem.setOnClickListener(this);
        phoneItem = (CustomItemView) findViewById(R.id.account_security_phone);
        phoneItem.setOnClickListener(this);
        findViewById(R.id.account_security_login_psd).setOnClickListener(this);
        securityView = (SecurityView) findViewById(R.id.account_security_circle_view);
        changeGestureView = (TextView) findViewById(R.id.account_security_change_gesture_psd);
        changeGestureView.setOnClickListener(this);
        gestureSwitch = (Switch) findViewById(R.id.account_security_gesture_switch_view);
    }

    @Override
    protected void initDependencies() {
        super.initDependencies();
        infoStore = AccountInfoStore.getInstance();
        dispatcher.register(infoStore);
    }

    @Override
    protected void onResume() {
        super.onResume();
        infoStore.register(this);

        if (refreshInfo && mUser != null){
            refreshInfo = false;
            appActionsCreator.updateUserInfoInAccountInfo(mUser.getUserId(), mUser.getToken());
        }

        gestureSwitch.setOnCheckedChangeListener(null);
        if (mUser != null) {
            String gesture = mUser.getHandPassword();
            if (TextUtils.isEmpty(gesture)) {
                gestureSwitch.setChecked(false);
                changeGestureView.setVisibility(View.GONE);
            } else {
                gestureSwitch.setChecked(true);
                changeGestureView.setVisibility(View.VISIBLE);
            }
            gestureSwitch.setOnCheckedChangeListener(this);
            setViewData(mUser);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        infoStore.unregister(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        dispatcher.unregister(infoStore);
    }

    @Subscribe
    public void onAccountInfoStoreChange(Store.StoreChangeEvent event) {
        String type = event.getType();
        switch (type){
            case AccountInfoAction.ACTION_REQUEST_START:
                showLoadingDialog();
                break;
            case AccountInfoAction.ACTION_REQUEST_FINISH:
                dismissLoadingDialog();
                break;
            case AccountInfoAction.ACTION_REQUEST_FAIL:
                UIHelper.ToastMessage(this, getString(R.string.net_work_error));
                break;
            case AccountInfoAction.ACTION_REQUEST_INVALID_TOKEN:
                UserRouter.getInstance(this).showActivity(UserUI.LoginActivity);
                break;
            case AccountInfoAction.ACTION_REQUEST_ERROR_MESSAGE:
                String msg = event.getMessage();
                UIHelper.ToastMessage(this, msg);
                break;
            case AccountInfoAction.ACTION_REQUEST_ACCOUNT_INFO_REFRUSH_DATA_SUCCESS:
                BaseModel baseModel = infoStore.getUser();
                if (baseModel != null){
                    if (baseModel.isSuccess()){
                        User user = (User) baseModel.getRows();
                        if (user != null){
                            LuoBoDBM.getInstance(this).updateUserInfo(user);
                            mUser = user;
                            appContext.setUser(mUser);
                            updateUserToken(mUser.getUserId(), mUser.getToken(), baseModel.getToken());
                            setViewData(mUser);
                        }
                    }
                }
                break;
        }
    }


    private void setViewData(User user) {
        if (user == null){
            return;
        }
        int security = 1;
        if (user.isAutonym()) {
            security = 2;
            String nameString = user.getUserName();
            String idString = user.getIdCard();
            if (!TextUtils.isEmpty(nameString) && nameString.length() > 1) {
                nameString = "*" + nameString.substring(1, nameString.length());
            }
            if (!TextUtils.isEmpty(idString) && idString.length() > 7) {
                idString = idString.substring(0, 3) + "***********" + idString.substring(idString.length() - 4, idString.length());
            }
            openAccountItem.setSubContent(nameString + " " + idString);
            String bankCard = user.getBankCard();
            if (!TextUtils.isEmpty(bankCard)) {
                security = 3;
            }
        } else {
            openAccountItem.setSubContent(getString(R.string.account_not_certification));
            openAccountItem.setOnClickListener(this);
        }

        String bankCard = mUser.getBankCard();
        if (bankCard != null && bankCard.length() > 7){
            bankCard = bankCard.substring(0, 4) + "**** **** ****" + bankCard.substring(bankCard.length() - 3, bankCard.length());
            bankItem.setSubContent(bankCard);
        }else {
            bankItem.setSubContent(getString(R.string.account_not_bound_card));
        }

        setSecurityRank(security);
        String phone = user.getMobile();
        if (phone != null && phone.length() == 11) {
            phoneItem.setSubContent(phone.substring(0, 3) + "****" + phone.substring(7, 11));
        }
    }


    @Override
    public void onClick(View v) {
        if (!Util.isNetworkAvailable(appContext)){
            UIHelper.ToastMessage(appContext, getString(R.string.net_work_invalid));
            return;
        }
        switch (v.getId()) {
            case R.id.account_security_open_account:
                refreshInfo = true;
                if (!mUser.isAutonym()) {
                    Bundle bundle = new Bundle();
                    bundle.putString(WebAction.WEB_REQUEST_TYPE, WebAction.REQUEST_OPEN_ACCOUNT);
                    UserRouter.getInstance(this).showActivity(UserUI.WebActivity, bundle);
                }
                break;
            case R.id.account_security_phone:
                UserRouter.getInstance(this).showActivity(UserUI.ChangeMobileStepOne);
                break;
            case R.id.account_security_login_psd:
                Bundle bundle = new Bundle();
                bundle.putString(ChangeLoginPsdAction.ACTION_CHANGE_PSD_TYPE, ChangeLoginPsdAction.ACTION_TYPE_CHANGE_LOGIN_PAS);
                UserRouter.getInstance(this).showActivity(UserUI.ChangeLoginPsd, bundle);
                break;
            case R.id.account_security_change_gesture_psd:
                Bundle verifyBundle = new Bundle();
                verifyBundle.putString(SecurityAction.SECURITY_GESTURE_VERIFY_ACTION, SecurityAction.GESTURE_VERIFY_ACTION_MODIFY);
                UserRouter.getInstance(this).showActivity(UserUI.GestureVerify, verifyBundle);
                break;
            case R.id.account_security_bound_bank_view:
                refreshInfo = true;
                if (!mUser.isAutonym()){
                    UIHelper.ToastMessage(AccountSecurityActivity.this, getString(R.string.please_first_register_in_huifu));
                    Bundle bankBundle = new Bundle();
                    bankBundle.putString(WebAction.WEB_REQUEST_TYPE, WebAction.REQUEST_OPEN_ACCOUNT);
                    UserRouter.getInstance(this).showActivity(UserUI.WebActivity, bankBundle);
                    return;
                }
                UserRouter.getInstance(this).showActivity(UserUI.BankCardManager);
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            Bundle bundle = new Bundle();
            bundle.putString(SecurityAction.SECURITY_GESTURE_EDIT_ACTION, SecurityAction.GESTURE_MODIFY_NEW);
            UserRouter.getInstance(this).showActivity(UserUI.GestureEdit, bundle);
        } else {
            Bundle bundle = new Bundle();
            bundle.putString(SecurityAction.SECURITY_GESTURE_VERIFY_ACTION, SecurityAction.GESTURE_VERIFY_ACTION_CLOSE);
            UserRouter.getInstance(this).showActivity(UserUI.GestureVerify, bundle);
        }
    }

    long duration = 3000;
    private float angle = 80;
    private int colorR = 0x7f;
    private int colorG;
    private int colorB;
    private String rankText;

    private void setSecurityRank(int rank) {
        rankText = getString(R.string.security_low);
        float sweepAngle;
        switch (rank) {
            case 1:
                sweepAngle = 80;
                break;
            case 2:
                sweepAngle = 160;
                break;
            case 3:
                sweepAngle = 240;
                break;
            default:
                sweepAngle = 60;
                break;
        }

        ValueAnimator animator = ValueAnimator.ofFloat(0, sweepAngle);
        animator.setDuration(duration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                angle = (float) animation.getAnimatedValue();
                securityView.setData(rankText, angle, colorR, colorG, colorB);

            }
        });
        animator.start();
        startLowTranslateColor(rank, 0xf3, 0xf3, 0xf3, 0xff, 0x7f, 0x68);
    }

    private void startLowTranslateColor(final int rank, int oldR, int oldG, int oldB, int newR, int newG, int newB) {
        int colorDuration;
        if (rank == 1) {
            colorDuration = 3000;
        } else if (rank == 2) {
            colorDuration = 1500;
        } else {
            colorDuration = 1000;
        }
        ValueAnimator rValueAnimator = ValueAnimator.ofInt(oldR, newR);
        rValueAnimator.setDuration(colorDuration);
        rValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                colorR = (int) animation.getAnimatedValue();
            }
        });
        rValueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (rank != 1) {
                    startMiddleTranslateColor(rank, 0xff, 0x7f, 0x68, 0xff, 0xc5, 0x62);
                }
            }
        });
        rValueAnimator.start();


        ValueAnimator gValueAnimator = ValueAnimator.ofInt(oldG, newG);
        gValueAnimator.setDuration(colorDuration);
        gValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                colorG = (int) animation.getAnimatedValue();

            }
        });
        gValueAnimator.start();


        ValueAnimator bValueAnimator = ValueAnimator.ofInt(oldB, newB);
        bValueAnimator.setDuration(colorDuration);
        bValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                colorB = (int) animation.getAnimatedValue();

            }
        });
        bValueAnimator.start();
    }

    private void startMiddleTranslateColor(final int rank, int oldR, int oldG, int oldB, int newR, int newG, int newB) {
        rankText = getString(R.string.security_middle);
        int colorDuration;
        if (rank == 1) {
            colorDuration = 3000;
        } else if (rank == 2) {
            colorDuration = 1500;
        } else {
            colorDuration = 1000;
        }
        ValueAnimator rValueAnimator = ValueAnimator.ofInt(oldR, newR);
        rValueAnimator.setDuration(colorDuration);
        rValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                colorR = (int) animation.getAnimatedValue();
            }
        });
        rValueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (rank == 3) {
                    startHighTranslateColor(0xff, 0xc5, 0x62, 0x7c, 0xed, 0xbd);
                }
            }
        });
        rValueAnimator.start();


        ValueAnimator gValueAnimator = ValueAnimator.ofInt(oldG, newG);
        gValueAnimator.setDuration(colorDuration);
        gValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                colorG = (int) animation.getAnimatedValue();

            }
        });
        gValueAnimator.start();


        ValueAnimator bValueAnimator = ValueAnimator.ofInt(oldB, newB);
        bValueAnimator.setDuration(colorDuration);
        bValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                colorB = (int) animation.getAnimatedValue();

            }
        });
        bValueAnimator.start();
    }

    private void startHighTranslateColor(int oldR, int oldG, int oldB, int newR, int newG, int newB) {
        rankText = getString(R.string.security_high);
        int colorDuration = 1000;
        ValueAnimator rValueAnimator = ValueAnimator.ofInt(oldR, newR);
        rValueAnimator.setDuration(colorDuration);
        rValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                colorR = (int) animation.getAnimatedValue();
            }
        });
        rValueAnimator.start();


        ValueAnimator gValueAnimator = ValueAnimator.ofInt(oldG, newG);
        gValueAnimator.setDuration(colorDuration);
        gValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                colorG = (int) animation.getAnimatedValue();

            }
        });
        gValueAnimator.start();


        ValueAnimator bValueAnimator = ValueAnimator.ofInt(oldB, newB);
        bValueAnimator.setDuration(colorDuration);
        bValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                colorB = (int) animation.getAnimatedValue();

            }
        });
        bValueAnimator.start();
    }
}
