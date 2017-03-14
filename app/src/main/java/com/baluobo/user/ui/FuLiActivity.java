package com.baluobo.user.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.baluobo.R;
import com.baluobo.app.ui.BaseToolBarActivity;
import com.baluobo.common.tools.UIHelper;
import com.baluobo.common.tools.Util;
import com.baluobo.user.actions.WebAction;
import com.baluobo.user.adapter.FuliPagerAdapter;
import com.baluobo.user.fragment.FuliFragment;
import com.baluobo.user.router.UserRouter;
import com.baluobo.user.router.UserUI;

import java.util.ArrayList;
import java.util.List;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/8/15.
 */
public class FuLiActivity extends BaseToolBarActivity {
    private String[] tabString;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fuli_center_activity_layout);
        setTitle(getString(R.string.fuli_center));
        setRightTextMenu(getString(R.string.red_packet_rule), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.isNetworkAvailable(appContext)){
                    Bundle bundle = new Bundle();
                    bundle.putString(WebAction.WEB_REQUEST_TYPE, WebAction.FULI_RULE_ACTIVITY);
                    UserRouter.getInstance(FuLiActivity.this).showActivity(UserUI.WebActivity, bundle);
                }else {
                    UIHelper.ToastMessage(appContext, getString(R.string.net_work_invalid));
                }

            }
        });
        setupViews();
    }

    @Override
    protected void setupViews() {
        super.setupViews();
        tabString = new String[]{getString(R.string.cash_red_packet), getString(R.string.principal_red_packet), getString(R.string.experience_ticket)};
        tabLayout = (TabLayout) findViewById(R.id.fuli_center_tab_layout);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        viewPager = (ViewPager) findViewById(R.id.fuli_center_view_pager);
        List<Fragment> fragments = new ArrayList<>();
        FuliFragment raiseRateFragment = FuliFragment.getInstance(0);
        fragments.add(raiseRateFragment);
        FuliFragment redPacketFragment = FuliFragment.getInstance(1);
        fragments.add(redPacketFragment);
        FuliFragment experienceTicketFragment = FuliFragment.getInstance(2);
        fragments.add(experienceTicketFragment);
        FuliPagerAdapter adapter = new FuliPagerAdapter(getSupportFragmentManager(), fragments, tabString);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }
}
