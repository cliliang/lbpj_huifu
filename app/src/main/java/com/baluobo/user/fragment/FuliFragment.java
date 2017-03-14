package com.baluobo.user.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.baluobo.R;
import com.baluobo.app.AppContext;
import com.baluobo.app.model.BaseModel;
import com.baluobo.app.router.MainRouter;
import com.baluobo.app.ui.BaseFragment;
import com.baluobo.common.config.AppConfig;
import com.baluobo.common.module.ModuleID;
import com.baluobo.common.net.APIClient;
import com.baluobo.common.tools.UIHelper;
import com.baluobo.user.adapter.RedPacketAdapter;
import com.baluobo.user.model.RedPacket;
import com.baluobo.user.model.User;
import com.baluobo.user.router.UserUI;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/8/15.
 */
public class FuliFragment extends BaseFragment {
    private RedPacketAdapter adapter;
    private PullToRefreshListView listView;
    private static final String PACKET_TYPE = "order_type";
    private final String SAVE_DATA_LIST = "save_data_list";
    private boolean loadData = false;
    private int packetType;
    private int page = 1;

    public static FuliFragment getInstance(int type){
        FuliFragment fragment = new FuliFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(PACKET_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fuli_fragment_layout, container, false);
        setupView(view);
        initDependencies();
        return view;
    }

    @Override
    protected void initDependencies() {
        super.initDependencies();
    }

    @Override
    public void onResume() {
        super.onResume();
//        listView.onRefreshComplete();
        if (!loadData){
            listView.setRefreshing();
            loadData = true;
        }
    }

    @Override
    protected void onSaveState(Bundle outState) {
        super.onSaveState(outState);
        ArrayList<RedPacket> data = adapter.getData();
        if (data != null){
            outState.putParcelableArrayList(SAVE_DATA_LIST, data);
        }
    }

    @Override
    protected void onRestoreState(Bundle savedInstanceState) {
        super.onRestoreState(savedInstanceState);
        ArrayList<RedPacket> saveData = savedInstanceState.getParcelableArrayList(SAVE_DATA_LIST);
        adapter.setData(saveData);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setupView(View view) {
        super.setupView(view);
        Bundle bundle = getArguments();
        if (bundle != null){
            packetType = bundle.getInt(PACKET_TYPE);
        }
        listView = (PullToRefreshListView) view.findViewById(R.id.fuli_recycler_view);
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                getRedPacketData(mUser.getUserId(), packetType, page, mUser.getToken());
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                getRedPacketData(mUser.getUserId(), packetType, page, mUser.getToken());
            }
        });
        adapter = new RedPacketAdapter(getActivity());
        listView.setAdapter(adapter);
    }

    private void getRedPacketData(int uid, int type, final int loadPage, String token){
        APIClient.getInstance().getRedPacketData(uid, type, loadPage, token).enqueue(new Callback<BaseModel<ArrayList<RedPacket>>>() {
            @Override
            public void onResponse(Call<BaseModel<ArrayList<RedPacket>>> call, Response<BaseModel<ArrayList<RedPacket>>> response) {
                listView.onRefreshComplete();
                BaseModel baseModel = response.body();
                if (baseModel != null){
                    if (baseModel.isSuccess()){
                        updateUserToken(mUser.getUserId(), mUser.getToken(), baseModel.getToken());
                        ArrayList<RedPacket> data = (ArrayList<RedPacket>) baseModel.getRows();
                        if (loadPage == 1){
                            if (data != null){
                                page++;
                                adapter.setData(data);
                                adapter.notifyDataSetChanged();
                            }
                        }else {
                            if (data != null){
                                page++;
                                adapter.setLoadData(data);
                                adapter.notifyDataSetChanged();
                                if (adapter.getCount() >= baseModel.getTotal() ){
                                    UIHelper.ToastMessage(getActivity(), getString(R.string.no_more_data));
                                }
                            }
                        }
                    }else {
                        String msg = baseModel.getMessage();
                        UIHelper.ToastMessage(getActivity(), msg);
                        if (baseModel.getFlg() == AppConfig.INVALID_TOKEN){
                            MainRouter.getInstance(getActivity()).showActivity(ModuleID.USER_MODULE_ID, UserUI.LoginActivity);
                            getActivity().finish();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel<ArrayList<RedPacket>>> call, Throwable t) {
                listView.onRefreshComplete();
            }
        });
    }
}
