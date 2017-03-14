package com.baluobo.user.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.baluobo.R;
import com.baluobo.app.ui.BaseToolBarActivity;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/9/19.
 */
public class FeedbackSuccessActivity extends BaseToolBarActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_success_activity_layout);
        setTitle(getString(R.string.feedback));
    }
}
