package com.example.android.newsfeed;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Marcin on 2017-05-21.
 */

public class DownloadAsyncTask extends AsyncTask<NewsAdapter.NewsViewHolder, Void, NewsAdapter.NewsViewHolder> {

    private Bitmap bitmap;

    @Override
    protected NewsAdapter.NewsViewHolder doInBackground(NewsAdapter.NewsViewHolder... params) {
        //load image directly
        NewsAdapter.NewsViewHolder viewHolder = params[0];
        try {
            URL imageURL = new URL(viewHolder.imageURL);
            bitmap = BitmapFactory.decodeStream(imageURL.openStream());
        } catch (IOException e) {
            Log.e("error", "Downloading Image Failed");
            bitmap = null;
        }
        return viewHolder;
    }

    @Override
    protected void onPostExecute(NewsAdapter.NewsViewHolder result) {
        if (bitmap == null) {
            result.imageView.setImageResource(R.mipmap.ic_launcher);
        } else {
            result.imageView.setImageBitmap(bitmap);
        }
    }
}
