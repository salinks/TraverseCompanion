package com.tc.crm.model.home.dashboard;

import com.google.gson.annotations.SerializedName;

public class UsersItem{

	@SerializedName("name")
	public String name;

	@SerializedName("memImage")
	public String memImage;

	@SerializedName("desName")
	public String desName;

	@SerializedName("memId")
	public int memId;

	@SerializedName("info")
	public Info info;
}