package com.tc.crm.model.clientDetails;

import com.google.gson.annotations.SerializedName;

public class CountryMasterItem{

	@SerializedName("cCode")
	public String cCode;

	@SerializedName("currCode")
	public String currCode;

	@SerializedName("currShortName")
	public String currShortName;

	@SerializedName("cName")
	public String cName;

	@SerializedName("phoneCode")
	public int phoneCode;

	@SerializedName("currName")
	public String currName;

	@SerializedName("createdOn")
	public String createdOn;

	@SerializedName("cStatus")
	public int cStatus;

	@SerializedName("cId")
	public int cId;
}