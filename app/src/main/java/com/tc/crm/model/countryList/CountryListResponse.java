package com.tc.crm.model.countryList;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CountryListResponse{

	@SerializedName("Results")
	public Results results;

	@SerializedName("isSuperAdmin")
	public boolean isSuperAdmin;

	@SerializedName("countryList")
	public ArrayList<CountryListItem> countryList;
}