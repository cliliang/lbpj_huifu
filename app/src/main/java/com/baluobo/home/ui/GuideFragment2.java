package com.baluobo.home.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baluobo.R;
import com.baluobo.app.ui.BaseFragment;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/4.
 */
public class GuideFragment2 extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.guide_fragmnet2_layout, container, false);
        return view;
    }
}
