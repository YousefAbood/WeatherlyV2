package com.meetingselect.weatherlyv2.data.Model.WeatherForecast;

import com.google.gson.annotations.SerializedName;

public class Astro{

	@SerializedName("moonset")
	private String moonset;

	@SerializedName("moon_illumination")
	private String moonIllumination;

	@SerializedName("sunrise")
	private String sunrise;

	@SerializedName("moon_phase")
	private String moonPhase;

	@SerializedName("sunset")
	private String sunset;

	@SerializedName("moonrise")
	private String moonrise;

	public String getMoonset(){
		return moonset;
	}

	public String getMoonIllumination(){
		return moonIllumination;
	}

	public String getSunrise(){
		return sunrise;
	}

	public String getMoonPhase(){
		return moonPhase;
	}

	public String getSunset(){
		return sunset;
	}

	public String getMoonrise(){
		return moonrise;
	}
}