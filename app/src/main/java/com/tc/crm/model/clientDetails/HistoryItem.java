package com.tc.crm.model.clientDetails;

import com.google.gson.annotations.SerializedName;

public class HistoryItem{

	@SerializedName("clientId")
	public int clientId;

	@SerializedName("actionByUserId")
	public int actionByUserId;

	@SerializedName("actionBy")
	public String actionBy;

	@SerializedName("action")
	public String action;

	@SerializedName("createdOn")
	public String createdOn;

	@SerializedName("recId")
	public int recId;
}