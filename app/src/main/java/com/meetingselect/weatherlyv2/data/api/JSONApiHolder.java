package com.meetingselect.weatherlyv2.data.api;

import com.meetingselect.weatherlyv2.data.Model.WeatherForecast.WeatherForecast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JSONApiHolder {

    @GET("v1/forecast.json?key=2fc725cddfc84683bc9160939212101")
    Call<WeatherForecast> getWeatherForecast(@Query("q") String latlon, @Query("days") int days);


}
