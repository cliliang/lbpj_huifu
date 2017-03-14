package com.baluobo.find.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/9/21.
 */
public class SignRecord {
    private int srId;
    private int uId;
    private int scReId;
    private String createTime;

    public int getSrId() {
        return srId;
    }

    public void setSrId(int srId) {
        this.srId = srId;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public int getScReId() {
        return scReId;
    }

    public void setScReId(int scReId) {
        this.scReId = scReId;
    }

    public Calendar getCreateTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        try {
            Date date = format.parse(createTime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
