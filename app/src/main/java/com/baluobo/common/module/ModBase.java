/*
 * Copyright (c) 2014 Beijing Dnurse Technology Ltd. All rights reserved.
 */

package com.baluobo.common.module;

import android.content.Context;

public class ModBase {

    private int modId;
    private String modName;

    public ModBase(int id, String name) {
        this.modId = id;
        this.modName = name;
    }


    public int getModId() {
        return modId;
    }

    public String getModName() {
        return modName;
    }

    /**
     * the module navigate router
     *
     * @param context
     * @return router or null
     */
    public RouterBase getRouter(Context context) {
        return null;
    }


}
