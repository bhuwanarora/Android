package com.csform.android.uiapptemplate.model;

/**
 * Created by bhuwan on 21/11/15.
 */
public class BookModel extends ContentModel {

    private String mImageURL;
    private String mIsbn;
    private String mPageCount;
    private static final String TYPE = "Book";
    private String mAuthorName;
    private String mPublishedYear;
    private String mRating;


    public BookModel(long id, String imageURL, String title, String authorName, String publishedYear, int duration, int iconRes, String url, String isbn, String pageCount, String rating){
        super(id, imageURL, title, authorName, publishedYear, duration, iconRes, url, TYPE);
        this.mIsbn = isbn;
        this.mRating = rating;
        this.mAuthorName = authorName;
        this.mPageCount = pageCount;
    }

    public String getAuthorName() {
        return mAuthorName;
    }

    public void setAuthorName(String mAuthorName) {
        this.mAuthorName = mAuthorName;
    }

    public String getPublishedYear() {
        return mPublishedYear;
    }

    public void setPublishedYear(String mPublishedYear) {
        this.mPublishedYear = mPublishedYear;
    }

    public String getRating() {
        return mRating;
    }

    public void setRating(String mRating) {
        this.mRating = mRating;
    }

    public String getImageURL() {
        return mImageURL;
//        return "https://rooms.oditty.me/assets/defpicbook.png";
    }

    public void setImageURL(String mImageURL) {
        this.mImageURL = mImageURL;
//        this.mImageURL = "https://rooms.oditty.me/assets/defpicbook.png";
    }

    public String getIsbn() {
        return mIsbn;
    }

    public void setIsbn(String mIsbn) {
        this.mIsbn = mIsbn;
    }

    public String getPageCount() {
        return mPageCount;
    }

    public void setPageCount(String mPageNumber) {
        this.mPageCount = mPageNumber;
    }
}
