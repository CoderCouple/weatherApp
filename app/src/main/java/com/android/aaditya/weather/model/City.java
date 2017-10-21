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

}
