package com.baluobo.product.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.baluobo.R;
import com.baluobo.app.flux.Store;
import com.baluobo.app.ui.BaseFragment;
import com.baluobo.common.tools.UIHelper;
import com.baluobo.product.actions.ProductInfoAction;
import com.baluobo.product.actions.ProductRecordAction;
import com.baluobo.product.adapter.ProductRecordAdapter;
import com.baluobo.product.stores.ProductRecordStore;
import com.baluobo.app.flux.AppActionsCreator;
import com.baluobo.user.model.Order;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/20.
 */
public class ProductRecordFragment extends BaseFragment {
    private PullToRefreshListView listView;
    private ProductRecordAdapter adapter;
    private ProductRecordStore recordStore;
    private int page = 2;
    private int goodId;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initDependencies();
        View view = inflater.inflate(R.layout.product_record_fragment_layout, container, false);
        setupView(view);
        return view;
    }

    @Override
    protected void initDependencies() {
        super.initDependencies();
        recordStore = ProductRecordStore.getInstance();
        dispatcher.register(recordStore);
    }

    @Override
    public void setupView(View view) {
        super.setupView(view);
        listView = (PullToRefreshListView) view.findViewById(R.id.product_info_record_list);
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                appActionsCreator.getMoreProductOrders(goodId, page);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                appActionsCreator.getMoreProductOrders(goodId, page);
            }
        });
        adapter = new ProductRecordAdapter(getActivity());
        Bundle bundle = getArguments();
        if (bundle != null){
            ArrayList<Order> data = bundle.getParcelableArrayList(ProductInfoAction.BOUND_FRAGMENT_RECORD_DATA);
            if (data != null){
                adapter.setProductRefreshData(data);
                if (data.size() > 0){
                    Order order = data.get(0);
                    goodId = order.getgId();
                }
            }
        }
        listView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        recordStore.register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        recordStore.unregister(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dispatcher.unregister(recordStore);
    }

    @Subscribe
    public void onProductRecordStoreChange(Store.StoreChangeEvent event) {
        String type = event.getType();
        listView.onRefreshComplete();
        switch (type){
            case ProductRecordAction.ACTION_REQUEST_FAIL:
                UIHelper.ToastMessage(getActivity(), getString(R.string.net_work_error));
                break;
            case ProductRecordAction.ACTION_REQUEST_ERROR_MESSAGE:
                String msg = event.getMessage();
                UIHelper.ToastMessage(getActivity(), msg);
                break;
            case ProductRecordAction.ACTION_REQUEST_REFRESH_ORDER_LIST_SUCCESS:
                ArrayList<Order> list = recordStore.getOrders();
                if (list != null && list.size() > 0){
                    adapter.setProductRefreshData(list);
                    adapter.notifyDataSetChanged();
                }
                break;
            case ProductRecordAction.ACTION_REQUEST_LOAD_ORDER_LIST_SUCCESS:
                ArrayList<Order> orders = recordStore.getOrders();
                if (orders != null && orders.size() > 0){
                    page++;
                    adapter.setProductLoadData(orders);
                    adapter.notifyDataSetChanged();
                    int totalSize = recordStore.getTotalSize();
                    if (adapter.getCount() >= totalSize){
                        UIHelper.ToastMessage(getActivity(), getString(R.string.no_more_data));
                        recordStore.clearMoreData();
                    }
                }
                break;
        }
    }
}
