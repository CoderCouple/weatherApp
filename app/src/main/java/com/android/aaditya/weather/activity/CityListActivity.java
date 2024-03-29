package com.android.aaditya.weather.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.android.aaditya.weather.adapter.CityRecyclerViewAdapter;
import com.android.aaditya.weather.service.Forecast.ForecastPresenter;
import com.android.aaditya.weather.service.Forecast.ForecastPresenterImpl;
import com.android.aaditya.weather.service.Forecast.ForecastViewInteractor;
import com.android.aaditya.weather.R;
import com.android.aaditya.weather.base.BaseActivity;
import com.android.aaditya.weather.model.City;
import com.android.aaditya.weather.util.WeatherPreferences;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class CityListActivity extends BaseActivity implements CityRecyclerViewAdapter.ItemClickListener, ForecastViewInteractor, BaseActivity.PermissionCallback {

    @BindView(R.id.root_layout)
    CoordinatorLayout rootLayout;
    private Map<String, City> cities = new HashMap<>();
    private List<City> cityList;
    private CityRecyclerViewAdapter adapter;
    private ForecastPresenter presenter;

    private WeatherPreferences preferences;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);

        preferences = new WeatherPreferences(this);
        presenter = new ForecastPresenterImpl();
        presenter.attachViewInteractor(this);
        cityList = preferences.readCityList();
        if (cityList.size() > 0) {
            for (City city : cityList)
                cities.put(city.getPlaceId(), city);
        }

        requestLocation();

        adapter = new CityRecyclerViewAdapter(this, cityList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(SettingActivity.class, null);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.setting)
    public void onSettingClick() {
        startActivity(SettingActivity.class, null);
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
                city.setLang(String.valueOf((double)Math.round((place.getLatLng().longitude) *100)/100));
                city.setLat(String.valueOf((double)Math.round((place.getLatLng().latitude) *100)/100));

                if (!cities.containsKey(city.getPlaceId())) {
                    cities.put(city.getPlaceId(), city);
                    presenter.getTimeZoneForCity(city);
                    //presenter.getCurrentForecast(city);
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
        preferences.saveCityList(cities);
    }

    @Override
    public void onCityClicked(int position) {
        Timber.d("Clicked");
        Bundle dataBundle = new Bundle();
        dataBundle.putInt("position", position);
        startActivity(CitySliderActivity.class, dataBundle);
        //TODO: intent to detail view
    }

    @Override
    public void onCityDelete(int position) {
        cities.remove(cityList.get(position).getPlaceId());
        cityList.remove(position);
        adapter.notifyDataSetChanged();
        onCityListChange(cityList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //updateWeather(cityList);
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
    public void onForecast(City city) {
        cities.put(city.getPlaceId(), city);
        cityList.clear();
        cityList.addAll(cities.values());
        adapter.notifyDataSetChanged();
        onCityListChange(cityList);
        requestLocation();
    }

    @Override
    public void onCurrentForecast(City city) {


    }

    @Override
    public void onTimeZoneForCity(City city) {


    }

    @Override
    public void onPermissionGranted(String[] grantedPermissions) {
        requestLocation();
    }

    @Override
    public void onPermissionDenied(String[] deniedPermissions) {

    }

    @Override
    public void onPermissionBlocked(String[] blockedPermissions) {

    }

    private void updateWeather(List<City> cityList) {
        for (City city : cityList) {
            presenter.getForecast(city);
        }
    }

    public void requestLocation() {
        LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, this);
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location current = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        //NOTE: No location available
        if (current == null) {
            Snackbar.make(rootLayout,"Location Service Not Available", Snackbar.LENGTH_LONG).show();
            return;
        }

        Timber.d(String.valueOf(current.getLatitude()));
        for (City city : cities.values()) {
            Location cityLocation = new Location("city");
            cityLocation.setLatitude(Double.parseDouble(city.getLat()));
            cityLocation.setLongitude(Double.parseDouble(city.getLang()));

            city.setCurrentCity((current.distanceTo(cityLocation) < 3000));

            cities.put(city.getPlaceId(), city);
        }
        cityList.clear();
        cityList.addAll(cities.values());
        onCityListChange(cityList);
    }
}
