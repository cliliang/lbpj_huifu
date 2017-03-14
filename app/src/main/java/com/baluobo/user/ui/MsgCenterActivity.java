package com.baluobo.user.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.baluobo.R;
import com.baluobo.app.flux.Store;
import com.baluobo.app.router.MainRouter;
import com.baluobo.app.ui.BaseToolBarActivity;
import com.baluobo.common.module.ModuleID;
import com.baluobo.common.tools.UIHelper;
import com.baluobo.common.tools.Util;
import com.baluobo.find.router.FindUI;
import com.baluobo.user.actions.MsgAction;
import com.baluobo.user.adapter.MsgCenterAdapter;
import com.baluobo.user.model.Message;
import com.baluobo.user.router.UserUI;
import com.baluobo.user.stores.MsgStore;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/15.
 */
public class MsgCenterActivity extends BaseToolBarActivity {
    private MsgStore msgStore;
    private PullToRefreshListView listView;
    private MsgCenterAdapter adapter;
    private String token;
    private int userId;
    private int page = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.msg_center_layout_layout);
        setTitle(getString(R.string.message_center));
        initDependencies();
        setupViews();
    }

    @Override
    protected void initDependencies() {
        super.initDependencies();
        msgStore = MsgStore.getInstance();
        dispatcher.register(msgStore);
    }

    @Override
    protected void setupViews() {
        super.setupViews();
        listView = (PullToRefreshListView) findViewById(R.id.msg_center_listview);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                appActionsCreator.getMessageData(page, userId, token);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                appActionsCreator.getMessageData(page, userId, token);
            }
        });

        View emptyView = LayoutInflater.from(this).inflate(R.layout.no_message_empty_view_layout, null);
        listView.setEmptyView(emptyView);
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        adapter = new MsgCenterAdapter(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!Util.isNetworkAvailable(appContext)){
                    UIHelper.ToastMessage(appContext, getString(R.string.net_work_invalid));
                    return;
                }
                Message msg = (Message) adapter.getItem(position - 1);
                if (msg != null){
                    int type = msg.getMessType();
                    // 2用户投资 3会员特权礼遇 4红包到期
                    switch (type){
                        case 2:
                            MainRouter.getInstance(MsgCenterActivity.this).showActivity(UserUI.MyInvest);
                            break;
                        case 3:
                            MainRouter.getInstance(MsgCenterActivity.this).showActivity(ModuleID.FIND_MODULE_ID, FindUI.VIPCenter);
                            break;
                        case 4:
                            MainRouter.getInstance(MsgCenterActivity.this).showActivity(UserUI.FuliCenter);
                            break;
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        msgStore.register(this);
        if (mUser != null){
            userId = mUser.getUserId();
            token = mUser.getToken();
            listView.setRefreshing();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        msgStore.unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dispatcher.unregister(msgStore);
    }

    @Subscribe
    public void onLoginStoreChange(Store.StoreChangeEvent event) {
        switch (event.getType()){
            case MsgAction.ACTION_REQUEST_FAIL:
                UIHelper.ToastMessage(this, getString(R.string.net_work_error));
                listView.onRefreshComplete();
                break;
            case MsgAction.ACTION_REQUEST_ERROR_MESSAGE:
                listView.onRefreshComplete();
                String msg = event.getMessage();
                UIHelper.ToastMessage(this, msg);
                break;
            case MsgAction.ACTION_REQUEST_INVALID_TOKEN:
                listView.onRefreshComplete();
                MainRouter.getInstance(this).showActivity(ModuleID.USER_MODULE_ID, UserUI.LoginActivity);
                finish();
                break;
            case MsgAction.ACTION_MSG_CENTER_REFRESH_SUCCESS:
                //标记已读
                appActionsCreator.seenAllMessage(userId, token);
                listView.onRefreshComplete();
                List<Message> data = msgStore.getMsgData();
                if (data != null){
                    adapter.setData(data);
                    adapter.notifyDataSetChanged();
                    page+=1;
                }
                break;
            case MsgAction.ACTION_MSG_CENTER_LOAD_MORE_SUCCESS:
                listView.onRefreshComplete();
                List<Message> loadData = msgStore.getMsgData();
                if (loadData != null && loadData.size() > 0){
                    adapter.setLoadData(loadData);
                    adapter.notifyDataSetChanged();
                    page+=1;
                }
                break;
            case MsgAction.ACTION_MSG_CENTER_SEEN_ALL_SUCCESS:
                List<Message> allData = msgStore.getMsgData();
                if (allData != null && allData.size() > 0){
                    List<Message> temp = new ArrayList<>();
                    for (Message message : allData){
                        message.setSeenType(1);
                        temp.add(message);
                    }
                    adapter.setData(temp);
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }
}
