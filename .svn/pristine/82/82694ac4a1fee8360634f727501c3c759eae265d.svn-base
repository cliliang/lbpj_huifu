package com.baluobo.find.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baluobo.R;
import com.baluobo.find.adapter.CalendarGridAdapter;
import com.baluobo.find.model.SignRecord;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/9/21.
 */
public class SignCalendarView extends LinearLayout implements View.OnClickListener{
    private HashMap<String, List<Calendar>> calendarMap;
    private Calendar showCalendar;
    private TextView dayTitle;
    private String startDay;
    private ImageView forwardView;
    private Context mContext;
    private OnSelecteCalendarListener listener;
    private CalendarGridAdapter adapter;
    public SignCalendarView(Context context) {
        super(context);
        init(context);
    }

    public SignCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SignCalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context cnt){
        this.mContext = cnt;
        calendarMap = new HashMap<>();
        LayoutInflater.from(cnt).inflate(R.layout.sign_calendar_view_layout, this);
        GridView gridView = (GridView) findViewById(R.id.sign_in_calendar_content_view);
        adapter = new CalendarGridAdapter(cnt);
        gridView.setAdapter(adapter);
        findViewById(R.id.roll_back_one_month).setOnClickListener(this);
        forwardView = (ImageView) findViewById(R.id.roll_forward_one_month);
        forwardView.setOnClickListener(this);
        dayTitle = (TextView) findViewById(R.id.calendar_now_date);

        showCalendar = Calendar.getInstance();
        int year = showCalendar.get(Calendar.YEAR);
        int month = showCalendar.get(Calendar.MONTH);
        showCalendar.clear();
        showCalendar.set(year, month, 1, 0, 0, 0);
        startDay = String.format(Locale.US, mContext.getString(R.string.sign_in_calendar_title), showCalendar.get(Calendar.YEAR), showCalendar.get(Calendar.MONTH) + 1);
        dayTitle.setText(startDay);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.roll_back_one_month:
                forwardView.setImageResource(R.drawable.calendar_right);
                Calendar backCalendar = (Calendar) showCalendar.clone();
                backCalendar.add(Calendar.MONTH, -1);
                String dayBack = String.format(Locale.US, "%tF", backCalendar.getTime());
                if (calendarMap.containsKey(dayBack)){
                    setCalendarData(String.format(Locale.US, "%tF", backCalendar.getTime()));
                }else {
                    if (listener != null){
                        listener.onBackCalendarSelected(backCalendar);
                    }
                }
                break;
            case R.id.roll_forward_one_month:
                Calendar forwardCalendar = (Calendar) showCalendar.clone();
                forwardCalendar.add(Calendar.MONTH, 1);
                String dayForward = String.format(Locale.US, "%tF", forwardCalendar.getTime());
//
                if (calendarMap.containsKey(dayForward)){
                    setCalendarData(String.format(Locale.US, "%tF", forwardCalendar.getTime()));
                    Calendar calendar = (Calendar) forwardCalendar.clone();
                    calendar.add(Calendar.MONTH, 1);
                    String forward = String.format(Locale.US, "%tF", calendar.getTime());
                    if (!calendarMap.containsKey(forward)){
                        forwardView.setImageResource(R.drawable.calendar_right_arrow);
                    }else {
                        forwardView.setImageResource(R.drawable.calendar_right);
                    }
                }
                break;
        }
    }

    public void setData(Calendar loadCalendar, ArrayList<SignRecord> records){
        if (loadCalendar == null){
            return;
        }
        if (records == null){
            return;
        }

        Calendar calendarOneStart, calendarOneEnd;
        Calendar calendarTwoStart, calendarTwoEnd;
        Calendar calendarThreeStart, calendarThreeEnd;

        int year = loadCalendar.get(Calendar.YEAR);
        int month = loadCalendar.get(Calendar.MONTH);
        loadCalendar.clear();
        loadCalendar.set(year, month, 1, 0, 0, 0);
        calendarOneStart = (Calendar) loadCalendar.clone();

        loadCalendar.add(Calendar.MONTH, 1);
        loadCalendar.set(Calendar.DAY_OF_MONTH, 0);
        calendarOneEnd = (Calendar) loadCalendar.clone();
        calendarOneEnd.set(Calendar.HOUR_OF_DAY, 23);
        calendarOneEnd.set(Calendar.MINUTE, 59);
        calendarOneEnd.set(Calendar.SECOND, 59);
        calendarOneEnd.set(Calendar.MILLISECOND, 999);

        String typeOne = String.format(Locale.CHINA, "%tF", calendarOneStart.getTime());
        List<Calendar> listOne = new ArrayList<>();
        for (SignRecord signRecord : records){
            if (signRecord != null){
                Calendar calendarOne = signRecord.getCreateTime();
                if (calendarOne.getTimeInMillis() >= calendarOneStart.getTimeInMillis() && calendarOne.getTimeInMillis() < calendarOneEnd.getTimeInMillis()){
                    listOne.add(calendarOne);
                }
            }
        }
        calendarMap.put(typeOne, listOne);


        loadCalendar.add(Calendar.MONTH, -1);
        loadCalendar.set(Calendar.DAY_OF_MONTH, 1);
        calendarTwoStart = (Calendar) loadCalendar.clone();

        loadCalendar.add(Calendar.MONTH, 1);
        loadCalendar.set(Calendar.DAY_OF_MONTH, 0);
        calendarTwoEnd = (Calendar) loadCalendar.clone();
        calendarTwoEnd.set(Calendar.HOUR_OF_DAY, 23);
        calendarTwoEnd.set(Calendar.MINUTE, 59);
        calendarTwoEnd.set(Calendar.SECOND, 59);
        calendarTwoEnd.set(Calendar.MILLISECOND, 999);

        String typeTwo = String.format(Locale.CHINA, "%tF", calendarTwoStart.getTime());
        List<Calendar> listTwo = new ArrayList<>();
        for (SignRecord signRecord : records){
            if (signRecord != null){
                Calendar calendarTwo = signRecord.getCreateTime();
                if (calendarTwo.getTimeInMillis() >= calendarTwoStart.getTimeInMillis() && calendarTwo.getTimeInMillis() < calendarTwoEnd.getTimeInMillis()){
                    listTwo.add(calendarTwo);
                }
            }
        }
        calendarMap.put(typeTwo, listTwo);

        loadCalendar.add(Calendar.MONTH, -1);
        loadCalendar.set(Calendar.DAY_OF_MONTH, 1);
        calendarThreeStart = (Calendar) loadCalendar.clone();

        loadCalendar.add(Calendar.MONTH, 1);
        loadCalendar.set(Calendar.DAY_OF_MONTH, 0);
        calendarThreeEnd = (Calendar) loadCalendar.clone();
        calendarThreeEnd.set(Calendar.HOUR_OF_DAY, 23);
        calendarThreeEnd.set(Calendar.MINUTE, 59);
        calendarThreeEnd.set(Calendar.SECOND, 59);
        calendarThreeEnd.set(Calendar.MILLISECOND, 999);

        String typeThree = String.format(Locale.CHINA, "%tF", calendarThreeStart.getTime());
        List<Calendar> listThree = new ArrayList<>();
        for (SignRecord signRecord : records){
            if (signRecord != null){
                Calendar calendarThree = signRecord.getCreateTime();
                if (calendarThree.getTimeInMillis() >= calendarThreeStart.getTimeInMillis() && calendarThree.getTimeInMillis() < calendarThreeEnd.getTimeInMillis()){
                    listThree.add(calendarThree);
                }
            }
        }
        calendarMap.put(typeThree, listThree);

        setCalendarData(typeOne);
    }

    private void setCalendarData(String dateString){
        Calendar calendar = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        try {
            Date date = format.parse(dateString);
            calendar = Calendar.getInstance();
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (calendar == null){
            return;
        }

        this.showCalendar = calendar;
        dayTitle.setText(String.format(Locale.US, mContext.getString(R.string.sign_in_calendar_title), showCalendar.get(Calendar.YEAR), showCalendar.get(Calendar.MONTH) + 1));

        //月共有多少天
        int dayCount = calendar.getActualMaximum(Calendar.DATE);
        List<Calendar> calendars = calendarMap.get(dateString);
        List<Calendar> monthCalendar = new ArrayList<>();
        for (int i = 0; i< dayCount; i++){
            monthCalendar.add(null);

        }
        for (Calendar cal : calendars){
            int dayIndex = cal.get(Calendar.DAY_OF_MONTH);
            monthCalendar.set(dayIndex - 1, cal);
        }

        //月第一天星期几
        int monthFirstDay = calendar.get(Calendar.DAY_OF_WEEK);
        int max;
        if (monthFirstDay == 1){
            max = 6;
        }else {
            max = monthFirstDay - 2;
        }
        for (int i = 0; i < max; i++){
            monthCalendar.add(0, null);
        }
        adapter.setData(monthCalendar, max);
        adapter.notifyDataSetChanged();
    }

    public void setOnSelectedBackCalendar(OnSelecteCalendarListener l){
        this.listener = l;
    }

    public interface OnSelecteCalendarListener{
        void onBackCalendarSelected(Calendar day);
    }
}
