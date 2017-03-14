package com.baluobo.common.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baluobo.R;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/19.
 */
public class FindBlockView extends LinearLayout {

    public FindBlockView(Context context) {
        super(context);
    }

    public FindBlockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FindBlockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet){
        LayoutInflater.from(context).inflate(R.layout.find_block_view_layout, this);
        ImageView iconView = (ImageView) findViewById(R.id.find_block_icon_view);
        TextView nameView = (TextView) findViewById(R.id.find_block_name_view);
        TextView descView = (TextView) findViewById(R.id.find_block_desc_view);
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.FindBlockView);
        try {
            String nameString = typedArray.getString(R.styleable.FindBlockView_blockName);
            String descString = typedArray.getString(R.styleable.FindBlockView_blickDesc);
            int textColor = typedArray.getColor(R.styleable.FindBlockView_blockColor, ContextCompat.getColor(context, R.color.color_40));
            int resource = typedArray.getResourceId(R.styleable.FindBlockView_blockIcon, R.drawable.icon_ding_tou);
            iconView.setImageResource(resource);
            nameView.setText(nameString);
            descView.setText(descString);
            nameView.setTextColor(textColor);
        }finally {
            typedArray.recycle();
        }
    }
}
