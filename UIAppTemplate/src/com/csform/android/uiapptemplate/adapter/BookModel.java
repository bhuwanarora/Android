package com.csform.android.uiapptemplate.adapter;

import android.text.format.Time;

import com.csform.android.uiapptemplate.model.ContentModel;

import java.util.Date;

/**
 * Created by bhuwan on 21/11/15.
 */
public class BookModel extends ContentModel {
    private String mAuthorName;

    public BookModel(long id, String imageURL, String title, String description, int viewCount, int iconRes, String authorName) {
        super(id, imageURL, title, description, String.valueOf((new Date().getTime()/1000)), viewCount, iconRes, "", "Book");
        this.mAuthorName = authorName;
    }

    public String getmAuthorName() {
        return mAuthorName;
    }

    public void setmAuthorName(String mAuthorName) {
        this.mAuthorName = mAuthorName;
    }
}
