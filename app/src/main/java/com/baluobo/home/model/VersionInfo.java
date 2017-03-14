package com.baluobo.home.model;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/11/30.
 */
public class VersionInfo {
    private String android_version_file;
    private int android_version_num;
    private String android_version_hash;
    private String android_version_info;
    private String android_version_upda;

    public String getAndroid_version_file() {
        return android_version_file;
    }

    public void setAndroid_version_file(String android_version_file) {
        this.android_version_file = android_version_file;
    }

    public int getAndroid_version_num() {
        return android_version_num;
    }

    public void setAndroid_version_num(int android_version_num) {
        this.android_version_num = android_version_num;
    }

    public String getAndroid_version_hash() {
        return android_version_hash;
    }

    public void setAndroid_version_hash(String android_version_hash) {
        this.android_version_hash = android_version_hash;
    }

    public String getAndroid_version_info() {
        return android_version_info;
    }

    public void setAndroid_version_info(String android_version_info) {
        this.android_version_info = android_version_info;
    }

    public boolean getAndroid_version_upda() {
        return "1".equals(android_version_upda);
    }

    public void setAndroid_version_upda(String android_version_upda) {
        this.android_version_upda = android_version_upda;
    }
}
