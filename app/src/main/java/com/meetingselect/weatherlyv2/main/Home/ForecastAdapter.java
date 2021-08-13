package com.meetingselect.weatherlyv2.main.Home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.meetingselect.weatherlyv2.R;
import com.meetingselect.weatherlyv2.data.Model.WeatherForecast.WeatherForecast;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {
    private static final String TAG = "Home";
    private MutableLiveData<WeatherForecast> mWeatherForecastHomeFragLiveData = new MutableLiveData<>();
    private Context context;

    public ForecastAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ForecastAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_weatherforecast_recyclerview, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    public void updateData(MutableLiveData<WeatherForecast> mWeatherForecastHomeFragLiveData) {
        this.mWeatherForecastHomeFragLiveData = mWeatherForecastHomeFragLiveData;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastAdapter.ViewHolder holder, int position) {

        WeatherForecast weatherForecast = mWeatherForecastHomeFragLiveData.getValue();
        HomeFragment homeFragment = new HomeFragment();

        if(position == 0) {
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
            position++; } else {

            Log.d(TAG, "onBindViewHolder: " + position);

            Glide.with(context)
                    .asBitmap()
                    .load("https:" + weatherForecast.getForecast().getForecastday().get(position).getDay().getCondition().getIcon())
                    .into(holder.WeatherForecastImage);

            holder.DayOfTheWeek.setText(homeFragment.getDayOfWeek(weatherForecast.getForecast().getForecastday().get(position).getDate()));
            holder.WeatherCondition.setText(weatherForecast.getForecast().getForecastday().get(position).getDay().getCondition().getText());
            holder.HighestTemp.setText(String.valueOf(weatherForecast.getForecast().getForecastday().get(position).getDay().getMaxtempC()));
            holder.LowestTemp.setText(String.valueOf(weatherForecast.getForecast().getForecastday().get(position).getDay().getMintempC()));
        }

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView DayOfTheWeek, WeatherCondition, HighestTemp, LowestTemp;
        ImageView WeatherForecastImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            DayOfTheWeek = itemView.findViewById(R.id.homeRV_dayofweek_string);
            WeatherCondition = itemView.findViewById(R.id.homeRV_weathercondition_string);
            HighestTemp = itemView.findViewById(R.id.homeRV_highestemp_string);
            LowestTemp = itemView.findViewById(R.id.homeRV_lowestemp_string);
            WeatherForecastImage = itemView.findViewById(R.id.homeRV_weatherforecasticon_imageview);
        }
    }
}
