package com.baluobo.user.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;

import com.baluobo.R;
import com.baluobo.app.ui.BaseToolBarActivity;
import com.baluobo.user.actions.WebAction;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/6/2.
 */
public class StaticWebActivity extends BaseToolBarActivity {
    private WebView webview;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.static_web_view_activity_layout);
        setupViews();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            String type = bundle.getString(WebAction.STATIC_WEB_BOUND_TYPE);
            if (WebAction.STATIC_WEB_TYPE_ABOUT_US.equals(type)){
                setTitle(getString(R.string.about_us));
                webview.loadUrl("file:///android_asset/appH5/aboutUs.html");
            }else if (WebAction.STATIC_WEB_TYPE_SERVICE.equals(type)){
                setTitle(getString(R.string.use_service_title));
                webview.loadUrl("file:///android_asset/appH5/protocol.html");
            }
        }
    }

    @Override
    protected void setupViews() {
        super.setupViews();
        webview = (WebView) findViewById(R.id.static_web_view);
    }
}
