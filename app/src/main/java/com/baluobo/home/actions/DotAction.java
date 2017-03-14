package com.baluobo.home.actions;

import com.baluobo.app.flux.Action;
import com.baluobo.app.model.BaseModel;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/7/26.
 */
public class DotAction extends Action {
    public BaseModel baseModel;
    public static final String RECALCULATE_STRING = "recalculate_string";

    public static final String MAIN_DOT_ACTION_HAVE_UNREAD_MESSAGE = "main_dot_action_have_unread_message";
    public static final String MAIN_DOT_ACTION_DONT_HAVE_UNREAD_MESSAGE = "main_dot_action_dot_have_unread_message";
    public DotAction(String type, BaseModel data) {
        super(type, data);
        this.baseModel = data;
    }

    public DotAction(String type) {
        super(type);
    }
}
