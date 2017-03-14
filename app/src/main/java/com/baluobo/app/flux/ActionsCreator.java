package com.baluobo.app.flux;

import com.baluobo.common.net.APIClient;
import com.baluobo.common.net.APIService;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/10.
 */
public class ActionsCreator {
    public Dispatcher dispatcher;
    public APIService apiService;
    public ActionsCreator(Dispatcher dispatcher){
        this.dispatcher = dispatcher;
        this.apiService = APIClient.getInstance();
    }
}
