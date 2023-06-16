package com.tc.crm.model.intakeSections;

import com.google.gson.annotations.SerializedName;

public class IntakeSectionRequest{

	@SerializedName("sectionName")
	public String sectionName;

	@SerializedName("month")
	public String month;

	@SerializedName("yId")
	public String yId;

	@SerializedName("year")
	public String year;

	@SerializedName("userId")
	public String userId;

	@SerializedName("countryId")
	public String countryId;
}