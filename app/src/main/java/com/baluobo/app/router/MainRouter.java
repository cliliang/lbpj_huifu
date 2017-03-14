/*
 * Copyright (c) 2014 Beijing Dnurse Technology Ltd. All rights reserved.
 */

package com.baluobo.app.router;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.baluobo.app.AppContext;
import com.baluobo.common.module.ModBase;
import com.baluobo.common.module.ModuleID;
import com.baluobo.common.module.RouterBase;

import java.util.List;

/**
 * desc: 处理垮模块界面跳转
 * Created by:chenliliang
 * Created on:16/5/4.
 */
public class MainRouter {
    private static MainRouter singleton = null;
    private Context context;

    public MainRouter(Context context) {
        this.context = context;
    }

    public static MainRouter getInstance(Context context) {
        if (singleton == null) {
            synchronized (MainRouter.class) {
                if (singleton == null) {
                    singleton = new MainRouter(context);
                }
            }
        }
        return singleton;
    }

    /**
     * 垮模块界面跳转
     * @param moduleId moduleId 目标模块id
     * @param activityId activityId 目标界面id
     * @param bundle bundle data
     * @param flags flags
     * @return
     */
    public boolean showActivity(int moduleId, int activityId, Bundle bundle, int flags) {
        AppContext appContext = (AppContext) context.getApplicationContext();
        List<ModBase> mods = appContext.getMods();
        if (moduleId != ModuleID.BLANK) {
            for (ModBase mod : mods) {
                if (mod.getModId() == moduleId) {
                    RouterBase router = mod.getRouter(context);
                    if (router != null) {
                        if (bundle != null) {
                            return router.showActivity(activityId,flags);
                        } else {
                            return router.showActivity(activityId, bundle,flags);
                        }
                    }
                }
            }
        } else {
            for (ModBase mod : mods) {
                RouterBase router = mod.getRouter(context);
                if (router != null) {
                    if (router.showActivity(activityId, bundle,flags)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean showActivity(int moduleId, int activityId, Bundle bundle) {
        AppContext appContext = (AppContext) context.getApplicationContext();
        List<ModBase> mods = appContext.getMods();
        if (moduleId != ModuleID.BLANK){
            for (ModBase mod : mods) {
                if (mod.getModId() == moduleId) {
                    RouterBase router = mod.getRouter(context);
                    if (router != null) {
                        if (bundle == null) {
                            return router.showActivity(activityId);
                        } else {
                            return router.showActivity(activityId, bundle);
                        }
                    }
                }
            }
        }else {
            for (ModBase mod : mods) {
                RouterBase router = mod.getRouter(context);
                if (router != null) {
                    if (router.showActivity(activityId, bundle)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean showActivity(int activityId) {
        return showActivity(ModuleID.BLANK, activityId, null);
    }

    public boolean showActivity(int activityId, Bundle bundle) {
        return showActivity(ModuleID.BLANK, activityId, bundle);
    }

    // Show activity with flags
    public boolean showActivity(int moduleId, int activityId) {
        return showActivity(moduleId, activityId, null);
    }

    public boolean showActivityForResult(int moduleId, Activity parentActivity, int activityId, int requestCode, Bundle param) {
        AppContext appContext = (AppContext) context.getApplicationContext();
        List<ModBase> mods = appContext.getMods();
        if (moduleId != ModuleID.BLANK) {
            for (ModBase mod : mods) {
                if (mod.getModId() == moduleId) {
                    RouterBase router = mod.getRouter(context);
                    if (router != null) {
                        return router.showActivityForResult(parentActivity, activityId, requestCode, param);
                    }
                }
            }
        } else {
            return showActivityForResult(parentActivity, activityId, requestCode, param);
        }
        return false;
    }

    public boolean showActivityForResult(Activity parentActivity, int activityId, int requestCode, Bundle param) {
        AppContext appContext = (AppContext) context.getApplicationContext();
        List<ModBase> mods = appContext.getMods();
        for (ModBase mod : mods) {
            RouterBase router = mod.getRouter(context);
            if (router != null) {
                return router.showActivityForResult(parentActivity, activityId, requestCode, param);
            }
        }

        return false;
    }

    public boolean showActivityForResult(Fragment fragment, int activityId, int requestCode, Bundle param) {
        if (fragment == null) return false;
        AppContext appContext = (AppContext) context.getApplicationContext();
        List<ModBase> mods = appContext.getMods();
        for (ModBase mod : mods) {
            RouterBase router = mod.getRouter(context);
            if (router != null) {
                return router.showActivityForResult(fragment, activityId, requestCode, param);
            }
        }

        return false;
    }
}
