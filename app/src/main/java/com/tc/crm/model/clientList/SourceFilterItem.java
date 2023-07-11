package com.tc.crm.model.clientList;

import com.google.gson.annotations.SerializedName;

public class SourceFilterItem{

	@SerializedName("sName")
	public String sName;

	@SerializedName("sId")
	public int sId;

	public boolean isSelected;
}