package com.baluobo.home.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.baluobo.R;
import com.baluobo.app.model.BaseModel;
import com.baluobo.app.router.MainRouter;
import com.baluobo.app.ui.BaseFragment;
import com.baluobo.common.module.ModuleID;
import com.baluobo.common.net.APIClient;
import com.baluobo.common.tools.CountDownTimer;
import com.baluobo.common.tools.LuoboImageLoader;
import com.baluobo.common.tools.UIHelper;
import com.baluobo.common.tools.Util;
import com.baluobo.common.views.CycleViewPager;
import com.baluobo.common.views.ProductItemView;
import com.baluobo.home.actions.DeclarationAction;
import com.baluobo.home.model.Banner;
import com.baluobo.home.model.Declaration;
import com.baluobo.home.router.HomeRouter;
import com.baluobo.home.router.HomeUI;
import com.baluobo.product.actions.ProductInfoAction;
import com.baluobo.product.actions.ProductListAction;
import com.baluobo.product.bean.HomeProduct;
import com.baluobo.product.bean.Product;
import com.baluobo.product.router.ProductUI;
import com.baluobo.product.views.ProductOnlineItemView;
import com.baluobo.user.actions.WebAction;
import com.baluobo.user.model.ProductEnum;
import com.baluobo.user.router.UserUI;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * desc: 首页fragment 没有用flux，因为fragment在跳转到后一个Activity后，才调用了onStop, onDestroyView
 * Created by:chenliliang
 * Created on:16/5/4.
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener{
    private PullToRefreshScrollView scrollView;
    private CycleViewPager imageCycleView;
    private LinearLayout recommendLayout, onlineLayout, netInvalidLayout;
    private TextView textSwitcher;
    private ArrayList<Banner> banners = new ArrayList<>();
    private List<Product> products = new ArrayList<>();
    //倒计时产品
    private List<Product> onlineProducts = new ArrayList<>();
    private ArrayList<Declaration> declarations = new ArrayList<>();
    //产品倒计时
    private List<CountDownTimer> countDownTimers = new ArrayList<>();
    private int refresh = 0;
    //倒计时时间
    private List<Long> countDownNumbers = new ArrayList<>();
    //如果是新加载的数据，就用新数据的倒计时
    private boolean reload = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDependencies();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment_layout, container, false);
        setupView(view);
        return view;
    }

    @Override
    protected void initDependencies() {
        super.initDependencies();
    }

    @Override
    public void setupView(View view) {
        super.setupView(view);
        scrollView = (PullToRefreshScrollView) view.findViewById(R.id.home_fragment_scroll_view);
        imageCycleView= (CycleViewPager) view.findViewById(R.id.cycleView);
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenWidth = metrics.widthPixels;
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) imageCycleView.getLayoutParams();
        params.height = (int)(screenWidth * 16 / 25f);
        imageCycleView.setLayoutParams(params);
        textSwitcher = (TextView) view.findViewById(R.id.declaration_view);
        textSwitcher.setOnClickListener(this);
        view.findViewById(R.id.find_block_luobo_ding_tou).setOnClickListener(this);
        view.findViewById(R.id.find_block_kuai_zhuan).setOnClickListener(this);
        view.findViewById(R.id.find_block_ti_yan).setOnClickListener(this);
        view.findViewById(R.id.find_block_yin_piao).setOnClickListener(this);
        view.findViewById(R.id.find_block_invite).setOnClickListener(this);
        view.findViewById(R.id.find_block_calculate).setOnClickListener(this);
        view.findViewById(R.id.home_reload_data_button).setOnClickListener(this);
        recommendLayout = (LinearLayout) view.findViewById(R.id.home_recommend_products_layout);
        onlineLayout = (LinearLayout) view.findViewById(R.id.home_recommend_online_product_layout);
        netInvalidLayout = (LinearLayout) view.findViewById(R.id.home_net_invalid_page);
        scrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        scrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                refresh = 0;
                getHomeBanners();
                getHomeProduct();
                getDeclarationData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
            }
        });

        if (Util.isNetworkAvailable(getActivity())){
            netInvalidLayout.setVisibility(View.GONE);
        }else {
            netInvalidLayout.setVisibility(View.VISIBLE);
        }
    }

    private void resetScrollView(){
        refresh++;
        if (refresh >= 3){
            scrollView.onRefreshComplete();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (refresh == 0){
            scrollView.setPullDownToRefreshing(scrollView);
        }else {
            if (banners == null || banners.size() < 1){
                getHomeBanners();
            }else {
                setBannerData();
            }
            if (products == null || products.size() < 1){
                getHomeProduct();
            }else {
                setRecommendData();
            }
            if (onlineProducts != null && onlineProducts.size() > 0){
                reload = false;
                setOnlineData();
            }
            if (declarations == null || declarations.size() < 1){
                getDeclarationData();
            }else {
                setDeclarationData();
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        if (imageCycleView != null){
            imageCycleView.stopImageCycle();
        }

        for (CountDownTimer countDownTimer : countDownTimers){
            if (countDownTimer != null && getActivity() != null){
                countDownTimer.cancel();
            }
        }
        countDownTimers.clear();
        countDownNumbers.clear();
        onlineProducts.clear();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.declaration_view:
                if (Util.isNetworkAvailable(appContext)){
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList(DeclarationAction.BOUND_DATA_DECLARATION, declarations);
                    HomeRouter.getInstance(getActivity()).showActivity(HomeUI.DeclarationList, bundle);
                }else {
                    UIHelper.ToastMessage(appContext, getString(R.string.net_work_invalid));
                }
                break;
            case R.id.find_block_luobo_ding_tou:
                if (Util.isNetworkAvailable(appContext)){
                    Bundle dingTouBundle = new Bundle();
                    dingTouBundle.putInt(ProductListAction.PRODUCT_TYPE, ProductEnum.LUOBO_DING_TOU.getProductTypeId());
                    MainRouter.getInstance(getActivity()).showActivity(ModuleID.PRODUCT_MODULE_ID, ProductUI.ProductList, dingTouBundle);
                }else {
                    UIHelper.ToastMessage(appContext, getString(R.string.net_work_invalid));
                }
                break;
            case R.id.find_block_kuai_zhuan:
                if (Util.isNetworkAvailable(appContext)){
                    Bundle kuaiZhuanBundle = new Bundle();
                    kuaiZhuanBundle.putInt(ProductInfoAction.BOUND_PRODUCT_DATA_ID, 0);
                    MainRouter.getInstance(getActivity()).showActivity(ModuleID.PRODUCT_MODULE_ID, ProductUI.KuaiZhuanInfo, kuaiZhuanBundle);
                }else {
                    UIHelper.ToastMessage(appContext, getString(R.string.net_work_invalid));
                }
                break;
            case R.id.find_block_ti_yan:
                if (Util.isNetworkAvailable(appContext)){
                    MainRouter.getInstance(getActivity()).showActivity(ModuleID.PRODUCT_MODULE_ID, ProductUI.Experience);
                }else {
                    UIHelper.ToastMessage(appContext, getString(R.string.net_work_invalid));
                }
                break;
            case R.id.find_block_yin_piao:
                if (Util.isNetworkAvailable(appContext)){
                    Bundle yinPiaoBundle = new Bundle();
                    yinPiaoBundle.putInt(ProductListAction.PRODUCT_TYPE, ProductEnum.LUOBO_YIN_PIAO.getProductTypeId());
                    MainRouter.getInstance(getActivity()).showActivity(ModuleID.PRODUCT_MODULE_ID, ProductUI.ProductList, yinPiaoBundle);
                }else {
                    UIHelper.ToastMessage(appContext, getString(R.string.net_work_invalid));
                }
                break;
            case R.id.find_block_invite:
                if (Util.isNetworkAvailable(appContext)){
                    if(mUser != null){
                        HomeRouter.getInstance(getActivity()).showActivity(HomeUI.Invite);
                    }else {
                        MainRouter.getInstance(getActivity()).showActivity(ModuleID.USER_MODULE_ID, UserUI.LoginActivity);
                    }
                }else {
                    UIHelper.ToastMessage(appContext, getString(R.string.net_work_invalid));
                }
                break;
            case R.id.find_block_calculate:
                HomeRouter.getInstance(getActivity()).showActivity(HomeUI.Calculate);
                break;
            case R.id.home_reload_data_button:
                if (Util.isNetworkAvailable(appContext)){
                    netInvalidLayout.setVisibility(View.GONE);
                    scrollView.setPullDownToRefreshing(scrollView);
                }else {
                    UIHelper.ToastMessage(getActivity(), getString(R.string.net_work_invalid));
                }
                break;
        }
    }

    private void initBannerView(ArrayList<String>urlList) {
        CycleViewPager.ImageCycleViewListener mAdCycleViewListener = new CycleViewPager.ImageCycleViewListener() {
            @Override
            public void onImageClick(int position, View imageView) {
                if (Util.isNetworkAvailable(appContext)){
                    Banner banner = banners.get(position);
                    if (banner != null){
                        Bundle bundle = new Bundle();
                        String bannerURL = banner.getActivityUrl();
                        if (!TextUtils.isEmpty(bannerURL) && !"000".equals(bannerURL)){
                            bundle.putString(WebAction.WEB_REQUEST_TYPE, WebAction.REQUEST_HOME_BANNER);
                            bundle.putString(WebAction.REQUEST_BANNER_URL, banner.getActivityUrl());
                            MainRouter.getInstance(getActivity()).showActivity(ModuleID.USER_MODULE_ID, UserUI.WebActivity, bundle);
                        }
                    }
                }else{
                    UIHelper.ToastMessage(appContext, getString(R.string.net_work_invalid));
                }
            }
            @Override
            public void displayImage(String imageURL, ImageView imageView) {
                LuoboImageLoader.getInstance().displayImage(imageURL, imageView);
            }
        };
        imageCycleView.setImageResources(urlList, mAdCycleViewListener);
        imageCycleView.startImageCycle();
    }

    private void getHomeBanners(){
        APIClient.getInstance().getHomeBanners(0).enqueue(new Callback<BaseModel<ArrayList<Banner>>>() {
            @Override
            public void onResponse(Call<BaseModel<ArrayList<Banner>>> call, Response<BaseModel<ArrayList<Banner>>> response) {
                resetScrollView();
                BaseModel baseModel = response.body();
                if (baseModel != null){
                    if (baseModel.isSuccess()){
                        ArrayList<Banner> list = (ArrayList<Banner>) baseModel.getRows();
                        if (list != null && list.size() > 0){
                            banners = list;
                            setBannerData();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel<ArrayList<Banner>>> call, Throwable t) {
                resetScrollView();
            }
        });
    }

    private void getHomeProduct(){
        APIClient.getInstance().getHomeProduct().enqueue(new Callback<BaseModel<HomeProduct>>() {
            @Override
            public void onResponse(Call<BaseModel<HomeProduct>> call, Response<BaseModel<HomeProduct>> response) {
                resetScrollView();
                BaseModel baseModel = response.body();
                if (baseModel != null){
                    if (baseModel.isSuccess()){
                        clearOldData();
                        HomeProduct homeProduct = (HomeProduct) baseModel.getRows();
                        if (homeProduct != null){
                            List<Product> onlineProduct = homeProduct.getOnLineGoods();
                            List<Product> commonProduct = homeProduct.getDtYpmGoods();
                            if (onlineProduct != null && onlineProduct.size() > 0){
                                onlineProducts = onlineProduct;
                                reload = true;
                                setOnlineData();
                            }
                            if (commonProduct != null && commonProduct.size() > 0){
                                products = commonProduct;
                                setRecommendData();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel<HomeProduct>> call, Throwable t) {
                resetScrollView();
            }
        });
    }

    private void clearOldData(){
        if (countDownTimers != null && countDownTimers.size() > 0){
            for (CountDownTimer countDownTimer : countDownTimers){
                countDownTimer.cancel();
            }
        }
        countDownTimers.clear();
        countDownNumbers.clear();
        onlineProducts.clear();
        onlineLayout.removeAllViews();
        recommendLayout.removeAllViews();

        products.clear();
    }

    private void getDeclarationData(){
        APIClient.getInstance().getDeclarationData().enqueue(new Callback<BaseModel<ArrayList<Declaration>>>() {
            @Override
            public void onResponse(Call<BaseModel<ArrayList<Declaration>>> call, Response<BaseModel<ArrayList<Declaration>>> response) {
                resetScrollView();
                BaseModel baseModel = response.body();
                if (baseModel != null){
                    if (baseModel.isSuccess()){
                        ArrayList<Declaration> list = (ArrayList<Declaration>) baseModel.getRows();
                        if (list != null && list.size() > 0){
                            declarations = list;
                            setDeclarationData();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel<ArrayList<Declaration>>> call, Throwable t) {
                resetScrollView();
            }
        });
    }

    private void setBannerData(){
        netInvalidLayout.setVisibility(View.GONE);
        ArrayList<String> urlList=new ArrayList<>();
        for (Banner banner : banners){
            urlList.add(APIClient.getBannerHost() + banner.getActivityScrollPic());
        }
        initBannerView(urlList);
    }

    private void setRecommendData(){
        netInvalidLayout.setVisibility(View.GONE);
        recommendLayout.removeAllViews();
        for (final Product product : products){
            ProductItemView itemView = new ProductItemView(getActivity());
            if (product != null){
                itemView.setItemData(product);
                recommendLayout.addView(itemView);
                if (ProductEnum.getProduct(product.getGcId()) == ProductEnum.LUOBO_KUAI_ZHUAN){
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (Util.isNetworkAvailable(appContext)){
                                MainRouter.getInstance(appContext).showActivity(ModuleID.PRODUCT_MODULE_ID, ProductUI.KuaiZhuanInfo);
                            }else{
                                UIHelper.ToastMessage(appContext, getString(R.string.net_work_invalid));
                            }
                        }
                    });
                }else {
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (Util.isNetworkAvailable(appContext)){
                                Bundle bundle = new Bundle();
                                bundle.putInt(ProductInfoAction.BOUND_PRODUCT_DATA_ID, product.getGoodId());
                                MainRouter.getInstance(getActivity()).showActivity(ModuleID.PRODUCT_MODULE_ID, ProductUI.ProductInfo, bundle);
                            }else{
                                UIHelper.ToastMessage(appContext, getString(R.string.net_work_invalid));
                            }
                        }
                    });
                }
            }
        }
    }

    private void setOnlineData(){
        netInvalidLayout.setVisibility(View.GONE);
        if (countDownTimers != null && countDownTimers.size() > 0){
            for (CountDownTimer countDownTimer : countDownTimers){
                countDownTimer.cancel();
            }
        }
        countDownTimers.clear();
        onlineLayout.removeAllViews();


        if (reload){
            countDownNumbers.clear();
        }
        if (onlineProducts != null && countDownTimers != null && onlineProducts.size() > 0){
            for (int i = 0; i < onlineProducts.size(); i++){
                final int j =i;
                Product product = onlineProducts.get(i);
                final ProductOnlineItemView itemView = new ProductOnlineItemView(getActivity());
                if (itemView != null){
                    itemView.setItemData(product);
                    onlineLayout.addView(itemView);
                    long countDown;
                    if (reload){
                        countDown = product.getOnLineTimeStamp() * 1000;
                        countDownNumbers.add(countDown);
                    }else {
                        countDown = countDownNumbers.get(i);
                    }
                    CountDownTimer countDownTimer = new CountDownTimer(5000+countDown, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            itemView.setCountDown(millisUntilFinished);
                            countDownNumbers.remove(j);
                            countDownNumbers.add(j, millisUntilFinished);
                        }

                        @Override
                        public void onFinish() {
                            itemView.setCountDown(0);
                            getHomeProduct();
                        }
                    }.start();
                    countDownTimers.add(countDownTimer);
                }
            }
        }
    }

    private void setDeclarationData(){
        netInvalidLayout.setVisibility(View.GONE);
        if (declarations != null && declarations.size() > 0){
            Declaration declaration = declarations.get(0);
            if (declaration != null){
                textSwitcher.setText(declaration.getNewsTitle());
            }
        }

    }
}
