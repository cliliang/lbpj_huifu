package com.baluobo.common.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baluobo.R;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/12.
 */
public class CustomItemView extends LinearLayout {
    private TextView subContent;

    public CustomItemView(Context context) {
        super(context);
    }

    public CustomItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){
        LayoutInflater.from(context).inflate(R.layout.custom_item_layout, this);
        IconTextView flagIcon = (IconTextView) findViewById(R.id.custom_item_flag_icon_id);
        IconTextView arrowIcon = (IconTextView) findViewById(R.id.custom_item_arrow_icon_id);
        TextView contentView = (TextView) findViewById(R.id.custom_item_content_text_id);
        subContent = (TextView) findViewById(R.id.custom_item_sub_content_text_id);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomItemView);
        try {
            String flagIconString = typedArray.getString(R.styleable.CustomItemView_iconFlag);
            int iconColor = typedArray.getColor(R.styleable.CustomItemView_iconFlagColor, ContextCompat.getColor(context, R.color.color_92));
            String contentViewString = typedArray.getString(R.styleable.CustomItemView_titleContent);
            String subContentViewString = typedArray.getString(R.styleable.CustomItemView_titleSubContent);
            boolean showFlagIcon = typedArray.getBoolean(R.styleable.CustomItemView_showIconFlag, true);
            boolean showIconArrow = typedArray.getBoolean(R.styleable.CustomItemView_showIconArrow, true);
            flagIcon.setText(flagIconString);
            flagIcon.setTextColor(iconColor);
            contentView.setText(contentViewString);
            subContent.setText(subContentViewString);
            flagIcon.setVisibility(showFlagIcon ? VISIBLE : GONE);
            arrowIcon.setVisibility(showIconArrow ? VISIBLE : INVISIBLE);
        }finally {
            typedArray.recycle();
        }
    }

    public void setSubContent(String subContentString){
        subContent.setText(subContentString);
    }
}
