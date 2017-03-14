package com.baluobo.home.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
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
import com.baluobo.common.db.LuoBoDBM;
import com.baluobo.common.module.ModuleID;
import com.baluobo.common.net.APIClient;
import com.baluobo.common.tools.UIHelper;
import com.baluobo.common.tools.Util;
import com.baluobo.common.views.FindItemView;
import com.baluobo.find.model.FindNews;
import com.baluobo.find.model.OnFindNewsItemClickListener;
import com.baluobo.find.router.FindUI;
import com.baluobo.home.adapter.FindActivityAdapter;
import com.baluobo.home.adapter.OnBannerItemClickListener;
import com.baluobo.home.model.Banner;
import com.baluobo.home.router.HomeRouter;
import com.baluobo.home.router.HomeUI;
import com.baluobo.product.actions.ProductInfoAction;
import com.baluobo.product.actions.ProductListAction;
import com.baluobo.product.router.ProductUI;
import com.baluobo.user.actions.WebAction;
import com.baluobo.user.model.ProductEnum;
import com.baluobo.user.model.User;
import com.baluobo.user.router.UserUI;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.senab.photoview.Compat;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/8/3.
 */
public class FindFragment extends BaseFragment implements View.OnClickListener{
    private RecyclerViewPager recyclerViewPager;
    private FindActivityAdapter viewpagerAdapter;
    private LinearLayout containerLayout, exchangeLayout, netInvalidLayout;
    private PullToRefreshScrollView scrollView;
    private TextView scoreNumberView, vipLevelView, signButton;
    private TextView exchangeResultView;
    private ImageView vipLevelImage;
    private ArrayList<FindNews> findNews = new ArrayList<>();
    private ArrayList<Banner> banners = new ArrayList<>();
    private int loadDataNumber = 0;
    private int newsPage = 1;
    private boolean showExchangeDialog = false;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initDependencies();
        View view = inflater.inflate(R.layout.find_fragment_layout, container, false);
        setupView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (loadDataNumber == 0){
            scrollView.setPullDownToRefreshing(scrollView);
        }else {
            if (findNews == null || findNews.size() < 1){
                getFindNews();
            }else {
                setNewsData();
            }

            if (banners == null || banners.size() < 1){
                getFindBanners();
            }else {
                setBannerData();
            }
        }

