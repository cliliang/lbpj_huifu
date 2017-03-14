package com.baluobo.user;

import android.content.Context;

import com.baluobo.common.module.ModBase;
import com.baluobo.common.module.ModuleID;
import com.baluobo.common.module.ModuleNames;
import com.baluobo.common.module.RouterBase;
import com.baluobo.user.router.UserRouter;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/10.
 */
public class UserModule extends ModBase {
    private static UserModule instance;
    private UserModule() {
        super(ModuleID.USER_MODULE_ID, ModuleNames.USER_MODULE_NAME);
    }

    public static UserModule getInstance(){
        if (instance == null){
            instance = new UserModule();
        }
        return instance;
    }

    @Override
    public RouterBase getRouter(Context context) {
        return UserRouter.getInstance(context);
    }
}
