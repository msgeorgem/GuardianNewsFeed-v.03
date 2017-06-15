package com.example.android.newsfeed;

/**
 * Created by Marcin on 2017-02-27.
 */

public class SingleNews {

    private final String mThumbnail;
    private String mTitle;
    private String mShorttext;
    private String mDateTime;
    private String mSectionName;
    private String mUrl;

    /**
     * Create a new SingleNews object.
     * @param title     is the title ot the news
     * @param shorttext is the shorttext of the news
     * @param dateTime  is the publish date of the news
     * @param sectionname  is the sectionname of the news
     * @param url       is the url of the website about specific news
     * @param thumbnail is the url of the thumbnail about specific news
     */

    public SingleNews(String title, String shorttext, String dateTime, String sectionname,
                      String url, String thumbnail) {
        mThumbnail = thumbnail;
        mTitle = title;
        mShorttext = shorttext;
        mDateTime = dateTime;
        mSectionName = sectionname;
        mUrl = url;
    }
    /**
     * Get the thumbnail of the article.
     */
    public String getThumbnail() {
        return mThumbnail;
    }
    /**
     * Get the title of the article.
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Get the shorttext of the article.
     */
    public String getShorttext() {
        return mShorttext;
    }

    /**
     * Get the date of the article.
     */
    public String getDateTime(){
        return mDateTime;
    }

    /**
     * Get the date of the article.
     */
    public String getSectionName(){
        return mSectionName;
    }
    /**
     * Get the url of the article.
     */
    public String getUrl() {
        return mUrl;
    }

}
