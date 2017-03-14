package com.baluobo.common.tools;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.baluobo.R;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/16.
 */
public class LoadingDialog extends Dialog {
    public LoadingDialog(Context context) {
        super(context);
    }

    public LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected LoadingDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public static class Build {
        private Context context;
        private String message;
        public Build(Context cnt){
            this.context = cnt;
        }
        public Build setMessage(String msg){
            this.message = msg;
            return this;
        }

        public LoadingDialog create(){
            final LoadingDialog dialog = new LoadingDialog(context, R.style.DialogStyle);
            View view = LayoutInflater.from(context).inflate(R.layout.common_progress_dialog_layout, null);
            TextView msgView = (TextView) view.findViewById(R.id.common_loading_text);
            if (!TextUtils.isEmpty(message)){
                msgView.setText(message);
            }
            dialog.setContentView(view);
            return dialog;
        }
    }
}
