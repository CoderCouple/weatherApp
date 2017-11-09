package com.android.aaditya.weather;

import android.content.Context;

import com.android.aaditya.weather.model.City;
import com.android.aaditya.weather.model.Forecast;
import com.android.aaditya.weather.model.Weather;
import com.android.aaditya.weather.util.WeatherPreferences;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by sunil28 on 10/23/17.
 */

public class TenDaySummary {


    private String mDay;
    private String mIcon;
    private String mMin;
    private String mMax;
    private Context mContext;
    private static WeatherPreferences preferences;


    public TenDaySummary(String day, String icon , String min, String max) {
        mDay = day;
        mIcon = icon;
        mMin = min;
        mMax = max;
    }

    public Context getContext() { return mContext; }

    public String getDay() {
        return mDay;
    }

    public String getIcon() {
        return mIcon;
    }

    public String getMin() {
        return mMin;
    }

    public String getMax() {
        return mMax;
    }

    public void setDay(String mDay) {
        this.mDay = mDay;
    }

    public void setIcon(String mIcon) {
        this.mIcon = mIcon;
    }

    public void setMin(String mMin) {
        this.mMin = mMin;
    }

    public void setMax(String mMax) {
        this.mMax = mMax;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public static double getCelsius(String degreesKelvin)
    {
        double degreesKelvinDouble = Double.parseDouble(degreesKelvin);
        double c = degreesKelvinDouble - 273.16;
        return c;
    }


    public static double getFahrenheit(String degreesKelvin)
    {
        double degreesKelvinDouble = Double.parseDouble(degreesKelvin);
        double f = (((degreesKelvinDouble - 273) * 9/5) + 32);
        return f;
    }

    private static String getConvertedTemp(String temp) {
        String unit = preferences.readUnit();

        unit = unit == null ? "F" : unit;
        switch (unit) {
            case "C" : return String.format("%.0f",(Float.parseFloat(temp) - 273)) + "°";

            case "F" : return String.format("%.0f",(((Float.parseFloat(temp) - 273) * 9/5) + 32)) + "°";

            default: return "NA";
        }
    }

    private static int lastItemId = 0;

    public static ArrayList<TenDaySummary> createTenDaySummaryList(City city,Context context,Map<String,Forecast> forecastMap) {
        preferences = new WeatherPreferences(context);
        Map<String, Forecast> map = new TreeMap<String, Forecast>(forecastMap);
        ArrayList<TenDaySummary> TenDaySummary = new ArrayList<TenDaySummary>();




            Set set2 = map.entrySet();
            Iterator iterator2 = set2.iterator();
            while(iterator2.hasNext()) {
                Map.Entry me2 = (Map.Entry)iterator2.next();

                //String day=  new DateTime(Long.parseLong(city.getForecasts().get(1).getDateTime())*1000l).withZone(DateTimeZone.forID(city.getTimeZone())).dayOfWeek().getAsText();
                Forecast forecastObj = (Forecast) me2.getValue();
                Weather weatherObj = forecastObj.getWeather();
                String day=  new DateTime(Long.parseLong(forecastObj.getDateTime())*1000l)
                        .withZone(DateTimeZone.forID(city.getTimeZone())).dayOfWeek().getAsText();
                String icon = weatherObj.getIcon().toString()+".png";
                String minTemp = getConvertedTemp(weatherObj.getTemperature().getMinTemp().toString());
                String maxTemp = getConvertedTemp(weatherObj.getTemperature().getMaxTemp().toString());

                TenDaySummary.add(new TenDaySummary(day,icon,minTemp,maxTemp));
            }



            lastItemId++;


        return TenDaySummary;
    }
}

