package com.meetingselect.weatherlyv2.main.Cities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.meetingselect.weatherlyv2.HelperClasses.GPSLocation;
import com.meetingselect.weatherlyv2.HelperClasses.SpacingItemDecorator;
import com.meetingselect.weatherlyv2.R;
import com.meetingselect.weatherlyv2.data.Model.WeatherForecast.WeatherForecast;
import com.meetingselect.weatherlyv2.mainviewmodel.MainViewModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class CitiesFragment extends Fragment implements CitiesAdapter.onCityForecastClicked{

    private static final String TAG = "CitiesFragment";
    private CompositeDisposable mDisposable;
    private ArrayList<String> mCityName = new ArrayList<>();
    private ArrayList<String> mCountryName = new ArrayList<>();
    private ArrayList<String> mCurrentTemp = new ArrayList<>();
    private ArrayList<String> mMinTemperatureCities = new ArrayList<>();
    private ArrayList<String> mCurrentWeatherConditionsCities = new ArrayList<>();
    private ArrayList<String> mCurrentWeatherConditionIconCities = new ArrayList<>();
    private ArrayList<String> mMaxTemperatureCities = new ArrayList<>();
    private ArrayList<String> mPrecipitaionCities = new ArrayList<>();
    private ArrayList<String> mWindSpeedCities = new ArrayList<>();
    private ArrayList<String> mHumidityCities = new ArrayList<>();
    private ArrayList<String> mPressureCities = new ArrayList<>();
    private ArrayList<String> mSunsetTime = new ArrayList<>();
    private ArrayList<String> mSunriseTime = new ArrayList<>();
    private ArrayList<String> mCityList = new ArrayList<>();
    private AlertDialog alertDialog;
    public int c = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cities, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ConstraintLayout constraintLayout = requireView().findViewById(R.id.cities_fourthheadlayout_constraintlayout);

        constraintLayout.setVisibility(View.INVISIBLE);

        SwipeRefreshLayout mSwipeRefreshLayout = requireView().findViewById(R.id.cities_secondheadlayout_swiperefreshlayout);
        mSwipeRefreshLayout.setRefreshing(false);
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mSwipeRefreshLayout.setRefreshing(true);
            Navigation.findNavController(requireView()).navigate(R.id.action_citiesFragment_self);
            mSwipeRefreshLayout.setRefreshing(false);
        });

        getCitiesForecast();

    }


    private void getCitiesForecast() {

        c = 0;

        mCityName.clear();
        mCountryName.clear();
        mCurrentTemp.clear();
        mCurrentWeatherConditionsCities.clear();
        mCurrentWeatherConditionIconCities.clear();
        mMinTemperatureCities.clear();
        mMaxTemperatureCities.clear();
        mPrecipitaionCities.clear();
        mWindSpeedCities.clear();
        mHumidityCities.clear();
        mPressureCities.clear();
        mSunriseTime.clear();
        mSunsetTime.clear();
        mDisposable = new CompositeDisposable();


        counterEmptyCityList();
        counterOnNoWifiOrLocation();


        if(haveNetworkConnection()) {

            MainViewModel mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

            List<String> cityNames = mainViewModel.getCityNamesFragment().getValue();

            ProgressBar progressBar = requireView().findViewById(R.id.cities_progressbar_progressbar);

            Log.d(TAG, "getCitiesForecastSize: " + cityNames.size());

            if(!(cityNames.size() == 0)) {
                for (int i = 0; i < cityNames.size(); i++) {
                    Log.d(TAG, "getCitiesForecast: " + i);
                    mainViewModel.getWeatherForecast(cityNames.get(i), 1);

                    Disposable disposable = mainViewModel.getmWeatherForecastMVVM().subscribe(v -> {
                        c++;
                        mCityName.add(v.getLocation().getName());
                        mCityList.add(v.getLocation().getRegion());
                        mCountryName.add(v.getLocation().getCountry());
                        mCurrentTemp.add(String.valueOf(v.getCurrent().getTempC()));
                        mCurrentWeatherConditionsCities.add(v.getCurrent().getCondition().getText());
                        mCurrentWeatherConditionIconCities.add(v.getCurrent().getCondition().getIcon());
                        mMinTemperatureCities.add(String.valueOf(v.getForecast().getForecastday().get(0).getDay().getMintempC()));
                        mMaxTemperatureCities.add(String.valueOf(v.getForecast().getForecastday().get(0).getDay().getMaxtempC()));
                        mPrecipitaionCities.add(v.getCurrent().getPrecipMm() + "%");
                        mWindSpeedCities.add(v.getCurrent().getWindKph() + " km/h");
                        mHumidityCities.add(v.getCurrent().getHumidity() + "%");
                        mPressureCities.add(v.getCurrent().getPressureMb() + " hPa");
                        mSunriseTime.add(String.valueOf(v.getForecast().getForecastday().get(0).getAstro().getSunrise()));
                        mSunsetTime.add(String.valueOf(v.getForecast().getForecastday().get(0).getAstro().getSunset()));

                        Log.d(TAG, "getCitiesForecast: " + mCityName.size());

                        initRecyclerViewCities(mCityList, mCityName, mCountryName, mCurrentTemp, mCurrentWeatherConditionsCities, mCurrentWeatherConditionIconCities, mMinTemperatureCities, mMaxTemperatureCities, mPrecipitaionCities, mWindSpeedCities, mHumidityCities, mPressureCities, mSunsetTime, mSunriseTime, c);

                    });

                    mDisposable.add(disposable);

                }

            } else {
                emptyCityList();

            }



        } else {

            onNoWifiOrLocation();


        }






    }


    private void initRecyclerViewCities(ArrayList<String> mCityList,
                                        ArrayList<String> mCityName,
                                        ArrayList<String> mCountryName,
                                        ArrayList<String> mCurrentTempAdapter,
                                        ArrayList<String> mMinTemperatureCities,
                                        ArrayList<String> mMaxTemperatureCities,
                                        ArrayList<String> mCurrentWeatherConditionsCities,
                                        ArrayList<String> mCurrentWeatherConditionIconCities,
                                        ArrayList<String> mPrecipitaionCities,
                                        ArrayList<String> mWindSpeedCities,
                                        ArrayList<String> mHumidityCities,
                                        ArrayList<String> mPressureCities,
                                        ArrayList<String> mSunsetTime,
                                        ArrayList<String> mSunriseTime,
                                        int numberofcities) {

        if(isAdded()) {

            ConstraintLayout constraintLayout = requireView().findViewById(R.id.cities_fourthheadlayout_constraintlayout);
            ProgressBar progressBar = requireView().findViewById(R.id.cities_progressbar_progressbar);
            RecyclerView recyclerView = requireView().findViewById(R.id.cities_cities_recyclerview);
            CitiesAdapter adapter = new CitiesAdapter(requireActivity(), numberofcities, this);
//            SpacingItemDecorator itemDecorator = new SpacingItemDecorator(5);
//            recyclerView.addItemDecoration(itemDecorator);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
            ViewCompat.setNestedScrollingEnabled(recyclerView, false);

            adapter.updateData(mCityList, mCityName, mCountryName, mCurrentTempAdapter, mCurrentWeatherConditionsCities, mCurrentWeatherConditionIconCities, mMinTemperatureCities, mMaxTemperatureCities, mPrecipitaionCities, mWindSpeedCities, mHumidityCities, mPressureCities, mSunsetTime, mSunriseTime);

            progressBar.setVisibility(View.INVISIBLE);
            constraintLayout.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void onCityForecastClicked(String cityName) {

        Log.d(TAG, "onCityClicked: " + cityName);
        MainViewModel mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        alertDialog = new AlertDialog.Builder(requireContext())
                .setTitle(cityName)
                .setMessage("Do you want to remove '" + cityName + "from list of Cities?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mainViewModel.removeCityName(cityName);
                        alertDialog = null;
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog = null;
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .create();

        alertDialog.show();

    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        try {
            mDisposable.clear();
        } catch (NullPointerException e) {
            Log.e(TAG, "onDestroy: ", e);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            mDisposable.clear();
        } catch (NullPointerException e) {
            Log.e(TAG, "onDestroy: ", e);
        }

    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;


        ConnectivityManager cm = (ConnectivityManager) requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }


    private void emptyCityList() {

        TextView emptyListString = requireView().findViewById(R.id.cities_nocitiesstring_string);
        ProgressBar progressBar = requireView().findViewById(R.id.cities_progressbar_progressbar);


        emptyListString.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);


    }

    private void onNoWifiOrLocation() {

        TextView noWifiLocation = requireView().findViewById(R.id.cities_nowifinolocationstring_string);
        ProgressBar progressBar = requireView().findViewById(R.id.cities_progressbar_progressbar);


        noWifiLocation.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);


    }

    private void counterEmptyCityList() {

        TextView emptyListString = requireView().findViewById(R.id.cities_nocitiesstring_string);
        ProgressBar progressBar = requireView().findViewById(R.id.cities_progressbar_progressbar);


        emptyListString.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);


    }

    private void counterOnNoWifiOrLocation() {

        TextView noWifiLocation = requireView().findViewById(R.id.cities_nowifinolocationstring_string);
        ProgressBar progressBar = requireView().findViewById(R.id.cities_progressbar_progressbar);


        noWifiLocation.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);


    }

}