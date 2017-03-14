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
 * Created on:16/5/17.
 */
public class AssetsItemView extends LinearLayout {
    private TextView percentView, moneyView;
    public AssetsItemView(Context context) {
        super(context);
    }

    public AssetsItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AssetsItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context cnt, AttributeSet attributeSet){
        LayoutInflater.from(cnt).inflate(R.layout.assets_item_view_layout, this);
        IconTextView iconView = (IconTextView) findViewById(R.id.assets_item_icon);
        TextView nameView = (TextView) findViewById(R.id.assets_item_name);
        percentView = (TextView) findViewById(R.id.assets_item_percent);
        moneyView = (TextView) findViewById(R.id.assets_item_money);
        TypedArray typedArray = cnt.obtainStyledAttributes(attributeSet, R.styleable.AssetsItemView);
        try {
            int color = typedArray.getColor(R.styleable.AssetsItemView_itemIconColor, ContextCompat.getColor(cnt, R.color.white));
            iconView.setTextColor(color);
            String name = typedArray.getString(R.styleable.AssetsItemView_itemName);
            nameView.setText(name);
        }finally {
            typedArray.recycle();
        }
    }

    public void setAssets(String percent, String money){
        percentView.setText(percent);
        moneyView.setText(money);
    }
}
