package com.baluobo.common.tools;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baluobo.R;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/12.
 */
public class SingleButtonDialog extends Dialog {


    public SingleButtonDialog(Context context) {
        super(context);
    }

    public SingleButtonDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected SingleButtonDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public static class Build {
        private Context context;
        private String message;
        private String buttonText;
        private DialogInterface.OnClickListener listener;
        public Build(Context cnt){
            this.context = cnt;
        }
        public Build setMessage(String msg){
            this.message = msg;
            return this;
        }

        public Build setButton(String btnString, DialogInterface.OnClickListener l){
            this.buttonText = btnString;
            this.listener = l;
            return this;
        }

        public SingleButtonDialog create(){
            final SingleButtonDialog dialog = new SingleButtonDialog(context, R.style.DialogStyle);
            View view = LayoutInflater.from(context).inflate(R.layout.single_button_dialog_layout, null);
            TextView msgView = (TextView) view.findViewById(R.id.single_button_dialog_msg);
            Button button = (Button) view.findViewById(R.id.single_button_dialog_button);
            msgView.setText(message);
            button.setText(buttonText);
            if (listener != null){
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                    }
                });
            }
            dialog.setContentView(view);
            return dialog;
        }
    }
}
