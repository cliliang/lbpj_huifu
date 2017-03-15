package com.baluobo.common.net;

import com.baluobo.common.config.AppConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/31.
 */
public class RedeemAPIClient extends APIClient{

    private static final String debugHuifuURL = getRequestTpye() + DEBUG_HOST_BASE + HUIFU;
    private static final String releaseHuifuURL = getRequestTpye() + RELEASE_HOST_BASE + HUIFU;

    private static APIService apiService;
    public static APIService getInstance(){
        if (apiService == null){
            synchronized (RedeemAPIClient.class){
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(getRedeemURL())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                apiService = retrofit.create(APIService.class);
            }
        }
        return apiService;
    }

    private static String getRedeemURL(){
        return AppConfig.isDebugVersion ? debugHuifuURL : releaseHuifuURL;
    }
}
