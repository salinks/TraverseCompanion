package com.tc.crm.model.clientDetails.req;

import com.google.gson.annotations.SerializedName;

public class UpdateStaffRequest{

	@SerializedName("clientId")
	public String clientId;

	@SerializedName("userId")
	public String userId;

	@SerializedName("staffId")
	public String staffId;
}