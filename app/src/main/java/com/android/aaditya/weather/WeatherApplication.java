package com.android.aaditya.weather;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

import timber.log.Timber;

/**
 * Created by aaditya on 10/20/17.
 */

public class WeatherApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());
        JodaTimeAndroid.init(this);
        //Observable<Response> forecastObservable = new WeatherService().get24HrForecast();
    }
}
