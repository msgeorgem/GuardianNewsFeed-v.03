/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.newsfeed;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity implements LoaderCallbacks<ArrayList<SingleNews>> {

    public static final String LOG_TAG = NewsActivity.class.getName();

    /**
     * Constant value for the news loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int NEWS_LOADER_ID = 1;
    private static final String GUARDIAN_REQUEST_URL =

            "https://content.guardianapis.com/search?" +
                    "&show-fields=headline,thumbnail,trailText,short-url,lastModified" +
                    "&show-tags=type";
    private ListView newsListView;
    /**
     * Adapter for the list of news
     */
    private NewsAdapter mAdapter;
    /**
     * TextView that is displayed when the list is empty
     */
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_fed_activity);

        Log.i(LOG_TAG, "initLoader");

        // Find a reference to the {@link ListView} in the layout
        newsListView = (ListView) findViewById(R.id.list);

        // Create a new adapter that takes an empty list of earthquakes as input
        mAdapter = new NewsAdapter(this, new ArrayList<SingleNews>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        newsListView.setAdapter(mAdapter);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        newsListView.setEmptyView(mEmptyStateTextView);

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Get the {@link Earthquake} object at the given position the user clicked on
                SingleNews singleNews = mAdapter.getItem(position);
                //Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri newsUri = Uri.parse(singleNews.getUrl());
                //Create a new intent to view the news URI
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, newsUri);
                //Send the intent to launch a new activity
                startActivity(browserIntent);
            }
        });
        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(NEWS_LOADER_ID, null, this).forceLoad();
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            // Set empty state text to display "No internet connection"
            mEmptyStateTextView.setText(R.string.no_internet);
        }


    }

    @Override
    public Loader<ArrayList<SingleNews>> onCreateLoader(int i, Bundle bundle) {

        Log.i(LOG_TAG, "onCreateLoader");
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String query = sharedPrefs.getString(
                getString(R.string.settings_query_key),
                getString(R.string.settings_query_default)
        );

        String tag = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );

        Uri baseUri = Uri.parse(GUARDIAN_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("api-key", "6a61772d-2298-4946-8002-86aee6d4caca");
        uriBuilder.appendQueryParameter("format", "json");
        uriBuilder.appendQueryParameter("pageSize", "20");
        uriBuilder.appendQueryParameter("from-date", "2015-01-01");
        uriBuilder.appendQueryParameter("q", query);
        uriBuilder.appendQueryParameter("tag", tag);
        uriBuilder.appendQueryParameter("order-by", "newest");

        return new NewsLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<SingleNews>> loader, ArrayList<SingleNews> news) {
        // Hide loading indicator because the data has been loaded
        Log.i(LOG_TAG, "onLoadFinished");
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);
        // Set empty state text to display "No internet connection"
        mEmptyStateTextView.setText(R.string.no_news);

        mAdapter.clear();

        // If there is a valid list of {@link News}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (news != null && !news.isEmpty()) {
            mAdapter.addAll(news);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<SingleNews>> loader) {
        // Loader reset, so we can clear out our existing data.
        Log.i(LOG_TAG, "onLoaderReset");
        mAdapter.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
