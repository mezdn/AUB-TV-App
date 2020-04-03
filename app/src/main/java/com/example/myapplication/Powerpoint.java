package com.example.myapplication;

import java.io.Serializable;

public class Powerpoint implements Serializable, DisplayObject{

    private static final String TAG = Powerpoint.class.getSimpleName();
    static final long serialVersionUID = 568128516429865054L;

    private long mId;
    private String mTitle;
    private String mDescription;
    private String mCardUrl;
    private int mNumberOfPages;
    private String [] mImagesURLs;
    private String [] mDescriptionURLs;

    public Powerpoint(){}
    public Powerpoint(String mTitle, String mDescription, String [] mImagesURLs) {
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mCardUrl = mImagesURLs[0];
        this.mNumberOfPages = mImagesURLs.length;
        this.mImagesURLs = mImagesURLs;
//        this.mDescriptionURLs = mDescriptionsURLs;
    }
    public static String getTAG() {
        return TAG;
    }
    public long getId() {
        return this.mId;
    }
    public String getTitle() {
        return this.mTitle;
    }
    public String getDescription() {
        return this.mDescription;
    }
    public String getCardUrl() {return this.mCardUrl;}
    public int getNumberOfPages() {
        return mNumberOfPages;
    }
    public String[] getImagesURLs() {
        return mImagesURLs;
    }
    public String[] getDescriptionURLs() {
        return mDescriptionURLs;
    }
    public void setId(Long mId) {
        this.mId = mId;
    }
    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }
    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }
    public void setCardUrl(String mCardUrl) {
        this.mCardUrl = mCardUrl;
    }
    public void setNumberOfPages(int mNumberOfPages) {
        this.mNumberOfPages = mNumberOfPages;
    }
    public void setImagesURLs(String[] mImagesURLs) {
        this.mImagesURLs = mImagesURLs;
    }
    public void setDescriptionURLs(String[] mDescriptionURLs) {
        this.mDescriptionURLs = mDescriptionURLs;
    }

    @Override
    public String toString() {
        return "Powerpoint{" +
                "type = powerpoint" +
                ", id=" + mId +
                ", title='" + mTitle + '\'' +
                ", description='" + mDescription +
                ", Category='" + mDescription +
                ", cardUrl='" + CardUrl + '\'' +
                '}';
    }
}

