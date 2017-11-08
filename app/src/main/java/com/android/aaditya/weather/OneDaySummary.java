package com.android.aaditya.weather;

        import android.content.Context;

        import com.android.aaditya.weather.model.City;
        import com.android.aaditya.weather.util.WeatherPreferences;

        import org.joda.time.DateTime;
        import org.joda.time.DateTimeZone;

        import java.util.ArrayList;

/**
 * Created by sunil28 on 10/23/17.
 */

public class OneDaySummary {


    private String mTime;
    private String mIcon;
    private String mTemp;
    private static WeatherPreferences preferences;


    public OneDaySummary(String time, String icon , String temp) {
        mTime = time;
        mIcon = icon;
        mTemp = temp;
    }

    public String getTime() {
        return mTime;
    }

    public String getIcon() {
        return mIcon;
    }

    public String getTemp() {
        return mTemp;
    }

    public void setTime(String mTime) {
        this.mTime = mTime;
    }

    public void setIcon(String mIcon) {
        this.mIcon = mIcon;
    }

    public void setTemp(String mTemp) {
        this.mTemp = mTemp;
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
            case "C" : return String.format("%.0f",(Float.parseFloat(temp) - 273)) + "°C";

            case "F" : return String.format("%.0f",(((Float.parseFloat(temp) - 273) * 9/5) + 32)) + "°F";

            default: return "NA";
        }
    }


    public static ArrayList<OneDaySummary> createOneDaySummaryList(City city,Context context) {
        preferences = new WeatherPreferences(context);
        ArrayList<OneDaySummary> OneDaySummary = new ArrayList<OneDaySummary>();


        for (int i = 1; i <= 8; i++) {

            String time=  new DateTime(Long.parseLong(city.getForecasts().get(i).getDateTime())*1000l).withZone(DateTimeZone.forID(city.getTimeZone())).toString("hh a");
            String icon = city.getForecasts().get(i).getWeather().getIcon().toString()+".png";
            String temp = getConvertedTemp(city.getForecasts().get(i).getWeather().getTemperature().getCurrentTemp().toString());

            OneDaySummary.add(new OneDaySummary(time,icon,temp));
        }

        return OneDaySummary;
    }
}

