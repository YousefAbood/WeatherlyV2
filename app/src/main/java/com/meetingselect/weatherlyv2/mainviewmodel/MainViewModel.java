package com.meetingselect.weatherlyv2.mainviewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.meetingselect.weatherlyv2.data.Model.WeatherForecast.WeatherForecast;
import com.meetingselect.weatherlyv2.main.Home.HomeRepo;

import io.reactivex.subjects.BehaviorSubject;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = "MainViewModel";

    // BehaviourSubject
    private BehaviorSubject<WeatherForecast> mWeatherForecastMVVM = BehaviorSubject.create();

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public void getWeatherForecast(String destination, int days) {
        HomeRepo homeRepo = new HomeRepo();
        homeRepo.getWeatherForecast(destination, days);
        mWeatherForecastMVVM = homeRepo.getWeatherForecastBehaviourSubjectFromRepo();
    }

    public BehaviorSubject<WeatherForecast> getmWeatherForecastMVVM() {
        return mWeatherForecastMVVM;
    }
}
