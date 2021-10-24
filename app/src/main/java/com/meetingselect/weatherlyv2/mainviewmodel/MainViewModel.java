package com.meetingselect.weatherlyv2.mainviewmodel;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.meetingselect.weatherlyv2.data.Model.SearchLocation.CityResultsItem;
import com.meetingselect.weatherlyv2.data.Model.WeatherForecast.WeatherForecast;
import com.meetingselect.weatherlyv2.main.Home.HomeRepo;
import com.meetingselect.weatherlyv2.main.Search.SearchCitiesRepo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import io.reactivex.subjects.BehaviorSubject;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = "MainViewModel";
    private final MutableLiveData<List<String>> Cities = new MutableLiveData<>(Collections.emptyList());

    // BehaviourSubject
    private BehaviorSubject<WeatherForecast> mWeatherForecastMVVM = BehaviorSubject.create();
    private BehaviorSubject<List<CityResultsItem>> mCityName = BehaviorSubject.create();


    // Shared Pref
    private SharedPreferences sharedPreferences;
    private Gson gson = new Gson();

    public MainViewModel(@NonNull Application application) {

        super(application);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(application);

        String jsonNames = sharedPreferences.getString("cityNames", "[]");

        Cities.setValue(gson.fromJson(jsonNames, new TypeToken<ArrayList<String>>() {}.getType()));


    }

    public LiveData<List<String>> getCityNamesFragment() {
        return Cities;
    }

    public void addCityName(String cityName) {
        List<String> newCityNames = new ArrayList<>(Objects.requireNonNull(this.Cities.getValue()));


        if(!newCityNames.contains(cityName)) {
            Log.d(TAG, "AddName: " + cityName + "successful.");
            newCityNames.add(cityName);
        }

        Cities.setValue(Collections.unmodifiableList(newCityNames));

        sharedPreferences.edit().putString("cityNames", gson.toJson(newCityNames)).apply();

    }

    public void removeCityName(String cityName) {
        List<String> newCityNames = new ArrayList<>(Objects.requireNonNull(this.Cities.getValue()));

        List<String> cityNameList = new ArrayList<>();

        cityNameList.add(cityName);

        Log.d(TAG, "removeCityName: " + newCityNames);
        Log.d(TAG, "removeCityName: " + cityNameList.get(0));


        try {

            newCityNames.remove(cityNameList.get(0));
        } catch (NullPointerException e) {

            Log.e(TAG, "removeCityName: ", e);
        }
        Cities.setValue(Collections.unmodifiableList(newCityNames));

        sharedPreferences.edit().putString("cityNames", gson.toJson(newCityNames)).apply();


    }


    public void getWeatherForecast(String destination, int days) {
        HomeRepo homeRepo = new HomeRepo();
        homeRepo.getWeatherForecast(destination, days);
        mWeatherForecastMVVM = homeRepo.getWeatherForecastBehaviourSubjectFromRepo();
    }

    public BehaviorSubject<WeatherForecast> getmWeatherForecastMVVM() {
        return mWeatherForecastMVVM;
    }

    public void getCityNames(String location) {
        SearchCitiesRepo searchCitiesRepo = new SearchCitiesRepo();
        searchCitiesRepo.getCityResultsRepo(location);
        mCityName = searchCitiesRepo.getmCityResultsItemBehaviourSubject();
    }

    public BehaviorSubject<List<CityResultsItem>> getmCityName() {
        return mCityName;
    }
}
