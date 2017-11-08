package com.android.aaditya.weather;

import android.content.Context;

import com.android.aaditya.weather.model.City;
import com.android.aaditya.weather.util.WeatherPreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunil28 on 10/23/17.
 */

public class TenDaySummary {


    private String mDay;
    private int mIcon;
    private int mMin;
    private int mMax;
    private Context mContext;


    public TenDaySummary(String day, int icon , int min, int max) {
        mDay = day;
        mIcon = icon;
        mMin = min;
        mMax = max;
    }

    public Context getContext() { return mContext; }

    public String getDay() {
        return mDay;
    }

    public int getIcon() {
        return mIcon;
    }

    public int getMin() {
        return mMin;
    }

    public int getMax() {
        return mMax;
    }

    public void setDay(String mDay) {
        this.mDay = mDay;
    }

    public void setIcon(int mIcon) {
        this.mIcon = mIcon;
    }

    public void setMin(int mMin) {
        this.mMin = mMin;
    }

    public void setMax(int mMax) {
        this.mMax = mMax;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    private static int lastItemId = 0;

    public static ArrayList<TenDaySummary> createTenDaySummaryList(City city) {
        ArrayList<TenDaySummary> TenDaySummary = new ArrayList<TenDaySummary>();

        for (int i = 1; i <=8; i++) {
            TenDaySummary.add(new TenDaySummary("Day" + lastItemId+1,R.drawable.d10,lastItemId,lastItemId+1));
            lastItemId++;
        }

        return TenDaySummary;
    }
}

