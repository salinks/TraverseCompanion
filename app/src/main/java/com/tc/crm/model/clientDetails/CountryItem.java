package com.tc.crm.model.clientDetails;

import com.google.gson.annotations.SerializedName;

public class CountryItem{

	@SerializedName("numcode")
	public String numcode;

	@SerializedName("iso")
	public String iso;

	@SerializedName("name")
	public String name;

	@SerializedName("nicename")
	public String nicename;

	@SerializedName("phonecode")
	public String phonecode;

	@SerializedName("id")
	public String id;

	@SerializedName("countryStatus")
	public String countryStatus;

	@SerializedName("iso3")
	public String iso3;
}