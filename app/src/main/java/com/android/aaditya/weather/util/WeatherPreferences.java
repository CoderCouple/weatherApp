package com.android.aaditya.weather.util;

import android.content.Context;

/**
 * Created by aaditya on 10/23/17.
 */

public class WeatherPreferences {

    private PreferenceUtil preferenceUtil;

    private static final String WEATHER_UNIT = "weather_unit";

    public WeatherPreferences(Context context) {
        preferenceUtil = new PreferenceUtil(context);
    }

    public void saveUnit(String unit){
        preferenceUtil.save(WEATHER_UNIT, unit);
    }

    public String readUnit(){
        return (String) preferenceUtil.read(WEATHER_UNIT,String.class);
    }

}
