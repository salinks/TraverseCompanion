package com.tc.crm.data.model;

import com.google.gson.annotations.SerializedName;

public class EnableDisableRequest{

	@SerializedName("userId")
	public String userId;

	@SerializedName("recId")
	public String recId;

	@SerializedName("actionName")
	public String actionName;
}