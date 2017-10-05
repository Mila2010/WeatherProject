package com.example.wesniemarcelin.chaseweatherappcodingchallenge.presenter.network;

import com.example.wesniemarcelin.chaseweatherappcodingchallenge.model.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by wesniemarcelin on 9/29/17.
 */

public interface WeatherService {
    @GET("weather")
    Call<WeatherResponse> getWeather(@Query("q") String city, @Query("APPID") String apiKey);
}
