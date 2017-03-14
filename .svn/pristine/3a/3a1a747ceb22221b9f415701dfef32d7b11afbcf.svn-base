package com.baluobo.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baluobo.R;
import com.makeramen.roundedimageview.RoundedImageView;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/8/5.
 */
public class FindMainAdapter extends RecyclerView.Adapter<FindMainAdapter.MainViewHolder>{
    private Context mContext;
    public FindMainAdapter(Context cnt){
        this.mContext = cnt;
    }
    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.find_main_recycle_view_item_layout, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class MainViewHolder extends RecyclerView.ViewHolder{
        private RoundedImageView imageView;
        private TextView titleView, timeView, contentView;
        public MainViewHolder(View itemView) {
            super(itemView);
            imageView = (RoundedImageView) itemView.findViewById(R.id.find_main_adapter_image_view);
            titleView = (TextView) itemView.findViewById(R.id.find_main_adapter_title_view);
            timeView = (TextView) itemView.findViewById(R.id.find_main_adapter_time_view);
            contentView = (TextView) itemView.findViewById(R.id.find_main_adapter_container_view);
        }
    }
}
