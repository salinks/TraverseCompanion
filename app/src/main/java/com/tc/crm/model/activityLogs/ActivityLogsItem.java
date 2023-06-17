package com.tc.crm.model.activityLogs;

import com.google.gson.annotations.SerializedName;

public class ActivityLogsItem{

	@SerializedName("actionJSON")
	public String actionJSON;

	@SerializedName("actionTag")
	public String actionTag;

	@SerializedName("actionBy")
	public String actionBy;

	@SerializedName("actionId")
	public int actionId;

	@SerializedName("actionDate")
	public String actionDate;

	@SerializedName("userId")
	public int userId;

	@SerializedName("actionDesc")
	public String actionDesc;


	public String actionName;
}