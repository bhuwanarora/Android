package com.csform.android.uiapptemplate.model;

/**
 * Created by bhuwan on 19/11/15.
 */
public class SearchItemNewsModel extends SearchItemModel{
    private static final String mType = "News";

    public SearchItemNewsModel(String name, String imageUrl, long id){
        super(name, imageUrl, mType, id);
    }

}
