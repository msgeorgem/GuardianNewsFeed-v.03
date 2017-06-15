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

public class DownloadAsyncTask extends AsyncTask<NewsAdapter.ViewHolder, Void, NewsAdapter.ViewHolder> {

    private Bitmap bitmap;

    @Override
    protected NewsAdapter.ViewHolder doInBackground(NewsAdapter.ViewHolder... params) {
        //load image directly
        NewsAdapter.ViewHolder viewHolder = params[0];
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
    protected void onPostExecute(NewsAdapter.ViewHolder result) {
        if (bitmap == null) {
            result.imageView.setImageResource(R.mipmap.ic_launcher);
        } else {
            result.imageView.setImageBitmap(bitmap);
        }
    }
}
