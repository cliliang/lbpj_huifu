package com.baluobo.common.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baluobo.R;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/8/5.
 */
public class FindBlankView extends LinearLayout {
    public FindBlankView(Context context) {
        super(context);
    }

    public FindBlankView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FindBlankView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){
        LayoutInflater.from(context).inflate(R.layout.find_blank_view_layout, this);
        ImageView imageView = (ImageView) findViewById(R.id.find_blank_image_view);
        TextView textView = (TextView) findViewById(R.id.find_blank_text_view);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FindBlankView);
        try {
            int res = typedArray.getResourceId(R.styleable.FindBlankView_blankImage, R.drawable.icon_ding_tou);
            String title = typedArray.getString(R.styleable.FindBlankView_blankTitle);
            imageView.setImageResource(res);
            textView.setText(title);
        }finally {
            typedArray.recycle();
        }
    }
}
