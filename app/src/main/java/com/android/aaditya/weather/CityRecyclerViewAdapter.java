package com.android.aaditya.weather;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.aaditya.weather.model.City;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

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
    private List<String> cities;

    public CityRecyclerViewAdapter(
            Context context, List<String> cities,
            ItemClickListener itemClickListener) {
        this.context = context;
        this.cities = cities;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder viewHolder, int position) {
        String item = cities.get(position);
        viewHolder.textViewData.setText(item);
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

        @BindView(R.id.swipe)
        SwipeLayout swipeLayout;
        @BindView(R.id.text)
        TextView textViewData;
        @BindView(R.id.trash)
        ImageView imgDelete;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        @OnClick(R.id.text)
        public void details() {
            itemClickListener.onItemClicked(null);
        }

        @OnClick(R.id.trash)
        public void delete() {
            Log.d(getClass().getSimpleName(), "onItemSelected: " + textViewData.getText().toString());
            Toast.makeText(context, "onItemSelected: " + textViewData.getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public static interface ItemClickListener {

        void onItemClicked(City city);

    }

}
