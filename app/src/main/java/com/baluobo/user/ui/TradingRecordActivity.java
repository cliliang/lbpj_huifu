package com.baluobo.user.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.baluobo.R;
import com.baluobo.app.flux.Store;
import com.baluobo.app.ui.BaseToolBarActivity;
import com.baluobo.common.tools.UIHelper;
import com.baluobo.common.tools.Util;
import com.baluobo.user.actions.TradingAction;
import com.baluobo.user.adapter.TradingRecordAdapter;
import com.baluobo.user.model.TradingRecord;
import com.baluobo.user.router.UserRouter;
import com.baluobo.user.router.UserUI;
import com.baluobo.user.stores.TradingStore;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.squareup.otto.Subscribe;

import java.util.List;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/18.
 */
public class TradingRecordActivity extends BaseToolBarActivity {
    private TradingStore tradingStore;
    private PullToRefreshListView listView;
    private TradingRecordAdapter adapter;
    private int page = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trading_record_activity_layout);
        setTitle(getString(R.string.trade_record));
        initDependencies();
        setupViews();
    }

    @Override
    protected void setupViews() {
        super.setupViews();
        listView = (PullToRefreshListView) findViewById(R.id.trading_recording_list);
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (!Util.isNetworkAvailable(appContext)){
                    UIHelper.ToastMessage(appContext, getString(R.string.net_work_invalid));
                    return;
                }
                page = 1;
                if (mUser == null){
                    return;
                }
                appActionsCreator.tradingRecordingData(mUser.getUserId(), mUser.getToken(), page);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (mUser == null){
                    return;
                }
                if (!Util.isNetworkAvailable(appContext)){
                    UIHelper.ToastMessage(appContext, getString(R.string.net_work_invalid));
                    return;
                }
                appActionsCreator.tradingRecordingData(mUser.getUserId(), mUser.getToken(), page);
            }
        });
        adapter = new TradingRecordAdapter(this);
        listView.setAdapter(adapter);
        View emptyView = LayoutInflater.from(this).inflate(R.layout.no_invest_empty_view_layout, null);
        listView.setEmptyView(emptyView);
    }

    @Override
    protected void initDependencies() {
        super.initDependencies();
        tradingStore = TradingStore.getInstance();
        dispatcher.register(tradingStore);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tradingStore.register(this);
        listView.setRefreshing();
    }

    @Override
    protected void onStop() {
        super.onStop();
        tradingStore.unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dispatcher.unregister(tradingStore);
    }

    @Subscribe
    public void onTradingRecordStoreChange(Store.StoreChangeEvent event) {
        switch (event.getType()){
            case TradingAction.ACTION_REQUEST_INVALID_TOKEN:
                listView.onRefreshComplete();
                UserRouter.getInstance(this).showActivity(UserUI.LoginActivity);
                finish();
                break;
            case TradingAction.ACTION_REQUEST_FAIL:
                listView.onRefreshComplete();
                UIHelper.ToastMessage(this, getString(R.string.net_work_error));
                break;
            case TradingAction.ACTION_REQUEST_ERROR_MESSAGE:
                listView.onRefreshComplete();
                String errorMessage = event.getMessage();
                UIHelper.ToastMessage(this, errorMessage);
                break;
            case TradingAction.ACTION_REQUEST_REFRESH_TRADING_RECORD_SUCCESS:
                listView.onRefreshComplete();
                List<TradingRecord> refreshData = tradingStore.getData();
                if (refreshData != null){
                    page++;
                    adapter.setRefreshData(refreshData);
                    adapter.notifyDataSetChanged();
                    if (refreshData.size() >= tradingStore.getTotalSize()){
//                        listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                    }
                }
                break;
            case TradingAction.ACTION_REQUEST_LOAD_TRADING_RECORD_SUCCESS:
                listView.onRefreshComplete();
                List<TradingRecord> loadData = tradingStore.getData();
                if (loadData != null){
                    page++;
                    adapter.setLoadingData(loadData);
                    adapter.notifyDataSetChanged();
                    if (adapter.getCount() >= tradingStore.getTotalSize()){
                        tradingStore.clearStoreData();
                        UIHelper.ToastMessage(this, getString(R.string.no_more_data));
                    }
                }
                break;
        }
    }
}
