package com.baluobo.find.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.baluobo.R;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/9/21.
 */
public class SignDayView extends FrameLayout {
    private Context mContext;
    private TextView textView;
    public SignDayView(Context context) {
        super(context);
        init(context);
    }

    public SignDayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SignDayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context cnt){
        this.mContext = cnt;
        LayoutInflater.from(cnt).inflate(R.layout.sign_day_view_layout, this);
        textView = (TextView) findViewById(R.id.sign_day_view_id);
    }

    public void setText(String string){
        textView.setText(string);
    }

    public void setSigned(boolean enable){
        textView.setEnabled(enable);
    }
}
