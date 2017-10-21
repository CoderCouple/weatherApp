package com.android.aaditya.weather;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import retrofit2.Response;

/**
 * Created by aaditya on 10/20/17.
 */

public abstract class ApiObserver<T extends Response> implements Observer<T> {

    /**
     * Publish result to observer.
     *
     * @param response
     */
    public abstract void onResponse(T response);

    @Override
    public void onError(@NonNull Throwable e) {

    }

}