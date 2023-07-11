package com.tc.crm.model.clientList;

import com.google.gson.annotations.SerializedName;

public class TeamFilterItem{

	@SerializedName("name")
	public String name;

	@SerializedName("memImage")
	public String memImage;

	@SerializedName("desName")
	public String desName;

	@SerializedName("memId")
	public int memId;


	public boolean isSelected;

}