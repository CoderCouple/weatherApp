package com.android.aaditya.weather.service;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;

/**
 * Created by Student on 10/24/17.
 */

public interface PlacesService {

    @GET("autocomplete/json")
    Observable<ResponseBody> getPrediction(String input, String key, String types);

    @GET("details/json")
    Observable<ResponseBody> getLatLong(String placeid, String key);
}
