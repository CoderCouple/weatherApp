package com.android.aaditya.weather.model;

import org.joda.time.DateTime;

/**
 * Created by aaditya on 10/20/17.
 */


public class Forecast {

    private Weather weather;
    private String dateTime;

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
