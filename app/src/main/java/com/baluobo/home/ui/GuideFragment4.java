package com.baluobo.home.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baluobo.R;
import com.baluobo.common.config.AppConfig;
import com.baluobo.home.router.HomeRouter;
import com.baluobo.home.router.HomeUI;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/6/6.
 */
public class GuideFragment4 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.guide_fragment4_layout, container, false);
        view.findViewById(R.id.guide_use_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConfig.getInstance(getActivity()).setShowGuide(true);
                HomeRouter.getInstance(getActivity()).showActivity(HomeUI.MainActivity);
                getActivity().finish();
            }
        });
        return view;
    }
}
