package com.baluobo.product.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.baluobo.R;
import com.baluobo.app.flux.Store;
import com.baluobo.app.ui.BaseToolBarActivity;
import com.baluobo.common.tools.UIHelper;
import com.baluobo.common.tools.Util;
import com.baluobo.product.actions.ProductInfoAction;
import com.baluobo.product.actions.ProductListAction;
import com.baluobo.product.adapter.ProductItemAdapter;
import com.baluobo.product.bean.Product;
import com.baluobo.product.router.ProductRouter;
import com.baluobo.product.router.ProductUI;
import com.baluobo.product.stores.ProductListStore;
import com.baluobo.user.model.ProductEnum;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/19.
 */
public class ProductListActivity extends BaseToolBarActivity {
    private PullToRefreshListView listView;
    private ProductListStore productStore;
    private ProductItemAdapter adapter;
    private int productType;
    private int page = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_base_activity_layout);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            productType = bundle.getInt(ProductListAction.PRODUCT_TYPE, ProductEnum.LUOBO_DING_TOU.getProductTypeId());
            ProductEnum productEnum = ProductEnum.getProduct(productType);
            switch (productEnum){
                case LUOBO_DING_TOU:
                    setTitle(getString(R.string.luobo_ding_tou));
                    break;
                case LUOBO_XIN_SHOU:
                    setTitle(getString(R.string.luobo_xin_shou));
                    break;
                case LUOBO_YIN_PIAO:
                    setTitle(getString(R.string.luobo_yin_piao));
                    break;
            }
        }
        initDependencies();
        setupViews();
    }

    @Override
    protected void setupViews() {
        super.setupViews();
        listView = (PullToRefreshListView) findViewById(R.id.product_list_view);
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                appActionsCreator.getProductList(productType, page);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                appActionsCreator.getProductList(productType, page);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                if (position < 2){
                    return;
                }
                if (!Util.isNetworkAvailable(appContext)){
                    UIHelper.ToastMessage(appContext, getString(R.string.net_work_invalid));
                    return;
                }
                Product product = (Product) adapter.getItem(position - 2);
                if (product != null){
                    bundle.putInt(ProductInfoAction.BOUND_PRODUCT_DATA_ID, product.getGoodId());
                    ProductRouter.getInstance(ProductListActivity.this).showActivity(ProductUI.ProductInfo, bundle);
                }
            }
        });
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenWidth = metrics.widthPixels;
        adapter = new ProductItemAdapter(this, screenWidth);
        listView.setAdapter(adapter);
        listView.setRefreshing(true);
    }

    @Override
    protected void initDependencies() {
        super.initDependencies();
        productStore = ProductListStore.getInstance();
        dispatcher.register(productStore);
    }

    @Override
    protected void onResume() {
        super.onResume();
        productStore.register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        productStore.unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dispatcher.unregister(productStore);
    }

    @Subscribe
    public void onProductStoreChange(Store.StoreChangeEvent event) {
        String type = event.getType();
        switch (type){
            case ProductListAction.ACTION_REQUEST_ERROR_MESSAGE:
                listView.onRefreshComplete();
                String msg = event.getMessage();
                UIHelper.ToastMessage(this, msg);
                break;
            case ProductListAction.ACTION_REQUEST_FAIL:
                listView.onRefreshComplete();
                UIHelper.ToastMessage(this, getString(R.string.net_work_error));
                break;
            case ProductListAction.PRODUCT_LIST_REQUEST_REFRESH_SUCCESS:
                listView.onRefreshComplete();
                ArrayList<Product> refreshData = productStore.getProducts();
                if (refreshData != null){
                    page++;
                    adapter.setRefreshData(refreshData);
                    adapter.notifyDataSetChanged();
                    productStore.clearCache();
                    int totalSize = productStore.getDataTotalSize();
//                    if (refreshData.size()  < totalSize){
//                        listView.setMode(PullToRefreshBase.Mode.BOTH);
//                    }else {
//                        listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
//                    }
                }
                break;
            case ProductListAction.PRODUCT_LIST_REQUEST_LOAD_SUCCESS:
                listView.onRefreshComplete();
                ArrayList<Product> loadData = productStore.getProducts();
                if (loadData != null && loadData.size() > 0){
                    page ++;
                    adapter.setLoadData(loadData);
                    adapter.notifyDataSetChanged();
                    int totalSize = productStore.getDataTotalSize();
                    if (adapter.getCount() - 1 >= totalSize){
                        UIHelper.ToastMessage(this, getString(R.string.no_more_data));
                    }
                    productStore.clearCache();
                }
                break;
        }
    }
}
