package com.tc.crm.model.countryList;

import com.google.gson.annotations.SerializedName;

public class CountryListItem{

	@SerializedName("numcode")
	public int numcode;

	@SerializedName("iso")
	public String iso;

	@SerializedName("name")
	public String name;

	@SerializedName("nicename")
	public String nicename;

	@SerializedName("phonecode")
	public String phonecode;

	@SerializedName("id")
	public int id;

	@SerializedName("countryStatus")
	public int countryStatus;

	@SerializedName("iso3")
	public String iso3;


	public String actionName;
}