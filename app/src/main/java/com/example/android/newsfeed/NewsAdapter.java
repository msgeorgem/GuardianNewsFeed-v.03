package com.example.android.newsfeed;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import static com.example.android.newsfeed.R.id.date;

/**
 * Created by Marcin on 2017-03-27.
 */

public class NewsAdapter extends ArrayAdapter<SingleNews> {


    public NewsAdapter(Activity context, List<SingleNews> listNews) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0b0, listNews);
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position    The position in the list of data that should be displayed in the
     *                    list item view.
     * @param convertView The recycled view to populate.
     * @param parent      The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Get the {@link News} object located at this position in the list
        SingleNews currentNews = getItem(position);

        viewHolder.imageURL = currentNews.getThumbnail();
        new DownloadAsyncTask().execute(viewHolder);

        viewHolder.titleTextView.setText(currentNews.getTitle());
        viewHolder.shortTextView.setText(currentNews.getShorttext());

        String formattedDate = (currentNews.getDateTime()).substring(0, 10);
        viewHolder.dateView.setText(formattedDate);
        viewHolder.typeTextView.setText(currentNews.getSectionName());

        return convertView;
    }

    class ViewHolder {
        public String imageURL;
        public ImageView imageView;
        private TextView titleTextView;
        private TextView shortTextView;
        private TextView dateView;
        private TextView typeTextView;


        public ViewHolder(@NonNull View view) {
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
