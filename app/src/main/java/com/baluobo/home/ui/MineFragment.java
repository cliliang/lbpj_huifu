package com.baluobo.home.ui;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baluobo.R;
import com.baluobo.app.flux.Store;
import com.baluobo.app.model.BaseModel;
import com.baluobo.app.router.MainRouter;
import com.baluobo.app.ui.BaseFragment;
import com.baluobo.common.config.AppConfig;
import com.baluobo.common.db.LuoBoDBM;
import com.baluobo.common.module.ModuleID;
import com.baluobo.common.tools.UIHelper;
import com.baluobo.common.tools.Util;
import com.baluobo.common.tools.WindowUtil;
import com.baluobo.common.views.CustomItemView;
import com.baluobo.common.views.WaveHelper;
import com.baluobo.common.views.WaveView;
import com.baluobo.home.actions.DotAction;
import com.baluobo.home.stores.MsgDotStore;
import com.baluobo.user.actions.MineAction;
import com.baluobo.user.actions.WebAction;
import com.baluobo.user.model.User;
import com.baluobo.user.router.UserUI;
import com.baluobo.user.stores.MineStore;
import com.squareup.otto.Subscribe;

import java.util.Locale;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/4.
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {
    private MineStore mineStore;
    private MsgDotStore dotStore;
    private TextView canUseAssetsView;
    private TextView totalAssetsView;
    private TextView tomorrowIncomeView, totalIncomeView;
    private TextView messageDotView;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            WindowUtil.showPopupWindow(getActivity(), WindowUtil.TYPE_MINE);
            return false;
        }
    });

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mine_fragment_layout, container, false);
        initDependencies();
        setupView(view);
