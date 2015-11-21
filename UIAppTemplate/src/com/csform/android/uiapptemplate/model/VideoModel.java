package com.csform.android.uiapptemplate.model;

/**
 * Created by bhuwan on 21/11/15.
 */
public class VideoModel extends ContentModel {

    public VideoModel(long id, String imageURL, String title, String description, String createdOn, int viewCount, int iconRes, String url){
        super(id, imageURL, title, description, createdOn, viewCount, iconRes, url, "Video");
    }
}
