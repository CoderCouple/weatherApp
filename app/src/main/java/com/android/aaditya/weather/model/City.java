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
    private String timeZone;
    private boolean currentCity;
    private Weather currentWeather;
    private List<Forecast> forecasts;

    public Weather getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(Weather currentWeather) {
        this.currentWeather = currentWeather;
    }

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

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public boolean isCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(boolean currentCity) {
        this.currentCity = currentCity;
    }

    public List<Forecast> getForecasts() {
        return forecasts;
    }

    public void setForecasts(List<Forecast> forecasts) {
        this.forecasts = forecasts;
    }
}
