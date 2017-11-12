package com.android.aaditya.weather.activity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.MenuItem;

import com.android.aaditya.weather.adapter.CityPagerAdapter;
import com.android.aaditya.weather.R;
import com.android.aaditya.weather.base.BaseActivity;
import com.android.aaditya.weather.model.City;
import com.android.aaditya.weather.util.WeatherPreferences;
import com.android.aaditya.weather.util.ZoomOutPageTransformer;

import java.util.List;

import butterknife.BindView;
import me.relex.circleindicator.CircleIndicator;

public class CitySliderActivity extends BaseActivity {

    @BindView(R.id.pager_city)
    ViewPager cityPager;
    @BindView(R.id.indicator)
    CircleIndicator indicator;

    private CityPagerAdapter adapter;
    private List<City> cityList;

    private WeatherPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_slider);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        preferences = new WeatherPreferences(this);
        Bundle extras = getIntent().getExtras();
        int positon = extras.getInt("position");

        cityList = preferences.readCityList();
        adapter = new CityPagerAdapter(getSupportFragmentManager(),cityList);
        cityPager.setAdapter(adapter);
        cityPager.setPageTransformer(true, new ZoomOutPageTransformer());
        cityPager.setCurrentItem(positon);

        indicator.setViewPager(cityPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

   /* @Override
    public void onBackPressed() {
        if (cityPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            cityPager.setCurrentItem(cityPager.getCurrentItem() - 1);
        }
    }*/
}
