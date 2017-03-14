package com.baluobo.home.actions;

import com.baluobo.app.flux.Action;
import com.baluobo.home.model.VersionInfo;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/11/30.
 */
public class VersionAction extends Action<VersionInfo> {
    private VersionInfo versionInfo;
    public static final String ACTION_REQUEST_VERSION_SUCCESS = "ACTION_REQUEST_VERSION_INFO_SUCCESS";
    public static final String ACTION_REQUEST_START = "REQUEST_VERSION_INFO_START";
    public static final String ACTION_REQUEST_FINISH = "REQUEST_VERSION_INFO_FINISH";
    public static final String ACTION_REQUEST_FAIL = "REQUEST_VERSION_INFO_FAIL";

    public VersionAction(String type, VersionInfo data) {
        super(type, data);
        this.versionInfo = data;
    }

    public VersionAction(String type) {
        super(type);
    }
}