        if (mUser != null){
            updateUserInfo(mUser.getUserId(), mUser.getToken());
        }
        setUserViewData();
    }

    private void resetLoadState(){
        loadDataNumber++;
        if (loadDataNumber >= 2){
            scrollView.onRefreshComplete();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void setupView(View view) {
        super.setupView(view);
        scrollView = (PullToRefreshScrollView) view.findViewById(R.id.find_home_root_scroll_view);
        TextView statusView = (TextView) view.findViewById(R.id.find_status_bar_view);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) statusView.getLayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            params.height = getStatusBarHeight();
        }else {
            params.height = 0;
        }

        scoreNumberView = (TextView) view.findViewById(R.id.find_fragment_user_score_view);
        vipLevelView = (TextView) view.findViewById(R.id.find_fragment_vip_level_view);
        vipLevelImage = (ImageView) view.findViewById(R.id.find_fragment_vip_level_image);
        view.findViewById(R.id.find_fragment_sign_button).setOnClickListener(this);

        view.findViewById(R.id.luobo_icon_show_layout).setOnClickListener(this);

        recyclerViewPager = (RecyclerViewPager) view.findViewById(R.id.find_banner_view_pager);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPager.setLayoutManager(layoutManager);
        viewpagerAdapter = new FindActivityAdapter(getActivity());
        recyclerViewPager.setAdapter(viewpagerAdapter);
        viewpagerAdapter.setOnBannerItemClickListener(new OnBannerItemClickListener() {
            @Override
            public void onItemClick(Banner banner) {
                if (Util.isNetworkAvailable(appContext)){
                    Bundle bundle = new Bundle();
                    String bannerURL = banner.getActivityUrl();
                    if (!TextUtils.isEmpty(bannerURL) && !"000".equals(bannerURL)){
                        if (bannerURL.contains("mobile1601")){
                            if (bannerURL.contains("theme")){

                            }else if (bannerURL.contains("dingtou")){
                                Bundle dingTouBundle = new Bundle();
                                dingTouBundle.putInt(ProductListAction.PRODUCT_TYPE, ProductEnum.LUOBO_DING_TOU.getProductTypeId());
                                MainRouter.getInstance(getActivity()).showActivity(ModuleID.PRODUCT_MODULE_ID, ProductUI.ProductList, dingTouBundle);
                            }else if (bannerURL.contains("yinpiaomiao")){
                                Bundle yinPiaoBundle = new Bundle();
                                yinPiaoBundle.putInt(ProductListAction.PRODUCT_TYPE, ProductEnum.LUOBO_YIN_PIAO.getProductTypeId());
                                MainRouter.getInstance(getActivity()).showActivity(ModuleID.PRODUCT_MODULE_ID, ProductUI.ProductList, yinPiaoBundle);
                            }else if (bannerURL.contains("yaoqingyouli")){
                                if(mUser != null){
                                    HomeRouter.getInstance(getActivity()).showActivity(HomeUI.Invite);
                                }else {
                                    MainRouter.getInstance(getActivity()).showActivity(ModuleID.USER_MODULE_ID, UserUI.LoginActivity);
                                }
                            }else if (bannerURL.contains("kuaizhuan")){
                                Bundle kuaiZhuanBundle = new Bundle();
                                kuaiZhuanBundle.putInt(ProductInfoAction.BOUND_PRODUCT_DATA_ID, 0);
                                MainRouter.getInstance(getActivity()).showActivity(ModuleID.PRODUCT_MODULE_ID, ProductUI.ProductInfo, kuaiZhuanBundle);
                            }else if (bannerURL.contains("tiyanjin")){
                                MainRouter.getInstance(getActivity()).showActivity(ModuleID.PRODUCT_MODULE_ID, ProductUI.Experience);
                            }else if (bannerURL.contains("jisuanqi")){
                                HomeRouter.getInstance(getActivity()).showActivity(HomeUI.Calculate);
                            }else if (bannerURL.contains("faxian")){

                            }else if (bannerURL.contains("jifenshangcheng")){
                                Bundle jiFenBundle = new Bundle();
                                jiFenBundle.putString(WebAction.WEB_REQUEST_TYPE, WebAction.REQUEST_MALL);
                                MainRouter.getInstance(getActivity()).showActivity(ModuleID.USER_MODULE_ID, UserUI.WebActivity, jiFenBundle);
                            }else if (bannerURL.contains("huiyuantequan")){
                                MainRouter.getInstance(getActivity()).showActivity(ModuleID.FIND_MODULE_ID, FindUI.VIPCenter);
                            }else if (bannerURL.contains("bangzhuzhongxin")){
                                Bundle helpBundle = new Bundle();
                                helpBundle.putString(WebAction.WEB_REQUEST_TYPE, WebAction.REQUEST_HELP);
                                MainRouter.getInstance(getActivity()).showActivity(ModuleID.USER_MODULE_ID, UserUI.WebActivity, helpBundle);
                            }else if (bannerURL.contains("wode")){

                            }
                        }else {
                            bundle.putString(WebAction.WEB_REQUEST_TYPE, WebAction.REQUEST_HOME_BANNER);
                            bundle.putString(WebAction.REQUEST_BANNER_URL, bannerURL);
                            MainRouter.getInstance(getActivity()).showActivity(ModuleID.USER_MODULE_ID, UserUI.WebActivity, bundle);
                        }
                    }
                }else {
                    UIHelper.ToastMessage(appContext, getString(R.string.net_work_invalid));
                }
            }
        });
        recyclerViewPager.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int childCount = recyclerViewPager.getChildCount();
                int width = recyclerViewPager.getChildAt(0).getWidth();
                int padding = (recyclerViewPager.getWidth() - width) / 2;

                for (int j = 0; j < childCount; j++) {
                    View v = recyclerView.getChildAt(j);
                    //往左 从 padding 到 -(v.getWidth()-padding) 的过程中，由大到小
                    float rate = 0;
                    if (v.getLeft() <= padding) {
                        if (v.getLeft() >= padding - v.getWidth()) {
                            rate = (padding - v.getLeft()) * 1f / v.getWidth();
                        } else {
                            rate = 1;
                        }
                        v.setScaleY(1 - rate * 0.3f);
                    } else {
                        //往右 从 padding 到 recyclerView.getWidth()-padding 的过程中，由大到小
                        if (v.getLeft() <= recyclerView.getWidth() - padding) {
                            rate = (recyclerView.getWidth() - padding - v.getLeft()) * 1f / v.getWidth();
                        }
                        v.setScaleY(0.7f + rate * 0.3f);
                    }
                }
            }
        });
        recyclerViewPager.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if(recyclerViewPager.getChildCount()<3){
                    if (recyclerViewPager.getChildAt(1) != null) {
                        View v1 = recyclerViewPager.getChildAt(1);
                        v1.setScaleY(0.7f);
                    }
                }else {
                    if (recyclerViewPager.getChildAt(0) != null) {
                        View v0 = recyclerViewPager.getChildAt(0);
                        v0.setScaleY(0.7f);
                    }
                    if (recyclerViewPager.getChildAt(2) != null) {
                        View v2 = recyclerViewPager.getChildAt(2);
                        v2.setScaleY(0.7f);
                    }
                }
            }
        });

        view.findViewById(R.id.find_blank_ji_fen).setOnClickListener(this);
        view.findViewById(R.id.find_blank_vip).setOnClickListener(this);
        view.findViewById(R.id.find_blank_help).setOnClickListener(this);
        view.findViewById(R.id.find_reload_data_button).setOnClickListener(this);

        scrollView.setMode(PullToRefreshBase.Mode.BOTH);
        scrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                loadDataNumber = 0;
                newsPage = 1;
                getFindNews();
                getFindBanners();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                getFindNews();
            }
        });
        containerLayout = (LinearLayout) view.findViewById(R.id.find_list_layout);

        netInvalidLayout = (LinearLayout) view.findViewById(R.id.find_net_invalid_page);
        if (Util.isNetworkAvailable(getActivity())){
            netInvalidLayout.setVisibility(View.GONE);
        }else {
            netInvalidLayout.setVisibility(View.VISIBLE);
        }
    }

    private void setUserViewData(){
        if (mUser != null){
            int score = mUser.getUserScore();
            scoreNumberView.setText(String.format(Locale.CHINESE, getString(R.string.user_score_number), score));
            int vipLevel = Util.getUserVIPLevel(mUser);
            if (vipLevel == 1){
                vipLevelImage.setImageResource(R.drawable.normal_vip_icon_selected);
                vipLevelView.setText("VIP1");
            }else if (vipLevel == 2){
                vipLevelImage.setImageResource(R.drawable.bronze_vip_icon_selected);
                vipLevelView.setText("VIP2");
            }else if (vipLevel == 3){
                vipLevelImage.setImageResource(R.drawable.silver_vip_icon_selected);
                vipLevelView.setText("VIP3");
            }else if (vipLevel == 4){
                vipLevelImage.setImageResource(R.drawable.gold_vip_icon_selected);
                vipLevelView.setText("VIP4");
            }else if (vipLevel == 5){
                vipLevelImage.setImageResource(R.drawable.diamond_vip_icon_selected);
                vipLevelView.setText("VIP5");
            }else if (vipLevel == 6){
                vipLevelImage.setImageResource(R.drawable.gold_diamond_vip_icon_selected);
                vipLevelView.setText("VIP6");
            }else {
                vipLevelImage.setImageResource(R.drawable.normal_vip_icon_selected);
                vipLevelView.setText("VIP1");
            }
        }else {
            scoreNumberView.setText(String.format(Locale.CHINESE, getString(R.string.user_score_number), 0));
            vipLevelView.setText("VIP");
            vipLevelImage.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        if (!Util.isNetworkAvailable(appContext)){
            UIHelper.ToastMessage(appContext, getString(R.string.net_work_invalid));
            return;
        }

        switch (v.getId()){
            case R.id.find_fragment_sign_button:
                if (mUser != null){
                    MainRouter.getInstance(getActivity()).showActivity(ModuleID.FIND_MODULE_ID, FindUI.SignIn);
                }else {
                    MainRouter.getInstance(getActivity()).showActivity(ModuleID.USER_MODULE_ID, UserUI.LoginActivity);
                }
                break;
            case R.id.find_blank_ji_fen:
                Bundle bundle = new Bundle();
                bundle.putString(WebAction.WEB_REQUEST_TYPE, WebAction.REQUEST_MALL);
                MainRouter.getInstance(getActivity()).showActivity(ModuleID.USER_MODULE_ID, UserUI.WebActivity, bundle);
                break;
            case R.id.find_blank_vip:
                MainRouter.getInstance(getActivity()).showActivity(ModuleID.FIND_MODULE_ID, FindUI.VIPCenter);
                break;
            case R.id.find_blank_help:
                Bundle helpBundle = new Bundle();
                helpBundle.putString(WebAction.WEB_REQUEST_TYPE, WebAction.REQUEST_HELP);
                MainRouter.getInstance(getActivity()).showActivity(ModuleID.USER_MODULE_ID, UserUI.WebActivity, helpBundle);
                break;
            case R.id.luobo_icon_show_layout:
                if (mUser != null){
                    queryTurnips(mUser.getUserId(), mUser.getToken());
                }
                break;
            case R.id.find_reload_data_button:
                netInvalidLayout.setVisibility(View.GONE);
                scrollView.setPullDownToRefreshing(scrollView);
                break;
        }
}

    public void getFindBanners(){
        APIClient.getInstance().getHomeBanners(1).enqueue(new Callback<BaseModel<ArrayList<Banner>>>() {
            @Override
            public void onResponse(Call<BaseModel<ArrayList<Banner>>> call, Response<BaseModel<ArrayList<Banner>>> response) {
                resetLoadState();
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
                resetLoadState();
            }
        });
    }

    private void getFindNews(){
        APIClient.getInstance().getFindNews(4, newsPage).enqueue(new Callback<BaseModel<ArrayList<FindNews>>>() {
            @Override
            public void onResponse(Call<BaseModel<ArrayList<FindNews>>> call, Response<BaseModel<ArrayList<FindNews>>> response) {
                resetLoadState();
                BaseModel baseModel = response.body();
                if (baseModel != null){
                    if (baseModel.isSuccess()){
                        ArrayList<FindNews> news = (ArrayList<FindNews>) baseModel.getRows();
                        if (news != null && news.size() > 0){
                            if (newsPage == 1){
                                findNews = news;
                            }else {
                                findNews.addAll(news);
                            }
                            setNewsData();
                            newsPage++;
                        }
                    }else {
                        UIHelper.ToastMessage(getActivity(), baseModel.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel<ArrayList<FindNews>>> call, Throwable t) {
                resetLoadState();
            }
        });
    }

    private void setBannerData(){
        if (banners == null){
            return;
        }
        netInvalidLayout.setVisibility(View.GONE);
        viewpagerAdapter.setBannerData(banners);
        viewpagerAdapter.notifyDataSetChanged();
        if (banners != null && banners.size() > 0){
            recyclerViewPager.scrollToPosition(banners.size() * 10000);
        }
    }

    private void setNewsData(){
        if (findNews == null || findNews.size() == 0){
            return;
        }
        netInvalidLayout.setVisibility(View.GONE);
        containerLayout.removeAllViews();
        for (final FindNews news : findNews){
            FindItemView itemView = new FindItemView(getActivity());
            itemView.setViewData(news);
            itemView.setOnFindNewsItemClickListener(new OnFindNewsItemClickListener() {
                @Override
                public void onNewsItemClick(int newsId) {
                    if (Util.isNetworkAvailable(appContext)){
                        Bundle bundle = new Bundle();
                        bundle.putString(WebAction.WEB_REQUEST_TYPE, WebAction.FIND_NEWS_DETAIL);
                        bundle.putInt(WebAction.FIND_NEWS_ID, news.getnId());
                        MainRouter.getInstance(getActivity()).showActivity(ModuleID.USER_MODULE_ID, UserUI.WebActivity, bundle);
                    }else {
                        UIHelper.ToastMessage(appContext, getString(R.string.net_work_invalid));
                    }
                }
            });
            containerLayout.addView(itemView);
        }
    }

    public void updateUserInfo(int userId, String token){
        APIClient.getInstance().updateUserInfo(userId, token).enqueue(new Callback<BaseModel<User>>() {
            @Override
            public void onResponse(Call<BaseModel<User>> call, Response<BaseModel<User>> response) {
                BaseModel baseModel = response.body();
                if (baseModel != null){
                    if (baseModel.isSuccess()){
                        User user = (User) baseModel.getRows();
                        LuoBoDBM.getInstance(getActivity()).updateUserInfo(user);
                        mUser = user;
                        appContext.setUser(mUser);
                        updateUserToken(mUser.getUserId(), mUser.getToken(), baseModel.getToken());
                        setUserViewData();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel<User>> call, Throwable t) {
            }
        });
    }

    private void queryTurnips(int uid, String token){
        APIClient.getInstance().queryTurnips(uid, token).enqueue(new Callback<BaseModel<Integer>>() {
            @Override
            public void onResponse(Call<BaseModel<Integer>> call, Response<BaseModel<Integer>> response) {
                BaseModel baseModel = response.body();
                if (baseModel != null){
                    if (baseModel.isSuccess()){
                        int turnips = (int) baseModel.getRows();
                        if (turnips > 0){
                            if (!showExchangeDialog){
                                showExchangeDialog = true;
                                showExchangeDialog(turnips);
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel<Integer>> call, Throwable t) {
            }
        });
    }

    private void goConvertScore(int uid, String token){
        if (exchangeLayout != null && exchangeResultView != null){
            exchangeLayout.setVisibility(View.INVISIBLE);
            exchangeResultView.setVisibility(View.VISIBLE);
            exchangeResultView.setText(getString(R.string.exchangeing_luobo_coin));
        }

        APIClient.getInstance().goConvertScore(uid, token).enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                BaseModel baseModel = response.body();
                if (baseModel != null && exchangeResultView != null){
                    if (baseModel.isSuccess()){
                        updateUserInfo(mUser.getUserId(), mUser.getToken());
                        exchangeResultView.setText(getString(R.string.congratulate_exchange_success));
                    }else {
                        exchangeResultView.setText(baseModel.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
            }
        });
    }

    private void showExchangeDialog(int turnips){
        final Dialog dialog = new Dialog(getActivity(), R.style.DialogStyle);
        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.luobo_coin_exchange_dialog_layout, null);
        TextView textView = (TextView) rootView.findViewById(R.id.exchange_luobo_coin_text);
        textView.setText(getExchangeText(turnips));
        exchangeResultView = (TextView) rootView.findViewById(R.id.exchange_luobo_coin_result);
        exchangeLayout = (LinearLayout) rootView.findViewById(R.id.exchange_luobo_coin_for_layout);
        ImageView closeView = (ImageView) rootView.findViewById(R.id.exchange_luobo_coin_close);
        closeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        rootView.findViewById(R.id.exchange_luobo_coin_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goConvertScore(mUser.getUserId(), mUser.getToken());
            }
        });
        dialog.setContentView(rootView);
        dialog.show();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                showExchangeDialog = false;
            }
        });
    }

    private SpannableStringBuilder getExchangeText(int luobo){
        int integral = (int)(luobo/100f);
        String string = String.format(Locale.CHINA, getString(R.string.exchange_luobo_coin_display), luobo, integral);
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(string);
        ForegroundColorSpan span1 = new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.colorBase));
        ForegroundColorSpan span2 = new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.colorBase));
        int size1 = String.valueOf(luobo).length();
        int size2 = String.valueOf(integral).length();
        stringBuilder.setSpan(span1, 3, 3 + size1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        stringBuilder.setSpan(span2, 10 + size1, 10 + size1 + size2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return stringBuilder;
    }
}