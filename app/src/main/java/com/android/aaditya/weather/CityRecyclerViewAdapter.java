package com.android.aaditya.weather;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.aaditya.weather.model.City;
import com.android.aaditya.weather.util.WeatherPreferences;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Student on 10/23/17.
 */

public class CityRecyclerViewAdapter extends RecyclerSwipeAdapter<CityRecyclerViewAdapter.SimpleViewHolder> {

    private Context context;
    private ItemClickListener itemClickListener;
    private List<City> cities;
    private WeatherPreferences weatherPreferences;

    public CityRecyclerViewAdapter(
            Context context, List<City> cities,
            ItemClickListener itemClickListener) {
        this.context = context;
        this.cities = cities;
        this.itemClickListener = itemClickListener;

        weatherPreferences = new WeatherPreferences(context);
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder viewHolder, int position) {
        City city = cities.get(position);
        viewHolder.temp.setText(city.getTemperature() + viewHolder.temp.getText() + weatherPreferences.readUnit());

        if( city.isCurrentCity())
            viewHolder.currentLocation.setVisibility(View.VISIBLE);

        viewHolder.cityName.setText(city.getName());

        DateTimeFormatter fmt = DateTimeFormat.forPattern("HH:mm aa");
        viewHolder.date.setText(fmt.print(city.getDateTime()));
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }


    public class SimpleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.date) TextView date;
        @BindView(R.id.current_location) ImageView currentLocation;
        @BindView(R.id.cityName) TextView cityName;
        @BindView(R.id.temp) TextView temp;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        @OnClick(R.id.info)
        public void details() {
            itemClickListener.onItemClicked(null);
        }

        @OnClick(R.id.trash)
        public void delete() {
            Log.d(getClass().getSimpleName(), "onItemSelected: " + cityName.getText().toString());
            Toast.makeText(context, "onItemSelected: " + cityName.getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public static interface ItemClickListener {

        void onItemClicked(City city);

    }

}
