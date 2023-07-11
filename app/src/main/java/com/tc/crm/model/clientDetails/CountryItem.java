package com.tc.crm.model.clientDetails;

import com.google.gson.annotations.SerializedName;

public class CountryItem{

	@SerializedName("numcode")
	public int numcode;

	@SerializedName("iso")
	public String iso;

	@SerializedName("name")
	public String name;

	@SerializedName("nicename")
	public String nicename;

	@SerializedName("phonecode")
	public int phonecode;

	@SerializedName("id")
	public int id;

	@SerializedName("countryStatus")
	public int countryStatus;

	@SerializedName("iso3")
	public String iso3;
}