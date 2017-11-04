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
import com.android.aaditya.weather.model.Forecast;
import com.android.aaditya.weather.model.Weather;
import com.google.gson.Gson;

import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import timber.log.Timber;

/**
 * Created by Student on 10/26/17.
 */

public class CitySliderFragment extends BaseFragment {

    private City city;
    private Map<String,Weather> forecastMap = new HashMap<>();

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
        getDailyForecast(city);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cityName.setText(city.getName());
    }

    private void getDailyForecast(City city) {
        List<Forecast> forecastList = city.getForecasts();
        Weather weather = null;
        for(Forecast forecast: forecastList) {
            DateTime dateTime = new DateTime(Integer.parseInt(forecast.getDateTime()) * 1000L);
            String key = String.valueOf(dateTime.getYear()) + dateTime.getMonthOfYear() + dateTime.getDayOfMonth();
            if (! forecastMap.containsKey(key)) {
                forecastMap.put(key, forecast.getWeather());
                weather = forecast.getWeather();
            }
            else {
                weather = forecastMap.get(key);
                if (Double.parseDouble(weather.getTemperature().getMaxTemp()) < Double.parseDouble(forecast.getWeather().getTemperature().getMaxTemp())){
                    weather.getTemperature().setMaxTemp(forecast.getWeather().getTemperature().getMaxTemp());
                }

                if (Double.parseDouble(weather.getTemperature().getMinTemp()) > Double.parseDouble(forecast.getWeather().getTemperature().getMinTemp())){
                    weather.getTemperature().setMinTemp(forecast.getWeather().getTemperature().getMinTemp());
                }
            }
            if (dateTime.getHourOfDay() >= 11 || dateTime.getHourOfDay() <= 2)
                weather.setIcon(forecast.getWeather().getIcon());

        }

        Timber.d(forecastMap.toString());
    }
}
