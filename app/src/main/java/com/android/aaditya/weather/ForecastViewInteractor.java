package com.android.aaditya.weather;

import com.android.aaditya.weather.base.ViewInteractor;
import com.android.aaditya.weather.model.City;

/**
 * Created by aaditya on 10/21/17.
 */

public interface ForecastViewInteractor extends ViewInteractor{

    void showProgress();

    void hideProgress();

    void onForecast(City city);

}
