package com.csform.android.uiapptemplate.model;

/**
 * Created by bhuwan on 19/11/15.
 */
public class SearchItemBookModel extends SearchItemModel{
    private static final String mType = "Book";
    private String mIsbn;
    private String mAuthorName;
    private long mAuthorId;


    public SearchItemBookModel(String name, String imageUrl, long id, String isbn, String authorName, long authorId){
        super(name, imageUrl, mType, id);
        mIsbn = isbn;
        mAuthorName = authorName;
        mAuthorId = authorId;
    }

    public String getImageUrl(){
        return "https://oditty.me/assets/defpicbook.png";
    }

    public String getIsbn() {
        return mIsbn;
    }

    public void setIsbn(String mIsbn) {
        this.mIsbn = mIsbn;
    }

    public String getAuthorName() {
        return mAuthorName;
    }

    public void setAuthorName(String mAuthorName) {
        this.mAuthorName = mAuthorName;
    }

    public long getmAuthorId() {
        return mAuthorId;
    }

    public void setmAuthorId(long mAuthorId) {
        this.mAuthorId = mAuthorId;
    }


}
