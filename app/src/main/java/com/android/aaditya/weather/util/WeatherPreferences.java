package com.android.aaditya.weather.util;

import android.content.Context;

import com.android.aaditya.weather.model.City;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by aaditya on 10/23/17.
 */

public class WeatherPreferences {

    private PreferenceUtil preferenceUtil;
    private Gson gson = new Gson();

    private static final String WEATHER_UNIT = "weather_unit";
    private static final String CITY_LIST = "city_list";

    public WeatherPreferences(Context context) {
        preferenceUtil = new PreferenceUtil(context);
    }

    public void saveUnit(String unit){
        preferenceUtil.save(WEATHER_UNIT, unit);
    }

    public String readUnit(){
        return (String) preferenceUtil.read(WEATHER_UNIT,String.class);
    }

    public void saveCityList(List<City> cityList) {
        String cityJson = gson.toJson(cityList);

        preferenceUtil.save(CITY_LIST, cityJson);
    }

    public List<City> readCityList() {
        String json = preferenceUtil.readString(CITY_LIST,"[]");

        Type listType = new TypeToken<List<City>>() {
        }.getType();

        return gson.fromJson(json, listType);
    }

}
