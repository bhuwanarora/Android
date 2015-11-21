package com.csform.android.uiapptemplate.model;

import com.csform.android.uiapptemplate.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NewsModel extends ContentModel{
    private String mTitle;
    public static final String NoNewsFound = "No News Found";

    public NewsModel(long id, String imageURL, String title, String description, String createdOn, int viewCount, int iconRes, String newsUrl) {
        super(id, imageURL, title, description, createdOn, viewCount, iconRes, newsUrl, "News");
        this.mTitle = title;
    }

    @Override
    public String getTitle() {
        if(mTitle.isEmpty()){
            mTitle = NoNewsFound;
        }
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public static NewsModel getEmptyNewsModel(){
        return new NewsModel(0L, "", NoNewsFound, null, null, 0, R.string.fontello_heart_empty, "");
    }

    private String handleDateFormat(String createdOn){
        return new SimpleDateFormat("dd MMM, yyyy").format(new Date(Long.valueOf(createdOn).longValue() * 1000L));
    }

    @Override
    public String toString() {
        return mTitle;
    }
}