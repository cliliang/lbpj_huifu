package com.baluobo.home.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.baluobo.app.model.BaseModel;
import com.baluobo.app.model.BaseParcelableModel;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/24.
 */
public class Declaration extends BaseParcelableModel{
    private int nId;
    private String newsTitle;
    private String newsIcon;
    private String createTime;
    private String newFrom;
    private String newsGuide;
    private String newsContent;
    private int contentType;

    public Declaration() {
    }

    public int getnId() {
        return nId;
    }

    public void setnId(int nId) {
        this.nId = nId;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsIcon() {
        return newsIcon;
    }

    public void setNewsIcon(String newsIcon) {
        this.newsIcon = newsIcon;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getNewFrom() {
        return newFrom;
    }

    public void setNewFrom(String newFrom) {
        this.newFrom = newFrom;
    }

    public String getNewsGuide() {
        return newsGuide;
    }

    public void setNewsGuide(String newsGuide) {
        this.newsGuide = newsGuide;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(nId);
        dest.writeString(newsTitle);
        dest.writeString(newsIcon);
        dest.writeString(createTime);
        dest.writeString(newFrom);
        dest.writeString(newsGuide);
        dest.writeString(newsContent);
        dest.writeInt(contentType);
    }

    public Declaration(Parcel source){
        nId = source.readInt();
        newsTitle = source.readString();
        newsIcon = source.readString();
        createTime = source.readString();
        newFrom = source.readString();
        newsGuide = source.readString();
        newsContent = source.readString();
        contentType = source.readInt();
    }

    public static final Parcelable.Creator<Declaration> CREATOR = new Parcelable.Creator<Declaration>(){

        @Override
        public Declaration createFromParcel(Parcel source) {
            return new Declaration(source);
        }

        @Override
        public Declaration[] newArray(int size) {
            return new Declaration[size];
        }
    };
}
