package com.csform.android.uiapptemplate.model;

/**
 * Created by bhuwan on 19/11/15.
 */
public class SearchItemAuthorModel extends SearchItemModel{
    private static final String mType = "Author";

    public SearchItemAuthorModel(String name, String imageUrl, long id){
        super(name, imageUrl, mType, id);
    }

    public String getImageUrl(){
        return "https://oditty.me/assets/author_profile.jpg";
    }
}
