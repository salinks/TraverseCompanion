package com.tc.crm.model.userGroups;

import com.google.gson.annotations.SerializedName;

public class UserGroupsItem{

	@SerializedName("desStatus")
	public int desStatus;

	@SerializedName("desId")
	public int desId;

	@SerializedName("createdOn")
	public String createdOn;

	@SerializedName("desName")
	public String desName;
	public String actionName;
}