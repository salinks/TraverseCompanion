package com.tc.crm.model.clientDetails;

import com.google.gson.annotations.SerializedName;

public class ClientDetailsRequest {

	@SerializedName("userId")
	public String userId;

	@SerializedName("clientId")
	public String clientId;
}