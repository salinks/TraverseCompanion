package com.tc.crm.model.login;

import com.google.gson.annotations.SerializedName;

public class UserData{

	@SerializedName("systemSettings")
	public String systemSettings;

	@SerializedName("name")
	public String name;

	@SerializedName("appMenu")
	public String appMenu;

	@SerializedName("memId")
	public int memId;

	@SerializedName("desName")
	public String desName;

	
}