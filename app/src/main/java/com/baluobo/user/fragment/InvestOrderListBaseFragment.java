package com.baluobo.user.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.baluobo.common.tools.Util;
import com.baluobo.common.tools.WindowUtil;
import com.baluobo.user.actions.InvestAction;
import com.baluobo.app.flux.AppActionsCreator;
import com.baluobo.user.adapter.InvestOrderAdapter;
import com.baluobo.user.model.ExpOrder;
import com.baluobo.user.model.InvestOrder;
import com.baluobo.user.model.Order;
import com.baluobo.user.model.User;
import com.baluobo.user.router.UserRouter;
import com.baluobo.user.router.UserUI;
import com.baluobo.user.stores.InvestStore;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/21.
 */
public class InvestOrderListBaseFragment extends BaseFragment {
    private PullToRefreshListView listView;
    private InvestStore investStore;
    private InvestOrderAdapter adapter;
    private static final String ORDER_TYPE = "order_type";
    private final String ORDER_SAVE_DATA = "order_save_data";
    private boolean loadData = false;
    private int orderType;
    private int page = 1;

    public static InvestOrderListBaseFragment getFragment(int orderType){
        InvestOrderListBaseFragment fragment = new InvestOrderListBaseFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ORDER_TYPE, orderType);
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
        View view = inflater.inflate(R.layout.my_invest_list_base_fragment_layout, container, false);
        setupView(view);
        initDependencies();
        return view;
    }

    @Override
    protected void onSaveState(Bundle outState) {
        super.onSaveState(outState);
        InvestOrder saveOrder = adapter.getData();
        if (saveOrder != null){
            outState.putParcelable(ORDER_SAVE_DATA, saveOrder);
        }

    }

    @Override
    protected void onRestoreState(Bundle savedInstanceState) {
        super.onRestoreState(savedInstanceState);
        InvestOrder saveData = savedInstanceState.getParcelable(ORDER_SAVE_DATA);
        adapter.setData(saveData);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setupView(View view) {
        super.setupView(view);
        Bundle bundle = getArguments();
        if (bundle != null){
            orderType = bundle.getInt(ORDER_TYPE);
        }

        listView = (PullToRefreshListView) view.findViewById(R.id.my_invest_list_view);
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (!Util.isNetworkAvailable(appContext)){
                    UIHelper.ToastMessage(appContext, getString(R.string.net_work_invalid));
                    return;
                }
                page = 1;
                myInvestData(orderType, page, mUser.getUserId(), mUser.getToken());
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (!Util.isNetworkAvailable(appContext)){
                    UIHelper.ToastMessage(appContext, getString(R.string.net_work_invalid));
                    return;
                }
                myInvestData(orderType, page, mUser.getUserId(), mUser.getToken());
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!Util.isNetworkAvailable(appContext)){
                    UIHelper.ToastMessage(appContext, getString(R.string.net_work_invalid));
                    return;
                }
                Object object = adapter.getItem(position);
                if (!(object instanceof ExpOrder)){
                    Order order = (Order) object;
                    if (order != null){
                        Bundle orderBundle = new Bundle();
                        orderBundle.putParcelable(InvestAction.BUNDLE_ORDER_DATA, order);
                        UserRouter.getInstance(getActivity()).showActivity(UserUI.InvestDetail, orderBundle);
                    }
                }
            }
        });
        View emptyView = LayoutInflater.from(getActivity()).inflate(R.layout.no_invest_empty_view_layout, null);
        listView.setEmptyView(emptyView);
        adapter = new InvestOrderAdapter(getActivity());
        listView.setAdapter(adapter);
    }

    @Override
    protected void initDependencies() {
        super.initDependencies();
        investStore = new InvestStore();
        dispatcher.register(investStore);
    }

    @Override
    public void onResume() {
        super.onResume();
        investStore.register(this);
//        listView.onRefreshComplete();
        if (!loadData){
            listView.setRefreshing();
            loadData = true;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onStop() {
        super.onStop();
        investStore.unregister(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dispatcher.unregister(investStore);
    }

    public Context getContext(){
        FragmentActivity fragmentActivity = getActivity();
        if (fragmentActivity != null){
            Context context = fragmentActivity.getApplicationContext();
            if (context != null){
                return context;
            }
        }
        return null;
    }

    public void myInvestData(int investType, final int loadPage, int uid, String token){
        APIClient.getInstance().myInvestData(investType, loadPage, uid, token).enqueue(new Callback<BaseModel<InvestOrder>>() {
            @Override
            public void onResponse(Call<BaseModel<InvestOrder>> call, Response<BaseModel<InvestOrder>> response) {
                listView.onRefreshComplete();
                BaseModel baseModel = response.body();
                if (baseModel != null){
                    if (baseModel.isSuccess()){
                        InvestOrder data = (InvestOrder) baseModel.getRows();
                        if (loadPage == 1){
                            if (data != null){
                                page++;
                                adapter.setData(data);
                                adapter.notifyDataSetChanged();
                                //只在第一次有定单时，显示新手引导
//                                Context context = getContext();
//                                if (context != null){
//                                    AppConfig appConfig = AppConfig.getInstance(context);
//                                    if (!appConfig.isInvestShowed()){
//                                        WindowUtil.showPopupWindow(getActivity(), WindowUtil.TYPE_INVEST);
//                                        appConfig.setInvestShowed(true);
//                                    }
//                                }
                            }
                        }else {
                            if (data != null){
                                page++;
                                adapter.setLoadingData(data.getBuyOrders());
                                adapter.notifyDataSetChanged();
                                if (adapter.getDataSize() >= baseModel.getTotal() ){
                                    Context context = getContext();
                                    if (context != null){
                                        UIHelper.ToastMessage(context, getString(R.string.no_more_data));
                                    }
                                }
                            }
                        }
                    }else {
                        String msg = baseModel.getMessage();
                        Context context = getContext();
                        if (context != null){
                            UIHelper.ToastMessage(context, msg);
                            if (baseModel.getFlg() == AppConfig.INVALID_TOKEN && orderType == 3){
                                MainRouter.getInstance(context).showActivity(ModuleID.USER_MODULE_ID, UserUI.LoginActivity);
                                getActivity().finish();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel<InvestOrder>> call, Throwable t) {
                listView.onRefreshComplete();
                Context context = getContext();
                if (context != null){
                    UIHelper.ToastMessage(context, getString(R.string.net_work_error));
                }
            }
        });
    }


//    @Subscribe
//    public void onInvestStoreChange(Store.StoreChangeEvent event) {
//        switch (event.getType()){
//            case Action.ACTION_INVALID_TOKEN:
//                MainRouter.getInstance(getActivity()).showActivity(ModuleID.USER_MODULE_ID, UserUI.LoginActivity);
//                break;
//            case Action.ACTION_REQUEST_MESSAGE:
//                String msg = event.getMessage();
//                UIHelper.ToastMessage(getActivity(), msg);
//                break;
//            case Action.ACTION_REQUEST_FAIL:
//                UIHelper.ToastMessage(getActivity(), getString(R.string.net_work_error));
//                break;
//            case InvestAction.ACTION_REQUEST_REFRESH_INVEST_DATA_SUCCESS:
//                listView.onRefreshComplete();
//                ArrayList<Order> data = investStore.getData();
//                if (data != null){
//                    adapter.setData(data);
//                    adapter.notifyDataSetChanged();
//                    if (data.size() < investStore.getDataSize()){
//                        listView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
//                    }
//                }
//                break;
//            case InvestAction.ACTION_REQUEST_LOAD_INVEST_DATA_SUCCESS:
//                List<Order> loadData = investStore.getData();
//                if (loadData != null){
//                    adapter.setLoadingData(loadData);
//                    adapter.notifyDataSetChanged();
//                    if (loadData.size() >= investStore.getDataSize()){
//                        listView.setMode(PullToRefreshBase.Mode.DISABLED);
//                    }
//                }
//                break;
//        }
//    }

}
