package com.meetingselect.weatherlyv2.main.Search;

import android.util.Log;

import com.meetingselect.weatherlyv2.data.Model.SearchLocation.CityResults;
import com.meetingselect.weatherlyv2.data.Model.SearchLocation.CityResultsItem;
import com.meetingselect.weatherlyv2.data.api.JSONApiHolder;

import java.io.IOException;
import java.util.List;

import io.reactivex.subjects.BehaviorSubject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchCitiesRepo {

    private static final String TAG = "SearchCities";

    private JSONApiHolder jsonApiHolder;

    private final BehaviorSubject<List<CityResultsItem>> mSearchCities = BehaviorSubject.create();

    public SearchCitiesRepo() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.weatherapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonApiHolder = retrofit.create(JSONApiHolder.class);
    }

    public void getCityResultsRepo(String cityName) {
        Call<List<CityResultsItem>> call = jsonApiHolder.getCityResults(cityName);

        call.enqueue(new Callback<List<CityResultsItem>>() {
            @Override
            public void onResponse(Call<List<CityResultsItem>> call, Response<List<CityResultsItem>> response) {
                if(!response.isSuccessful()) {
                    Log.d(TAG, "ResponseCode: " + response.code());
                }

                List<CityResultsItem> cityResultsItemList = response.body();

                Log.d(TAG, "onResponse: " + cityResultsItemList.size());

                mSearchCities.onNext(cityResultsItemList);

            }

            @Override
            public void onFailure(Call<List<CityResultsItem>> call, Throwable t) {
                if(t instanceof IOException) {
                    Log.e(TAG, "onFailure: ", t);
                }

                Log.d(TAG, "onFailure: " + t);
            }
        });


    }

    public BehaviorSubject<List<CityResultsItem>> getmCityResultsItemBehaviourSubject() {
        return mSearchCities;
    }
}
