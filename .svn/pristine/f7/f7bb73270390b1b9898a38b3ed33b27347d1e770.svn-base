package com.baluobo.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baluobo.R;
import com.baluobo.home.model.Declaration;

import java.util.ArrayList;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/19.
 */
public class DeclarationAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Declaration> declarations;
    public DeclarationAdapter(Context cnt){
        this.context = cnt;
        declarations = new ArrayList<>();
    }

    public void setData(ArrayList<Declaration> list){
        if (list != null){
            this.declarations = list;
        }
    }

    @Override
    public int getCount() {
        return declarations.size();
    }

    @Override
    public Object getItem(int position) {
        return declarations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.declaration_item_layout, null);
        }

        TextView titleView = (TextView) convertView.findViewById(R.id.declaration_item_title);
        TextView timeView = (TextView) convertView.findViewById(R.id.declaration_item_time);
        TextView contentView = (TextView) convertView.findViewById(R.id.declaration_item_content);
        Declaration declaration = declarations.get(position);
        if (declarations != null){
            titleView.setText(declaration.getNewsTitle());
            timeView.setText(declaration.getCreateTime());
            contentView.setText(declaration.getNewsContent());
        }
        return convertView;
    }
}
