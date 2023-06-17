package com.tc.crm.model.activityLogs;

import com.google.gson.annotations.SerializedName;

public class Results{

	@SerializedName("message")
	public String message;

	@SerializedName("status")
	public boolean status;
}