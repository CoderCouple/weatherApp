package com.android.aaditya.weather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.aaditya.weather.Config;
import com.android.aaditya.weather.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by sunil28 on 10/23/17.
 */

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class TenDaySummaryAdapter extends RecyclerView.Adapter<TenDaySummaryAdapter.ViewHolder> {

    // Store a member variable for the contacts
    private List<TenDaySummary> mTenDaySummary;
    // Store the context for easy access
    private Context mContext;

    // Pass in the contact array into the constructor
    public TenDaySummaryAdapter(Context context, List<TenDaySummary> TenDaySummary) {
        mTenDaySummary = TenDaySummary;
        mContext = context;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;

    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View TenDaySummaryView = inflater.inflate(R.layout.item_summary, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(TenDaySummaryView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        // Get the data model based on position
        TenDaySummary TenDaySummary = mTenDaySummary.get(position);

        // Set item views based on your views and data model
        TextView dayNameTextView = viewHolder.dayNameTextView;
        dayNameTextView.setText(TenDaySummary.getDay());
        ImageView iconImageViewTenDay = viewHolder.iconImageViewTenDay;
        Picasso.with(getContext()).load(Config.WEATHER_ICON_URL+TenDaySummary.getIcon()).into(iconImageViewTenDay);
        //iconImageViewTenDay.setImageResource(TenDaySummary.getIcon());
        TextView minTempTextView = viewHolder.minTempTextView;
        minTempTextView.setText(String.valueOf(TenDaySummary.getMin()));
        TextView maxTempTextView = viewHolder.maxTempTextView;
        maxTempTextView.setText(String.valueOf(TenDaySummary.getMax()));


    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mTenDaySummary.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView dayNameTextView;
        public ImageView iconImageViewTenDay;
        public TextView minTempTextView;
        public TextView maxTempTextView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            dayNameTextView = (TextView) itemView.findViewById(R.id.dayNameTextView);
            iconImageViewTenDay = (ImageView) itemView.findViewById(R.id.iconImageViewTenDay);
            minTempTextView = (TextView) itemView.findViewById(R.id.minTempTextView);
            maxTempTextView = (TextView) itemView.findViewById(R.id.maxTempTextView);
        }



    }
}
