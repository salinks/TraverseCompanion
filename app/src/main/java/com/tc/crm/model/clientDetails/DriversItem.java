package com.tc.crm.model.clientDetails;

import com.google.gson.annotations.SerializedName;

public class DriversItem{

	@SerializedName("airportId")
	public String airportId;

	@SerializedName("addressLineOne")
	public String addressLineOne;

	@SerializedName("dName")
	public String dName;

	@SerializedName("createdOn")
	public String createdOn;

	@SerializedName("countryId")
	public int countryId;

	@SerializedName("emailAddress")
	public String emailAddress;

	@SerializedName("addressLineTwo")
	public String addressLineTwo;

	@SerializedName("phoneNumber")
	public long phoneNumber;

	@SerializedName("cityName")
	public String cityName;

	@SerializedName("pinCode")
	public int pinCode;

	@SerializedName("dStatus")
	public int dStatus;

	@SerializedName("additionalInfo")
	public String additionalInfo;

	@SerializedName("phoneCode")
	public int phoneCode;

	@SerializedName("countryName")
	public String countryName;

	@SerializedName("dId")
	public int dId;
}