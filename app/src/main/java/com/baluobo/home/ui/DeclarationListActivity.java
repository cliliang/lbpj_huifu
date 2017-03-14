package com.baluobo.home.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.baluobo.R;
import com.baluobo.app.router.MainRouter;
import com.baluobo.app.ui.BaseToolBarActivity;
import com.baluobo.common.module.ModuleID;
import com.baluobo.common.tools.UIHelper;
import com.baluobo.common.tools.Util;
import com.baluobo.home.actions.DeclarationAction;
import com.baluobo.home.adapter.DeclarationAdapter;
import com.baluobo.home.model.Declaration;
import com.baluobo.home.router.HomeRouter;
import com.baluobo.home.router.HomeUI;
import com.baluobo.user.actions.WebAction;
import com.baluobo.user.router.UserUI;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/19.
 */
public class DeclarationListActivity extends BaseToolBarActivity {
    private PullToRefreshListView listView;
    private DeclarationAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.declaration_activity_layout);
        setTitle(getString(R.string.declaration));
        setupViews();
    }

    @Override
    protected void setupViews() {
        super.setupViews();
        Bundle bundle = getIntent().getExtras();
        ArrayList<Declaration> declarations = null;
        if (bundle != null){
             declarations = bundle.getParcelableArrayList(DeclarationAction.BOUND_DATA_DECLARATION);
        }
        listView = (PullToRefreshListView) findViewById(R.id.declaration_list_view);
        listView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    return;
                }
                if (!Util.isNetworkAvailable(appContext)){
                    UIHelper.ToastMessage(appContext, getString(R.string.net_work_invalid));
                    return;
                }
                Declaration declaration = (Declaration) adapter.getItem(position - 1);
                if (declaration != null){
                    Bundle detailBundle = new Bundle();
                    detailBundle.putString(WebAction.WEB_REQUEST_TYPE, WebAction.REQUEST_HOME_DECLARATION);
                    detailBundle.putInt(WebAction.REQUEST_DECLARATION_ID, declaration.getnId());
                    MainRouter.getInstance(DeclarationListActivity.this).showActivity(ModuleID.USER_MODULE_ID, UserUI.WebActivity, detailBundle);
                }
            }
        });
        adapter = new DeclarationAdapter(this);
        if (declarations != null){
            adapter.setData(declarations);
        }
        listView.setAdapter(adapter);
    }
}
