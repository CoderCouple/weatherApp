package com.android.aaditya.weather;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by aaditya on 10/19/17.
 */

public interface WeatherService {

    @GET("forecast")
    Observable<ResponseBody> get24HrForecast(@Query("id") String id, @Query("appid") String key);

    @GET("forecast/daily")
    Observable<ResponseBody> getTenDayForecast(@Query("id") String id, @Query("appid") String key);

}
