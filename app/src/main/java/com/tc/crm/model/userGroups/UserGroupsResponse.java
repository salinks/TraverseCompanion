package com.tc.crm.model.userGroups;

import java.util.ArrayList;
import com.google.gson.annotations.SerializedName;

public class UserGroupsResponse{

	@SerializedName("Results")
	public Results results;

	@SerializedName("isSuperAdmin")
	public boolean isSuperAdmin;

	@SerializedName("UserGroups")
	public ArrayList<UserGroupsItem> userGroups;
}