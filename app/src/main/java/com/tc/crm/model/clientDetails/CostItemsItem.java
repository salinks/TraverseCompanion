package com.tc.crm.model.clientDetails;

import com.google.gson.annotations.SerializedName;

public class CostItemsItem{

	@SerializedName("createdBy")
	public int createdBy;

	@SerializedName("cName")
	public String cName;

	@SerializedName("createdOn")
	public String createdOn;

	@SerializedName("recId")
	public int recId;

	@SerializedName("cStatus")
	public int cStatus;
}