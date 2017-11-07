package com.android.aaditya.weather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.aaditya.weather.base.BaseFragment;
import com.android.aaditya.weather.model.City;
import com.android.aaditya.weather.model.Forecast;
import com.android.aaditya.weather.model.Weather;
import com.google.gson.Gson;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.Unbinder;
import timber.log.Timber;

/**
 * Created by Student on 10/26/17.
 */

public class CitySliderFragment extends BaseFragment {

    private City city;
    private Map<String,Weather> forecastMap = new HashMap<>();
    ArrayList<TenDaySummary> mTenDaySummary;
    ArrayList<OneDaySummary> mOneDaySummary;

    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.rvContacts)
    RecyclerView rvContacts;
    Unbinder unbinder;
    @BindView(R.id.cityNameTextView)
    TextView cityNameTextView;
    @BindView(R.id.weatherStatusTextView)
    TextView weatherStatusTextView;
    @BindView(R.id.currTempTextView)
    TextView currTempTextView;
    @BindView(R.id.dayTextView)
    TextView dayTextView;
    @BindView(R.id.dayNameTextView)
    TextView dayNameTextView;
    @BindView(R.id.minTempTextView)
    TextView minTempTextView;
    @BindView(R.id.maxTempTextView)
    TextView maxTempTextView;
    //@BindView(R.id.cityName)
    //TextView cityName;


    public double getCelsius(String degreesKelvin)
    {
        double degreesKelvinDouble = Double.parseDouble(degreesKelvin);
        double c = degreesKelvinDouble - 273.16;
        return c;
    }


    public double getFahrenheit(String degreesKelvin)
    {
        double degreesKelvinDouble = Double.parseDouble(degreesKelvin);
        double f = (((degreesKelvinDouble - 273) * 9/5) + 32);
        return f;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.city_slider_item, container, false);

        //RecyclerView rvContacts = (RecyclerView) container.findViewById(R.id.rvContacts);

        Bundle args = getArguments();
        String cityJson = (String) args.get("city");
        city = new Gson().fromJson(cityJson, City.class);
        getDailyForecast(city);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        /*DateTimeZone timeZone = DateTimeZone.forID( "Europe/Paris" );
        DateTime dateTime = new DateTime( 1510088400, timeZone );
        int dayOfWeekNumber = dateTime.getDayOfWeek(); // ISO 8601 standard says Monday is 1.
        DateTimeFormatter formatter = DateTimeFormat.forPattern( "EEEE" ).withLocale( java.util.Locale.ENGLISH );
        String dayOfWeekName = formatter.print( dateTime );*/

        cityNameTextView.setText(city.getName());
        weatherStatusTextView.setText(city.getCurrentWeather().getStatus());
        currTempTextView.setText(String.format("%.1f", getCelsius(city.getCurrentWeather().getTemperature().getCurrentTemp()))+"°");
        dayTextView.setText("Today");
        dayNameTextView.setText("Sunday"/*city.getForecasts().get(0).getDateTime()*/);
        minTempTextView.setText(String.format("%.1f", getCelsius(city.getCurrentWeather().getTemperature().getMinTemp())));
        maxTempTextView.setText(String.format("%.1f", getCelsius(city.getCurrentWeather().getTemperature().getMaxTemp())));


        // Initialize contacts
        mTenDaySummary = TenDaySummary.createTenDaySummaryList(20);
        // Create adapter passing in the sample user data
        TenDaySummaryAdapter adapter1 = new TenDaySummaryAdapter(getContext(), mTenDaySummary);
        // Attach the adapter to the recyclerview to populate items
        rvContacts.setAdapter(adapter1);
        // Set layout manager to position the items
        rvContacts.setLayoutManager(new LinearLayoutManager(getContext()));


        // Initialize contacts
        mOneDaySummary = OneDaySummary.createOneDaySummaryList(20);
        // Create adapter passing in the sample user data
        OneDaySummaryAdapter adapter2 = new OneDaySummaryAdapter(getContext(), mOneDaySummary);
        // Attach the adapter to the recyclerview to populate items
        list.setAdapter(adapter2);
        // Set layout manager to position the items
        list.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));



    }

    private void getDailyForecast(City city) {
        Timber.d(city.toString());
        List<Forecast> forecastList = city.getForecasts();
        Weather weather = null;
        for(Forecast forecast: forecastList) {
            DateTime dateTime = new DateTime(Integer.parseInt(forecast.getDateTime()) * 1000L);
            String key = String.valueOf(dateTime.getYear()) + dateTime.getMonthOfYear() + dateTime.getDayOfMonth();
            if (! forecastMap.containsKey(key)) {
                forecastMap.put(key, forecast.getWeather());
                weather = forecast.getWeather();
            }
            else {
                weather = forecastMap.get(key);
                if (Double.parseDouble(weather.getTemperature().getMaxTemp()) < Double.parseDouble(forecast.getWeather().getTemperature().getMaxTemp())){
                    weather.getTemperature().setMaxTemp(forecast.getWeather().getTemperature().getMaxTemp());
                }

                if (Double.parseDouble(weather.getTemperature().getMinTemp()) > Double.parseDouble(forecast.getWeather().getTemperature().getMinTemp())){
                    weather.getTemperature().setMinTemp(forecast.getWeather().getTemperature().getMinTemp());
                }
            }
            if (dateTime.getHourOfDay() >= 11 || dateTime.getHourOfDay() <= 2)
                weather.setIcon(forecast.getWeather().getIcon());

        }

        Timber.d(forecastMap.toString());
    }
}
