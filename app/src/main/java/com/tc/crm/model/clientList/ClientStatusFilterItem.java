package com.tc.crm.model.clientList;

import com.google.gson.annotations.SerializedName;

public class ClientStatusFilterItem{

	@SerializedName("id")
	public int id;

	@SerializedName("value")
	public String value;


	public boolean isSelected;


}