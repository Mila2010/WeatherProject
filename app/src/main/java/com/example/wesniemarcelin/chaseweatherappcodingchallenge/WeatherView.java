package com.example.wesniemarcelin.chaseweatherappcodingchallenge;

import android.content.Context;
import android.support.v4.util.ArrayMap;

/**
 * Created by mila on 10/4/17.
 */

public interface WeatherView {

    void showWeather(ArrayMap<String,String> weatherDetails);
    Context getContext();
}
