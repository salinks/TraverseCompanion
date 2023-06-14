package com.tc.crm.model.countries;

import com.google.gson.annotations.SerializedName;

public class CountryUpdateEvent{

	@SerializedName("refreshRequired")
	public boolean refreshRequired;
}