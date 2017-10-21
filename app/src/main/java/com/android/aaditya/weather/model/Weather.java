package com.android.aaditya.weather.model;

/**
 * Created by aaditya on 10/20/17.
 */

public class Weather {

    private Temperature temperature;
    private String status;
    private String icon;

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
