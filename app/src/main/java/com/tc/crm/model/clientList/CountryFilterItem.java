package com.tc.crm.model.clientList;

import com.google.gson.annotations.SerializedName;

public class CountryFilterItem{

	@SerializedName("cName")
	public String cName;

	@SerializedName("cId")
	public int cId;

	public  boolean isSelected;
}