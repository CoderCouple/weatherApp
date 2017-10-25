package com.android.aaditya.weather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.android.aaditya.weather.base.BaseActivity;
import com.android.aaditya.weather.model.City;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

public class CityListActivity extends BaseActivity implements CityRecyclerViewAdapter.ItemClickListener, ForecastViewInteractor {

    private Map<String,City> cities = new HashMap<>();
    private List<City> cityList;
    private CityRecyclerViewAdapter adapter;
    private ForecastPresenter presenter;

    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.progressBar) ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        presenter = new ForecastPresenterImpl();
        presenter.attachViewInteractor(this);
        cityList = new ArrayList<City>(cities.values());
        adapter = new CityRecyclerViewAdapter(this, cityList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


    }

    @OnClick(R.id.fab)
    public void onAddClick() {
        Timber.d("on FAB button clicked");
        try {
            AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                    .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                    .build();
            Intent intent =
                    new PlaceAutocomplete
                            .IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .setFilter(typeFilter)
                            .build(this);
            startActivityForResult(intent, 1);
        } catch (GooglePlayServicesRepairableException e) {
            Timber.e(e.getMessage());
        } catch (GooglePlayServicesNotAvailableException e) {
            Timber.e(e.getMessage());
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                // retrive the data by using getPlace() method.
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.e("Tag", "Place: " + place.getAddress() + place.getPhoneNumber());
                City city = new City();
                city.setName(place.getName().toString());
                city.setPlaceId(place.getId());
                city.setLang(String.valueOf(place.getLatLng().longitude));
                city.setLat(String.valueOf(place.getLatLng().latitude));

                if (!cities.containsKey(city.getPlaceId())) {
                    cities.put(city.getPlaceId(),city);
                    presenter.get10DaysData(city);
                    presenter.get24HourData(city);
                }
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);

                Timber.e(status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    private void onCityListChange(List<City> cities) {
        for (City city : cities) {

        }
    }

    @Override
    public void onItemClicked(City city) {
        Timber.d("Clicked");
        //TODO: intent to detail view
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void on24hourData(City city) {
        cities.put(city.getPlaceId(), city);
        cityList.clear();
        cityList.addAll(cities.values());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void on10DaysData(City city) {
        cities.put(city.getPlaceId(), city);
        cityList.clear();
        cityList.addAll(cities.values());
        adapter.notifyDataSetChanged();
    }
}
