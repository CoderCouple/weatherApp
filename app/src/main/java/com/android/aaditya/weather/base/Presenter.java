package com.android.aaditya.weather.base;

/**
 * @author Aaditya deowanshi
 */
public interface Presenter<T extends ViewInteractor> {

    void attachViewInteractor(T viewInteractor);

    void detachViewInteractor();

}
