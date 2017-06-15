package com.example.android.newsfeed;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;


/**
 * Loads a list of news by using an AsyncTask to perform the
 * network request to the given URL.
 */
public class NewsLoader extends AsyncTaskLoader<ArrayList<SingleNews>> {

    /** Tag for log messages */
    private static final String LOG_TAG = NewsLoader.class.getName();

    /** Query URL */
    private String mUrl;

    /**
     * Constructs a new {@link NewsLoader}.
     *
     * @param context of the activity
     * @param url to load data from
     */
    public NewsLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG,"onStartLoading");
        forceLoad();
        }

    /**
     * This is on a background thread.
     */
    @Override
    public ArrayList<SingleNews> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        Log.i(LOG_TAG,"loadInBackground");
        // Perform the network request, parse the response, and extract a list of news.
        ArrayList<SingleNews> singleNews = QueryUtils.fetchNewsData(mUrl);
        return singleNews;
    }
}
