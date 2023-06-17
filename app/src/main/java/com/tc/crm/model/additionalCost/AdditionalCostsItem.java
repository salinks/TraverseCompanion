package com.tc.crm.model.additionalCost;

import com.google.gson.annotations.SerializedName;

public class AdditionalCostsItem{

	@SerializedName("createdBy")
	public String createdBy;

	@SerializedName("cName")
	public String cName;

	@SerializedName("createdOn")
	public String createdOn;

	@SerializedName("recId")
	public int recId;

	@SerializedName("cStatus")
	public int cStatus;


	public String actionName;
}