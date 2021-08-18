package com.meetingselect.weatherlyv2.data.Model.SearchLocation;

import com.google.gson.annotations.SerializedName;

public class CityResultsItem{

	@SerializedName("country")
	private String country;

	@SerializedName("name")
	private String name;

	@SerializedName("lon")
	private double lon;

	@SerializedName("id")
	private int id;

	@SerializedName("region")
	private String region;

	@SerializedName("lat")
	private double lat;

	@SerializedName("url")
	private String url;

	public String getCountry(){
		return country;
	}

	public String getName(){
		return name;
	}

	public double getLon(){
		return lon;
	}

	public int getId(){
		return id;
	}

	public String getRegion(){
		return region;
	}

	public double getLat(){
		return lat;
	}

	public String getUrl(){
		return url;
	}
}