package com.tc.crm.model.home.dashboard;

import com.google.gson.annotations.SerializedName;

public class DatabasesItem{

	@SerializedName("sectionName")
	public String sectionName;

	@SerializedName("cCode")
	public String cCode;

	@SerializedName("yId")
	public int yId;

	@SerializedName("cName")
	public String cName;

	@SerializedName("info")
	public Info info;
}