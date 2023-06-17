package com.tc.crm.model.additionalCost;

import com.google.gson.annotations.SerializedName;

public class AdditionalCostRequest {

	@SerializedName("cName")
	public String cName;

	@SerializedName("userId")
	public String userId;

	@SerializedName("recId")
	public String recId;
}