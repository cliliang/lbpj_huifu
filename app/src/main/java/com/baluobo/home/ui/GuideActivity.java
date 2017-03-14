package com.baluobo.home.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.baluobo.R;
import com.baluobo.app.ui.BaseStatusBarActivity;
import com.baluobo.common.views.IndicatorView;

import java.util.ArrayList;
import java.util.List;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/4.
 */
public class GuideActivity extends BaseStatusBarActivity {
    private ViewPager viewPager;
    private IndicatorView indicatorView;
    private List<Fragment> fragments = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_activity_layout);
        setupViews();
        GuideFragment1 fragment1 = new GuideFragment1();
        GuideFragment2 fragment2 = new GuideFragment2();
        GuideFragment3 fragment3 = new GuideFragment3();
        GuideFragment4 fragment4 = new GuideFragment4();
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        fragments.add(fragment4);
        GuideViewPagerAdapter adapter = new GuideViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        indicatorView.setViewPager(viewPager);
    }

    @Override
    protected void setupViews() {
        super.setupViews();
        viewPager = (ViewPager) findViewById(R.id.guide_view_pager);
        indicatorView = (IndicatorView) findViewById(R.id.guide_indicator_view);

    }

    private class GuideViewPagerAdapter extends FragmentPagerAdapter{
        private List<Fragment> fragmentList;
        public GuideViewPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.fragmentList = list;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}
