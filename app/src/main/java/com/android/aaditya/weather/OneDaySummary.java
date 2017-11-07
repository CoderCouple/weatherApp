package com.android.aaditya.weather;

        import java.util.ArrayList;

/**
 * Created by sunil28 on 10/23/17.
 */

public class OneDaySummary {


    private String mTime;
    private int mIcon;
    private int mTemp;


    public OneDaySummary(String time, int icon , int temp) {
        mTime = time;
        mIcon = icon;
        mTemp = temp;
    }

    public String getTime() {
        return mTime;
    }

    public int getIcon() {
        return mIcon;
    }

    public int getTemp() {
        return mTemp;
    }


    private static int lastItemId = 0;

    public static ArrayList<OneDaySummary> createOneDaySummaryList(int numListLength) {
        ArrayList<OneDaySummary> OneDaySummary = new ArrayList<OneDaySummary>();
        for (int i = 1; i <= numListLength; i++) {
            OneDaySummary.add(new OneDaySummary(lastItemId+1+"AM",R.drawable.d10,lastItemId));
            lastItemId++;
        }

        return OneDaySummary;
    }
}

