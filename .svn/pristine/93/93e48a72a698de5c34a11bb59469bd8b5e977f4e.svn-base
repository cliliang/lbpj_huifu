package com.baluobo.user.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.baluobo.user.fragment.FuliFragment;

import java.util.List;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/8/18.
 */
public class FuliPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private String[] tabString;
    public FuliPagerAdapter(FragmentManager fragmentManager, List<Fragment> list, String[] tabs) {
        super(fragmentManager);
        this.fragments = list;
        this.tabString = tabs;
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
        return tabString[position];
    }
}
