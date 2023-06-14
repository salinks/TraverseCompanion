package com.tc.crm.model.countries;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CountriesResponse {

	@SerializedName("Results")
	public Results results;

	@SerializedName("CountryMaster")
	public ArrayList<CountryMasterItem> countryMaster;
}