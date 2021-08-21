package com.meetingselect.weatherlyv2.main.Home;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.meetingselect.weatherlyv2.HelperClasses.GPSLocation;
import com.meetingselect.weatherlyv2.R;
import com.meetingselect.weatherlyv2.data.Model.WeatherForecast.WeatherForecast;
import com.meetingselect.weatherlyv2.mainviewmodel.MainViewModel;

import java.util.Calendar;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";

    private final MutableLiveData<WeatherForecast> mWeatherForecastHomeFragLiveData = new MutableLiveData<>();

    private CompositeDisposable mDisposable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        changeStatusBarColorDay();

        SwipeRefreshLayout mSwipeRefreshLayout = requireView().findViewById(R.id.home_mainheadlayout_swiperefreshlayout);
        mSwipeRefreshLayout.setRefreshing(false);
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mSwipeRefreshLayout.setRefreshing(true);
            getWeatherConditions();
            mSwipeRefreshLayout.setRefreshing(false);
        });

        mSwipeRefreshLayout.setColorSchemeResources(R.color.OfficialPurple,
                R.color.Official2ndPurple,
                R.color.Official3rdPurple);


//        mSwipeRefreshLayout.setRefreshing(true);
        getWeatherConditions();

    }

    private void getWeatherConditions() {

        ProgressBar progressBar = requireView().findViewById(R.id.home_progressbar_progressbar);

        ConstraintLayout layout = requireView().findViewById(R.id.home_thirdmainheadlayout_constraintlayout);
        layout.setVisibility(View.INVISIBLE);

        ImageView TodayWeatherIcon = requireView().findViewById(R.id.home_todaysweathericon_imageview);
        TextView TodaysDate = requireView().findViewById(R.id.home_todaysdate_textview);
        TextView CurrentTemperature = requireView().findViewById(R.id.home_currenttemperature_textview);
        TextView CurrentCity = requireView().findViewById(R.id.home_currentcity_textview);
        TextView FeelsLikeTemp = requireView().findViewById(R.id.home_feelsliketemp_textview);
        TextView TopTemp = requireView().findViewById(R.id.home_toptemptoday_textview);
        TextView LowTemp = requireView().findViewById(R.id.home_lowesttemptoday_textview);
        TextView SunriseTime = requireView().findViewById(R.id.home_sunrisetime_textview);
        TextView Precipitation = requireView().findViewById(R.id.home_precipitationpercentage_textview);
        TextView Humidity = requireView().findViewById(R.id.home_humiditypercentage_textview);
        TextView SunsetTime = requireView().findViewById(R.id.home_sunsetime_textview);
        TextView Wind = requireView().findViewById(R.id.home_windspeed_textview);
        TextView Pressure = requireView().findViewById(R.id.home_pressurepercentage_textview);

        MainViewModel mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        Log.d(TAG, "getWeatherConditions: " + getLocation());
        mainViewModel.getWeatherForecast(getLocation(), 4);

        mDisposable = new CompositeDisposable();

        mainViewModel.getmWeatherForecastMVVM().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<WeatherForecast>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                mDisposable.add(d);
            }

            @Override
            public void onNext(@NonNull WeatherForecast weatherForecast) {

                Log.d(TAG, "onNext: Success" );
                // Forecast Days
                mWeatherForecastHomeFragLiveData.setValue(weatherForecast);

                Log.d(TAG, "onNext0: " + weatherForecast.getForecast().getForecastday().get(0).getDay().getCondition().getText());
                Log.d(TAG, "onNext1: " + weatherForecast.getForecast().getForecastday().get(1).getDay().getCondition().getText());
                Log.d(TAG, "onNext2: " + weatherForecast.getForecast().getForecastday().get(2).getDay().getCondition().getText());


                // Today
                Glide.with(requireActivity())
                        .asBitmap()
                        .load("https://" + weatherForecast.getForecast().getForecastday().get(0).getDay().getCondition().getIcon())
                        .apply(new RequestOptions().override(80, 80))
                        .into(TodayWeatherIcon);

                String WeatherForecastDate = weatherForecast.getForecast().getForecastday().get(0).getDate();
                String TodaysDateS = getDayOfWeek(WeatherForecastDate) + getMonth(WeatherForecastDate);
                TodaysDate.setText(TodaysDateS);

                CurrentTemperature.setText(String.valueOf(weatherForecast.getCurrent().getTempC()));
                CurrentCity.setText(weatherForecast.getLocation().getName() + ", " + weatherForecast.getLocation().getCountry());
                FeelsLikeTemp.setText("Feels like " + weatherForecast.getCurrent().getFeelslikeC());
                TopTemp.setText(String.valueOf(weatherForecast.getForecast().getForecastday().get(0).getDay().getMaxtempC()));
                LowTemp.setText(String.valueOf(weatherForecast.getForecast().getForecastday().get(0).getDay().getMintempC()));
                SunriseTime.setText(String.valueOf(weatherForecast.getForecast().getForecastday().get(0).getAstro().getSunrise()));
                SunsetTime.setText(String.valueOf(weatherForecast.getForecast().getForecastday().get(0).getAstro().getSunset()));
                Log.d(TAG, "onNext: " + weatherForecast.getForecast().getForecastday().get(0).getAstro().getSunset());
                Precipitation.setText(weatherForecast.getCurrent().getPrecipMm() + "%");
                Humidity.setText(weatherForecast.getCurrent().getHumidity() + "%");
                Wind.setText(weatherForecast.getCurrent().getWindKph() + " km/h");
                Pressure.setText(weatherForecast.getCurrent().getPressureMb() + " hPa");


                initRecyclerViewWeatherForecast();
                progressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e(TAG, "onError: ", e);
            }

            @Override
            public void onComplete() {


            }
        });






    }


    public void initRecyclerViewWeatherForecast() {
        if(isAdded()) {

            ConstraintLayout layout = requireView().findViewById(R.id.home_thirdmainheadlayout_constraintlayout);
            SwipeRefreshLayout mSwipeRefreshLayout = requireView().findViewById(R.id.home_mainheadlayout_swiperefreshlayout);

            RecyclerView recyclerView = requireView().findViewById(R.id.home_forecastview_recyclerview);
            ForecastAdapter adapter = new ForecastAdapter(requireActivity());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

            adapter.updateData(mWeatherForecastHomeFragLiveData);

            mSwipeRefreshLayout.setRefreshing(false);
            layout.setVisibility(View.VISIBLE);


        }


    }

    // Get Days


    public String getDayOfWeek(String date) {
        String yearS = date.substring(0, 4);
        String monthS = date.substring(5, 7);
        String dayS = date.substring(8, 10);

        String[] weekDays = new String[]{
                "Sun",
                "Mon",
                "Tue",
                "Wed",
                "Thu",
                "Fri",
                "Sat"
        };

        int day = Integer.parseInt(dayS);
        int month = Integer.parseInt(monthS);
        int year = Integer.parseInt(yearS);

        Calendar c = Calendar.getInstance();
        c.set(year, month - 1, day);
        int dayOfWeekInt = c.get(Calendar.DAY_OF_WEEK);
        String dayOfWeekString = weekDays[dayOfWeekInt - 1];

        return dayOfWeekString;
    }

    public String getMonth(String date) {
        String yearS = date.substring(0, 4);
        String monthS = date.substring(5, 7);
        String dayS = date.substring(8, 10);

        String[] months = new String[]{
                "Jan",
                "Feb",
                "Mar",
                "Apr",
                "May",
                "Jun",
                "Jul",
                "Aug",
                "Sep",
                "Oct",
                "Nov",
                "Dec"
        };

        int day = Integer.parseInt(dayS);
        int month = Integer.parseInt(monthS);
        int year = Integer.parseInt(yearS);

        Calendar c = Calendar.getInstance();
        c.set(year, month - 1, day);
        int monthOfYearInt = c.get(Calendar.MONTH);
        String monthOfYearString = ", " + months[monthOfYearInt] + " " + dayS;

        return monthOfYearString;
    }

    // Location
    public String getLocation() {
        GPSLocation gpsLocation = new GPSLocation(getActivity());
        if (gpsLocation.canGetLocation()) {
            double latitude = gpsLocation.getLatitude();
            double longitude = gpsLocation.getLongitude();
            String latitudeS = String.valueOf(latitude);
            String longitudeS = String.valueOf(longitude);
            String latlong = latitudeS + "," + longitudeS;
            Log.d(TAG, "getLocation: " + latlong);
            return latlong;
        } else {
//            gpsLocation.showSettingsAlert();
            Log.d(TAG, "getLocation: tt");
            return "nothing";
        }
    }


    private void changeStatusBarColorDay() {
        Window window = requireActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(requireActivity(), R.color.Official3rdPurple));
    }

    private void changeStatusBarColorNight() {
        Window window = requireActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(requireActivity(), R.color.Official3rdPurple));
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mDisposable.clear();
    }

    @Override
    public void onPause() {
        super.onPause();
        mDisposable.clear();
    }

}