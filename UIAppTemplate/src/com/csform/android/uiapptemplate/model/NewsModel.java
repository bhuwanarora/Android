package com.csform.android.uiapptemplate.model;

import com.csform.android.uiapptemplate.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NewsModel {

    private long mId;
    private String mImageURL;
    private String mTitle;
    private String mDescription;
    private String mCreatedOn;
    private int mIconRes;
    private int mViewCount;
    public static final String NoNewsFound = "No News Found";

    public NewsModel() {
    }

    public NewsModel(long id, String imageURL, String title, String description, String createdOn, int viewCount, int iconRes) {
        mId = id;
        mImageURL = imageURL;
        mTitle = title;
        mDescription = description;
        mCreatedOn = handleDateFormat(createdOn);
        mIconRes = iconRes;
        mViewCount = viewCount;
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
        if(mTitle.isEmpty()){
            mTitle = NoNewsFound;
        }
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

    public static NewsModel getEmptyNewsModel(){
        return new NewsModel(0L, "", NoNewsFound, null, null, 0, R.string.fontello_heart_empty);
    }

    private String handleDateFormat(String createdOn){
        return new SimpleDateFormat("dd MMM, yyyy").format(new Date(Long.valueOf(createdOn).longValue() * 1000L));
    }

    @Override
    public String toString() {
        return mTitle;
    }
}