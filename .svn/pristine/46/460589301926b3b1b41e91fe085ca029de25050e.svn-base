package com.baluobo.find.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.baluobo.R;
import com.baluobo.find.views.SignDayView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/9/23.
 */
public class CalendarGridAdapter extends BaseAdapter {
    private List<Calendar> list;
    private Context mContext;
    private int blankDay;
    public CalendarGridAdapter(Context cnt){
        this.mContext = cnt;
        list = new ArrayList<>();
    }

    public void setData(List<Calendar> calendars, int blank){
        this.list = calendars;
        this.blankDay = blank;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.sign_in_calendar_item_layout, null);
        }
        SignDayView dayView = (SignDayView) convertView;
        Calendar calendar = list.get(position);
        if (position < blankDay){
            dayView.setSigned(false);
            dayView.setText("");
        }else {
            if (calendar != null){
                dayView.setSigned(true);
                dayView.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
            }else {
                dayView.setSigned(false);
                dayView.setText(String.valueOf(position - blankDay + 1));
            }
        }
        return convertView;
    }
}
