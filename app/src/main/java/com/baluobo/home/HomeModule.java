package com.baluobo.home;

import android.content.Context;

import com.baluobo.common.module.ModBase;
import com.baluobo.common.module.ModuleID;
import com.baluobo.common.module.ModuleNames;
import com.baluobo.common.module.RouterBase;
import com.baluobo.home.router.HomeRouter;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/4.
 */
public class HomeModule extends ModBase {
    private static HomeModule instance;
    private HomeModule() {
        super(ModuleID.HOME_MODULE_ID, ModuleNames.HOME_MODULE_NAME);
    }

    public static HomeModule getInstance(){
        if (instance == null){
            instance = new HomeModule();
        }
        return instance;
    }

    @Override
    public RouterBase getRouter(Context context) {
        return HomeRouter.getInstance(context);
    }
}
