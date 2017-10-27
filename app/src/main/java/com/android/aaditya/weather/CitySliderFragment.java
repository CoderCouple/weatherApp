package com.android.aaditya.weather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.aaditya.weather.base.BaseFragment;
import com.android.aaditya.weather.model.City;
import com.google.gson.Gson;

import butterknife.BindView;

/**
 * Created by Student on 10/26/17.
 */

public class CitySliderFragment extends BaseFragment {

    private City city;

    @BindView(R.id.cityName)
    TextView cityName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.city_slider_item, container, false);

        Bundle args = getArguments();
        String cityJson = (String) args.get("city");
        city = new Gson().fromJson(cityJson, City.class);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cityName.setText(city.getName());
    }
}
