package com.android.aaditya.weather;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.aaditya.weather.base.BaseFragment;
import com.android.aaditya.weather.model.City;
import com.android.aaditya.weather.model.Forecast;
import com.android.aaditya.weather.model.Weather;
import com.android.aaditya.weather.util.WeatherPreferences;
import com.google.gson.Gson;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.Unbinder;
import timber.log.Timber;

/**
 * Created by Student on 10/26/17.
 */

public class CitySliderFragment extends BaseFragment {

    private City city;
    private Map<String,Forecast> forecastMap = new LinkedHashMap<>();
    ArrayList<TenDaySummary> mTenDaySummary;
    ArrayList<OneDaySummary> mOneDaySummary;
    private static WeatherPreferences preferences ;
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
    @BindView(R.id.root_layout)
    LinearLayout rootLayout;
    @BindView(R.id.current_location)
    ImageView currentLocation;

    private static String getConvertedTemp(String temp) {
        String unit = preferences.readUnit();

        unit = unit == null ? "F" : unit;
        switch (unit) {
            case "C" : return String.format("%.0f",(Float.parseFloat(temp) - 273)) + "°";

            case "F" : return String.format("%.0f",(((Float.parseFloat(temp) - 273) * 9/5) + 32)) + "°";

            default: return "NA";
        }
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
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        preferences = new WeatherPreferences(getContext());

        /*DateTimeZone timeZone = DateTimeZone.forID( "Europe/Paris" );
        DateTime dateTime = new DateTime( 1510088400, timeZone );
        int dayOfWeekNumber = dateTime.getDayOfWeek(); // ISO 8601 standard says Monday is 1.
        DateTimeFormatter formatter = DateTimeFormat.forPattern( "EEEE" ).withLocale( java.util.Locale.ENGLISH );
        String dayOfWeekName = formatter.print( dateTime );*/

        cityNameTextView.setText(city.getName());
        weatherStatusTextView.setText(city.getCurrentWeather().getStatus());
        currTempTextView.setText(getConvertedTemp(city.getCurrentWeather().getTemperature().getCurrentTemp()));
        dayTextView.setText("Today");
        dayNameTextView.setText(DateTime.now().withZone(DateTimeZone.forID(city.getTimeZone())).toString("EEEE MMM dd"));
        /*city.getForecasts().get(0).getDateTime()*/;
        minTempTextView.setText(getConvertedTemp(city.getCurrentWeather().getTemperature().getMinTemp()));
        maxTempTextView.setText(getConvertedTemp(city.getCurrentWeather().getTemperature().getMaxTemp()));
        if (city.getCurrentWeather().getIcon().contains("d"));
        rootLayout.setBackgroundColor(Color.parseColor("#4fafca"));
        if (city.getCurrentWeather().getIcon().contains("n"))
            rootLayout.setBackgroundColor(Color.parseColor("#464d4e"));

        if (city.isCurrentCity())
            currentLocation.setVisibility(View.VISIBLE);


        // Initialize contacts
        mOneDaySummary = OneDaySummary.createOneDaySummaryList(city,getContext());
        // Create adapter passing in the sample user data
        OneDaySummaryAdapter adapter2 = new OneDaySummaryAdapter(getContext(), mOneDaySummary);
        // Attach the adapter to the recyclerview to populate items
        list.setAdapter(adapter2);
        // Set layout manager to position the items
        list.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        // Initialize contacts
        getDailyForecast(city);
        mTenDaySummary = TenDaySummary.createTenDaySummaryList(city,getContext(),forecastMap);
        // Create adapter passing in the sample user data
        TenDaySummaryAdapter adapter1 = new TenDaySummaryAdapter(getContext(), mTenDaySummary);
        // Attach the adapter to the recyclerview to populate items
        rvContacts.setAdapter(adapter1);
        // Set layout manager to position the items
        rvContacts.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void getDailyForecast(City city) {
        Timber.d(city.toString());
        List<Forecast> forecastList = new ArrayList<>(city.getForecasts());
        Forecast dailyForecast = null;
        Weather weather = null;
        for(Forecast item: forecastList) {
            DateTime dateTime = new DateTime(Integer.parseInt(item.getDateTime()) * 1000L).withZone(DateTimeZone.forID(city.getTimeZone()));
            //String date=  new DateTime(Long.parseLong(forecast.getDateTime())*1000l).withZone(DateTimeZone.forID(city.getTimeZone())).dayOfWeek().getAsText();
            String key = String.valueOf(dateTime.getYear()) + dateTime.getMonthOfYear() + dateTime.getDayOfMonth();

            if (! forecastMap.containsKey(key)) {
                dailyForecast = item;
                weather = dailyForecast.getWeather();
                forecastMap.put(key, dailyForecast);

            }
            else {
                dailyForecast = forecastMap.get(key);
                if (Double.parseDouble(weather.getTemperature().getMaxTemp()) < Double.parseDouble(item.getWeather().getTemperature().getMaxTemp())){
                    weather.getTemperature().setMaxTemp(item.getWeather().getTemperature().getMaxTemp());
                }

                if (Double.parseDouble(weather.getTemperature().getMinTemp()) > Double.parseDouble(item.getWeather().getTemperature().getMinTemp())){
                    weather.getTemperature().setMinTemp(item.getWeather().getTemperature().getMinTemp());
                }

                dailyForecast.setWeather(weather);
                forecastMap.put(key, dailyForecast);
            }
            if (dateTime.getHourOfDay() >= 11 || dateTime.getHourOfDay() <= 2) {
                weather.setIcon(item.getWeather().getIcon());
                dailyForecast.setDateTime(item.getDateTime());
                forecastMap.put(key,dailyForecast);
            }



        }

        //List<String> mapList = new ArrayList<String>(forecastMap.keySet());
        //Collections.sort(mapList);
        //String key = mapList.get(0);
        forecastMap.remove(forecastMap.keySet().iterator().next());
        //forecastMap.remove(new ArrayList<>(forecastMap.keySet()).get(forecastMap.size() - 1));
        Timber.d(forecastMap.toString());
    }
}
