package com.baluobo.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baluobo.R;
import com.baluobo.common.net.APIClient;
import com.baluobo.common.tools.LuoboImageLoader;
import com.baluobo.find.model.OnFindNewsItemClickListener;
import com.baluobo.home.model.Banner;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/8/5.
 */
public class FindActivityAdapter extends RecyclerView.Adapter<FindActivityAdapter.ActivityViewHolder> {
    private Context mContext;
    private ArrayList<Banner> banners;
    private OnBannerItemClickListener listener;
    public FindActivityAdapter(Context cnt){
        this.mContext = cnt;
        banners = new ArrayList<>();
    }

    public void setBannerData(ArrayList<Banner> data){
        if (data == null){
            return;
        }
        this.banners = data;
    }

    public void setOnBannerItemClickListener(OnBannerItemClickListener l){
        this.listener = l;
    }

    @Override
    public ActivityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.find_activity_view_pager_adapter_layout, parent, false);
        return new ActivityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ActivityViewHolder holder, int position) {
        if (banners == null || banners.size() == 0){
            return;
        }
        final Banner banner = banners.get(position%banners.size());
        if (banner == null){
            return;
        }
        LuoboImageLoader.getInstance().displayImage(APIClient.getBannerHost() + banner.getActivityScrollPic(), holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    listener.onItemClick(banner);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    class ActivityViewHolder extends RecyclerView.ViewHolder{
        private RoundedImageView imageView;
        public ActivityViewHolder(View itemView) {
            super(itemView);
            imageView = (RoundedImageView) itemView.findViewById(R.id.find_banner_adapter_image);
        }
    }
}
