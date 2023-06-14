package com.tc.crm.model.countries;

import com.google.gson.annotations.SerializedName;

public class CountryMasterItem{

	public String actionName;
	@SerializedName("enquiryCount")
	public String enquiryCount;

	@SerializedName("cCode")
	public String cCode;

	@SerializedName("currCode")
	public String currCode;

	@SerializedName("currShortName")
	public String currShortName;

	@SerializedName("intakeCount")
	public String intakeCount;

	@SerializedName("universityCount")
	public String universityCount;

	@SerializedName("createdOn")
	public String createdOn;

	@SerializedName("cStatus")
	public int cStatus;

	@SerializedName("cName")
	public String cName;

	@SerializedName("campusCount")
	public String campusCount;

	@SerializedName("phoneCode")
	public String phoneCode;

	@SerializedName("currName")
	public String currName;

	@SerializedName("cId")
	public String cId;
}