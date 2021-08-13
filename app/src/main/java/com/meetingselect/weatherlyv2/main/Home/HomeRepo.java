package com.meetingselect.weatherlyv2.main.Home;

import android.util.Log;

import com.meetingselect.weatherlyv2.data.Model.WeatherForecast.WeatherForecast;
import com.meetingselect.weatherlyv2.data.api.JSONApiHolder;

import java.io.IOException;

import io.reactivex.subjects.BehaviorSubject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeRepo {

    private static final String TAG = "HomeRepo";

    JSONApiHolder jsonApiHolder;

    private final BehaviorSubject<WeatherForecast> mWeatherForecast = BehaviorSubject.create();

    public HomeRepo() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.weatherapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonApiHolder = retrofit.create(JSONApiHolder.class);
    }

    public void getWeatherForecast(String destination, int days) {

        Log.d(TAG, "getWeatherForecast: " + destination);

        Call<WeatherForecast> call = jsonApiHolder.getWeatherForecast(destination, days);

        call.enqueue(new Callback<WeatherForecast>() {
            @Override
            public void onResponse(Call<WeatherForecast> call, Response<WeatherForecast> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, "response code " + response.code());
                    Log.d(TAG, "onResponse: " + response.message());
                    Log.d(TAG, "onResponse: " + response.raw());
                    return;
                }

                WeatherForecast weatherForecast = response.body();

                mWeatherForecast.onNext(weatherForecast);

                Log.d(TAG, "onResponse: " + weatherForecast.getLocation().getCountry());
            }

            @Override
            public void onFailure(Call<WeatherForecast> call, Throwable t) {
                if (t instanceof IOException) {
                    Log.e(TAG, "onFailure: " + t);
                }

                Log.d(TAG, "onFailure: ");
            }
        });
    }

    public BehaviorSubject<WeatherForecast> getWeatherForecastBehaviourSubjectFromRepo() {
        return mWeatherForecast;
    }

}
