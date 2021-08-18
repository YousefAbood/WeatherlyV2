package com.meetingselect.weatherlyv2.main.Cities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.CursorIndexOutOfBoundsException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.meetingselect.weatherlyv2.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.ViewHolder> {

    private static final String TAG = "CitiesAdapter";
    private Context context;
    private onCityForecastClicked onCityForecastClicked;
    private ArrayList<String> mCityList = new ArrayList<>();
    private ArrayList<String> mCityNameAdapter = new ArrayList<>();
    private ArrayList<String> mCountryNameAdapter = new ArrayList<>();
    private ArrayList<String> mCurrentTempAdapter = new ArrayList<>();
    private ArrayList<String> mMinTemperatureCitiesAdapter = new ArrayList<>();
    private ArrayList<String> mCurrentWeatherConditionsCitiesAdapter = new ArrayList<>();
    private ArrayList<String> mCurrentWeatherConditionIconCitiesAdapter = new ArrayList<>();
    private ArrayList<String> mMaxTemperatureCitiesAdapter = new ArrayList<>();
    private ArrayList<String> mPrecipitaionCitiesAdapter = new ArrayList<>();
    private ArrayList<String> mWindSpeedCitiesAdapter = new ArrayList<>();
    private ArrayList<String> mHumidityCitiesAdapter = new ArrayList<>();
    private ArrayList<String> mPressureCitiesAdapter = new ArrayList<>();
    private ArrayList<String> mSunsetTimeAdapter = new ArrayList<>();
    private ArrayList<String> mSunriseTimeAdapter = new ArrayList<>();

    private int numberOfCities;

    public CitiesAdapter(Context context, int numberOfCities, onCityForecastClicked onCityForecastClicked) {
        this.context = context;
        this.numberOfCities = numberOfCities;
        this.onCityForecastClicked = onCityForecastClicked;
    }

    public void updateData(ArrayList<String> mCityList,
                            ArrayList<String> mCityNameAdapter,
                           ArrayList<String> mCountryNameAdapter,
                           ArrayList<String> mCurrentTempAdapter,
                           ArrayList<String> mMinTemperatureCitiesAdapter,
                           ArrayList<String> mMaxTemperatureCitiesAdapter,
                           ArrayList<String> mCurrentWeatherConditionsCitiesAdapter,
                           ArrayList<String> mCurrentWeatherConditionIconCitiesAdapter,
                           ArrayList<String> mPrecipitaionCitiesAdapter,
                           ArrayList<String> mWindSpeedCitiesAdapter,
                           ArrayList<String> mHumidityCitiesAdapter,
                           ArrayList<String> mPressureCitiesAdapter,
                           ArrayList<String> mSunsetTimeAdapter,
                           ArrayList<String> mSunriseTimeAdapter) {
        this.mCityList = mCityList;
        this.mCityNameAdapter = mCityNameAdapter;
        this.mCountryNameAdapter = mCountryNameAdapter;
        this.mCurrentTempAdapter = mCurrentTempAdapter;
        this.mMinTemperatureCitiesAdapter = mMinTemperatureCitiesAdapter;
        this.mMaxTemperatureCitiesAdapter = mMaxTemperatureCitiesAdapter;
        this.mCurrentWeatherConditionsCitiesAdapter = mCurrentWeatherConditionsCitiesAdapter;
        this.mCurrentWeatherConditionIconCitiesAdapter = mCurrentWeatherConditionIconCitiesAdapter;
        this.mPrecipitaionCitiesAdapter = mPrecipitaionCitiesAdapter;
        this.mWindSpeedCitiesAdapter = mWindSpeedCitiesAdapter;
        this.mHumidityCitiesAdapter = mHumidityCitiesAdapter;
        this.mPressureCitiesAdapter = mPressureCitiesAdapter;
        this.mSunsetTimeAdapter = mSunsetTimeAdapter;
        this.mSunriseTimeAdapter = mSunriseTimeAdapter;
    }

    @NonNull
    @Override
    public CitiesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cities_citylist_recyclerview, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CitiesAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
//        Log.d(TAG, "onBindViewHolder: " + mCitiesWeatherForecast.getValue().getLocation().getCountry());


        Glide.with(context)
                .asBitmap()
                .load("https://" + mCurrentWeatherConditionIconCitiesAdapter.get(position))
                .apply(new RequestOptions().override(120, 120))
                .into(holder.CurrentConditionIcon);

        holder.CurrentTemp.setText(mCurrentTempAdapter.get(position));
        holder.CityName.setText(mCityNameAdapter.get(position));
        holder.CountryName.setText(mCountryNameAdapter.get(position));
        holder.SunsetTime.setText(mSunsetTimeAdapter.get(position));
        holder.SunriseTime.setText(mSunriseTimeAdapter.get(position));
        holder.HighestTemp.setText(mMaxTemperatureCitiesAdapter.get(position));
        holder.LowestTemp.setText(mMinTemperatureCitiesAdapter.get(position));
        holder.Precipitation.setText(mPrecipitaionCitiesAdapter.get(position));
        holder.Humidity.setText(mHumidityCitiesAdapter.get(position));
        holder.Wind.setText(mWindSpeedCitiesAdapter.get(position));
        holder.Pressure.setText(mPressureCitiesAdapter.get(position));



        holder.AdapterLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedCity = mCityList.get(position);
                Log.d(TAG, "onClick: " + selectedCity);
                onCityForecastClicked.onCityForecastClicked(selectedCity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return numberOfCities;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView CurrentTemp, CityName, CountryName, SunriseTime, SunsetTime, HighestTemp, LowestTemp, Precipitation, Wind, Humidity, Pressure;
        ImageView CurrentConditionIcon;
        ConstraintLayout AdapterLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            CurrentTemp = itemView.findViewById(R.id.citiesRV_temperature_string);
            CityName = itemView.findViewById(R.id.citiesRV_cityname_string);
            CountryName = itemView.findViewById(R.id.citiesRV_countryname_string);
            SunsetTime = itemView.findViewById(R.id.citiesRV_sunset_string);
            SunriseTime = itemView.findViewById(R.id.citiesRV_sunrise_string);
            HighestTemp = itemView.findViewById(R.id.citiesRV_highesttemp_string);
            LowestTemp = itemView.findViewById(R.id.citiesRV_lowesttemp_string);
            Precipitation = itemView.findViewById(R.id.citiesRV_precipitation_string);
            Wind = itemView.findViewById(R.id.citiesRV_wind_string);
            Humidity = itemView.findViewById(R.id.citiesRV_humidity_string);
            Pressure = itemView.findViewById(R.id.citiesRV_pressure_string);
            CurrentConditionIcon = itemView.findViewById(R.id.citiesRV_currentweather_imageview);

            AdapterLayout = itemView.findViewById(R.id.citiesRV_recyclerview_constraintlayout);
        }
    }

    public interface onCityForecastClicked {
        void onCityForecastClicked(String cityname);
    }
}
