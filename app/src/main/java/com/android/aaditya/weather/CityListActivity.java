package com.android.aaditya.weather;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.aaditya.weather.base.BaseActivity;
import com.android.aaditya.weather.model.City;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

public class CityListActivity extends BaseActivity implements CityRecyclerViewAdapter.ItemClickListener{

    private List<City> cities = new ArrayList<>();
    private CityRecyclerViewAdapter adapter;

    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        cities.add(new City("San jose",true));
        cities.add(new City("Fremont",false));
        cities.add(new City("Mumbai",false));
        cities.add(new City("Fremont",false));
        cities.add(new City("Mumbai",false));
        cities.add(new City("Fremont",false));
        cities.add(new City("Mumbai",false));
        cities.add(new City("Fremont",false));
        cities.add(new City("Mumbai",false));
        cities.add(new City("Fremont",false));
        cities.add(new City("Mumbai",false));
        cities.add(new City("Fremont",false));
        cities.add(new City("Mumbai",false));
        cities.add(new City("Fremont",false));
        cities.add(new City("Mumbai",false));

        adapter = new CityRecyclerViewAdapter(this, cities, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.fab)
    public void onAddClick() {
        Timber.d("on FAB button clicked");
    }

    @Override
    public void onItemClicked(City city) {
        Timber.d("Clicked");
    }
}
