package com.tc.crm.model.userGroups;

import com.google.gson.annotations.SerializedName;

public class UserGroupsRequest{

	@SerializedName("userId")
	public String userId;

	@SerializedName("desId")
	public String desId;

	@SerializedName("desName")
	public String desName;
}