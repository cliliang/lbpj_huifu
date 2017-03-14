package com.baluobo.find;

import android.content.Context;

import com.baluobo.common.module.ModBase;
import com.baluobo.common.module.ModuleID;
import com.baluobo.common.module.ModuleNames;
import com.baluobo.common.module.RouterBase;
import com.baluobo.find.router.FindRouter;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/8/5.
 */
public class FindModule extends ModBase {
    private static FindModule instance;
    private FindModule() {
        super(ModuleID.FIND_MODULE_ID, ModuleNames.FIND_MODULE_NAME);
    }

    public static FindModule getInstance(){
        if (instance == null){
            instance = new FindModule();
        }
        return instance;
    }

    @Override
    public RouterBase getRouter(Context context) {
        return FindRouter.getInstance(context);
    }
}
