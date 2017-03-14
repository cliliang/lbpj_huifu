package com.baluobo.user.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baluobo.R;
import com.baluobo.common.views.IconTextView;
import com.baluobo.user.model.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/15.
 */
public class MsgCenterAdapter extends BaseAdapter {
    private List<Message> data;
    private Context context;
    public MsgCenterAdapter(Context cnt){
        this.context = cnt;
        data = new ArrayList<>();
    }

    public void setData(List<Message> list){
        if (list != null){
            data.clear();
            this.data = list;
        }
    }

    public void setLoadData(List<Message> list){
        if (list != null && list.size() > 0){
            data.addAll(list);
        }
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.msg_center_list_item_layout, null);
        }
        TextView msgTitleView = (TextView) convertView.findViewById(R.id.msg_item_title);
        TextView msgTimeView = (TextView) convertView.findViewById(R.id.msg_item_time);
        TextView msgContentView = (TextView) convertView.findViewById(R.id.msg_item_content);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.msg_item_arrow);
        Message msg = data.get(position);
        if (msg != null){
            msgTitleView.setText(msg.getTitle());
            msgTimeView.setText(msg.getCreateTime());
            msgContentView.setText(msg.getMessDesc());

            if (msg.getSeenType()){
                msgTitleView.setTextColor(ContextCompat.getColor(context, R.color.color_92));
                msgTimeView.setTextColor(ContextCompat.getColor(context, R.color.color_92));
                msgContentView.setTextColor(ContextCompat.getColor(context, R.color.color_92));
            }else {
                msgTitleView.setTextColor(ContextCompat.getColor(context, R.color.black));
                msgTimeView.setTextColor(ContextCompat.getColor(context, R.color.black));
                msgContentView.setTextColor(ContextCompat.getColor(context, R.color.black));
            }

            int type = msg.getMessType();
            if (type == 1){
                imageView.setVisibility(View.INVISIBLE);
            }else {
                imageView.setVisibility(View.VISIBLE);
            }
        }
        return convertView;
    }
}
