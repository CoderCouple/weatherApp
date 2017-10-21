package com.android.aaditya.weather.model;

import org.joda.time.DateTime;

/**
 * Created by aaditya on 10/20/17.
 */

public class ForecastInterval {

    private Weather weather;
    private DateTime dateTime;

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }
}
