package com.android.aaditya.weather;

import com.android.aaditya.weather.base.ViewInteractor;
import com.android.aaditya.weather.model.ForecastDay;
import com.android.aaditya.weather.model.ForecastInterval;

import java.util.List;

/**
 * Created by aaditya on 10/21/17.
 */

public interface ForecastViewInteractor extends ViewInteractor{

    void showProgress();

    void hideProgress();

    void on24hourData(List<ForecastInterval> forecastIntervals);

    void on10DaysData(List<ForecastDay> forecastDays);
}
