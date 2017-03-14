package com.baluobo.app.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/10.
 */
public class BaseParcelableModel extends BaseModel implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public static final Parcelable.Creator<BaseModel> CREATOR = new Parcelable.Creator<BaseModel>(){

        @Override
        public BaseModel createFromParcel(Parcel source) {
            return null;
        }

        @Override
        public BaseModel[] newArray(int size) {
            return new BaseModel[size];
        }
    };
}
