package com.baluobo.home.router;

import android.content.Context;

import com.baluobo.common.module.RouterBase;
import com.baluobo.home.ui.CalculateActivity;
import com.baluobo.home.ui.DeclarationListActivity;
import com.baluobo.home.ui.GuideActivity;
import com.baluobo.home.ui.InviteActivity;
import com.baluobo.home.ui.MainActivity;
import com.baluobo.home.ui.RecalculateActivity;
import com.baluobo.home.ui.StartActivity;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/4.
 */
public class HomeRouter extends RouterBase{
    private static HomeRouter instance;
    public HomeRouter(Context context) {
        super(context);
        maps.put(HomeUI.StartActivity, StartActivity.class);
        maps.put(HomeUI.GuideActivity, GuideActivity.class);
        maps.put(HomeUI.MainActivity, MainActivity.class);
        maps.put(HomeUI.DeclarationList, DeclarationListActivity.class);
        maps.put(HomeUI.Calculate, CalculateActivity.class);
        maps.put(HomeUI.Recalculate, RecalculateActivity.class);
        maps.put(HomeUI.Invite, InviteActivity.class);
    }

    public static HomeRouter getInstance(Context cnt){
        if (instance == null){
            synchronized (HomeRouter.class){
                instance = new HomeRouter(cnt);
            }
        }
        return instance;
    }
}
