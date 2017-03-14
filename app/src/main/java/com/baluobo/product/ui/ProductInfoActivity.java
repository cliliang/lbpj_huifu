package com.baluobo.product.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baluobo.R;
import com.baluobo.app.AppContext;
import com.baluobo.app.flux.Store;
import com.baluobo.app.model.BaseModel;
import com.baluobo.app.router.MainRouter;
import com.baluobo.app.ui.BaseToolBarActivity;
import com.baluobo.common.db.LuoBoDBM;
import com.baluobo.common.module.ModuleID;
import com.baluobo.common.tools.UIHelper;
import com.baluobo.common.tools.Util;
import com.baluobo.product.actions.ProductInfoAction;
import com.baluobo.product.bean.Product;
import com.baluobo.product.bean.ProductInfo;
import com.baluobo.product.router.ProductRouter;
import com.baluobo.product.router.ProductUI;
import com.baluobo.product.stores.ProductInfoStore;
import com.baluobo.product.views.ProductSellProgress;
import com.baluobo.user.actions.UserInfoAction;
import com.baluobo.user.actions.WebAction;
import com.baluobo.user.model.ProductEnum;
import com.baluobo.user.model.User;
import com.baluobo.user.router.UserUI;
import com.squareup.otto.Subscribe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/19.
 */
