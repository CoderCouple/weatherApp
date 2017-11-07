package com.android.aaditya.weather;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.aaditya.weather.model.City;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

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

    public CityRecyclerViewAdapter(
            Context context, List<City> cityList,
            ItemClickListener itemClickListener) {
        this.context = context;
        this.cityList = cityList;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder viewHolder, int position) {
        City city = cityList.get(position);

        if( city.isCurrentCity())
            viewHolder.currentLocation.setVisibility(View.VISIBLE);

        viewHolder.cityName.setText(city.getName());
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


    public class SimpleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.current_location) ImageView currentLocation;
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
