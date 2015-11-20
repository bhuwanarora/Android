package com.csform.android.uiapptemplate.model;

/**
 * Created by bhuwan on 19/11/15.
 */
public class SearchItemUserModel extends SearchItemModel{
    private static final String mType = "User";

    public SearchItemUserModel(String name, String imageUrl, long id){
        super(name, imageUrl, mType, id);
    }

}
