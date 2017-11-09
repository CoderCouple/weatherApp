package com.android.aaditya.weather;

import android.os.Bundle;
import android.os.Handler;

import com.android.aaditya.weather.base.BaseActivity;

public class SpalshActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(CityListActivity.class,null);
                finish();
            }
        }, 3000);
    }
}
