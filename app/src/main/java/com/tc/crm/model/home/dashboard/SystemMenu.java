package com.tc.crm.model.home.dashboard;

import com.google.gson.annotations.SerializedName;

public class SystemMenu{

	@SerializedName("DataSection")
	public boolean dataSection;

	@SerializedName("EmailConfig")
	public boolean emailConfig;

	@SerializedName("CheckList")
	public boolean checkList;

	@SerializedName("Airports")
	public boolean airports;

	@SerializedName("CountryMaster")
	public boolean countryMaster;

	@SerializedName("SourceCategory")
	public boolean sourceCategory;

	@SerializedName("systemMenus")
	public boolean systemMenus;

	@SerializedName("UserGroups")
	public boolean userGroups;

	@SerializedName("Amenities")
	public boolean amenities;
}