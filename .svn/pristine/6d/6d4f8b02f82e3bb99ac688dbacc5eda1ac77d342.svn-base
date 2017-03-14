package com.baluobo.common.views;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.baluobo.R;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/8/17.
 */
public class VIPContentView extends TextView {
    public VIPContentView(Context context) {
        super(context);
        init(context);
    }

    public VIPContentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public VIPContentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context cnt){
        String text = getText().toString();
        int index = 4;
        if (text.length() > 100){
            index = 6;
        }
        if (text.length() > index){
            setText("");
            SpannableString spannableString = new SpannableString(text);
            spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(cnt, R.color.color_40)), 0, index, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            append(spannableString);
        }
    }
}
