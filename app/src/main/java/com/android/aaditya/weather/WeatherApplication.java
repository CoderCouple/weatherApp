package com.android.aaditya.weather;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by aaditya on 10/20/17.
 */

public class WeatherApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());

        //Observable<Response> forecastObservable = new WeatherService().getForecast();
    }
}
