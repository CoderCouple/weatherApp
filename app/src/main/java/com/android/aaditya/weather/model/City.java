package com.android.aaditya.weather.model;

import java.util.List;

/**
 * Created by aaditya on 10/20/17.
 */

public class City {

    private String name;
    private String placeId;
    private String lat;
    private String lang;
    private List<ForecastDay> forecastDays;
    private List<ForecastInterval> forecastIntervals;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public List<ForecastDay> getForecastDays() {
        return forecastDays;
    }

    public void setForecastDays(List<ForecastDay> forecastDays) {
        this.forecastDays = forecastDays;
    }

    public List<ForecastInterval> getForecastIntervals() {
        return forecastIntervals;
    }

    public void setForecastIntervals(List<ForecastInterval> forecastIntervals) {
        this.forecastIntervals = forecastIntervals;
    }
}