public class ProductInfoActivity extends BaseToolBarActivity {
    private TabLayout tableLayout;
    private ViewPager viewPager;
    private ProductInfoStore productInfoStore;
    private ProductSellProgress productProgress;
    private TextView investDayView, aprView, canBuyView, buyUnit;
    private TextView dayView1, dayView2, payLabelView;
    private TextView buyButton;
    private TextView saleDayView, startIncomeDayView, endIncomeDayView, latestAccountDayView;
    private ImageView dotView1, dotView2, dotView3, dotView4;
    private FrameLayout dateLayout;
    private Product product;
    private int productId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_info_base_activity_layout);
        initDependencies();
        setupViews();
    }

    @Override
    protected void initDependencies() {
        super.initDependencies();
        Log.i("chen", ProductInfoActivity.this.getClass().getName() + ":onCreate");
        productInfoStore = ProductInfoStore.getInstance();
        dispatcher.register(productInfoStore);
    }

    @Override
    protected void setupViews() {
        super.setupViews();
        dayView1 = (TextView) findViewById(R.id.product_day_view1);
        dayView2 = (TextView) findViewById(R.id.product_day_view2);

        productProgress = (ProductSellProgress) findViewById(R.id.product_info_sell_progress);
        productProgress.setRate(0f);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenWidth = metrics.widthPixels;

        LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) dayView1.getLayoutParams();
        params1.leftMargin = screenWidth / 8;
        dayView1.setLayoutParams(params1);

        LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) dayView2.getLayoutParams();
        params2.rightMargin = screenWidth / 8;
        dayView2.setLayoutParams(params2);

        dateLayout = (FrameLayout) findViewById(R.id.product_info_show_date_layout);

        dotView1 = (ImageView) findViewById(R.id.day_dot_view1);
        dotView2 = (ImageView) findViewById(R.id.day_dot_view2);
        dotView3 = (ImageView) findViewById(R.id.day_dot_view3);
        dotView4 = (ImageView) findViewById(R.id.day_dot_view4);

        payLabelView = (TextView) findViewById(R.id.product_info_pay_label);
        buyUnit = (TextView) findViewById(R.id.product_info_start_buy_money);
        saleDayView = (TextView) findViewById(R.id.product_info_start_sale_day_view);
        startIncomeDayView = (TextView) findViewById(R.id.product_info_start_income_day_view);
        endIncomeDayView = (TextView) findViewById(R.id.product_info_end_income_day_view);
        latestAccountDayView = (TextView) findViewById(R.id.product_info_latest_back_day_view);

        investDayView = (TextView) findViewById(R.id.product_info_days_total);
        aprView = (TextView) findViewById(R.id.product_info_rate);
        canBuyView = (TextView) findViewById(R.id.product_info_can_buy_total_money_view);
        viewPager = (ViewPager) findViewById(R.id.product_info_view_pager);
        tableLayout = (TabLayout) findViewById(R.id.product_info_table_layout);
        tableLayout.setTabMode(TabLayout.MODE_FIXED);
        buyButton = (TextView) findViewById(R.id.product_info_buy_button);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            productId = bundle.getInt(ProductInfoAction.BOUND_PRODUCT_DATA_ID);
            showLoadingDialog();
            appActionsCreator.getProductInfo(productId);
        }
    }

    private void setViewData(ProductInfo productInfo) {
        if (productInfo == null) {
            return;
        }
        product = productInfo.getGood();
        if (product == null) {
            return;
        }
        setTitle(product.getGoodName());

        if (ProductEnum.getProduct(product.getGcId()) == ProductEnum.LUOBO_YIN_PIAO){
            dateLayout.setVisibility(View.VISIBLE);

            String payLabel = product.getPayLabel();
            if (payLabel.length() >= 8){
                payLabel  = payLabel.substring(3, payLabel.length() - 5);
            }
            payLabelView.setText(payLabel);
            String createDay;
            //如果是定时产品，就用这个时间，如果不是，就用createTime
            String onlineTimeString = product.getOnLineTime();
            if (TextUtils.isEmpty(onlineTimeString)){
                createDay = product.getCreateTime();
            }else {
                createDay = onlineTimeString;
            }
            if (createDay.length() > 9) {
                createDay = createDay.substring(0, 10);
            }
            saleDayView.setText(createDay);
            String startDay = product.getValuesTime();
            startIncomeDayView.setText(startDay);
            String endDay = product.getValueTime();
            endIncomeDayView.setText(endDay);
            String lastDay = product.getValuedTime();
            latestAccountDayView.setText(lastDay);
            setDayView(new String[]{createDay, startDay, endDay, lastDay});
        }else {
            dateLayout.setVisibility(View.GONE);
        }

        buyUnit.setText(String.format(Locale.CHINA, "%.0f", product.getInvestUnit()));
        investDayView.setText(String.valueOf(product.getInvestTime()));
        aprView.setText(String.format(Locale.US, "%.2f", product.getProceeds()));
        canBuyView.setText(String.format(Locale.US, "%.0f", product.getSurplusMoney()));

        String[] titles = new String[]{getString(R.string.product_detail), getString(R.string.product_record)};
        List<Fragment> list = new ArrayList<>();
        ProductDetailFragment detailFragment = new ProductDetailFragment();
        Bundle infoBound = new Bundle();
        infoBound.putString(ProductInfoAction.BOUND_FRAGMENT_SHEN_HE_DATA, productInfo.getImgUrl());
        infoBound.putParcelable(ProductInfoAction.BOUND_FRAGMENT_INFO_DATA, product);
        detailFragment.setArguments(infoBound);

        ProductRecordFragment recordFragment = new ProductRecordFragment();
        Bundle recordBundle = new Bundle();
        recordBundle.putParcelableArrayList(ProductInfoAction.BOUND_FRAGMENT_RECORD_DATA, productInfo.getBuyOrders());
        recordFragment.setArguments(recordBundle);


        list.add(detailFragment);
        list.add(recordFragment);
        ProductInfoAdapter adapter = new ProductInfoAdapter(getSupportFragmentManager(), titles, list);
        viewPager.setAdapter(adapter);
        tableLayout.setupWithViewPager(viewPager);

        int buyFlag = product.getBuyflg();
        if (buyFlag == 4) {
            productProgress.setRate((float) (1 - (product.getSurplusMoney() / product.getBuyMoney())));
            buyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!Util.isNetworkAvailable(appContext)){
                        UIHelper.ToastMessage(appContext, getString(R.string.net_work_invalid));
                        return;
                    }
                    if (product == null) {
                        return;
                    }
                    User user = ((AppContext) getApplication()).getUser();
                    if (user == null) {
                        MainRouter.getInstance(ProductInfoActivity.this).showActivity(ModuleID.USER_MODULE_ID, UserUI.LoginActivity);
                        return;
                    }
                    if (user.getUserType() != 0 && product.getGcId() == ProductEnum.LUOBO_XIN_SHOU.getProductTypeId()) {
                        UIHelper.ToastMessage(ProductInfoActivity.this, R.string.product_buy_only_for_new);
                        return;
                    }

                    Bundle bundle = new Bundle();
                    bundle.putParcelable(ProductInfoAction.BOUND_FRAGMENT_INFO_DATA, product);
                    ProductRouter.getInstance(ProductInfoActivity.this).showActivityForResult(ProductInfoActivity.this, ProductUI.BuyActivity, WebAction.BUY_PRODUCT_REQUEST_CODE, bundle);
                }
            });
        } else {
            canBuyView.setText("0");
            productProgress.setRate(1f);
            buyButton.setEnabled(false);
            if (buyFlag == 1){
                buyButton.setText(getString(R.string.product_buy_button_back_already));
            }else if (buyFlag == 2){
                buyButton.setText(getString(R.string.product_buy_button_back_now));
            }else  if (buyFlag == 3){
                buyButton.setText(getString(R.string.product_buy_button_no_product));
            }
        }
    }

    private void setDayView(String[] day) {
        if (day.length != 4) {
            return;
        }
        LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) dayView1.getLayoutParams();
        LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) dayView2.getLayoutParams();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Calendar cal = Calendar.getInstance();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long todayTime = calendar.getTimeInMillis();
        long createTime = 0;
        long startTime = 0;
        long endTime = 0;
        long lastTime = 0;
        try {
            cal.setTime(sdf.parse(day[0]));
            createTime = cal.getTimeInMillis();

            cal.setTime(sdf.parse(day[1]));
            startTime = cal.getTimeInMillis();

            cal.setTime(sdf.parse(day[2]));
            endTime = cal.getTimeInMillis();

            cal.setTime(sdf.parse(day[3]));
            lastTime = cal.getTimeInMillis();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (todayTime < createTime) {
            params1.weight = 0;
            params2.weight = 1;
            dayView1.setLayoutParams(params1);
            dayView2.setLayoutParams(params2);
            dotView1.setImageResource(R.drawable.icon_day_normal);
            dotView2.setImageResource(R.drawable.icon_day_normal);
            dotView3.setImageResource(R.drawable.icon_day_normal);
            dotView4.setImageResource(R.drawable.icon_day_normal);
        } else if (todayTime >= lastTime) {
            params1.weight = 1;
            params2.weight = 0;
            dayView1.setLayoutParams(params1);
            dayView2.setLayoutParams(params2);
            dotView1.setImageResource(R.drawable.icon_day_selected);
            dotView2.setImageResource(R.drawable.icon_day_selected);
            dotView3.setImageResource(R.drawable.icon_day_selected);
            dotView4.setImageResource(R.drawable.icon_day_selected);
        } else if (todayTime >= createTime && todayTime < startTime) {
            float createToToday = daysBetween(createTime, todayTime);
            float todayToStart = daysBetween(todayTime, startTime);
            float createToStart = daysBetween(createTime, startTime);
            params1.weight = createToToday / createToStart;
            params2.weight = todayToStart / createToStart + 2;
            dayView1.setLayoutParams(params1);
            dayView2.setLayoutParams(params2);
            dotView1.setImageResource(R.drawable.icon_day_selected);
            dotView2.setImageResource(R.drawable.icon_day_normal);
            dotView3.setImageResource(R.drawable.icon_day_normal);
            dotView4.setImageResource(R.drawable.icon_day_normal);
        } else if (todayTime >= startTime && todayTime < endTime) {
            float startToToday = daysBetween(startTime, todayTime);
            float todayToEnd = daysBetween(todayTime, endTime);
            float startToEnd = daysBetween(startTime, endTime);
            params1.weight = startToToday / startToEnd + 1;
            params2.weight = todayToEnd / startToEnd + 1;
            dayView1.setLayoutParams(params1);
            dayView2.setLayoutParams(params2);
            dotView1.setImageResource(R.drawable.icon_day_selected);
            dotView2.setImageResource(R.drawable.icon_day_selected);
            dotView3.setImageResource(R.drawable.icon_day_normal);
            dotView4.setImageResource(R.drawable.icon_day_normal);
        } else if (todayTime >= endTime && todayTime < lastTime) {
            float endToToday = daysBetween(endTime, todayTime);
            float todayToLast = daysBetween(todayTime, lastTime);
            float endToLast = daysBetween(endTime, lastTime);
            params1.weight = endToToday / endToLast + 2;
            params2.weight = todayToLast / endToLast;
            dayView1.setLayoutParams(params1);
            dayView2.setLayoutParams(params2);
            dotView1.setImageResource(R.drawable.icon_day_selected);
            dotView2.setImageResource(R.drawable.icon_day_selected);
            dotView3.setImageResource(R.drawable.icon_day_selected);
            dotView4.setImageResource(R.drawable.icon_day_normal);
        }
    }

    private float daysBetween(long timeStart, long timeEnd) {
        long between_days = (timeEnd - timeStart) / (1000 * 3600 * 24L);
        return (float) between_days;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("chen", ProductInfoActivity.this.getClass().getName() + ":onResume");
        productInfoStore.register(this);
        if (mUser != null) {
            appActionsCreator.updateUserInfoInProductInfo(mUser.getUserId(), mUser.getToken());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("chen", ProductInfoActivity.this.getClass().getName() + ":onStop");
        productInfoStore.unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("chen", ProductInfoActivity.this.getClass().getName() + ":onDestroy");
        dispatcher.unregister(productInfoStore);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == WebAction.BUY_PRODUCT_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
//            appActionsCreator.getProductInfo(productId);
            finish();
            Log.i("chen", ProductInfoActivity.this.getClass().getName() + ":onActivityResult");
        }
    }

    @Subscribe
    public void onProductInfoStoreChange(Store.StoreChangeEvent event) {
        String type = event.getType();
        switch (type) {
            case ProductInfoAction.ACTION_REQUEST_START:
                showLoadingDialog();
                break;
            case ProductInfoAction.ACTION_REQUEST_FINISH:
                dismissLoadingDialog();
                break;
            case ProductInfoAction.ACTION_REQUEST_FAIL:
                UIHelper.ToastMessage(this, getString(R.string.net_work_error));
                break;
            case ProductInfoAction.ACTION_REQUEST_ERROR_MESSAGE:
                String msg = event.getMessage();
                UIHelper.ToastMessage(this, msg);
                break;
            case ProductInfoAction.ACTION_REQUEST_PRODUCT_INFO_SUCCESS:
                ProductInfo productInfo = productInfoStore.getProductInfo();
                if (productInfo != null) {
                    setViewData(productInfo);
                }
                break;
            case UserInfoAction.ACTION_REQUEST_UPDATE_USER_INFO_SUCCESS:
                BaseModel userBaseModel = productInfoStore.getUserInfo();
                if (userBaseModel != null) {
                    User user = (User) userBaseModel.getRows();
                    if (user != null){
                        LuoBoDBM.getInstance(this).updateUserInfo(user);
                        mUser = user;
                        appContext.setUser(mUser);
                        updateUserToken(user.getUserId(), user.getToken(), userBaseModel.getToken());
                    }
                }
                break;
        }
    }

    private class ProductInfoAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;
        private String[] titleArray;

        public ProductInfoAdapter(FragmentManager fm, String[] titles, List<Fragment> list) {
            super(fm);
            fragments = list;
            titleArray = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleArray[position];
        }
    }


}
