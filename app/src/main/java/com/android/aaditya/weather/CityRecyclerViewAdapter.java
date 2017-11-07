package com.android.aaditya.weather;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.aaditya.weather.model.City;
import com.android.aaditya.weather.util.WeatherPreferences;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * Created by Student on 10/23/17.
 */

public class CityRecyclerViewAdapter extends RecyclerSwipeAdapter<CityRecyclerViewAdapter.SimpleViewHolder> {

    private Context context;
    private ItemClickListener itemClickListener;
    private List<City> cityList;
    private WeatherPreferences preferences;

    public CityRecyclerViewAdapter(
            Context context, List<City> cityList,
            ItemClickListener itemClickListener) {
        this.context = context;
        this.cityList = cityList;
        this.itemClickListener = itemClickListener;
        preferences = new WeatherPreferences(context);
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder viewHolder, int position) {
        City city = cityList.get(position);

        String temp = getConvertedTemp(city.getCurrentWeather().getTemperature().getCurrentTemp());
        viewHolder.cityTemp.setText(temp);
        viewHolder.cityName.setText(city.getName());
        String time = DateTime.now().withZone(DateTimeZone.forID(city.getTimeZone())).toString("HH:mm:ss");
        viewHolder.time.setText(time);
        mItemManger.bindView(viewHolder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }


    private String getConvertedTemp(String temp) {
        String unit = preferences.readUnit();

        switch (unit) {
            case "C" : return (Float.parseFloat(temp) - 273) + "°C";

            case "F" : return (((Float.parseFloat(temp) - 273) * 9/5) + 32) + "°F";

            default: return "NA";
        }
    }

    public class SimpleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.time) TextView time;
        @BindView(R.id.cityTemp) TextView cityTemp;
        @BindView(R.id.cityName) TextView cityName;
        @BindView(R.id.swipe) SwipeLayout swipeLayout;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        @OnClick(R.id.info)
        public void details() {
            itemClickListener.onCityClicked(getAdapterPosition());
        }

        @OnClick(R.id.trash)
        public void delete() {
            Timber.d(cityList.get(getAdapterPosition()).getName());
            itemClickListener.onCityDelete(getAdapterPosition());
            swipeLayout.close();
            //Toast.makeText(context, "onItemSelected: " + cityName.getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public interface ItemClickListener {

        void onCityClicked(int position);

        void onCityDelete(int position);

    }

}
