package com.tc.crm.model.clientDetails.req;

import com.google.gson.annotations.SerializedName;

public class BasicInfoUpdateRequest{

	@SerializedName("clientId")
	public String clientId;

	@SerializedName("passengerType")
	public String passengerType;

	@SerializedName("surName")
	public String surName;

	@SerializedName("gender")
	public String gender;

	@SerializedName("dob")
	public String dob;

	@SerializedName("givenName")
	public String givenName;

	@SerializedName("userId")
	public String userId;

	@SerializedName("countryId")
	public String countryId;
}