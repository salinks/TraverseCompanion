package com.tc.crm.model.home.dashboard;

import com.google.gson.annotations.SerializedName;

public class UserData{

	@SerializedName("country")
	public int country;

	@SerializedName("userStatus")
	public int userStatus;

	@SerializedName("city_state")
	public String cityState;

	@SerializedName("systemSettings")
	public Object systemSettings;

	@SerializedName("addressLineOne")
	public String addressLineOne;

	@SerializedName("appMenu")
	public Object appMenu;

	@SerializedName("memImage")
	public String memImage;

	@SerializedName("clinetFormAction")
	public int clinetFormAction;

	@SerializedName("addressLineTwo")
	public String addressLineTwo;

	@SerializedName("phoneNumber")
	public long phoneNumber;

	@SerializedName("hasAdminPrivileges")
	public int hasAdminPrivileges;

	@SerializedName("pinCode")
	public int pinCode;

	@SerializedName("name")
	public String name;

	@SerializedName("phoneCode")
	public int phoneCode;

	@SerializedName("countryName")
	public String countryName;

	@SerializedName("userRole")
	public int userRole;

	@SerializedName("department")
	public String department;

	@SerializedName("email")
	public String email;

	@SerializedName("memId")
	public int memId;

	@SerializedName("username")
	public String username;

	@SerializedName("desName")
	public String desName;
}