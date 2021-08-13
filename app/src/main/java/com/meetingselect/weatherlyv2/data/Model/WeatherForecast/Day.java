package com.meetingselect.weatherlyv2.data.Model.WeatherForecast;

import com.google.gson.annotations.SerializedName;

public class Day{

	@SerializedName("avgvis_km")
	private double avgvisKm;

	@SerializedName("uv")
	private double uv;

	@SerializedName("avgtemp_f")
	private double avgtempF;

	@SerializedName("avgtemp_c")
	private double avgtempC;

	@SerializedName("daily_chance_of_snow")
	private String dailyChanceOfSnow;

	@SerializedName("maxtemp_c")
	private double maxtempC;

	@SerializedName("maxtemp_f")
	private double maxtempF;

	@SerializedName("mintemp_c")
	private double mintempC;

	@SerializedName("avgvis_miles")
	private double avgvisMiles;

	@SerializedName("daily_will_it_rain")
	private int dailyWillItRain;

	@SerializedName("mintemp_f")
	private double mintempF;

	@SerializedName("totalprecip_in")
	private double totalprecipIn;

	@SerializedName("avghumidity")
	private double avghumidity;

	@SerializedName("condition")
	private Condition condition;

	@SerializedName("maxwind_kph")
	private double maxwindKph;

	@SerializedName("maxwind_mph")
	private double maxwindMph;

	@SerializedName("daily_chance_of_rain")
	private String dailyChanceOfRain;

	@SerializedName("totalprecip_mm")
	private double totalprecipMm;

	@SerializedName("daily_will_it_snow")
	private int dailyWillItSnow;

	public double getAvgvisKm(){
		return avgvisKm;
	}

	public double getUv(){
		return uv;
	}

	public double getAvgtempF(){
		return avgtempF;
	}

	public double getAvgtempC(){
		return avgtempC;
	}

	public String getDailyChanceOfSnow(){
		return dailyChanceOfSnow;
	}

	public double getMaxtempC(){
		return maxtempC;
	}

	public double getMaxtempF(){
		return maxtempF;
	}

	public double getMintempC(){
		return mintempC;
	}

	public double getAvgvisMiles(){
		return avgvisMiles;
	}

	public int getDailyWillItRain(){
		return dailyWillItRain;
	}

	public double getMintempF(){
		return mintempF;
	}

	public double getTotalprecipIn(){
		return totalprecipIn;
	}

	public double getAvghumidity(){
		return avghumidity;
	}

	public Condition getCondition(){
		return condition;
	}

	public double getMaxwindKph(){
		return maxwindKph;
	}

	public double getMaxwindMph(){
		return maxwindMph;
	}

	public String getDailyChanceOfRain(){
		return dailyChanceOfRain;
	}

	public double getTotalprecipMm(){
		return totalprecipMm;
	}

	public int getDailyWillItSnow(){
		return dailyWillItSnow;
	}
}