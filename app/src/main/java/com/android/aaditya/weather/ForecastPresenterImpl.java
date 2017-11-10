package com.android.aaditya.weather;

import com.android.aaditya.weather.base.BasePresenter;
import com.android.aaditya.weather.model.City;
import com.android.aaditya.weather.model.Forecast;
import com.android.aaditya.weather.model.Temperature;
import com.android.aaditya.weather.model.Weather;
import com.android.aaditya.weather.service.ApiModule;
import com.android.aaditya.weather.service.PlacesService;
import com.android.aaditya.weather.service.WeatherService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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

    private WeatherService weatherService = ApiModule.getInstance().getWeatherService();
    private PlacesService placesService = ApiModule.getInstance().getPlacesService();
    private JsonObject response1;
    private JsonObject response2;
    private City city ;


    @Override
    public void getForecast(final City city) {
        this.city = city;

        getViewInteractor().showProgress();
        Observable<ResponseBody> observable = weatherService.get24HrForecast(city.getLat(),city.getLang(), Config.KEY_);
        new CompositeDisposable().add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ResponseBody>() {
                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {
                        try {
                            response1 = (JsonObject) new JsonParser().parse(String.valueOf((responseBody).string()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        List<Forecast> forecastList = new ArrayList<>();

                        JsonArray list = (JsonArray) response1.get("list");
                        for (JsonElement element : list) {
                            JsonObject item = element.getAsJsonObject();

                            Forecast forecast = new Forecast();
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

                            forecast.setDateTime(item.get("dt").getAsString());
                            forecast.setDateText(item.get("dt_txt").getAsString());
                            forecast.setWeather(weather);

                            forecastList.add(forecast);
                        }

                        city.setForecasts(forecastList);
                        getCurrentForecast(city);


                        /*Timber.d(String.valueOf(forecastList.size()));
                        getViewInteractor().hideProgress();
                        city.setForecasts(forecastList);
                        getViewInteractor().onForecast(city);*/
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

    public void getCurrentForecast(final City city) {
        this.city = city;

        //getViewInteractor().showProgress();
        Observable<ResponseBody> observable = weatherService.getCurrentForecast(city.getLat(),city.getLang(), Config.KEY_);
        new CompositeDisposable().add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ResponseBody>() {
                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {
                        try {
                            response2 = (JsonObject) new JsonParser().parse(String.valueOf((responseBody).string()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Weather currentWeather = new Weather();
                        JsonObject currTemp = (JsonObject) response2.get("main");
                        Temperature temperature = new Temperature();

                        temperature.setCurrentTemp(currTemp.get("temp").getAsString());
                        temperature.setMaxTemp(currTemp.get("temp_max").getAsString());
                        temperature.setMinTemp(currTemp.get("temp_min").getAsString());



                            JsonObject weatherJson = ((JsonArray)response2.get("weather")).get(0).getAsJsonObject();
                            currentWeather.setIcon(weatherJson.get("icon").getAsString());
                            currentWeather.setStatus(weatherJson.get("main").getAsString());
                            currentWeather.setTemperature(temperature);



                        getViewInteractor().hideProgress();
                        city.setCurrentWeather(currentWeather);
                        getViewInteractor().onForecast(city);
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



        public void getTimeZoneForCity(final City city) {
        this.city = city;
        getViewInteractor().showProgress();
        Observable<ResponseBody> observable =  placesService.getTimeZoneForCity(Config.KEY_TIMEZONE,Config.FORMAT_TIMEZONE,city.getLat(),city.getLang(), Config.BY_TIMEZONE);
        new CompositeDisposable().add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ResponseBody>() {
                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {
                        try {
                            response2 = (JsonObject) new JsonParser().parse(String.valueOf((responseBody).string()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Weather currentWeather = new Weather();
                        String cityTimeZone =  response2.get("zoneName").getAsString();

                        getViewInteractor().hideProgress();
                        city.setTimeZone(cityTimeZone);
                        getForecast(city);
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
