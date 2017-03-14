package com.baluobo.home.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.baluobo.app.model.BaseModel;
import com.baluobo.app.model.BaseParcelableModel;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/23.
 */
public class Banner extends BaseParcelableModel{
    private int activityId;
    private String activityTitle;
    private String createTime;
    private String startTime;
    private String activityPic;  //图片下载路径
    private String activityPicForPc;
    private String activityUrl;
    private String activityContent;
    private String activityScrollPic;
    private String activityScrollPicPc;
    private long startTimeStamp;
    private long createTimeStamp; //app启动页开始显示时间(格式为时间戳)
    private long endTimeStamp; //app启动页结束显示时间(格式为时间戳)
    private String imgMd5; //图片的md5值

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public String getActivityTitle() {
        return activityTitle;
    }

    public void setActivityTitle(String activityTitle) {
        this.activityTitle = activityTitle;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getActivityPic() {
        return activityPic;
    }

    public void setActivityPic(String activityPic) {
        this.activityPic = activityPic;
    }

    public String getActivityPicForPc() {
        return activityPicForPc;
    }

    public void setActivityPicForPc(String activityPicForPc) {
        this.activityPicForPc = activityPicForPc;
    }

    public String getActivityUrl() {
        return activityUrl;
    }

    public void setActivityUrl(String activityUrl) {
        this.activityUrl = activityUrl;
    }

    public String getActivityContent() {
        return activityContent;
    }

    public void setActivityContent(String activityContent) {
        this.activityContent = activityContent;
    }

    public String getActivityScrollPic() {
        return activityScrollPic;
    }

    public void setActivityScrollPic(String activityScrollPic) {
        this.activityScrollPic = activityScrollPic;
    }

    public String getActivityScrollPicPc() {
        return activityScrollPicPc;
    }

    public void setActivityScrollPicPc(String activityScrollPicPc) {
        this.activityScrollPicPc = activityScrollPicPc;
    }
    public long getStartTimeStamp() {
        return startTimeStamp;
    }

    public void setStartTimeStamp(long startTimeStamp) {
        this.startTimeStamp = startTimeStamp;
    }

    public void setCreateTimeStamp(long createTimeStamp) {
        this.createTimeStamp = createTimeStamp;
    }

    public long getEndTimeStamp() {
        return endTimeStamp;
    }

    public void setEndTimeStamp(long endTimeStamp) {
        this.endTimeStamp = endTimeStamp;
    }

    public String getImgMd5() {
        return imgMd5;
    }

    public void setImgMd5(String imgMd5) {
        this.imgMd5 = imgMd5;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(activityId);
        dest.writeString(activityTitle);
        dest.writeString(createTime);
        dest.writeString(startTime);
        dest.writeString(activityPic);
        dest.writeString(activityPicForPc);
        dest.writeString(activityUrl);
        dest.writeString(activityContent);
        dest.writeString(activityScrollPic);
        dest.writeString(activityScrollPicPc);
    }

    public Banner(Parcel source){
        activityId = source.readInt();
        activityTitle = source.readString();
        createTime = source.readString();
        startTime = source.readString();
        activityPic = source.readString();
        activityPicForPc = source.readString();
        activityUrl = source.readString();
        activityContent = source.readString();
        activityScrollPic = source.readString();
        activityScrollPicPc = source.readString();
    }

    public static final Parcelable.Creator<BaseModel> CREATOR = new Parcelable.Creator<BaseModel>(){

        @Override
        public BaseModel createFromParcel(Parcel source) {
            return new Banner(source);
        }

        @Override
        public BaseModel[] newArray(int size) {
            return new BaseModel[size];
        }
    };
}
