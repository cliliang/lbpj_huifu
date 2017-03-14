package com.baluobo.user.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.baluobo.R;
import com.baluobo.app.AppContext;
import com.baluobo.app.flux.Store;
import com.baluobo.app.router.MainRouter;
import com.baluobo.app.ui.BaseToolBarActivity;
import com.baluobo.common.tools.UIHelper;
import com.baluobo.common.tools.Util;
import com.baluobo.user.actions.RedeemListAction;
import com.baluobo.app.flux.AppActionsCreator;
import com.baluobo.user.adapter.OnRedeemButtonClickListener;
import com.baluobo.user.adapter.RedeemAdapter;
import com.baluobo.user.model.Order;
import com.baluobo.user.model.User;
import com.baluobo.user.router.UserRouter;
import com.baluobo.user.router.UserUI;
import com.baluobo.user.stores.RedeemListStore;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/18.
 */
public class RedeemListActivity extends BaseToolBarActivity {
    private PullToRefreshListView listView;
    private RedeemListStore redeemListStore;
    private RedeemAdapter adapter;
    private int page = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.redeem_activity_layout);
        setTitle(getString(R.string.redeem));
        initDependencies();
        setupViews();
    }

    @Override
    protected void setupViews() {
        super.setupViews();
        listView = (PullToRefreshListView) findViewById(R.id.redeem_list_view);
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                appActionsCreator.getRedeemList(mUser.getUserId(), mUser.getToken(), page);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                appActionsCreator.getRedeemList(mUser.getUserId(), mUser.getToken(), page);
            }
        });
        adapter = new RedeemAdapter(this);
        adapter.setOnRedeemButtonClickListener(new OnRedeemButtonClickListener() {
            @Override
            public void onRedeemButtonClickListener(Order order) {
                if (!Util.isNetworkAvailable(appContext)){
                    UIHelper.ToastMessage(appContext, getString(R.string.net_work_invalid));
                    return;
                }
                if (order != null){
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(RedeemListAction.BOUND_REDEEM_DATA, order);
                    UserRouter.getInstance(RedeemListActivity.this).showActivity(UserUI.RedeemBackActivity, bundle);
                }
            }
        });
        listView.setAdapter(adapter);
        View emptyView = LayoutInflater.from(this).inflate(R.layout.no_invest_empty_view_layout, null);
        listView.setEmptyView(emptyView);
    }

    @Override
    protected void initDependencies() {
        super.initDependencies();
        redeemListStore = RedeemListStore.getInstance();
        dispatcher.register(redeemListStore);
    }

    @Override
    protected void onResume() {
        super.onResume();
        redeemListStore.register(this);
        listView.setRefreshing();
        if (mUser == null){
            finish();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        redeemListStore.unregister(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        redeemListStore.clearMoreData();
        dispatcher.unregister(redeemListStore);
    }
    @Subscribe
    public void onRdeemStoreChange(Store.StoreChangeEvent event) {
        String type = event.getType();
        switch (type){
            case RedeemListAction.ACTION_REQUEST_FAIL:
                listView.onRefreshComplete();
                UIHelper.ToastMessage(this, getString(R.string.net_work_error));
                break;
            case RedeemListAction.ACTION_REQUEST_ERROR_MESSAGE:
                listView.onRefreshComplete();
                String msg = event.getMessage();
                UIHelper.ToastMessage(this, msg);
                break;
            case RedeemListAction.ACTION_REQUEST_INVALID_TOKEN:
                listView.onRefreshComplete();
                MainRouter.getInstance(this).showActivity(UserUI.LoginActivity);
                finish();
                break;
            case RedeemListAction.ACTION_REQUEST_REFRESH_DATA_SUCCESS:
                listView.onRefreshComplete();
                ArrayList<Order> refreshData = redeemListStore.getProducts();
                adapter.setData(refreshData);
                adapter.notifyDataSetChanged();
                page++;
                break;
            case RedeemListAction.ACTION_REQUEUST_LOAD_DATA_SUCCESS:
                listView.onRefreshComplete();
                ArrayList<Order> loadData = redeemListStore.getProducts();
                if (loadData != null){
                    page++;
                    adapter.setLoadData(loadData);
                    adapter.notifyDataSetChanged();
                    int dataSize = adapter.getCount();
                    if (dataSize >= redeemListStore.getTotalSize()){
                        UIHelper.ToastMessage(this, getString(R.string.no_more_data));
                    }
                    redeemListStore.clearMoreData();
                }
                break;
        }
    }

}
