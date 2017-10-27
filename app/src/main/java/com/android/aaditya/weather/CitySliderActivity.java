package com.android.aaditya.weather;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
    public void onBackPressed() {
        if (cityPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            cityPager.setCurrentItem(cityPager.getCurrentItem() - 1);
        }
    }
}
