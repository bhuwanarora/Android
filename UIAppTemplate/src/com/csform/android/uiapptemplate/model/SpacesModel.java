package com.csform.android.uiapptemplate.model;

public class SpacesModel {

    private long mId;
    private String mImageURL;
    private String mName;
    private int mIconRes;
    private int mViewCount;

    public SpacesModel(long id, String imageURL, String name, int ViewCount, int iconRes){
        mId = id;
        mImageURL = imageURL;
        mName = name;
        mIconRes = iconRes;
        mViewCount = ViewCount;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getImageURL() {
//        return mImageURL;
        return "https://oditty.me/assets/rooms.png";
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public int getViewCount() {
        return mViewCount;
    }

    public void setViewCount(int mViewCount) {
        this.mViewCount = mViewCount;
    }

    public void setImageURL(String imageURL) {
        mImageURL = imageURL;
    }

    public int getIconRes() {
        return mIconRes;
    }

    public void setIconRes(int iconRes) {
        mIconRes = iconRes;
    }

    @Override
    public String toString() {
        return mName;
    }
}