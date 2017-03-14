package com.baluobo.user.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.baluobo.R;
import com.baluobo.app.flux.Store;
import com.baluobo.app.router.MainRouter;
import com.baluobo.app.ui.BaseToolBarActivity;
import com.baluobo.common.db.LuoBoDBM;
import com.baluobo.common.module.ModuleID;
import com.baluobo.common.tools.UIHelper;
import com.baluobo.common.views.gesture.GestureCallBack;
import com.baluobo.common.views.gesture.GestureContentView;
import com.baluobo.common.views.gesture.LockIndicator;
import com.baluobo.home.router.HomeUI;
import com.baluobo.user.actions.SecurityAction;
import com.baluobo.user.router.UserRouter;
import com.baluobo.user.router.UserUI;
import com.baluobo.user.stores.GestureEditStore;
import com.squareup.otto.Subscribe;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/7/8.
 */
public class GestureEditActivity extends BaseToolBarActivity {
    private LockIndicator indicatorView;
    private TextView statusView;
    private FrameLayout frameLayout;
    private GestureContentView gestureView;
    private GestureEditStore gestureStore;

    private boolean mIsFirstInput = true;
    private String mFirstPassword = null;
    private String gestureModifyType;
    private int modifyErrorCount = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gesture_edit_activity_layout);
        setTitle(getString(R.string.gesture_psd));
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            gestureModifyType = bundle.getString(SecurityAction.SECURITY_GESTURE_EDIT_ACTION);
        }else {
            finish();
        }
        initDependencies();
        setupViews();
    }

    @Override
    protected void initDependencies() {
        super.initDependencies();
        gestureStore = GestureEditStore.getInstance();
        dispatcher.register(gestureStore);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gestureStore.register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        gestureStore.unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dispatcher.unregister(gestureStore);
    }

    @Override
    protected void setupViews() {
        super.setupViews();
        indicatorView = (LockIndicator) findViewById(R.id.gesture_indicator_view);
        statusView = (TextView) findViewById(R.id.gesture_status_view);
        frameLayout = (FrameLayout) findViewById(R.id.gesture_root_view);
        gestureView = new GestureContentView(this, false, "", new GestureCallBack() {
            @Override
            public void onGestureCodeInput(String inputCode) {
                if (!isInputPassValidate(inputCode)){
                    statusView.setText(getString(R.string.draw_gesture_psd_lest_4));
                    gestureView.clearDrawlineState(0L);
                    return;
                }
                if (mIsFirstInput){
                    mFirstPassword = inputCode;
                    updateCodeList(inputCode);
                    gestureView.clearDrawlineState(0L);
                    statusView.setText(getString(R.string.please_draw_your_gesture_psd_again));
                }else {
                    if (inputCode.equals(mFirstPassword)){
                        gestureView.clearDrawlineState(0L);
                        statusView.setText(getString(R.string.draw_gesture_psd_success));
                        if (mUser != null){
                            appActionsCreator.updateUserGesture(mUser.getUserId(), mUser.getToken(), inputCode);
                        }
                    }else {
                        statusView.setText(getString(R.string.tow_draw_gesture_psd_different));
                        gestureView.clearDrawlineState(600L);
                        if (SecurityAction.GESTURE_MODIFY_CHANGE.equals(gestureModifyType)){
                            modifyErrorCount++;
                            if (modifyErrorCount >= 2){
                                UIHelper.ToastMessage(GestureEditActivity.this, getString(R.string.two_different_so_reset));
                                finish();
                            }
                        }
                    }
                }
                mIsFirstInput = false;
            }

            @Override
            public void checkedSuccess() {

            }

            @Override
            public void checkedFail() {

            }
        });
        gestureView.setParentView(frameLayout);
        updateCodeList("");
    }

    @Subscribe
    public void onGestureEditStoreChange(Store.StoreChangeEvent event) {
        String type = event.getType();
        switch (type){
            case SecurityAction.ACTION_RESULT_START:
                showLoadingDialog();
                break;
            case SecurityAction.ACTION_RESULT_FINISH:
                dismissLoadingDialog();
                break;
            case SecurityAction.ACTION_RESULT_FAIL:
                UIHelper.ToastMessage(this, getString(R.string.net_work_error));
                break;
            case SecurityAction.ACTION_RESULT_ERROR_MESSAGE:
                String msg = event.getMessage();
                UIHelper.ToastMessage(this, msg);
                break;
            case SecurityAction.ACTION_RESULT_VALID_TOKEN:
                UserRouter.getInstance(this).showActivity(UserUI.LoginActivity);
                break;
            case SecurityAction.ACTION_RESULT_SUCCESS:
                if (mUser != null){
                    LuoBoDBM dbm = LuoBoDBM.getInstance(this);
                    if (dbm.updateUserGesture(mUser, mFirstPassword)){
                        mUser.setHandPassword(mFirstPassword);
                        appContext.setUser(mUser);
                    }
                }
                if (SecurityAction.GESTURE_MODIFY_NEW.equals(gestureModifyType)){
                    //开启手势密码
                }else if (SecurityAction.GESTURE_MODIFY_CHANGE.equals(gestureModifyType)){
                    //修改手势密码
                    setResult(RESULT_OK);
                }else if (SecurityAction.GESTURE_MODIFY_RESET.equals(gestureModifyType)){
                    //忘记手势密码后重置
                    MainRouter.getInstance(GestureEditActivity.this).showActivity(ModuleID.HOME_MODULE_ID, HomeUI.MainActivity);
                }else if (SecurityAction.GESTURE_MODIFY_HOME.equals(gestureModifyType)){
                    Intent intent = new Intent(SecurityAction.RECEIVER_ACTION_DISMISS_GESTURE_DIALOG);
                    this.sendBroadcast(intent);
                }
                finish();
                break;
        }

    }

    private void updateCodeList(String inputCode) {
        indicatorView.setPath(inputCode);
    }

    private boolean isInputPassValidate(String inputPassword) {
        if (TextUtils.isEmpty(inputPassword) || inputPassword.length() < 4) {
            return false;
        }
        return true;
    }
}
