package com.meetingselect.weatherlyv2.data.Model.WeatherForecast;

import com.google.gson.annotations.SerializedName;

public class HourItem{

	@SerializedName("condition")
	private Condition condition;

	public Condition getCondition(){
		return condition;
	}
}