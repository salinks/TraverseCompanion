package com.tc.crm.model.clientDetails.req;


import com.google.gson.annotations.SerializedName;

public class CountryUploadRequest {

	@SerializedName("userId")
	public String userId;


	@SerializedName("clientId")
	public String clientId;

	@SerializedName("countryId")
	public String countryId;

}