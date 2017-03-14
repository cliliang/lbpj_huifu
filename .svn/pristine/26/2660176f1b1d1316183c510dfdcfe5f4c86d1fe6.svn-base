package com.baluobo.common.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baluobo.R;
import com.baluobo.common.net.APIClient;
import com.baluobo.common.tools.LuoboImageLoader;
import com.baluobo.find.model.FindNews;
import com.baluobo.find.model.OnFindNewsItemClickListener;
import com.makeramen.roundedimageview.RoundedImageView;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/8/16.
 */
public class FindItemView extends LinearLayout {
    private RoundedImageView imageView;
    private TextView titleView, dateView, containerView;
    private OnFindNewsItemClickListener listener;
    public FindItemView(Context context) {
        super(context);
        init(context);
    }

    public FindItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FindItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        LayoutInflater.from(context).inflate(R.layout.find_main_recycle_view_item_layout, this);
        imageView = (RoundedImageView) findViewById(R.id.find_main_adapter_image_view);
        titleView = (TextView) findViewById(R.id.find_main_adapter_title_view);
        dateView = (TextView) findViewById(R.id.find_main_adapter_time_view);
        containerView = (TextView) findViewById(R.id.find_main_adapter_container_view);
    }

    public void setViewData(final FindNews news){
        if (news == null){
            return;
        }
        LuoboImageLoader.getInstance().displayImage(APIClient.getBannerHost() + news.getNewsIcon(), imageView);
        titleView.setText(news.getNewsTitle());
        dateView.setText(news.getCreateTime());
        containerView.setText(news.getDescription());
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    listener.onNewsItemClick(news.getnId());
                }
            }
        });
    }

    public void setOnFindNewsItemClickListener(OnFindNewsItemClickListener l){
        this.listener = l;
    }
}
