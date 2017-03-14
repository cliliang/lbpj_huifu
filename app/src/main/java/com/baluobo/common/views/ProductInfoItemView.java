package com.baluobo.common.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baluobo.R;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/27.
 */
public class ProductInfoItemView extends LinearLayout {

    private TextView contentView;
    public ProductInfoItemView(Context context) {
        super(context);
    }

    public ProductInfoItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ProductInfoItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet){
        View view = LayoutInflater.from(context).inflate(R.layout.product_info_item_layout, this);
        TextView titleView = (TextView) view.findViewById(R.id.product_info_item_title);
        contentView = (TextView) view.findViewById(R.id.product_info_item_content);
        TypedArray typeArray = context.obtainStyledAttributes(attributeSet, R.styleable.ProductInfoItemView);
        try {
            String titleString = typeArray.getString(R.styleable.ProductInfoItemView_infoTitle);
            String contentString = typeArray.getString(R.styleable.ProductInfoItemView_infoContent);
            titleView.setText(titleString);
            contentView.setText(contentString);
        }finally {
            typeArray.recycle();
        }
    }

    public void setContentView(String content){
        contentView.setText(content);
    }
}
