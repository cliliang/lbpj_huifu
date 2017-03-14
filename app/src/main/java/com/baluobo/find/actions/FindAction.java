package com.baluobo.find.actions;

import com.baluobo.app.flux.Action;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/8/29.
 */
public class FindAction extends Action {
    public static final String ACTION_REQUEST_FIND_UPDATE_DATE = "find_fragment_update_user_date";
    public FindAction(String type, Object data) {
        super(type, data);
    }

    public FindAction(String type) {
        super(type);
    }
}
