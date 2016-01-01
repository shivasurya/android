package com.ivb.udacity.modal;

/**
 * Created by S.Shivasurya on 1/1/2016 - androidStudio.
 */
public class movieGeneralModal {
    String mTitle;
    String mThumbnail;
    String mVote;

    public movieGeneralModal(String mTitle, String mThumbnail, String mVote) {
        this.mThumbnail = mThumbnail;
        this.mTitle = mTitle;
        this.mVote = mVote;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public String getThumbnail() {
        String url = "http://image.tmdb.org/t/p/w185/" + this.mThumbnail;
        return url;
    }

    public String getmVote() {
        return this.mVote;
    }
}
