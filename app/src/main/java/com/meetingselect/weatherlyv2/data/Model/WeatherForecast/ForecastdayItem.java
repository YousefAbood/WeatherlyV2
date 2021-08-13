package com.meetingselect.weatherlyv2.data.Model.WeatherForecast;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ForecastdayItem{

	@SerializedName("date")
	private String date;

	@SerializedName("astro")
	private Astro astro;

	@SerializedName("date_epoch")
	private int dateEpoch;

	@SerializedName("hour")
	private List<HourItem> hour;

	@SerializedName("day")
	private Day day;

	public String getDate(){
		return date;
	}

	public Astro getAstro(){
		return astro;
	}

	public int getDateEpoch(){
		return dateEpoch;
	}

	public List<HourItem> getHour(){
		return hour;
	}

	public Day getDay(){
		return day;
	}
}