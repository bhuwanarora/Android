package com.csform.android.uiapptemplate.model;

/**
 * Created by bhuwan on 19/11/15.
 */
public class SearchItemModel {
    private String mName;
    private String mImageUrl;
    private String mType;
    private long mId;

    public SearchItemModel(String name, String imageUrl, String type, long id){
        mName = name;
        mImageUrl = imageUrl;
        mType = type;
        mId = id;
    }

    public String toString(){
        return mName;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getType() {
        return mType;
    }

    public void setType(String mType) {
        this.mType = mType;
    }

    public long getId() {
        return mId;
    }

    public void setId(long mId) {
        this.mId = mId;
    }

}
