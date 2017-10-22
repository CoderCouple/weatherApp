package com.android.aaditya.weather;

import com.android.aaditya.weather.base.Presenter;

/**
 * Created by aaditya on 10/21/17.
 */

public interface ForecastPresenter extends Presenter<ForecastViewInteractor> {

    void get24HourData();

    void get10DaysData();
}
