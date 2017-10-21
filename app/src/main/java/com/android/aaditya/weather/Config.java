package com.android.aaditya.weather;

import android.content.pm.ActivityInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aaditya on 10/20/17.
 */

public class Config {

    //--------------------------------------------------------------------------------
    // App general configurations
    //--------------------------------------------------------------------------------
    public static final boolean DEBUG = true;

    public static final int ORIENTATION_PORTRAIT    = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    public static final int ORIENTATION_LANDSCAPE   = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
    public static final int ORIENTATION_SENSOR      = ActivityInfo.SCREEN_ORIENTATION_SENSOR;
    public static final int ORIENTATION_DEFAULT     = ORIENTATION_PORTRAIT;

    //--------------------------------------------------------------------------------
    // API related constants/configurations - used in ApiModule
    //--------------------------------------------------------------------------------
    public static final String API_BASE_URL_PRODUCTION = "http://api.openweathermap.org/data/2.5/";
    public static final String API_BASE_URL_MOCK = "api.openweathermap.org/data/2.5/";
    // Active base url
    public static final String API_BASE_URL = API_BASE_URL_PRODUCTION;

    // Common http headers required to be added by interceptor
    public static final Map<String, String> API_HEADERS = new HashMap<String, String>() {{
        put("User-Agent", "Weather-App");
        put("Content-Type", "application/json");
    }};

    // Key
    public static final String KEY_ = "b2c2f73ec36ac4a0a2ccd2107e34f54d";

}
