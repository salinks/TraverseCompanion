package com.tc.crm.model.clientDetails;

import com.google.gson.annotations.SerializedName;

public class ActionsItem{

	@SerializedName("actionTime")
	public String actionTime;

	@SerializedName("clientId")
	public int clientId;

	@SerializedName("createdBy")
	public String createdBy;

	@SerializedName("description")
	public String description;

	@SerializedName("userId")
	public int userId;

	@SerializedName("createdOn")
	public String createdOn;

	@SerializedName("recId")
	public int recId;
}