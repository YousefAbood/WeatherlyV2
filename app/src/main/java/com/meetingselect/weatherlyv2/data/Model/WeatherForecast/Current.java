package com.meetingselect.weatherlyv2.data.Model.WeatherForecast;

import com.google.gson.annotations.SerializedName;

public class Current{

	@SerializedName("feelslike_c")
	private double feelslikeC;

	@SerializedName("uv")
	private double uv;

	@SerializedName("last_updated")
	private String lastUpdated;

	@SerializedName("feelslike_f")
	private double feelslikeF;

	@SerializedName("wind_degree")
	private int windDegree;

	@SerializedName("last_updated_epoch")
	private int lastUpdatedEpoch;

	@SerializedName("is_day")
	private int isDay;

	@SerializedName("precip_in")
	private double precipIn;

	@SerializedName("wind_dir")
	private String windDir;

	@SerializedName("gust_mph")
	private double gustMph;

	@SerializedName("temp_c")
	private double tempC;

	@SerializedName("pressure_in")
	private double pressureIn;

	@SerializedName("gust_kph")
	private double gustKph;

	@SerializedName("temp_f")
	private double tempF;

	@SerializedName("precip_mm")
	private double precipMm;

	@SerializedName("cloud")
	private int cloud;

	@SerializedName("wind_kph")
	private double windKph;

	@SerializedName("condition")
	private Condition condition;

	@SerializedName("wind_mph")
	private double windMph;

	@SerializedName("vis_km")
	private double visKm;

	@SerializedName("humidity")
	private int humidity;

	@SerializedName("pressure_mb")
	private double pressureMb;

	@SerializedName("vis_miles")
	private double visMiles;

	public double getFeelslikeC(){
		return feelslikeC;
	}

	public double getUv(){
		return uv;
	}

	public String getLastUpdated(){
		return lastUpdated;
	}

	public double getFeelslikeF(){
		return feelslikeF;
	}

	public int getWindDegree(){
		return windDegree;
	}

	public int getLastUpdatedEpoch(){
		return lastUpdatedEpoch;
	}

	public int getIsDay(){
		return isDay;
	}

	public double getPrecipIn(){
		return precipIn;
	}

	public String getWindDir(){
		return windDir;
	}

	public double getGustMph(){
		return gustMph;
	}

	public double getTempC(){
		return tempC;
	}

	public double getPressureIn(){
		return pressureIn;
	}

	public double getGustKph(){
		return gustKph;
	}

	public double getTempF(){
		return tempF;
	}

	public double getPrecipMm(){
		return precipMm;
	}

	public int getCloud(){
		return cloud;
	}

	public double getWindKph(){
		return windKph;
	}

	public Condition getCondition(){
		return condition;
	}

	public double getWindMph(){
		return windMph;
	}

	public double getVisKm(){
		return visKm;
	}

	public int getHumidity(){
		return humidity;
	}

	public double getPressureMb(){
		return pressureMb;
	}

	public double getVisMiles(){
		return visMiles;
	}
}