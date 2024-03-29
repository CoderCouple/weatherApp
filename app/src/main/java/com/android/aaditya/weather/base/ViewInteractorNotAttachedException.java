package com.android.aaditya.weather.base;

/**
 * @author Aaditya Deowanshi
 */
public class ViewInteractorNotAttachedException extends RuntimeException {

    public ViewInteractorNotAttachedException() {
        super("Presenter.attachViewViewInteractor(viewInteractor) should be called before" +
                " accessing presenter methods which uses view interactor object");
    }

}