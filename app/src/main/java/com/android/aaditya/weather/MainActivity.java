package com.android.aaditya.weather;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.aaditya.weather.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

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
        responseView.setText("Will show size");
    }

    public void showListView(View view) {
        startActivity(CityListActivity.class, null);
    }

}
