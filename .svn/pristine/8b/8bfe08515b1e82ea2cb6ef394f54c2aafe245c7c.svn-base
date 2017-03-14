package com.baluobo.common.tools;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.baluobo.R;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/8/8.
 */
public class SharePopupWindow extends PopupWindow {
    private View mMenuView;
    private Activity mContext;
    private String inviteCode;
    private int shareType;
    private String calculateResult;
    public SharePopupWindow(Activity context) {
        this.mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.share_popup_window_layout, null);
        this.setContentView(mMenuView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.shareWindowStyle);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        this.setBackgroundDrawable(dw);
        mMenuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mMenuView.findViewById(R.id.share_icon_wechat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (shareType == 1){
                    new ShareClient(mContext).shareWX(inviteCode);
                }else if (shareType == 2){
                    new ShareClient(mContext).shareWXCalculateResult(calculateResult);
                }else {
                    new ShareClient(mContext).shareWXMallResult();
                }

            }
        });
        mMenuView.findViewById(R.id.share_icon_circle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (shareType == 1){
                    new ShareClient(mContext).shareWXCircle(inviteCode);
                }else if (shareType == 2){
                    new ShareClient(mContext).shareWXCircleCalculateResult(calculateResult);
                }else {
                    new ShareClient(mContext).shareWXCircleMallResult();
                }
            }
        });
    }

    public void setInviteData(int type, String myCode){
        this.shareType = type;
        this.inviteCode = myCode;
    }

    public void setShareCalculate(int type, String result){
        this.shareType = type;
        this.calculateResult = result;
    }

    public void setMallShare(int type){
        this.shareType = type;
    }
}
