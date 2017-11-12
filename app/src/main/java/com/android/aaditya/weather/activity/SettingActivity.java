package com.android.aaditya.weather.activity;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.android.aaditya.weather.R;
import com.android.aaditya.weather.base.BaseActivity;
import com.android.aaditya.weather.util.SwitchButton;
import com.android.aaditya.weather.util.WeatherPreferences;

import butterknife.BindView;
import timber.log.Timber;

public class SettingActivity extends BaseActivity {

    enum Units{
        FAHRENHEIT("F"),
        CELSIUS("C");
        String unit;

        Units(String unit) {
            this.unit = unit;
        }

        @Override
        public String toString() {
            return unit;
        }

    }

    @BindView(R.id.switch_button)  SwitchButton switchButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Settings");
        final WeatherPreferences preferences = new WeatherPreferences(this);


        String unit = preferences.readUnit();

        if (unit == null || unit.isEmpty()) {
            unit = "F";
            preferences.saveUnit(unit);
        }

        switch (unit) {
            case "F": switchButton.setChecked(false);
                break;
            case "C": switchButton.setChecked(true);
        }


        switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                preferences.saveUnit(getUnit(isChecked));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private String getUnit(boolean value) {
        return  value ? "C" :"F";
    }


}
