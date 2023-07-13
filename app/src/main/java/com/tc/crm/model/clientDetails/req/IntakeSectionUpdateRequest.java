package com.tc.crm.model.clientDetails.req;


import com.google.gson.annotations.SerializedName;

public class IntakeSectionUpdateRequest {

	@SerializedName("userId")
	public String userId;

	@SerializedName("clientId")
	public String clientId;

	@SerializedName("sectionId")
	public String sectionId;



}