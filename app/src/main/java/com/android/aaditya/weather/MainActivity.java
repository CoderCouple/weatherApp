package com.android.aaditya.weather;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.aaditya.weather.base.BaseActivity;
import com.android.aaditya.weather.model.ForecastDay;
import com.android.aaditya.weather.model.ForecastInterval;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements ForecastViewInteractor {

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.responseView)
    TextView responseView;
    private ForecastPresenterImpl presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new ForecastPresenterImpl();
        presenter.attachViewInteractor(this);

        responseView.setText("Will show size");
    }

    public void get24hour(View v) {
        presenter.get24HourData();
    }

    public void get10days(View view) {
        presenter.get10DaysData();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void on24hourData(List<ForecastInterval> forecastIntervals) {
        responseView.setText(String.valueOf(forecastIntervals.size()));
    }

    @Override
    public void on10DaysData(List<ForecastDay> forecastDays) {
        responseView.setText(String.valueOf(forecastDays.size()));
    }
}
