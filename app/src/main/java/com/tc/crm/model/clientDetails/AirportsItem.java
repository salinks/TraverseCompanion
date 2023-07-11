package com.tc.crm.model.clientDetails;

import com.google.gson.annotations.SerializedName;

public class AirportsItem{

	@SerializedName("airportName")
	public String airportName;

	@SerializedName("airportCode")
	public int airportCode;

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
	public int countryId;
}