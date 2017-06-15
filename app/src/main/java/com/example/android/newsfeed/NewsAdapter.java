package com.example.android.newsfeed;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.android.newsfeed.R.id.date;

/**
 * Created by Marcin on 2017-03-27.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    NewsActivity mContext;
    private NewsActivity activity = new NewsActivity();
    private ArrayList<SingleNews> mListAdapter;


    public NewsAdapter(NewsActivity context, ArrayList<SingleNews> listNews) {
        mListAdapter = listNews;
        mContext = context;
    }

    @Override
    public int getItemCount() {
        return mListAdapter.size();
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        NewsViewHolder vh = new NewsViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(final NewsViewHolder viewHolder, int position) {
        // Get the {@link News} object located at this position in the list
        final SingleNews currentNews = mListAdapter.get(position);

        viewHolder.imageURL = currentNews.getThumbnail();
//        AsyncTask.execute((Runnable) viewHolder);

        viewHolder.titleTextView.setText(currentNews.getTitle());
        viewHolder.shortTextView.setText(currentNews.getShorttext());

        String formattedDate = (currentNews.getDateTime()).substring(0, 10);
        viewHolder.dateView.setText(formattedDate);
        viewHolder.typeTextView.setText(currentNews.getSectionName());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = viewHolder.itemView.getContext();
                //Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri newsUri = Uri.parse(currentNews.getUrl());
                //Create a new intent to view the news URI
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, newsUri);
                context.startActivity(browserIntent);
            }
        });

    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        public String imageURL;
        public ImageView imageView;
        private TextView titleTextView;
        private TextView shortTextView;
        private TextView dateView;
        private TextView typeTextView;


        public NewsViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view
                    .findViewById(R.id.thumbnail);
            this.titleTextView = (TextView) view
                    .findViewById(R.id.headline);
            this.shortTextView = (TextView) view
                    .findViewById(R.id.shorttext);
            this.dateView = (TextView) view
                    .findViewById(date);
            this.typeTextView = (TextView) view
                    .findViewById(R.id.SectionName);

        }
    }
}
