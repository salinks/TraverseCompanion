package com.tc.crm.model.clientList;

import com.google.gson.annotations.SerializedName;

public class UniversityFiltersItem{

	@SerializedName("uId")
	public int uId;

	@SerializedName("uName")
	public String uName;

	public boolean isSelected ;
}