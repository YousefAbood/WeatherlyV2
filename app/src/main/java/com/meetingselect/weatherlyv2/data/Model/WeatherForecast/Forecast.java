package com.meetingselect.weatherlyv2.data.Model.WeatherForecast;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Forecast{

	@SerializedName("forecastday")
	private List<ForecastdayItem> forecastday;

	public List<ForecastdayItem> getForecastday(){
		return forecastday;
	}
}