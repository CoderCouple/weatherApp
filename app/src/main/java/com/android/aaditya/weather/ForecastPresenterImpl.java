package com.android.aaditya.weather;

import com.android.aaditya.weather.base.BasePresenter;
import com.android.aaditya.weather.model.ForecastDay;
import com.android.aaditya.weather.model.ForecastInterval;
import com.android.aaditya.weather.model.Temperature;
import com.android.aaditya.weather.model.Weather;
import com.android.aaditya.weather.service.ApiModule;
import com.android.aaditya.weather.service.WeatherService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import timber.log.Timber;

/**
 * Created by aaditya on 10/20/17.
 */

public class ForecastPresenterImpl extends BasePresenter<ForecastViewInteractor> implements ForecastPresenter {

    private WeatherService weatherService = ApiModule.getInstance().getApi();
    private JsonObject response;


    @Override
    public void get24HourData() {
        getViewInteractor().showProgress();
        Observable<ResponseBody> observable = weatherService.get24HrForecast("1275339", Config.KEY_);
        new CompositeDisposable().add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ResponseBody>() {
                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {
                        try {
                            response = (JsonObject) new JsonParser().parse(String.valueOf((responseBody).string()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        List<ForecastInterval> forecastIntervalList = new ArrayList<>();

                        JsonArray list = (JsonArray) response.get("list");
                        for (JsonElement element : list) {
                            JsonObject item = element.getAsJsonObject();

                            ForecastInterval forecastInterval = new ForecastInterval();
                            Weather weather = new Weather();
                            Temperature temperature = new Temperature();

                            JsonObject main = item.get("main").getAsJsonObject();
                            temperature.setCurrentTemp(main.get("temp").getAsString());
                            temperature.setMaxTemp(main.get("temp_max").getAsString());
                            temperature.setMinTemp(main.get("temp_min").getAsString());

                            JsonObject weatherJson = ((JsonArray)item.get("weather")).get(0).getAsJsonObject();
                            weather.setIcon(weatherJson.get("icon").getAsString());
                            weather.setStatus(weatherJson.get("main").getAsString());
                            weather.setTemperature(temperature);

                            forecastInterval.setDateTime(new DateTime(Long.valueOf(item.get("dt").getAsString()) *1000));
                            forecastInterval.setWeather(weather);

                            forecastIntervalList.add(forecastInterval);
                        }

                        Timber.d(String.valueOf(forecastIntervalList.size()));
                        getViewInteractor().hideProgress();
                        getViewInteractor().on24hourData(forecastIntervalList);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @Override
    public void get10DaysData() {
        getViewInteractor().showProgress();
        Observable<ResponseBody> observable = weatherService.getTenDayForecast("1275339",Config.KEY_);
        new CompositeDisposable().add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ResponseBody>() {
                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {
                        try {
                            response = (JsonObject) new JsonParser().parse(String.valueOf((responseBody).string()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        List<ForecastDay> forecastDayList = new ArrayList<>();
                        JsonArray list = (JsonArray) response.get("list");
                        for (JsonElement element : list) {
                            JsonObject item = element.getAsJsonObject();

                            ForecastDay forecastDay = new ForecastDay();
                            Weather weather = new Weather();
                            Temperature temperature = new Temperature();

                            JsonObject main = item.get("temp").getAsJsonObject();
                            //temperature.setCurrentTemp(main.get("temp").getAsString());
                            temperature.setMaxTemp(main.get("max").getAsString());
                            temperature.setMinTemp(main.get("min").getAsString());

                            JsonObject weatherJson = ((JsonArray)item.get("weather")).get(0).getAsJsonObject();
                            weather.setIcon(weatherJson.get("icon").getAsString());
                            weather.setStatus(weatherJson.get("main").getAsString());
                            weather.setTemperature(temperature);
                            forecastDay.setDateTime(new DateTime(Long.valueOf(item.get("dt").getAsString()) *1000));
                            forecastDay.setWeather(weather);

                            forecastDayList.add(forecastDay);
                        }

                        Timber.d(String.valueOf(forecastDayList.size()));
                        getViewInteractor().hideProgress();
                        getViewInteractor().on10DaysData(forecastDayList);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }
}
