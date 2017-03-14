package com.baluobo.user.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
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
import com.baluobo.user.actions.FeedbackAction;
import com.baluobo.app.flux.AppActionsCreator;
import com.baluobo.user.model.User;
import com.baluobo.user.router.UserRouter;
import com.baluobo.user.router.UserUI;
import com.baluobo.user.stores.FeedbackStore;
import com.squareup.otto.Subscribe;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/15.
 */
public class FeedbackActivity extends BaseToolBarActivity {
    private FeedbackStore feedbackStore;
    private EditText inputView, contactsView;
    private TextView numberView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_activity_layout);
        setTitle(getString(R.string.feedback));
        setupViews();
        initDependencies();
    }

    @Override
    protected void setupViews() {
        super.setupViews();
        inputView = (EditText) findViewById(R.id.feedback_input_view);
        contactsView = (EditText) findViewById(R.id.feedback_input_contacts_view);
        numberView = (TextView) findViewById(R.id.feedback_input_number_view);
        findViewById(R.id.feedback_hot_line_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHotLine();
            }
        });
        findViewById(R.id.feedback_submit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Util.isNetworkAvailable(appContext)){
                    UIHelper.ToastMessage(appContext, getString(R.string.net_work_invalid));
                    return;
                }
                if (mUser != null){
                    appActionsCreator.submitFeedback(inputView.getText().toString(), contactsView.getText().toString(),mUser.getUserId());
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
                numberView.setText(s.length() + "/200");
            }
        });
    }

    @Override
    protected void initDependencies() {
        super.initDependencies();
        feedbackStore = FeedbackStore.getInstance();
        dispatcher.register(feedbackStore);
    }

    @Override
    protected void onResume() {
        super.onResume();
        feedbackStore.register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        feedbackStore.unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dispatcher.unregister(feedbackStore);
    }

    @Subscribe
    public void onFeedbackStoreChange(Store.StoreChangeEvent event) {
        switch (event.getType()){
            case FeedbackAction.ACTION_FEEDBACK_INPUT_LESS_MIN:
                UIHelper.ToastMessage(this, getString(R.string.alert_feedabck_input_less_5));
                break;
            case FeedbackAction.ACTION_REQUEST_START:
                showLoadingDialog();
                break;
            case FeedbackAction.ACTION_REQUEST_FINISH:
                dismissLoadingDialog();
                break;
            case FeedbackAction.ACTION_REQUEST_ERROR_MESSAGE:
                String msg = event.getMessage();
                UIHelper.ToastMessage(this, msg);
                break;
            case FeedbackAction.ACTION_REQUEST_FAIL:
                UIHelper.ToastMessage(this, getString(R.string.net_work_error));
                break;
            case FeedbackAction.ACTION_REQUEST_TOKEN_INVALID:
                UserRouter.getInstance(this).showActivity(UserUI.LoginActivity);
                break;
            case FeedbackAction.ACTION_FEEDBACK_SUCCESS:
                UserRouter.getInstance(this).showActivity(UserUI.FeedbackSuccess);
                finish();
                break;
        }
    }
}
