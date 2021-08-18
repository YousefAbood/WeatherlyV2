package com.meetingselect.weatherlyv2.data.Model.SearchLocation;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CityResults{

	@SerializedName("CityResults")
	private List<CityResultsItem> cityResults;

	public List<CityResultsItem> getCityResults(){
		return cityResults;
	}
}