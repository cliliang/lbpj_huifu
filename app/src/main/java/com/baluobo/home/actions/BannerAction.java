package com.baluobo.home.actions;

import com.baluobo.app.flux.Action;
import com.baluobo.home.model.Banner;

import java.util.List;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/23.
 */
public class BannerAction extends Action<List<Banner>> {
    private List<Banner> data;
    public static final String ACTION_REQUEST_BANNER_SUCCESS = "action_request_banner_success";
    public BannerAction(String type, List<Banner> data) {
        super(type, data);
        this.data = data;
    }

    public BannerAction(String type){
        super(type, null);
    }
}
