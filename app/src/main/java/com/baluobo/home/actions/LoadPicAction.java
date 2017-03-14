package com.baluobo.home.actions;

import com.baluobo.app.flux.Action;
import com.baluobo.home.model.Banner;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/12/15.
 */
public class LoadPicAction extends Action<Banner> {
    public static final String LOAD_PIC_ACTION_SUCCESS = "LOAD_PIC_ACTON_SUCCESS";
    public static final String DOWNLOAD_LOAD_PIC_FINISH = "DOWNLOAD_LOAD_PIC_FINISH";
    private Banner banner;
    public LoadPicAction(String type, Banner data) {
        super(type, data);
        this.banner = data;
    }

    public LoadPicAction(String type) {
        super(type);
    }
}
