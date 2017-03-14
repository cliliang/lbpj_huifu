package com.baluobo.find.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.baluobo.R;
import com.baluobo.app.flux.Store;
import com.baluobo.app.model.BaseModel;
import com.baluobo.app.router.MainRouter;
import com.baluobo.app.ui.BaseToolBarActivity;
import com.baluobo.common.db.LuoBoDBM;
import com.baluobo.common.module.ModuleID;
import com.baluobo.common.tools.UIHelper;
import com.baluobo.find.actions.SignInAction;
import com.baluobo.find.actions.SignInNoticeAction;
import com.baluobo.find.actions.SignRecordAction;
import com.baluobo.find.model.SignRecord;
import com.baluobo.find.stores.SignInNoticeStore;
import com.baluobo.find.stores.SignInStore;
import com.baluobo.find.stores.SignRecordStore;
import com.baluobo.find.views.SignCalendarView;
import com.baluobo.user.model.User;
import com.baluobo.user.router.UserUI;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/9/19.
 */
public class SignInActivity extends BaseToolBarActivity {
    private SignInStore signInStore;
    private SignRecordStore recordStore;
    private SignInNoticeStore signInNoticeStore;
    private SignCalendarView calendarView;
    private Calendar loadCalendar;
    private TextView countView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_activity_layout);
        setTitle(getString(R.string.sign_in_every_day));
        initDependencies();
        setupViews();
        User user = appContext.getUser();
        if (user != null){
            loadCalendar = Calendar.getInstance();
            appActionsCreator.signInRecord(user.getUserId(), user.getToken(), loadCalendar);
        }
    }

    @Override
    protected void initDependencies() {
        super.initDependencies();
        signInStore = SignInStore.getInstance();
        dispatcher.register(signInStore);
        recordStore = SignRecordStore.getInstance();
        dispatcher.register(recordStore);
        signInNoticeStore = SignInNoticeStore.getInstance();
        dispatcher.register(signInNoticeStore);
    }

    @Override
    protected void setupViews() {
        super.setupViews();
        calendarView = (SignCalendarView) findViewById(R.id.sign_in_calendar_view);
        calendarView.setOnSelectedBackCalendar(new SignCalendarView.OnSelecteCalendarListener() {
            @Override
            public void onBackCalendarSelected(Calendar calendar) {
                if (mUser != null){
                    loadCalendar = calendar;
                    appActionsCreator.signInRecord(mUser.getUserId(), mUser.getToken(), calendar);
                }
            }
        });
        Switch aSwitch = (Switch) findViewById(R.id.sign_in_calendar_switch);
        User user = appContext.getUser();
        if (user != null){
            aSwitch.setChecked(user.getIsOpenSignIn() == 1);
        }
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mUser != null){
                    appActionsCreator.autoNoticeSignIn(mUser.getUserId(), mUser.getToken(), isChecked ? 1: 0);
                }
            }
        });
        countView = (TextView) findViewById(R.id.sign_in_number_view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        signInStore.register(this);
        if (mUser != null){
            countView.setText(String.format(Locale.US, getString(R.string.sign_in_get_already), mUser.getUserScore()));
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        signInStore.unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dispatcher.unregister(signInStore);
        dispatcher.unregister(recordStore);
        dispatcher.unregister(signInNoticeStore);
    }

    @Subscribe
    public void onFeedbackStoreChange(Store.StoreChangeEvent event) {
        String type = event.getType();
        switch (type){
            case SignRecordAction.ACTION_REQUEST_START:
            case SignInAction.ACTION_REQUEST_START:
                showLoadingDialog();
                break;
            case SignRecordAction.ACTION_REQUEST_FINISH:
            case SignInAction.ACTION_REQUEST_FINISH:
                dismissLoadingDialog();
                break;
            case SignRecordAction.ACTION_REQUEST_FAIL:
            case SignInAction.ACTION_REQUEST_FAIL:
                UIHelper.ToastMessage(this, getString(R.string.net_work_error));
                break;
            case SignRecordAction.ACTION_REQUEST_INVALID_TOKEN:
            case SignInAction.ACTION_REQUEST_INVALID_TOKEN:
                MainRouter.getInstance(this).showActivity(ModuleID.USER_MODULE_ID, UserUI.LoginActivity);
                break;
            case SignRecordAction.ACTION_REQUEST_ERROR_MESSAGE:
            case SignInAction.ACTION_REQUEST_ERROR_MESSAGE:
                String msg = event.getMessage();
                UIHelper.ToastMessage(this, msg);
                break;
            case SignInAction.ACTION_REQUEST_SIGN_IN_SUCCESS:
                BaseModel baseModel = signInStore.getData();
                if (baseModel != null){
                    User newInfo = (User) baseModel.getRows();
                    if (newInfo != null){
                        newInfo.setGetScoreTime(Calendar.getInstance().getTimeInMillis());
                        mUser = newInfo;
                        appContext.setUser(mUser);
                        updateUserToken(mUser.getUserId(), mUser.getToken(), baseModel.getToken());
                        showSignDialog(this, baseModel.getMessage());
                    }
                }
                break;
            case SignRecordAction.ACTION_REQUEST_SIGN_RECORD_SUCCESS:
                ArrayList<SignRecord> signRecords = recordStore.getSignRecords();
                if (signRecords != null){
                    calendarView.setData(loadCalendar, signRecords);
                }
                break;
            case SignInNoticeAction.ACTION_SIGN_IN_NOTICE_CLOSE:
                if (mUser != null){
                    mUser.setIsOpenSignIn(0);
                    appContext.setUser(mUser);
                    LuoBoDBM.getInstance(this).updateUserInfo(mUser);
                }
                break;
            case SignInNoticeAction.ACTION_SIGN_IN_NOTICE_OPEN:
                if (mUser != null){
                    mUser.setIsOpenSignIn(1);
                    appContext.setUser(mUser);
                    LuoBoDBM.getInstance(this).updateUserInfo(mUser);
                }
                break;
        }
    }
}
