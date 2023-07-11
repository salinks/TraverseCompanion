package com.tc.crm.model.clientDetails;

import com.google.gson.annotations.SerializedName;

public class LettingsItem{

	@SerializedName("cleaning")
	public int cleaning;

	@SerializedName("lName")
	public String lName;

	@SerializedName("amenities")
	public String amenities;

	@SerializedName("town")
	public String town;

	@SerializedName("lId")
	public int lId;

	@SerializedName("landlordSource")
	public String landlordSource;

	@SerializedName("utility")
	public int utility;

	@SerializedName("bedCapacity")
	public String bedCapacity;

	@SerializedName("createdOn")
	public String createdOn;

	@SerializedName("lStatus")
	public int lStatus;

	@SerializedName("countryId")
	public int countryId;

	@SerializedName("emailAddress")
	public String emailAddress;

	@SerializedName("phoneNumber")
	public int phoneNumber;

	@SerializedName("universities")
	public String universities;

	@SerializedName("doorNumber")
	public int doorNumber;

	@SerializedName("street")
	public String street;

	@SerializedName("pinCode")
	public int pinCode;

	@SerializedName("additionalInfo")
	public String additionalInfo;

	@SerializedName("phoneCode")
	public int phoneCode;

	@SerializedName("deposit")
	public int deposit;

	@SerializedName("adminCost")
	public int adminCost;

	@SerializedName("countryName")
	public String countryName;

	@SerializedName("roomType")
	public String roomType;

	@SerializedName("campuses")
	public String campuses;
}