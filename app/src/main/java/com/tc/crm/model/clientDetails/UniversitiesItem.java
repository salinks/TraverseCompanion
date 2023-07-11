package com.tc.crm.model.clientDetails;

import com.google.gson.annotations.SerializedName;

public class UniversitiesItem{

	@SerializedName("uId")
	public int uId;

	@SerializedName("uName")
	public String uName;

	@SerializedName("countryName")
	public String countryName;

	@SerializedName("uStatus")
	public int uStatus;

	@SerializedName("createdOn")
	public String createdOn;

	@SerializedName("cId")
	public int cId;
}