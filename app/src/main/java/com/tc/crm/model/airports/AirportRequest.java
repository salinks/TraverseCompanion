package com.tc.crm.model.airports;

import com.google.gson.annotations.SerializedName;

public class AirportRequest {

	@SerializedName("airportName")
	public String airportName;

	@SerializedName("airportCode")
	public String airportCode;

	@SerializedName("userId")
	public String userId;

	@SerializedName("aId")
	public String aId;

	@SerializedName("terminals")
	public String terminals;

	@SerializedName("countryId")
	public String countryId;
}