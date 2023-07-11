package com.tc.crm.model.clientList;

import com.google.gson.annotations.SerializedName;

public class ClientTypeFilterItem{

	@SerializedName("id")
	public String id;

	@SerializedName("value")
	public String value;

	public boolean isSelected;
}