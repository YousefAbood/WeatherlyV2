package com.meetingselect.weatherlyv2.data.Model.WeatherForecast;

import com.google.gson.annotations.SerializedName;

public class Condition{

	@SerializedName("code")
	private int code;

	@SerializedName("icon")
	private String icon;

	@SerializedName("text")
	private String text;

	public int getCode(){
		return code;
	}

	public String getIcon(){
		return icon;
	}

	public String getText(){
		return text;
	}
}