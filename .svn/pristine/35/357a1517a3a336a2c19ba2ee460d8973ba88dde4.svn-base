package com.baluobo.find.router;

import android.content.Context;

import com.baluobo.common.module.RouterBase;
import com.baluobo.find.ui.SignInActivity;
import com.baluobo.find.ui.VIPCenterActivity;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/8/5.
 */
public class FindRouter extends RouterBase {
    private static FindRouter instance;
    private FindRouter(Context context) {
        super(context);
        maps.put(FindUI.VIPCenter, VIPCenterActivity.class);
        maps.put(FindUI.SignIn, SignInActivity.class);

    }
    public static FindRouter getInstance(Context cnt){
        if (instance == null){
            synchronized (FindRouter.class){
                instance = new FindRouter(cnt);
            }
        }
        return instance;
    }
}
