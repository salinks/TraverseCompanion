package com.tc.crm.model.countries;

import com.google.gson.annotations.SerializedName;

public class CountryRequest{

	@SerializedName("currShortName")
	public String currShortName;

	@SerializedName("currencyName")
	public String currencyName;

	@SerializedName("countryCode")
	public String countryCode;

	@SerializedName("phoneCode")
	public String phoneCode;

	@SerializedName("countryName")
	public String countryName;

	@SerializedName("userId")
	public String userId;

	@SerializedName("currencyCode")
	public String currencyCode;

	@SerializedName("countryId")
	public String countryId;
}