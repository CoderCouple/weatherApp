package com.android.aaditya.weather;

import java.util.ArrayList;

/**
 * Created by sunil28 on 10/23/17.
 */

public class TenDaySummary {

    private String mDay;
    private int mIcon;
    private int mMin;
    private int mMax;


    public TenDaySummary(String day, int icon , int min, int max) {
        mDay = day;
        mIcon = icon;
        mMin = min;
        mMax = max;
    }


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

    private static int lastItemId = 0;

    public static ArrayList<TenDaySummary> createTenDaySummaryList(int numListLength) {
        ArrayList<TenDaySummary> TenDaySummary = new ArrayList<TenDaySummary>();

        for (int i = 1; i <= numListLength; i++) {
            TenDaySummary.add(new TenDaySummary("Day" + lastItemId+1,R.drawable.d10,lastItemId,lastItemId+1));
            lastItemId++;
        }

        return TenDaySummary;
    }
}