//        AppConfig appConfig = AppConfig.getInstance(getActivity().getApplicationContext());
//        if (!appConfig.isTotalAssetsShowed()){
//            mHandler.sendEmptyMessageDelayed(1, 300);
//            appConfig.setTotalAssetsShowed(true);
//        }
        return view;
    }

    @Override
    protected void initDependencies() {
        super.initDependencies();
        mineStore = MineStore.getInstance();
        dispatcher.register(mineStore);
        dotStore = MsgDotStore.getInstance();
        dispatcher.register(dotStore);
    }

    public void setupView(View view) {
        TextView statusView = (TextView) view.findViewById(R.id.mine_status_bar_view);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) statusView.getLayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            params.height = getStatusBarHeight();
        }else {
            params.height = 0;
        }

        view.findViewById(R.id.online_contact_us).setOnClickListener(this);

        WaveView waveView = (WaveView) view.findViewById(R.id.waveView);
        WaveHelper waveHelper = new WaveHelper(waveView);
        waveHelper.start();

        CustomItemView tradeRecordView = (CustomItemView) view.findViewById(R.id.mine_trade_record_view);
        tradeRecordView.setOnClickListener(this);

        view.findViewById(R.id.mine_total_assets_layout_view).setOnClickListener(this);

        totalAssetsView = (TextView) view.findViewById(R.id.mine_asset_total_money);

        canUseAssetsView = (TextView) view.findViewById(R.id.mine_can_use_assets_view);

        tomorrowIncomeView = (TextView) view.findViewById(R.id.mine_tomorrow_income);
        totalIncomeView = (TextView) view.findViewById(R.id.mine_total_income);

        view.findViewById(R.id.mine_recharge_button).setOnClickListener(this);

        CustomItemView kuaiZhuanView = (CustomItemView) view.findViewById(R.id.mine_luobo_kuai_zhuan_view);
        kuaiZhuanView.setOnClickListener(this);

        CustomItemView investView = (CustomItemView) view.findViewById(R.id.mine_invest_view);
        investView.setOnClickListener(this);

        view.findViewById(R.id.mine_message_center_view).setOnClickListener(this);

        messageDotView = (TextView) view.findViewById(R.id.message_dot_view);

        CustomItemView securityView = (CustomItemView) view.findViewById(R.id.mine_account_security_view);
        securityView.setOnClickListener(this);

        CustomItemView moreView = (CustomItemView) view.findViewById(R.id.mine_account_more_view);
        moreView.setOnClickListener(this);

        CustomItemView fuliView = (CustomItemView) view.findViewById(R.id.mine_account_fuli_view);
        fuliView.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mineStore.register(this);
        if (mUser != null) {
            appActionsCreator.updateUserInfoInMine(mUser.getUserId(), mUser.getToken());
            appActionsCreator.getUnreadMessage(mUser.getUserId(), mUser.getToken());
            setViewData(mUser);
        } else {
            totalAssetsView.setText("0.00");
            canUseAssetsView.setText(String.format(Locale.US, getString(R.string.mine_usable_asset), "0.0"));
            tomorrowIncomeView.setText("0.00");
            totalIncomeView.setText("0.00");
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mineStore.unregister(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dispatcher.unregister(mineStore);
        dispatcher.unregister(dotStore);
    }

    @Subscribe
    public void onMineStoreChange(Store.StoreChangeEvent event) {
        String type = event.getType();
        switch (type){
            case MineAction.MINE_ACTION_REQUEST_UPDATE_SUCCESS:
                BaseModel baseModel = mineStore.getUserData();
                if (baseModel != null){
                    if (baseModel.isSuccess()){
                        User user = (User) baseModel.getRows();
                        if (user != null){
                            LuoBoDBM.getInstance(getActivity()).updateUserInfo(user);
                            mUser = user;
                            appContext.setUser(user);
                            updateUserToken(mUser.getUserId(), mUser.getToken(), baseModel.getToken());
                            setViewData(user);
                        }
                    }
                }
                break;
            case DotAction.MAIN_DOT_ACTION_DONT_HAVE_UNREAD_MESSAGE:
                messageDotView.setVisibility(View.GONE);
                break;
            case DotAction.MAIN_DOT_ACTION_HAVE_UNREAD_MESSAGE:
                messageDotView.setVisibility(View.VISIBLE);
                BaseModel model = dotStore.getMsg();
                if (model != null){
                    int size = model.getTotal();
                    if (size < 100){
                        messageDotView.setText(String.valueOf(size));
                    }else {
                        messageDotView.setText("...");
                    }
                }
                break;
        }
    }

    private void setViewData(User user){
        if (user == null){
            return;
        }
        String totalMoneyString = user.getCountMoney();
        if (!TextUtils.isEmpty(totalMoneyString)){
            totalAssetsView.setText(totalMoneyString);
        }else {
            totalAssetsView.setText("0.00");
        }
        String enableMoneyString = user.getEnAbleMoney();
        if (TextUtils.isEmpty(enableMoneyString)){
            canUseAssetsView.setText(String.format(Locale.US, getString(R.string.mine_usable_asset), "0.0"));
        }else {
            canUseAssetsView.setText(String.format(Locale.US, getString(R.string.mine_usable_asset), enableMoneyString));
        }
        tomorrowIncomeView.setText(String.format(Locale.US, "%.2f", user.getYesterdayIncome()));
        String sumProceeds = user.getSumProceeds();
        if (TextUtils.isEmpty(sumProceeds)){
            totalIncomeView.setText("0.00");
        }else {
            totalIncomeView.setText(sumProceeds);
        }
    }

    @Override
    public void onClick(View v) {
        if (!Util.isNetworkAvailable(appContext)){
            UIHelper.ToastMessage(appContext, getString(R.string.net_work_invalid));
            return;
        }
        int viewId = v.getId();
        switch (viewId) {
            case R.id.mine_total_assets_layout_view:
                if (mUser != null) {
                    //先开户
                    if (!mUser.isAutonym()){
                        UIHelper.ToastMessage(getActivity(), getString(R.string.please_first_register_in_huifu));
                        Bundle bundle = new Bundle();
                        bundle.putString(WebAction.WEB_REQUEST_TYPE, WebAction.REQUEST_OPEN_ACCOUNT);
                        MainRouter.getInstance(getActivity()).showActivity(ModuleID.USER_MODULE_ID, UserUI.WebActivity, bundle);
                        return;
                    }
                    MainRouter.getInstance(getActivity()).showActivity(ModuleID.USER_MODULE_ID, UserUI.TotalAssets);
                } else {
                    MainRouter.getInstance(getActivity()).showActivity(ModuleID.USER_MODULE_ID, UserUI.LoginActivity);
                }
                break;
            case R.id.mine_trade_record_view:
                if (mUser != null) {
                    MainRouter.getInstance(getActivity()).showActivity(ModuleID.USER_MODULE_ID, UserUI.TradingRecord);
                } else {
                    MainRouter.getInstance(getActivity()).showActivity(ModuleID.USER_MODULE_ID, UserUI.LoginActivity);
                }
                break;
            case R.id.mine_recharge_button:
                if (mUser != null) {
                    //先开户
                    if (!mUser.isAutonym()){
                        UIHelper.ToastMessage(getActivity(), getString(R.string.please_first_register_in_huifu));
                        Bundle bundle = new Bundle();
                        bundle.putString(WebAction.WEB_REQUEST_TYPE, WebAction.REQUEST_OPEN_ACCOUNT);
                        MainRouter.getInstance(getActivity()).showActivity(ModuleID.USER_MODULE_ID, UserUI.WebActivity, bundle);
                        return;
                    }
                    //再冲值
                    Bundle bundle = new Bundle();
                    bundle.putString(WebAction.WEB_REQUEST_TYPE, WebAction.REQUEST_RECHARGE);
                    MainRouter.getInstance(getActivity()).showActivity(ModuleID.USER_MODULE_ID, UserUI.RechargeActivity, bundle);
                } else {
                    MainRouter.getInstance(getActivity()).showActivity(ModuleID.USER_MODULE_ID, UserUI.LoginActivity);
                }
                break;
            case R.id.mine_luobo_kuai_zhuan_view:
                if (mUser != null) {
                    MainRouter.getInstance(getActivity()).showActivity(ModuleID.USER_MODULE_ID, UserUI.LuoboKuaiZhuan);
                } else {
                    MainRouter.getInstance(getActivity()).showActivity(ModuleID.USER_MODULE_ID, UserUI.LoginActivity);
                }
                break;
            case R.id.mine_invest_view:
                if (mUser != null) {
                    MainRouter.getInstance(getActivity()).showActivity(ModuleID.USER_MODULE_ID, UserUI.MyInvest);
                } else {
                    MainRouter.getInstance(getActivity()).showActivity(ModuleID.USER_MODULE_ID, UserUI.LoginActivity);
                }
                break;
            case R.id.mine_message_center_view:
                if (mUser != null) {
                    MainRouter.getInstance(getActivity()).showActivity(ModuleID.USER_MODULE_ID, UserUI.MsgCenter);
                } else {
                    MainRouter.getInstance(getActivity()).showActivity(ModuleID.USER_MODULE_ID, UserUI.LoginActivity);
                }
                break;
            case R.id.mine_account_security_view:
                if (mUser != null) {
                    MainRouter.getInstance(getActivity()).showActivity(ModuleID.USER_MODULE_ID, UserUI.AccountSecurity);
                } else {
                    MainRouter.getInstance(getActivity()).showActivity(ModuleID.USER_MODULE_ID, UserUI.LoginActivity);
                }
                break;
            case R.id.mine_account_more_view:
                MainRouter.getInstance(getActivity()).showActivity(ModuleID.USER_MODULE_ID, UserUI.More);
                break;
            case R.id.mine_account_fuli_view:
                if (mUser != null) {
                    MainRouter.getInstance(getActivity()).showActivity(ModuleID.USER_MODULE_ID, UserUI.FuliCenter);
                } else {
                    MainRouter.getInstance(getActivity()).showActivity(ModuleID.USER_MODULE_ID, UserUI.LoginActivity);
                }
                break;
            case R.id.online_contact_us:
                Bundle bundle = new Bundle();
                bundle.putString(WebAction.WEB_REQUEST_TYPE, WebAction.ONLINE_SERVICE);
                MainRouter.getInstance(getActivity()).showActivity(ModuleID.USER_MODULE_ID, UserUI.WebActivity, bundle);
                break;
        }
    }
}
