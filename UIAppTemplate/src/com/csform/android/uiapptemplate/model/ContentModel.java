package com.csform.android.uiapptemplate.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ContentModel {

    private long mId;
    private String mImageURL;
    private String mTitle;
    private String mDescription;
    private String mCreatedOn;
    private String mUrl;
    private int mIconRes;
    private int mViewCount;
    private String mType;

    public ContentModel(long id, String imageURL, String title, String description, String createdOn, int viewCount, int iconRes, String url, String type){
        mId = id;
        mImageURL = imageURL;
        mTitle = title;
        mDescription = description;
        mCreatedOn = handleDateFormat(createdOn);
        mIconRes = iconRes;
        mViewCount = viewCount;
        mUrl = url;
        mType = type;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setNewsUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getImageURL() {
        return mImageURL;
    }

    public void setImageURL(String imageURL) {
        mImageURL = imageURL;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public int getIconRes() {
        return mIconRes;
    }

    public void setIconRes(int iconRes) {
        mIconRes = iconRes;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getCreatedOn() {
        return mCreatedOn;
    }

    public void setCreatedOn(String mCreatedOn) {
        this.mCreatedOn = handleDateFormat(mCreatedOn);
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public int getViewCount() {
        return mViewCount;
    }

    public void setViewCount(int mViewCount) {
        this.mViewCount = mViewCount;
    }

    private String handleDateFormat(String createdOn){
        return new SimpleDateFormat("dd MMM, yyyy").format(new Date(Long.valueOf(createdOn).longValue() * 1000L));
    }

    @Override
    public String toString() {
        return mTitle;
    }
}