package com.tc.crm.model.intakeSections;

import com.google.gson.annotations.SerializedName;

public class IntakeSectionsItem{

	@SerializedName("sectionName")
	public String sectionName;

	@SerializedName("month")
	public String month;

	@SerializedName("yId")
	public int yId;

	@SerializedName("year")
	public String year;

	@SerializedName("countryName")
	public String countryName;

	@SerializedName("createdOn")
	public String createdOn;

	@SerializedName("countryId")
	public String countryId;

	@SerializedName("yStatus")
	public int yStatus;

	public String actionName;
}