package com.android.aaditya.weather.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.android.aaditya.weather.fragment.CitySliderFragment;
import com.android.aaditya.weather.model.City;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Student on 10/26/17.
 */

public class CityPagerAdapter extends FragmentStatePagerAdapter {
    private List<City> cityList;

    public CityPagerAdapter(FragmentManager fm, List<City> cityList) {
        super(fm);
        this.cityList = cityList;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        String cityJson = new Gson().toJson(cityList.get(position));
        bundle.putString("city",cityJson);
        Fragment fragment = new CitySliderFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return cityList.size();
    }
}

