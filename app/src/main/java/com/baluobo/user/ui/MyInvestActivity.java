package com.baluobo.user.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.baluobo.R;
import com.baluobo.app.ui.BaseToolBarActivity;
import com.baluobo.home.ui.GuideFragment1;
import com.baluobo.home.ui.GuideFragment2;
import com.baluobo.home.ui.GuideFragment3;
import com.baluobo.user.actions.InvestAction;
import com.baluobo.user.fragment.InvestOrderListBaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/18.
 */
public class MyInvestActivity extends BaseToolBarActivity {

    private final String TAG_INVESTING = "tag_investing";
    private final String TAG_REPAYING = "tag_repaying";
    private final String TAG_COMPLETE = "tag_complete";

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String[] tabString;
    private final String[] tags = {
            TAG_INVESTING, TAG_REPAYING, TAG_COMPLETE
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_invest_activity_layout);
        setTitle(getString(R.string.mine_invest));
        setupViews();
    }

    @Override
    protected void setupViews() {
        super.setupViews();
        tabString = new String[]{getString(R.string.my_invest_investing), getString(R.string.my_invest_repay), getString(R.string.my_invest_complete)};
        tabLayout = (TabLayout) findViewById(R.id.my_invest_tab_layout);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        viewPager = (ViewPager) findViewById(R.id.my_invest_view_pager);
        List<Fragment> fragmentList = new ArrayList<>();
        InvestOrderListBaseFragment investingFragment = InvestOrderListBaseFragment.getFragment(InvestAction.ORDER_STATE_INVESTING);
        InvestOrderListBaseFragment repayFragment = InvestOrderListBaseFragment.getFragment(InvestAction.ORDER_STATE_REPAY);
        InvestOrderListBaseFragment completeFragment = InvestOrderListBaseFragment.getFragment(InvestAction.ORDER_STATE_COMPLETE);
        fragmentList.add(investingFragment);
        fragmentList.add(repayFragment);
        fragmentList.add(completeFragment);
        InvestPagerAdapter adapter = new InvestPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private class InvestPagerAdapter extends FragmentPagerAdapter{
        private List<Fragment> fragments;
        public InvestPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            fragments = list;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        //此方法用来显示tab上的名字
        @Override
        public CharSequence getPageTitle(int position) {
            return tabString[position];
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }
}
