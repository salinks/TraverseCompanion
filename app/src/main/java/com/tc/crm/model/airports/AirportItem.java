package com.tc.crm.model.airports;

import com.google.gson.annotations.SerializedName;

public class AirportItem{

	@SerializedName("airportName")
	public String airportName;

	@SerializedName("airportCode")
	public String airportCode;

	@SerializedName("countryName")
	public String countryName;

	@SerializedName("aStatus")
	public int aStatus;

	@SerializedName("aId")
	public int aId;

	@SerializedName("terminals")
	public String terminals;

	@SerializedName("createdOn")
	public String createdOn;

	@SerializedName("countryId")
	public String countryId;


	public String actionName;
}