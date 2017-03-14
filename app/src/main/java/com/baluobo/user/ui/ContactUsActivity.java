package com.baluobo.user.ui;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.baluobo.R;
import com.baluobo.app.ui.BaseToolBarActivity;
import com.baluobo.user.router.UserUI;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/14.
 */
public class ContactUsActivity extends BaseToolBarActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_us_activity_layout);
        setTitle(getString(R.string.contact_us));
        setupViews();
    }

    @Override
    protected void setupViews() {
        super.setupViews();
        findViewById(R.id.contact_us_hot_line_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHotLine();
            }
        });
    }
}
