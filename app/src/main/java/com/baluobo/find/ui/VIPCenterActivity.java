package com.baluobo.find.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baluobo.R;
import com.baluobo.app.flux.Store;
import com.baluobo.app.model.BaseModel;
import com.baluobo.app.router.MainRouter;
import com.baluobo.app.ui.BaseToolBarActivity;
import com.baluobo.common.module.ModuleID;
import com.baluobo.common.tools.UIHelper;
import com.baluobo.common.tools.Util;
import com.baluobo.common.views.VIPTicketView;
import com.baluobo.common.views.clip.ClipViewPager;
import com.baluobo.common.views.clip.RecyclingPagerAdapter;
import com.baluobo.common.views.clip.ScalePageTransformer;
import com.baluobo.find.actions.VIPCenterAction;
import com.baluobo.find.model.RedTicket;
import com.baluobo.find.stores.VIPCenterStore;
import com.baluobo.user.router.UserUI;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/8/16.
 */
public class VIPCenterActivity extends BaseToolBarActivity implements VIPTicketView.OnVIPTicketClickListener{
    private ClipViewPager mViewPager;
    private TextView vipTypeView, conditionView;
    private TextView principalRedPacketView, surpriseRedPacketView, birthdayRedPacketView, privilegeRedPacketView, privateServiceView, doubleIntegralView, giftView;
    private TextView takeTicketsTitle;
    private TubatuAdapter mPagerAdapter;
    private VIPCenterStore vipCenterStore;
    private LinearLayout containerLayout;
    private int ticketId;
    private ArrayList<RedTicket> redTickets;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vip_center_activity_layout);
        setTitle(getString(R.string.find_blank_vip_title));
        initDependencies();
        setupViews();
    }

    @Override
    protected void initDependencies() {
        super.initDependencies();
        vipCenterStore = VIPCenterStore.getInstance();
        dispatcher.register(vipCenterStore);
    }

    @Override
    protected void onResume() {
        super.onResume();
        vipCenterStore.register(this);
        if (mUser != null){
            appActionsCreator.getVipRedTicket(mUser.getUserId(), mUser.getToken());
            int userLevel = Util.getUserVIPLevel(mUser);
            mPagerAdapter.setLevel(userLevel);
            mPagerAdapter.notifyDataSetChanged();
            if (userLevel > 0){
                mViewPager.setCurrentItem(userLevel - 1);
            }else {
                mViewPager.setCurrentItem(0);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        vipCenterStore.unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dispatcher.unregister(vipCenterStore);
    }

    @Override
    protected void setupViews() {
        super.setupViews();
        vipTypeView = (TextView) findViewById(R.id.vip_type_view);
        conditionView = (TextView) findViewById(R.id.vip_group_up_condition);
        principalRedPacketView = (TextView) findViewById(R.id.vip_red_packet_icon_view);
        surpriseRedPacketView = (TextView) findViewById(R.id.vip_surprise_packet_icon_view);
        birthdayRedPacketView = (TextView) findViewById(R.id.vip_birthday_packet_icon_view);
        privilegeRedPacketView = (TextView) findViewById(R.id.vip_privilege_red_packet_icon_view);
        privateServiceView = (TextView) findViewById(R.id.vip_private_customer_service_icon_view);
        doubleIntegralView = (TextView) findViewById(R.id.vip_double_integral_icon_view);
        giftView = (TextView) findViewById(R.id.vip_private_gift_icon_view);
        containerLayout = (LinearLayout) findViewById(R.id.take_vip_ticket_layout);
        takeTicketsTitle = (TextView) findViewById(R.id.red_tickets_title_view);

        mViewPager = (ClipViewPager) findViewById(R.id.vip_center_view_pager);
        mViewPager.setPageTransformer(true, new ScalePageTransformer());
        mViewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.vip_icon_item_margin));
        findViewById(R.id.vip_pager_container).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mViewPager.dispatchTouchEvent(event);
            }
        });

        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.normal_vip_icon_selected);
        list.add(R.drawable.bronze_vip_icon_selected);
        list.add(R.drawable.silver_vip_icon_selected);
        list.add(R.drawable.gold_vip_icon_selected);
        list.add(R.drawable.diamond_vip_icon_selected);
        list.add(R.drawable.gold_diamond_vip_icon_selected);

        mPagerAdapter = new TubatuAdapter(this, list);

        //设置OffscreenPageLimit
        mViewPager.setOffscreenPageLimit(list.size());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void pageSelected(int position){
        switch (position){
            case 0:
                vipTypeView.setText(getString(R.string.normal_vip));
                conditionView.setText(getString(R.string.normal_vip_group_up_condition));
                principalRedPacketView.setEnabled(false);
                surpriseRedPacketView.setEnabled(false);
                birthdayRedPacketView.setEnabled(false);
                privilegeRedPacketView.setEnabled(false);
                privateServiceView.setEnabled(false);
                doubleIntegralView.setEnabled(false);
                giftView.setEnabled(false);
                break;
            case 1:
                vipTypeView.setText(getString(R.string.bronze_vip));
                conditionView.setText(getString(R.string.bronze_vip_group_up_condition));
                principalRedPacketView.setEnabled(true);
                surpriseRedPacketView.setEnabled(false);
                birthdayRedPacketView.setEnabled(true);
                privilegeRedPacketView.setEnabled(false);
                privateServiceView.setEnabled(false);
                doubleIntegralView.setEnabled(false);
                giftView.setEnabled(false);
                break;
            case 2:
                vipTypeView.setText(getString(R.string.silver_vip));
                conditionView.setText(getString(R.string.silver_vip_group_up_condition));
                principalRedPacketView.setEnabled(false);
                surpriseRedPacketView.setEnabled(true);
                birthdayRedPacketView.setEnabled(true);
                privilegeRedPacketView.setEnabled(true);
                privateServiceView.setEnabled(false);
                doubleIntegralView.setEnabled(false);
                giftView.setEnabled(false);
                break;
            case 3:
                vipTypeView.setText(getString(R.string.gold_vip));
                conditionView.setText(getString(R.string.gold_vip_group_up_condition));
                principalRedPacketView.setEnabled(false);
                surpriseRedPacketView.setEnabled(true);
                birthdayRedPacketView.setEnabled(true);
                privilegeRedPacketView.setEnabled(true);
                privateServiceView.setEnabled(false);
                doubleIntegralView.setEnabled(true);
                giftView.setEnabled(false);
                break;
            case 4:
                vipTypeView.setText(getString(R.string.diamond_vip));
                conditionView.setText(getString(R.string.diamond_vip_group_up_condition));
                principalRedPacketView.setEnabled(false);
                surpriseRedPacketView.setEnabled(true);
                birthdayRedPacketView.setEnabled(true);
                privilegeRedPacketView.setEnabled(true);
                privateServiceView.setEnabled(true);
                doubleIntegralView.setEnabled(true);
                giftView.setEnabled(true);
                break;
            case 5:
                vipTypeView.setText(getString(R.string.gold_diamond_vip));
                conditionView.setText(getString(R.string.gold_diamond_vip_group_up_condition));
                principalRedPacketView.setEnabled(false);
                surpriseRedPacketView.setEnabled(true);
                birthdayRedPacketView.setEnabled(true);
                privilegeRedPacketView.setEnabled(true);
                privateServiceView.setEnabled(true);
                doubleIntegralView.setEnabled(true);
                giftView.setEnabled(true);
                break;
        }
    }

    @Subscribe
    public void onLoginStoreChange(Store.StoreChangeEvent event) {
        String type = event.getType();
        switch (type){
            case VIPCenterAction.ACTION_REQUEST_START:
                showLoadingDialog();
                break;
            case VIPCenterAction.ACTION_REQUEST_FINISH:
                dismissLoadingDialog();
                break;
            case VIPCenterAction.ACTION_REQUEST_FAIL:
                UIHelper.ToastMessage(this, getString(R.string.net_work_error));
                break;
            case VIPCenterAction.ACTION_REQUEST_INVALID_TOKEN:
                MainRouter.getInstance(this).showActivity(ModuleID.USER_MODULE_ID, UserUI.LoginActivity);
                break;
            case VIPCenterAction.ACTION_REQUEST_ERROR_MESSAGE:
                String msg = event.getMessage();
                UIHelper.ToastMessage(this, msg);
                break;
            case VIPCenterAction.ACTION_REQUEST_RED_TICKET_SUCCESS:
                BaseModel baseModel = vipCenterStore.getVipData();
                if (baseModel != null && baseModel.isSuccess()){
                    ArrayList<RedTicket> tickets = (ArrayList<RedTicket>) baseModel.getRows();
                    if (tickets != null && tickets.size() > 0){
                        this.redTickets = tickets;
                        setTicketsView();
                    }
                }
                break;
            case VIPCenterAction.ACTION_REQUEST_TAKE_RED_TICKET_SUCCESS:
                UIHelper.ToastMessage(this, getString(R.string.take_red_ticket_success));
                for (RedTicket redTicket : redTickets){
                    if (redTicket.getVid() == ticketId){
                        redTicket.setGtype(1);
                    }
                }
                setTicketsView();
                break;
        }
    }

    private void setTicketsView(){
        if (redTickets == null || redTickets.size() == 0){
            return;
        }
        containerLayout.removeAllViews();
        takeTicketsTitle.setVisibility(View.VISIBLE);
        int size = redTickets.size();
        for (int i = 0; i < size; i+=3){
            int end = i + 3;
            if (end <= size){
                VIPTicketView view = new VIPTicketView(this);
                view.setVIPTicketViewData(redTickets.subList(i, end));
                view.setOnVIPTicketClickListener(this);
                containerLayout.addView(view);
            }
        }
    }

    @Override
    public void onVipTicketClickListener(int id) {
        if (!Util.isNetworkAvailable(appContext)){
            UIHelper.ToastMessage(appContext, getString(R.string.net_work_invalid));
            return;
        }
        if (mUser != null){
            ticketId = id;
            appActionsCreator.getVipRedTicket(mUser.getUserId(), id, mUser.getToken());
        }
    }

    public class TubatuAdapter extends RecyclingPagerAdapter {
        private final List<Integer> mList;
        private final Context mContext;
        private int userLevel = -1;
        public TubatuAdapter(Context context, List<Integer> list) {
            mList = list;
            mContext = context;
        }

        public void setLevel(int level){
            this.userLevel = level;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup container) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.vip_icon_adapter_item_layout, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.vip_icon_item_image);
            imageView.setImageResource(mList.get(position));
            ImageView level = (ImageView) view.findViewById(R.id.vip_icon_my_level);
            if (position == userLevel - 1){
                level.setVisibility(View.VISIBLE);
            }else {
                level.setVisibility(View.GONE);
            }
            return view;
        }

        @Override
        public int getCount() {
            return mList.size();
        }
    }
}
