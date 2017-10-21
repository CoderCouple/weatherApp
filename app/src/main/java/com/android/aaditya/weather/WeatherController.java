package com.android.aaditya.weather;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by aaditya on 10/20/17.
 */

public class WeatherController {

    public void start() {

        WeatherService weatherService = ApiModule.getInstance().getApi();

        weatherService.getForecast("1275339", Config.KEY_).
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(@NonNull ResponseBody responseBody) throws Exception {
                        JsonObject response = (JsonObject) new JsonParser().parse(String.valueOf((responseBody).string()));
                        JsonArray list = (JsonArray) response.get("list");
                    }
                });

    }

}
