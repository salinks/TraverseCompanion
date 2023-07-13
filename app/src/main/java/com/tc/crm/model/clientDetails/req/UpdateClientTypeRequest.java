package com.tc.crm.model.clientDetails.req;

import com.google.gson.annotations.SerializedName;

public class UpdateClientTypeRequest{

	@SerializedName("clientId")
	public String clientId;

	@SerializedName("clientType")
	public String clientType;

	@SerializedName("userId")
	public String userId;
}