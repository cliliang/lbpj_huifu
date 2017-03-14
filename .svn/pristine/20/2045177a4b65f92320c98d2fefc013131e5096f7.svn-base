

package com.baluobo.common.module;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.baluobo.app.ui.BaseActivity;

import java.util.HashMap;

/**
 * desc: 处理模块内界面的跳转
 * Created by:chenliliang
 * Created on:16/5/4.
 */
public class RouterBase {
    protected HashMap<Integer, Class<? extends BaseActivity>> maps = new HashMap<>();
    private Context context;

    public RouterBase(Context context) {
        this.context = context;
    }

    /**
     * show activity with bundle and flags
     * @param activityId activityId
     * @param bundle bundle data
     * @param flags flags FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_NEW_TASK|
     *              FLAG_ACTIVITY_NO_HISTORY | FLAG_ACTIVITY_SINGLE_TOP
     * @return show true or false
     */
    public boolean showActivity(int activityId, Bundle bundle, int flags) {
        Class<? extends BaseActivity> lookClass = maps.get(activityId);
        if (lookClass != null) {
            Intent intent = new Intent(context, lookClass);
            intent.setFlags(flags);
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            if (!(context instanceof Activity)) {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent);
            return true;
        }
        Log.i(getClass().getName(), "activity " + activityId + " is not register in it's module");
        return false;
    }

    /**
     * show activity with bundle
     * @param activityId activityId
     * @param bundle bundle data
     * @return show true or false
     */
    public boolean showActivity(int activityId, Bundle bundle) {
        Class<? extends BaseActivity> lookClass = maps.get(activityId);
        if (lookClass != null) {
            Intent intent = new Intent(context, lookClass);
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            if (!(context instanceof Activity)) {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent);
            return true;
        }
        return false;
    }

    /**
     * show activity with flags
     * @param activityId activityId
     * @param flags flags
     * @return show true or false
     */
    public boolean showActivity(int activityId,int flags) {
        return showActivity(activityId, null,flags);
    }

    public boolean showActivity(int activityId) {
        return showActivity(activityId, null);
    }


    public boolean showActivityForResult(Activity parentActivity, int activityId, int requestCode, Bundle param) {
        Class<? extends BaseActivity> lookClass = maps.get(activityId);
        if (lookClass != null) {
            Intent intent = new Intent(context, lookClass);
            if (param != null) {
                intent.putExtras(param);
            }
            parentActivity.startActivityForResult(intent, requestCode);
            return true;
        }
        return false;
    }

    public boolean showActivityForResult(Fragment fragment, int activityId, int requestCode, Bundle param) {
        if (fragment == null) return false;
        Class<? extends BaseActivity> lookClass = maps.get(activityId);
        if (lookClass != null) {
            Intent intent = new Intent(context, lookClass);
            if (param != null) {
                intent.putExtras(param);
            }
            fragment.startActivityForResult(intent, requestCode);
            return true;
        }
        return false;
    }
}
