package com.android.aaditya.weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private WeatherController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controller = new WeatherController();
    }

    public void get24hour(View v) {
        controller.forecast24Hour();
    }

    public void get10days(View view) {
        controller.forecast10days();
    }
}
