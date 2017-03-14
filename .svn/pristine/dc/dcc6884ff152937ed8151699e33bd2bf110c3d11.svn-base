package com.baluobo.common.tools;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.baluobo.R;
import com.baluobo.common.config.AppConfig;
import com.baluobo.common.net.APIClient;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/8/9.
 */
public class ShareClient {
    private Activity activity;
    private UMImage umImage;
    private String shareTitle;
    private String shareContent;
    private String mallTitle;
    private String mallContent;
    public ShareClient(Activity context){
        this.activity = context;
        umImage = new UMImage(activity, BitmapFactory.decodeResource(activity.getResources(), R.mipmap.icon_share));
        shareTitle = activity.getResources().getString(R.string.share_title);
        shareContent = activity.getResources().getString(R.string.share_content);
        mallTitle = activity.getResources().getString(R.string.share_mall_title);
        mallContent = activity.getResources().getString(R.string.share_mall_content);
    }

    public void shareWX(String code){
        new ShareAction(activity)
                .setPlatform(SHARE_MEDIA.WEIXIN)
                .setCallback(shareListener)
                .withText(shareContent)
                .withTitle(shareTitle)
                .withTargetUrl(APIClient.getBannerHost() + "website/toInvite.html?inviteCode=" + code)
                .withMedia(umImage)
                .share();
    }

    public void shareWXCircle(String code){
        new ShareAction(activity)
                .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(shareListener)
                .withText(shareContent)
                .withTitle(shareTitle)
                .withTargetUrl(APIClient.getBannerHost() + "website/toInvite.html?inviteCode=" + code)
                .withMedia(umImage)
                .share();
    }

    public void shareWXCalculateResult(String result){
        new ShareAction(activity)
                .setPlatform(SHARE_MEDIA.WEIXIN)
                .setCallback(shareListener)
                .withText(result)
                .share();
    }

    public void shareWXMallResult(){
        new ShareAction(activity)
                .setPlatform(SHARE_MEDIA.WEIXIN)
                .setCallback(shareListener)
                .withTitle(mallTitle)
                .withText(mallContent)
                .withTargetUrl(APIClient.getBannerHost() + "website/turnplate.html")
                .withMedia(umImage)
                .share();
    }

    public void shareWXCircleMallResult(){
        new ShareAction(activity)
                .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(shareListener)
                .withTitle(mallTitle)
                .withText(mallContent)
                .withTargetUrl(APIClient.getBannerHost() + "website/turnplate.html")
                .withMedia(umImage)
                .share();
    }

    public void shareWXCircleCalculateResult(String result){
        new ShareAction(activity)
                .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(shareListener)
                .withText(result)
                .share();
    }

    UMShareListener shareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA share_media) {

        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {

        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {

        }
    };
}
